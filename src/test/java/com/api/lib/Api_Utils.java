package com.api.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import com.api.reports.ReportUtil;
import com.api.utilities.CommonUtils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Api_Utils extends Http_Methods{
	/**
	 * Keyword: validateReponsePayload
	 * Author: Subrato Sarkar
	 * Date: 11/10/2020
	 * @return responseCode
	 */
	public static void validateReponsePayload (String requestPayLoad, String URI) {
		JsonPath jsonPath = get(URI).jsonPath();
		String[] reqPayLoad = requestPayLoad.split(",");
		String key = null, value = null;
		int count = 0;
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < reqPayLoad.length; i++) {
			key = reqPayLoad[i].split(":")[0].trim();
			value = reqPayLoad[i].split(":")[1].trim();
			if (String.valueOf(jsonPath.get(key.toLowerCase())).equals(value)) {
				count ++;
			} else {
				map.put(key, value);
			}
		}
		if (count == reqPayLoad.length) {
			ReportUtil.markPassed("Response payload values is verified successfully with request payload");
		} else {
			ReportUtil.markFailed("Unverified: " + map.toString());
		}
	}
	
	/**
	 * Keyword: validateReponseHeader
	 * Author: Subrato Sarkar
	 * Date: 11/10/2020
	 * @return responseCode
	 */
	public static void validateReponseHeader (String URI, Response response) {
		if (response.header("content-type").contains("application/json")) {
			ReportUtil.markPassed("Response header is correctly validated");
		} else {
			ReportUtil.markFailed("Reponse header is not validated");
		}
	}
	
	public static long getResponseTime (Response response) {
		return response.getTime();
	}
	
	/**
	 * Keyword: getAccessToken
	 * Author: Subrato Sarkar
	 * Date: 12/15/2020
	 * @return Access Token
	 */
	public static String getAccessToken () {
		if (BEARER_TOKEN == null) {
			Response response = Http_Methods.get_access_refresh_token();
			BEARER_TOKEN = response.jsonPath().get("access_token");
		}
		return BEARER_TOKEN;
	}
	
	/**
	 * Keyword: getRefreshToken
	 * Author: Subrato Sarkar
	 * Date: 12/15/2020
	 * @return Refresh Token
	 */
	public static String getRefreshToken () {
		if (REFRESH_TOKEN == null) {
			Response response = Http_Methods.get_access_refresh_token();
			REFRESH_TOKEN = response.jsonPath().get("refresh_token");
		}
		return REFRESH_TOKEN;
	}
	
	/**
	 * Keyword: request_body_to_create_playlist
	 * Author: Subrato Sarkar
	 * Date: 12/15/2020
	 * @return JSONObject
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject parse_json_to_create_playlist (String request_body) {
		String[] arrPost = request_body.split(",");
		JSONObject json = new JSONObject();
		String key, value;
		for (int i = 0; i < arrPost.length; i++) {
			key = arrPost[i].split(":")[0].trim();
			value = arrPost[i].split(":")[1].trim();
			if (key.toUpperCase().equals("PUBLIC")) {
				json.put(key.toLowerCase(), Boolean.parseBoolean(value));
			} else {
				json.put(key.toLowerCase(), value);
			}
		}
		return json;
	}
	
	/**
	 * Keyword: parse_json_to_create_tracks_to_playlist
	 * Author: Subrato Sarkar
	 * Date: 12/15/2020
	 * @return JSONObject
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject parse_json_to_create_tracks_to_playlist(String request_body) {
		JSONObject json = new JSONObject();
		String[] array_uri = getSpotifyURI_ByTrackId(request_body);
		json.put("uris", array_uri);
		return json;
	}
	
	/**
	 * Keyword: parse_json_to_dlete_tracks_from_playlist
	 * Author: Subrato Sarkar
	 * Date: 12/15/2020
	 * @return JSONObject
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject parse_json_to_delete_tracks_from_playlist (String request_body) {
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject json = new JSONObject();
		String[] array = request_body.split(",");
		for (int i = 0; i < array.length; i++) {
			json.put("uri", "spotify:track:" + array[i].trim());
			list.add(json);
			json = new JSONObject();
		}
		json = new JSONObject();
		json.put("tracks", list);
		return json;
	}
	
	/**
	 * Function: getSpotifyURI_ByTrackId
	 * Author: Subrato Sarkar
	 * Date: 12/19/2020
	 * @return: Spotify URI by Track ID
	 */
	public static String[] getSpotifyURI_ByTrackId (String trackId) {
		List<String> list = new ArrayList<String>();
		String[] track = trackId.split(",");
		for (int i = 0; i < track.length; i++) {
			list.add("spotify:track:" + track[i].trim());
		}
		return list.toArray(new String[list.size()]);
	}
	
}