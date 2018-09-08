package com.qwerty.mockstagram.service.impl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qwerty.mockstagram.dao.InfluencersTimeseriesDao;
import com.qwerty.mockstagram.model.Influencer;
import com.qwerty.mockstagram.model.InfluencerTimeseries;
import com.qwerty.mockstagram.model.TimeseriesVo;
import com.qwerty.mockstagram.service.InfluencerService;
import com.qwerty.mockstagram.service.RedisService;

@Service
public class InfluencerServiceImpl implements InfluencerService {

	@Autowired
	InfluencersTimeseriesDao	influencersTimeseriesDao;

	@Autowired
	RedisService				redisService;

	private Logger				logger	= LoggerFactory.getLogger(InfluencerServiceImpl.class);

	@Override
	public void persistData(List<InfluencerTimeseries> influencersTimeseries) throws InterruptedException {

		// executorservice with fixedthreadpool of size 2
		// 1 for redis and 1 for cassandra
		ExecutorService executorService = Executors.newFixedThreadPool(2);

		// save data in cassandra
		executorService.execute(() -> influencersTimeseriesDao.batchInsert(influencersTimeseries));

		// convert timeseries data to latest influencer data object
		List<Influencer> influencers = influencersTimeseries.parallelStream().map(this::mapToInfluencers).collect(Collectors.toList());
		// save data in redis
		executorService.execute(() -> redisService.persistAllInfluencers(influencers));

		// shutdown executorService so that it won't accept any more jobs
		executorService.shutdown();

		// await termination until all the threads have completed their
		// execution
		executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
	}

	private Influencer mapToInfluencers(InfluencerTimeseries influencerTimeseries) {

		// Map InfluencerTimeseries to Influencer fields
		Influencer influencer = new Influencer();
		influencer.setId(influencerTimeseries.getInfluencerTimeseriesKey().getId());
		influencer.setUsername(influencerTimeseries.getUsername());
		influencer.setFollowers(influencerTimeseries.getFollowersCount());
		influencer.setFollowing(influencerTimeseries.getFollowingCount());
		influencer.setUpdatedTs(influencerTimeseries.getInfluencerTimeseriesKey().getCreatedTs());

		return influencer;
	}

	@Override
	public List<TimeseriesVo> fetchTimeseriesData(Integer influencerId) {

		// fetch timeseries data from cassandra for given id
		List<InfluencerTimeseries> influencerTimeseriesList = influencersTimeseriesDao.readAll(influencerId);

		// Map it to timeseries view object
		return influencerTimeseriesList.parallelStream().map(this::mapToTimeseriesVo).collect(Collectors.toList());
	}

	private TimeseriesVo mapToTimeseriesVo(InfluencerTimeseries influencerTimeseries) {

		// map InfluencerTimeseries to TimeseriesVo fields
		TimeseriesVo timeseriesVo = new TimeseriesVo();
		timeseriesVo.setFollowersCount(influencerTimeseries.getFollowersCount());
		timeseriesVo.setFollowingCount(influencerTimeseries.getFollowingCount());
		timeseriesVo.setTimestamp(influencerTimeseries.getInfluencerTimeseriesKey().getCreatedTs());

		return timeseriesVo;
	}

	@Override
	public Influencer fetchData(Integer influencerId) {

		// convert Integer key to String key, fetch data from redis
		return redisService.fetchData(String.valueOf(influencerId));
	}

	@Override
	public Double getAverageFollowers() {

		// get average follower score from redis
		return redisService.getAverageScore();
	}

	@Override
	public List<Influencer> fetchAllInfluencers(List<Integer> ids) {

		// convert Integer key to String key, fetch data from redis
		return redisService.fetchAllData(ids.stream().map(String::valueOf).collect(Collectors.toList()));
	}

	@Override
	public void updateSuspiciousInfluencers(String influencerId, Boolean suspicious) {

		// update suspicious influencers in redis
		redisService.updateInfluencerSuspicious(influencerId, suspicious);
	}
}
