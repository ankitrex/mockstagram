package com.qwerty.mockstagram.repository;

import java.util.List;

import com.qwerty.mockstagram.model.InfluencerTimeseries;

public interface CustomInfluencersTimeseriesRepository {

	/**
	 * Custom batch insert implementation
	 * 
	 * @param influencers
	 * @return
	 */
	boolean batchInsert(List<InfluencerTimeseries> influencers);
}
