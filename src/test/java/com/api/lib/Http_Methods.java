package com.api.lib;

import static io.restassured.RestAssured.given;
import org.json.simple.JSONObject;
import com.api.reports.ReportUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Http_Methods extends AuthFactory{
	
	/**
	 * Keyword: get
	 * Author: Subrato Sarkar
	 * Date: 12/11/2020
	 * @return response
	 */
	public static Response get (String URI) {
		RequestSpecification request = RestAssured.given();
		Response response = request
								.header("Authorization", "Bearer " + BEARER_TOKEN)
								.get(URI);
		return response;
	}
	
	/**
	 * Keyword: request
	 * Author: Subrato Sarkar
	 * Date: 12/24/2020
	 * @return RequestSpecification
	 */
	public static RequestSpecification request () {
		RequestSpecification request = RestAssured.given();
		return request.header("Authorization", "Bearer " + BEARER_TOKEN);
	}
	
	/**
	 * Keyword: post
	 * Author: Subrato Sarkar
	 * Date: 11/09/2020
	 * @return response
	 */
	public static Response post (String endpoint, JSONObject json) {
		try {
			Response response = 
					given()
						.header("Authorization", "Bearer " + BEARER_TOKEN)
						.accept("application/json")
						.contentType("application/json")
						.body(json)
					.when()
						.post(endpoint);
			return response;
		} catch (Throwable t) {
			ReportUtil.markFailed("POST method is failed");
			t.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Keyword: put
	 * Author: Subrato Sarkar
	 * Date: 12/21/2020
	 * @return response
	 */
	public static Response put (String endpoint, JSONObject json) {
		try {	
			Response response = 
					given()
						.header("Authorization", "Bearer " + BEARER_TOKEN)
						.accept("application/json")
						.contentType("application/json")
						.body(json)
					.when()
						.put(endpoint);
			return response;
		} catch (Throwable t) {
			ReportUtil.markFailed("PUT command is failed");
			t.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Keyword: delete
	 * Author: Subrato Sarkar
	 * Date: 11/09/2020
	 * @return response
	 */
	public static Response delete (String endpoint, JSONObject json) {
		RequestSpecification request = RestAssured.given();
		Response response = request.get(endpoint);
		response = 
				given()
					.header("Authorization", "Bearer " + BEARER_TOKEN)
					.accept("application/json")
					.contentType("application/json")
					.body(json)
				.when()
					.delete(endpoint);
		return response;
	}
	
	/**
	 * Keyword: patch
	 * Author: Subrato Sarkar
	 * Date: 11/10/2020
	 * @return response
	 */
	public static Response patch (String endpoint, JSONObject json) {
		Response response = 
				given()
					.header("Authorization", "Bearer " + BEARER_TOKEN)
					.accept("application/json")
					.contentType("application/json")
					.body(json)
				.when()
					.patch(endpoint);
		return response;
	}
}