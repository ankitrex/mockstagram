package com.qwerty.mockstagram.dao;

import java.util.List;

import com.qwerty.mockstagram.model.InfluencerTimeseries;

public interface InfluencersTimeseriesDao {

	/**
	 * insert all influencers as a batch query
	 * 
	 * @param influencers
	 */
	void batchInsert(List<InfluencerTimeseries> influencers);

	/**
	 * read influencer data by influencerId
	 * 
	 * @param id
	 * @return
	 */
	List<InfluencerTimeseries> readAll(Integer id);
}
