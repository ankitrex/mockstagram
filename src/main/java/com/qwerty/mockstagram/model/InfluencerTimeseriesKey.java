package com.qwerty.mockstagram.model;

import java.time.LocalDateTime;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class InfluencerTimeseriesKey {

	@PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED)
	private Integer			id;

	@PrimaryKeyColumn(name = "created_ts", ordinal = 0, ordering = Ordering.DESCENDING)
	private LocalDateTime	createdTs;

	public InfluencerTimeseriesKey(Integer id, LocalDateTime createdTs) {
		super();
		this.id = id;
		this.createdTs = createdTs;
	}

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public LocalDateTime getCreatedTs() {

		return createdTs;
	}

	public void setCreatedTs(LocalDateTime createdTs) {

		this.createdTs = createdTs;
	}

	@Override
	public String toString() {

		return "InfluencerTimeseriesKey [id=" + id + ", createdTs=" + createdTs + "]";
	}

}
