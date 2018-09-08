package com.qwerty.mockstagram.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qwerty.mockstagram.dao.InfluencersTimeseriesDao;
import com.qwerty.mockstagram.model.InfluencerTimeseries;
import com.qwerty.mockstagram.repository.InfluencersTimeseriesRepository;

@Component
public class InfluencersTimeseriesDaoImpl implements InfluencersTimeseriesDao {

	@Autowired
	InfluencersTimeseriesRepository influencersTimeseriesRepository;

	@Override
	public void batchInsert(List<InfluencerTimeseries> influencers) {

		influencersTimeseriesRepository.batchInsert(influencers);
	}

	@Override
	public List<InfluencerTimeseries> readAll(Integer id) {

		return influencersTimeseriesRepository.findByInfluencerId(id);
	}

}
