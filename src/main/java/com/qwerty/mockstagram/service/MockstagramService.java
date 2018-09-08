package com.qwerty.mockstagram.service;

import java.util.concurrent.ExecutionException;

public interface MockstagramService {

	/**
	 * Main process - execute to refresh all influencer data
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	void init() throws InterruptedException, ExecutionException;

	/**
	 * Process to check whether an influencer is suspicious or not
	 * 
	 * @throws InterruptedException
	 */
	void suspiciousInfluencers() throws InterruptedException;
}
