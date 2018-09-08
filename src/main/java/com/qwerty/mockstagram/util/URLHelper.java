package com.qwerty.mockstagram.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class URLHelper {

	private Logger	logger	= LoggerFactory.getLogger(URLHelper.class);

	@Value("${mockstagram.api.base-url}")
	private String	baseUrl;

	@Value("${mockstagram.api-v1}")
	private String	apiV1;

	@Value("${mockstagram.influencers.endpoint}")
	private String	influencersEndpoint;

	@Value("${mockstagram.influencers.suspicious-endpoint}")
	private String	suspiciousInfluencerEndpoint;

	/**
	 * Fetches data from a url and returns String response
	 * 
	 * @param url
	 * @return String
	 */
	public String fetchDataFromUrl(String url) {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {

			StringBuilder sb = new StringBuilder();

			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			return sb.toString();
		}
		catch (Exception e) {
			logger.error("Error reading data from url:: ", e);
		}

		return null;
	}

	/**
	 * Makes http post request to a url with jsonobject as raw body data.
	 * Returns String response
	 * 
	 * @param url
	 * @param body
	 * @return String
	 */
	public String influencerPostRequest(String url, JSONObject body) {

		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;

		try {

			httpClient = HttpClientBuilder.create().build();

			HttpPost httpPost = new HttpPost(url);

			StringEntity data = new StringEntity(body.toString());
			data.setContentType("application/json");
			httpPost.setEntity(data);

			response = httpClient.execute(httpPost);

			return EntityUtils.toString(response.getEntity());
		}
		catch (IOException e) {
			logger.error("Error fetching data", e);
		}
		finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
			}
			catch (IOException e) {
				logger.error("Error closing httpClient", e);
			}
			try {
				if (response != null) {
					response.close();
				}
			}
			catch (IOException e) {
				logger.error("Error closing response", e);
			}
		}
		return null;
	}

	/**
	 * Generate URL for mockstagram api endpoints
	 * 
	 * @param id
	 * @param type
	 * @return
	 */
	public String generateMockstagramUrl(int id, String type) {

		StringBuilder sb = new StringBuilder(baseUrl);
		sb.append(apiV1);
		sb.append(influencersEndpoint);
		if (Constants.URL_TYPE_SUSPICIOUS.equals(type)) {
			sb.append(suspiciousInfluencerEndpoint);
		}
		if (Constants.URL_TYPE_INFLUENCER.equals(type)) {
			sb.append("/");
			sb.append(id);
		}

		return sb.toString();
	}
}
