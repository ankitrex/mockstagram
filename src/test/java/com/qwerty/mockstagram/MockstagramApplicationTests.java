package com.qwerty.mockstagram;

import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.qwerty.mockstagram.service.MockstagramService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MockstagramApplicationTests {

	// @Autowired
	// RedisService redisService;

	@Autowired
	MockstagramService mockstagramService;

	@Test
	public void contextLoads() throws InterruptedException, ExecutionException {

		long start = System.currentTimeMillis();

		// mockstagramService.init();
		mockstagramService.suspiciousInfluencers();

		System.out.println(System.currentTimeMillis() - start);

		// redisService.updateInfluencerSuspicious("1000001", true);

		// List<Influencer> influencers = new ArrayList<>();
		//
		// for (int i = 0; i < 1000000; i++) {
		// Influencer influencer = new Influencer();
		// influencer.setId(i);
		// influencer.setUsername("user" + i);
		// influencer.setFollowers((int) (Math.random() * 10000));
		// influencer.setFollowing((int) (Math.random() * 10000));
		// influencer.setUpdatedTs(LocalDateTime.now());
		// influencers.add(influencer);
		// }
		// long start = System.currentTimeMillis();
		//
		// redisService.persistAllInfluencers(influencers);
		//
		// System.out.println(System.currentTimeMillis() - start);
	}
}
