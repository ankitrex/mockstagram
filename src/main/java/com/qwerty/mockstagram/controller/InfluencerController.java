package com.qwerty.mockstagram.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.qwerty.mockstagram.model.Influencer;
import com.qwerty.mockstagram.model.TimeseriesVo;
import com.qwerty.mockstagram.service.InfluencerService;
import com.qwerty.mockstagram.service.MockstagramService;

import net.minidev.json.JSONObject;

@RestController()
public class InfluencerController {

	@Autowired
	InfluencerService	influencerService;

	@Autowired
	MockstagramService	mockstagramService;

	/**
	 * fetch timeseries data for an influencerId
	 * 
	 * @param influencerId
	 * @return
	 */
	@GetMapping("/influencer/{influencerId}/timeseries-data")
	public List<TimeseriesVo> getInfluencerTimeseriesData(@PathVariable("influencerId") Integer influencerId) {

		return influencerService.fetchTimeseriesData(influencerId);
	}

	/**
	 * fetch lates data for an influencerId
	 * 
	 * @param influencerId
	 * @return
	 */
	@GetMapping("/influencer/{influencerId}")
	public Influencer getInfluencerData(@PathVariable("influencerId") Integer influencerId) {

		return influencerService.fetchData(influencerId);
	}

	/**
	 * get average follower count across all influencers
	 * 
	 * @return
	 */
	@GetMapping("/average-follower-count")
	public JSONObject getAverageFollowerCount() {

		JSONObject object = new JSONObject();
		object.put("averageFollowerCount", influencerService.getAverageFollowers());

		return object;
	}

	/**
	 * 
	 * start fetching data of all the influencers. Will be replaced by a
	 * scheduler
	 * 
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@GetMapping("/start-main")
	public String startMain() throws InterruptedException, ExecutionException {

		mockstagramService.init();

		return "completed";
	}

	/**
	 * Start checking if influencers are suspicious or not. Will be replaced by
	 * a scheduler
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	@GetMapping("/proc1")
	public String startProc1() throws InterruptedException {

		mockstagramService.suspiciousInfluencers();

		return "completed";
	}
}
