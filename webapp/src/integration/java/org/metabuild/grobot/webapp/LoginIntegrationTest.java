/*
 * Copyright 2012 Metabuild Software, LLC. (http://www.metabuild.org)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.metabuild.grobot.webapp;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class LoginIntegrationTest {

	private final static String LOGIN_GET_PATH = "/login";
	private final static String LOGIN_POST_PATH = "/j_spring_security_check";
	private final static String LOGOUT_GET_PATH = "/logout";
	private final static String LOGOUT_POST_PATH = "/j_spring_security_logout";
	private final static String PROTECTED_RESOURCE = "/bots";
	
	private static DefaultHttpClient httpClient;

	@Test
	public void testLoginForm() throws ClientProtocolException, IOException {
		String loginUrl = IntegrationTestUrlBuilder.getString(LOGIN_GET_PATH);
		HttpGet get = new HttpGet(loginUrl);
		httpClient = new DefaultHttpClient();
		try {
			httpClient.getCookieStore().clear();
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			HttpResponse response = httpClient.execute(get);
			assertEquals(200,response.getStatusLine().getStatusCode());
			
			String responseBody = responseHandler.handleResponse(response);
			assertTrue("Could not find login page!", responseBody.contains("Grobot Server Login"));
			EntityUtils.consume(response.getEntity());
		} finally {
			get.releaseConnection();
		}
	}
	
	@Test
	public void testLoginAndLogoutOk() throws ClientProtocolException, IOException {
		
		String loginUrl = IntegrationTestUrlBuilder.getString(LOGIN_POST_PATH);
		HttpPost post = new HttpPost(loginUrl);
		httpClient = new DefaultHttpClient();
		
		try {
			httpClient.getCookieStore().clear();
			List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
			valuePairs.add(new BasicNameValuePair("j_username", "user"));
			valuePairs.add(new BasicNameValuePair("j_password", "password"));
			post.setEntity(new UrlEncodedFormEntity(valuePairs, Consts.UTF_8));
			
			HttpResponse httpPostResponse  = httpClient.execute(post);
			assertEquals(302,httpPostResponse.getStatusLine().getStatusCode());
			
			String sessionId = null;
			List<Cookie> cookies = httpClient.getCookieStore().getCookies();
			for (Cookie cookie : cookies) {
				if ("JSESSIONID".equals(cookie.getName()))
					sessionId = cookie.getValue();
			}
			assertNotNull("Could not find a valid session id", sessionId);
			
			Header[] locationHeaders = httpPostResponse.getHeaders("location");
			assertEquals("Incorrect number of location headers", 1, locationHeaders.length);
			post.releaseConnection();
			
			Header location = locationHeaders[0];
			HttpGet get = new HttpGet(location.getValue());
			HttpResponse httpGetResponse  = httpClient.execute(get);
			assertEquals(200,httpGetResponse.getStatusLine().getStatusCode());
			
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String reponseBody = responseHandler.handleResponse(httpGetResponse);
			assertTrue("Was not able to login successfully.", reponseBody.contains("TASKS"));
			get.releaseConnection();
			
			// TODO: logout and verify
			
		} catch (RuntimeException e) {
			fail(e.getMessage());
		} finally {
			post.releaseConnection();
		}
	}
	
	@Test
	public void testLoginBadCredentials() throws ClientProtocolException, IOException {

		String loginUrl = IntegrationTestUrlBuilder.getString(LOGIN_POST_PATH);
		HttpPost post = new HttpPost(loginUrl);
		httpClient = new DefaultHttpClient();
		
		try {
			httpClient.getCookieStore().clear();
			List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
			valuePairs.add(new BasicNameValuePair("j_username", "bad"));
			valuePairs.add(new BasicNameValuePair("j_password", "password"));
			post.setEntity(new UrlEncodedFormEntity(valuePairs, Consts.UTF_8));
			
			HttpResponse httpPostResponse  = httpClient.execute(post);
			assertEquals(302,httpPostResponse.getStatusLine().getStatusCode());
			
			String sessionId = null;
			List<Cookie> cookies = httpClient.getCookieStore().getCookies();
			for (Cookie cookie : cookies) {
				if ("JSESSIONID".equals(cookie.getName()))
					sessionId = cookie.getValue();
			}
			assertNotNull("Could not find a valid session id", sessionId);
			
			Header[] locationHeaders = httpPostResponse.getHeaders("location");
			assertEquals("Incorrect number of location headers", 1, locationHeaders.length);
			post.releaseConnection();
			
			Header location = locationHeaders[0];
			HttpGet get = new HttpGet(location.getValue());
			HttpResponse httpGetResponse  = httpClient.execute(get);
			assertEquals(200,httpGetResponse.getStatusLine().getStatusCode());
			
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = responseHandler.handleResponse(httpGetResponse);

			assertTrue("Could not find login error message.", responseBody.contains("Failed to login"));
			post.releaseConnection();
			
		} catch (RuntimeException e) {
			fail(e.getMessage());
		} finally {
			post.releaseConnection();
		}
	}
	
	@Test
	public void testNotLoggedInAccessDenied() throws ClientProtocolException, IOException {
		String protectedUrl = IntegrationTestUrlBuilder.getString(PROTECTED_RESOURCE);
		HttpGet get = new HttpGet(protectedUrl);
		httpClient = new DefaultHttpClient();
		try {
			httpClient.getCookieStore().clear();
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			HttpResponse response = httpClient.execute(get);
			assertEquals(200,response.getStatusLine().getStatusCode());
			
			String responseBody = responseHandler.handleResponse(response);
			assertTrue("Could not find login page!", responseBody.contains("Grobot Server Login"));
			EntityUtils.consume(response.getEntity());
		} finally {
			get.releaseConnection();
		}
	}

}
