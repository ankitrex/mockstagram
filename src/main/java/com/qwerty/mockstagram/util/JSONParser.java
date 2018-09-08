package com.qwerty.mockstagram.util;

import java.time.LocalDateTime;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.qwerty.mockstagram.model.Influencer;
import com.qwerty.mockstagram.model.InfluencerTimeseries;
import com.qwerty.mockstagram.model.InfluencerTimeseriesKey;

@Component
public class JSONParser {

	private String	idKey				= "pk";
	private String	usernameKey			= "username";
	private String	followersCountKey	= "followerCount";
	private String	followingCountKey	= "followingCount";

	/**
	 * Map JSONObject String to InfluencerTimeseries
	 * 
	 * @param jsonString
	 * @return
	 * @throws JSONException
	 */
	public InfluencerTimeseries parseInfluencerJsonString(String jsonString) throws JSONException {

		JSONObject jsonObject = new JSONObject(jsonString);

		InfluencerTimeseriesKey influencerTimeseriesKey = new InfluencerTimeseriesKey(jsonObject.getInt(idKey), LocalDateTime.now());

		return new InfluencerTimeseries(influencerTimeseriesKey, jsonObject.getString(usernameKey), jsonObject.getInt(followersCountKey), jsonObject.getInt(followingCountKey));
	}

	/**
	 * Get JSONObject of Influencer model
	 * 
	 * @param influencer
	 * @return
	 * @throws JSONException
	 */
	public JSONObject getInfluencerJson(Influencer influencer) throws JSONException {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(idKey, influencer.getId());
		jsonObject.put(usernameKey, influencer.getUsername());
		jsonObject.put(followersCountKey, influencer.getFollowing());
		jsonObject.put(followingCountKey, influencer.getFollowers());

		return jsonObject;
	}
}
