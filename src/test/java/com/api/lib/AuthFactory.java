package com.api.lib;

import com.api.base.DriverScript;
import com.api.utilities.CommonUtils;
import com.api.utilities.Constants;
import com.web.lib.WebDriverUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AuthFactory extends DriverScript{

	static String TEST_DATA = Constants.TEST_DATA;
	static String CLIENT_ID = CommonUtils.getProperty("client_id");
	static String CLIENT_SECRET = CommonUtils.getProperty("client_secret");
	static String REDIRECT_URI = CommonUtils.getProperty("redirect_uri");
	static String BEARER_TOKEN = null;
	static String REFRESH_TOKEN = null;

	/**
	 * Keyword: get_auth_url
	 * Author: Subrato Sarkar
	 * Date: 12/12/2020
	 * @return Get authentication url by user login
	 */
	public static String get_auth_url () {
		String scope = "Playlist-read-private Playlist-modify-public "
				+ "User-read-recently-played User-read-playback-state "
				+ "User-read-private";
		try {
			String auth_url = "https://accounts.spotify.com/authorize?client_id="
					+ "" + CLIENT_ID + ""
					+ "&response_type=code&redirect_uri=" 
					+ CommonUtils.encodeUrl(REDIRECT_URI) + "&scope=" + scope.toLowerCase();
			return auth_url;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Keyword: get_access_refresh_token
	 * Author: Subrato Sarkar
	 * Date: 12/12/2020
	 * @return Get Access token and Refresh token, oAuth 2.0
	 */
	public static Response get_access_refresh_token () {
		Response response = null;
		try {
			String code = WebDriverUtils.get_auth_code(get_auth_url());
			response = RestAssured
							.given()
							.formParam("client_id", CLIENT_ID)
							.formParam("client_secret", CLIENT_SECRET)
							.formParam("grant_type", "authorization_code")
							.formParam("code", code)
							.formParam("redirect_uri", REDIRECT_URI)
							.post("https://accounts.spotify.com/api/token");
						
		} catch (Throwable e) {
			e.printStackTrace();
		}
		BEARER_TOKEN = response.jsonPath().get("access_token");
		System.out.println("Bearer token: " + BEARER_TOKEN);
		
		REFRESH_TOKEN = response.jsonPath().get("refresh_token");
		System.out.println("Refresh token: " + REFRESH_TOKEN);
		return response;
	}
	
}