package com.qwerty.mockstagram.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Influencer implements Serializable {

	private static final long	serialVersionUID	= 1L;

	private Integer				id;
	private Integer				followers;
	private Integer				following;
	private String				username;
	private Boolean				suspicious;
	private Long				rank;
	private LocalDateTime		updatedTs;
	private Double				followerRatio;

	public String getId() {

		return String.valueOf(id);
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public Integer getFollowers() {

		return followers;
	}

	public void setFollowers(Integer followers) {

		this.followers = followers;
		this.followerRatio = this.followers != null && this.following != null ? (double) this.followers / this.following : null;
	}

	public Integer getFollowing() {

		return following;
	}

	public void setFollowing(Integer following) {

		this.following = following;
		this.followerRatio = this.followers != null && this.following != null ? (double) this.followers / this.following : null;
	}

	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public Boolean getSuspicious() {

		return suspicious;
	}

	public void setSuspicious(Boolean suspicious) {

		this.suspicious = suspicious;
	}

	public Long getRank() {

		return rank;
	}

	public void setRank(Long rank) {

		this.rank = rank;
	}

	public LocalDateTime getUpdatedTs() {

		return updatedTs;
	}

	public void setUpdatedTs(LocalDateTime updatedTs) {

		this.updatedTs = updatedTs;
	}

	public Double getFollowerRatio() {

		return followerRatio;
	}

	@Override
	public String toString() {

		return "Influencer [id=" + id + ", followers=" + followers + ", following=" + following + ", username=" + username + ", suspicious=" + suspicious + ", rank=" + rank
				+ ", updatedTs=" + updatedTs + ", followerRatio=" + followerRatio + "]";
	}

}
