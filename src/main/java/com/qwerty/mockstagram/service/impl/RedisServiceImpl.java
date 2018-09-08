package com.qwerty.mockstagram.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.qwerty.mockstagram.model.Influencer;
import com.qwerty.mockstagram.service.RedisService;

@Service
public class RedisServiceImpl implements RedisService {

	@Autowired
	private RedisTemplate<String, Object>	redisTemplate;

	private String							influencerKey		= "influencerScores";

	private String							suspiciousKeyPrefix	= "susp-";

	@Override
	public void persistAllInfluencers(List<Influencer> influencers) {

		ZSetOperations<String, Object> orderedSet = redisTemplate.opsForZSet();

		ValueOperations<String, Object> redisKeyValue = redisTemplate.opsForValue();

		// iterate through the list
		// parallel stream can be used for enhancing the performance
		influencers.stream().forEach(influencer -> {

			// fetch is_suspicious data of influencer from redis
			influencer.setSuspicious((Boolean) redisKeyValue.get(suspiciousKeyPrefix + influencer.getId()));

			// add influencer to redis with Key = id and Value = Influencer
			redisKeyValue.set(influencer.getId(), influencer);

			// add influencer id and score to orderedSet for ranking purposes
			orderedSet.add(influencerKey, influencer.getId(), influencer.getFollowers());
		});
	}

	@Override
	public Influencer fetchData(String id) {

		ZSetOperations<String, Object> orderedSet = redisTemplate.opsForZSet();

		ValueOperations<String, Object> redisKeyValue = redisTemplate.opsForValue();

		// fetch latest influencer data
		Influencer influencer = (Influencer) redisKeyValue.get(id);

		// fetch rank and update in object
		influencer.setRank(orderedSet.reverseRank(influencerKey, id));

		return influencer;
	}

	@Override
	public Double getAverageScore() {

		ZSetOperations<String, Object> orderedSet = redisTemplate.opsForZSet();

		// fetch all scores for influencers from redis
		Set<TypedTuple<Object>> allScores = orderedSet.rangeWithScores(influencerKey, 0, -1);

		// find average
		OptionalDouble total = allScores.parallelStream().mapToDouble(TypedTuple<Object>::getScore).average();

		return total.isPresent() ? total.getAsDouble() : null;
	}

	@Override
	public List<Influencer> fetchAllData(List<String> ids) {

		List<Influencer> influencers = new ArrayList<>();

		ValueOperations<String, Object> redisKeyValue = redisTemplate.opsForValue();

		// fetch data for list of ids
		ids.parallelStream().forEach(id -> {
			Influencer influencer = (Influencer) redisKeyValue.get(id);
			influencers.add(influencer);
		});

		return influencers;
	}

	@Override
	public void updateInfluencerSuspicious(String id, Boolean suspicious) {

		ValueOperations<String, Object> redisKeyValue = redisTemplate.opsForValue();

		// update suspicious status of an influencer in key value format
		redisKeyValue.set(suspiciousKeyPrefix + id, suspicious);
	}

}
