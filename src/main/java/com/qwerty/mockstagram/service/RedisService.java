package com.qwerty.mockstagram.service;

import java.util.List;

import com.qwerty.mockstagram.model.Influencer;

public interface RedisService {

	/**
	 * save all Influencers in redis
	 * 
	 * @param influencers
	 */
	void persistAllInfluencers(List<Influencer> influencers);

	/**
	 * fetch data from redis for an influencerId
	 * 
	 * @param id
	 * @return
	 */
	Influencer fetchData(String id);

	/**
	 * fetch data from redis for list of influencerIds
	 * 
	 * @param ids
	 * @return
	 */
	List<Influencer> fetchAllData(List<String> ids);

	/**
	 * get average score from the set
	 * 
	 * @return
	 */
	Double getAverageScore();

	/**
	 * update the suspicious status of an influencer in redis
	 * 
	 * @param id
	 * @param suspicious
	 */
	void updateInfluencerSuspicious(String id, Boolean suspicious);
}
