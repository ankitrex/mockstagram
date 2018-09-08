package com.qwerty.mockstagram.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TimeseriesVo implements Serializable {

	private static final long	serialVersionUID	= 1L;

	private LocalDateTime		timestamp;
	private Integer				followingCount;
	private Integer				followersCount;
	private Double				followerRatio;

	public LocalDateTime getTimestamp() {

		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {

		this.timestamp = timestamp;
	}

	public Integer getFollowingCount() {

		return followingCount;
	}

	public void setFollowingCount(Integer followingCount) {

		this.followingCount = followingCount;
		this.followerRatio = this.followersCount != null && this.followingCount != null ? (double) this.followersCount / this.followingCount : null;
	}

	public Integer getFollowersCount() {

		return followersCount;
	}

	public void setFollowersCount(Integer followersCount) {

		this.followersCount = followersCount;
		this.followerRatio = this.followersCount != null && this.followingCount != null ? (double) this.followersCount / this.followingCount : null;
	}

	public Double getFollowerRatio() {

		return followerRatio;
	}

	@Override
	public String toString() {

		return "TimeseriesVo [timestamp=" + timestamp + ", followingCount=" + followingCount + ", followersCount=" + followersCount + ", followerRatio=" + followerRatio + "]";
	}

}
