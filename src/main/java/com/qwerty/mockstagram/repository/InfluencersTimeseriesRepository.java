package com.qwerty.mockstagram.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.qwerty.mockstagram.model.InfluencerTimeseries;
import com.qwerty.mockstagram.model.InfluencerTimeseriesKey;

public interface InfluencersTimeseriesRepository extends CassandraRepository<InfluencerTimeseries, InfluencerTimeseriesKey>, CustomInfluencersTimeseriesRepository {

	/**
	 * custom select query implementation
	 * 
	 * @param id
	 * @return
	 */
	@Query("select * from influencers_timeseries where id=?0")
	public List<InfluencerTimeseries> findByInfluencerId(Integer id);
}
