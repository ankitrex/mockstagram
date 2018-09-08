package com.qwerty.mockstagram.service;

import java.util.List;

import com.qwerty.mockstagram.model.Influencer;
import com.qwerty.mockstagram.model.InfluencerTimeseries;
import com.qwerty.mockstagram.model.TimeseriesVo;

public interface InfluencerService {

	/**
	 * Save all influencer data in cassandra(timeseries) and redis(latest)
	 * 
	 * @param influencersTimeseries
	 * @throws InterruptedException
	 */
	void persistData(List<InfluencerTimeseries> influencersTimeseries) throws InterruptedException;

	/**
	 * fetch timeseries data for an influencerId from cassandra
	 * 
	 * @param influencerId
	 * @return List<TimeseriesVo>
	 */
	List<TimeseriesVo> fetchTimeseriesData(Integer influencerId);

	/**
	 * fetch latest data for an influencerId from redis
	 * 
	 * @param influencerId
	 * @return Influencer
	 */
	Influencer fetchData(Integer influencerId);

	/**
	 * fetch latest data for all influencers from redis
	 * 
	 * @param ids
	 * @return List<Influencer>
	 */
	List<Influencer> fetchAllInfluencers(List<Integer> ids);

	/**
	 * update in redis if the influencer is suspicious or not
	 * 
	 * @param influencerId
	 * @param suspicious
	 */
	void updateSuspiciousInfluencers(String influencerId, Boolean suspicious);

	/**
	 * Fetch average followers by aggregating all the latest influencer data
	 * from redis
	 * 
	 * @return Double
	 */
	Double getAverageFollowers();
}
