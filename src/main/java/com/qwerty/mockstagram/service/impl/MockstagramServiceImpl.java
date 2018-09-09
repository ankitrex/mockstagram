package com.qwerty.mockstagram.service.impl;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.qwerty.mockstagram.model.Influencer;
import com.qwerty.mockstagram.model.InfluencerTimeseries;
import com.qwerty.mockstagram.service.InfluencerService;
import com.qwerty.mockstagram.service.MockstagramService;
import com.qwerty.mockstagram.util.Constants;
import com.qwerty.mockstagram.util.JSONParser;
import com.qwerty.mockstagram.util.URLHelper;

@Service
public class MockstagramServiceImpl implements MockstagramService {

	@Autowired
	InfluencerService	influencerService;

	@Autowired
	URLHelper			urlHelper;

	@Autowired
	JSONParser			jsonParser;

	private Logger		logger					= LoggerFactory.getLogger(MockstagramServiceImpl.class);

	@Value("${mockstagram.user.id.start}")
	private Integer		startId;

	@Value("${mockstagram.user.id.end}")
	private Integer		endId;

	@Value("${mockstagram.cassandra.max.batch}")
	private Integer		maxBatch;

	@Value("${mockstagram.java.max.threads.main}")
	private Integer		maxThreadsMain;

	@Value("${mockstagram.java.max.threads.proc-1}")
	private Integer		maxThreadsProc1;

	private String		executorStartedMsg		= "Init process - ExecutorService started";
	private String		executorJobsSubmitted	= "All jobs submitted to ExectorService";

	@Override
	// Will get executed when the server starts. Next execution will start after
	// method has finished executing. There will be a break of 1000ms.
	@Scheduled(fixedDelay = 1000)
	public void init() throws InterruptedException, ExecutionException {

		// Start time of the process
		long startTime = System.currentTimeMillis();

		// dynamic start end to process in batches
		Integer currentStart = startId;
		Integer currentEnd = startId + maxBatch;

		// executor services with fixed thread pool. Number of threads defined
		// in properties file
		ExecutorService executorService = Executors.newFixedThreadPool(maxThreadsMain);

		logger.info(executorStartedMsg);

		// process in batches until all Influencers are done
		while (currentEnd - endId < maxBatch) {

			// logic to process remaining influencers if total influencer not
			// directly divisible by batch size
			int endRange = currentEnd;
			if (currentEnd - endId > 0) {
				endRange = endId;
			}

			// generate influencer ids in batch
			// ideally these batches should be fetched from a file or database
			List<Integer> ids = IntStream.range(currentStart, endRange).boxed().collect(Collectors.toList());

			// submit a batch to the executor service for further processing
			executorService.submit(() -> processInfluencerBatch(ids));

			currentStart += maxBatch;
			currentEnd += maxBatch;
		}

		logger.info(executorJobsSubmitted);

		// shutdown executorService so that it won't accept any more jobs
		executorService.shutdown();

		// await termination until all the threads have completed their
		// execution
		executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

		// print total time
		String completedIn = String.format("Total time %d", System.currentTimeMillis() - startTime);
		logger.info(completedIn);
	}

	private String processInfluencerBatch(List<Integer> ids) throws InterruptedException {

		// fetch influencer data from api in parallel
		// parallel stream will create (no. of cores - 1) threads.
		// since response time is not that much its okay to use parallel stream
		// else use a executor service with more threads for faster processing
		List<InfluencerTimeseries> influencers = ids.parallelStream().map(this::fetchInfluencerData).collect(Collectors.toList());

		// store all the fetched data into cassandra and redis
		influencerService.persistData(influencers);

		// return thread name for debugging purposes
		return Thread.currentThread().getName();
	}

	private InfluencerTimeseries fetchInfluencerData(int id) {

		// generate url for influencer endpoint
		String url = urlHelper.generateMockstagramUrl(id, Constants.URL_TYPE_INFLUENCER);

		// fetch influencer data from url
		String data = urlHelper.fetchDataFromUrl(url);

		try {
			// parse json data and return it in InfluencerTimeseries format
			return jsonParser.parseInfluencerJsonString(data);
		}
		catch (JSONException e) {
			logger.error("JSON Parsing error:: ", e);
		}

		return null;
	}

	@Override
	// will run once everyday at 1 a.m.
	@Scheduled(cron = "0 0 1 * * *")
	public void suspiciousInfluencers() throws InterruptedException {

		logger.info("Suspicious influencer process started");

		// fetch all the influencer IDs that need to be updated
		List<Integer> ids = IntStream.range(startId, endId).boxed().collect(Collectors.toList());

		// fetch latest data for all influencers from redis
		List<Influencer> influencers = influencerService.fetchAllInfluencers(ids);

		// executor services with fixed thread pool. Number of threads defined
		// in properties file
		// Number of threads for this process needs to be high. Response
		// time of this API is more so multithreading will be very useful
		ExecutorService executorService = Executors.newFixedThreadPool(maxThreadsProc1);

		// submit all jobs to executorservice
		influencers.forEach(influencer -> executorService.submit(() -> checkInfluencerSuspicious(influencer)));

		// shutdown executorService so that it won't accept any more jobs
		executorService.shutdown();

		// await termination until all the threads have completed their
		// execution
		executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

		logger.info("Suspicious influencer process ended");
	}

	private void checkInfluencerSuspicious(Influencer influencer) {

		// generate url for is_suspicious endpoiny
		String url = urlHelper.generateMockstagramUrl(0, Constants.URL_TYPE_SUSPICIOUS);

		try {
			// parse json to be passed in body of request
			JSONObject body = jsonParser.getInfluencerJson(influencer);

			// response from the url
			JSONObject responseJson = new JSONObject(urlHelper.influencerPostRequest(url, body));

			// update data in datastore
			influencerService.updateSuspiciousInfluencers(influencer.getId(), responseJson.getBoolean("suspicious"));
		}
		catch (JSONException e) {
			logger.error("Error parsing to json", e);
		}
	}

}
