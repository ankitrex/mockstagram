package com.qwerty.mockstagram.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

import com.qwerty.mockstagram.model.InfluencerTimeseries;
import com.qwerty.mockstagram.repository.CustomInfluencersTimeseriesRepository;

@Repository
public class CustomInfluencersTimeseriesRepositoryImpl implements CustomInfluencersTimeseriesRepository {

	@Autowired
	private CassandraOperations cassandraTemplate;

	@Override
	public boolean batchInsert(List<InfluencerTimeseries> influencers) {

		// Create and execute batchQuery using CassandraBatchOperations
		CassandraBatchOperations batchOperations = cassandraTemplate.batchOps();
		batchOperations.insert(influencers);
		batchOperations.execute();

		return true;
	}

}
