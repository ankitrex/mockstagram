package com.qwerty.mockstagram.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("influencers_timeseries")
public class InfluencerTimeseries {

	@PrimaryKey
	private InfluencerTimeseriesKey	influencerTimeseriesKey;

	@Column("username")
	private String					username;

	@Column("followers_count")
	private Integer					followersCount;

	@Column("following_count")
	private Integer					followingCount;

	public InfluencerTimeseries(InfluencerTimeseriesKey influencerTimeseriesKey, String username, Integer followersCount, Integer followingCount) {
		super();
		this.influencerTimeseriesKey = influencerTimeseriesKey;
		this.username = username;
		this.followersCount = followersCount;
		this.followingCount = followingCount;
	}

	public InfluencerTimeseriesKey getInfluencerTimeseriesKey() {

		return influencerTimeseriesKey;
	}

	public void setInfluencerTimeseriesKey(InfluencerTimeseriesKey influencerTimeseriesKey) {

		this.influencerTimeseriesKey = influencerTimeseriesKey;
	}

	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public Integer getFollowersCount() {

		return followersCount;
	}

	public void setFollowersCount(Integer followersCount) {

		this.followersCount = followersCount;
	}

	public Integer getFollowingCount() {

		return followingCount;
	}

	public void setFollowingCount(Integer followingCount) {

		this.followingCount = followingCount;
	}

	@Override
	public String toString() {

		return "InfluencerTimeseries [influencerTimeseriesKey=" + influencerTimeseriesKey + ", username=" + username + ", followersCount=" + followersCount + ", followingCount="
				+ followingCount + "]";
	}

}
