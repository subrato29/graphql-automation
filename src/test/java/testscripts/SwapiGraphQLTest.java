package testscripts;

import org.testng.annotations.Test;

import com.graphql.pojo.GraphQLQuery;
import com.graphql.pojo.QueryVariable;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class SwapiGraphQLTest {

	String BEARER_TOKEN = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik9FWTJSVGM1UlVOR05qSXhSRUV5TURJNFFUWXdNekZETWtReU1EQXdSVUV4UVVRM05EazFNQSJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWRlZmF1bHQtcm9sZSI6InVzZXIiLCJ4LWhhc3VyYS1hbGxvd2VkLXJvbGVzIjpbInVzZXIiXSwieC1oYXN1cmEtdXNlci1pZCI6ImF1dGgwfDYwYzgxYzMwYjA2NzQ0MDA2OTdlYzdmNCJ9LCJuaWNrbmFtZSI6InN1YnJhdG9zYXJrYXIyOSIsIm5hbWUiOiJzdWJyYXRvc2Fya2FyMjlAZ21haWwuY29tIiwicGljdHVyZSI6Imh0dHBzOi8vcy5ncmF2YXRhci5jb20vYXZhdGFyLzNjNGYzNzFlNGVmNzU3YjYwNWJmZjA2ODIxYWUxNWVhP3M9NDgwJnI9cGcmZD1odHRwcyUzQSUyRiUyRmNkbi5hdXRoMC5jb20lMkZhdmF0YXJzJTJGc3UucG5nIiwidXBkYXRlZF9hdCI6IjIwMjEtMDYtMTlUMTg6MjE6NDIuNzcxWiIsImlzcyI6Imh0dHBzOi8vZ3JhcGhxbC10dXRvcmlhbHMuYXV0aDAuY29tLyIsInN1YiI6ImF1dGgwfDYwYzgxYzMwYjA2NzQ0MDA2OTdlYzdmNCIsImF1ZCI6IlAzOHFuRm8xbEZBUUpyemt1bi0td0V6cWxqVk5HY1dXIiwiaWF0IjoxNjI0NzMxMjgyLCJleHAiOjE2MjQ3NjcyODIsImF0X2hhc2giOiJDNnRnRnBMWkRlNjE0Z2FKVURFN1pRIiwibm9uY2UiOiJoY1gzVWcwRzJoSWhBNDhhUm5ncmNnNDlsN0ZRZlFRdCJ9.HD5oaUB5uF9FvCpgrNtnVRwFj9tnz_c6vSu5f7ZLeNi39jPoXcVVCIIddbtCRItY09-ur1hU42M9lKQyyYLAKH4-xnErjYmgrREkpvFsM15r7goMQ_lUX1syyTzBesEaiOK9AqyBeRvgV27osDgOcTyvxy9i4j2vzZvd7bIArnAaqOjwR59ubIV09-XQzVhqC9e1nYiqI2pKXY0xq7zPU6A3RlEZ4xz75JqdqQIy8GxFh9jHKTEtSXXki1OM6hhnqb3OLgL6Xjasc42FVQzznQoH_Ns75syogxbJAbqI9rbhAwUzQgqNu25eA2CUFMaTnQGE_omU7s-mhmbyVRoKWg";
	
	//https://swapi-graphql.netlify.app/.netlify/functions/index
	
	@Test
	public void getAllFilmsTest() {
		RestAssured.baseURI = "https://swapi-graphql.netlify.app";
		String query = "{\"query\":\"{\\n  allFilms {\\n    films {\\n      title\\n    }\\n  }\\n}\\n\",\"variables\":null}";
		
		given().log().all()
			.contentType("application/json")
			.body(query)
				.when().log().all()
					.post("/.netlify/functions/index")
						.then().log().all()
							.assertThat()
								.statusCode(200)
									.and()
										.body("data.allFilms.films[0].title", equalTo("A New Hope"));
	}
	
	@Test
	public void getAllUsers() {
		RestAssured.baseURI = "https://hasura.io";
		String query = "{\"query\":\"{\\n  users(limit: 10) {\\n    id\\n    name\\n  }\\n}\\n\",\"variables\":null}";		
		
		given().log().all()
			.contentType("application/json")
			.header("Authorization", BEARER_TOKEN)
				.body(query)
					.when().log().all()
						.post("/learn/graphql")
							.then().log().all()
								.assertThat()
									.statusCode(200)
										.and()
											.body("data.users[0].name", equalTo("tui.glen"));
	}
	
	@Test
	public void getAllUsers_POJOTest() {
		RestAssured.baseURI = "https://hasura.io";
		GraphQLQuery query = new GraphQLQuery();

		query.setQuery("query ($limit: Int!, $name: String!) {\n"
				+ "  users(limit: $limit, where: {name: {_eq: $name}}) {\n"
				+ "    id\n"
				+ "    name\n"
				+ "  }\n"
				+ "}");
		
		QueryVariable variable = new QueryVariable();
		variable.setLimit(5);
		variable.setName("tui.glen");
		
		query.setVariables(variable);
		
		given().log().all()
			.contentType(ContentType.JSON)
			.header("Authorization", BEARER_TOKEN)
				.body(query)
					.when().log().all()
						.post("/learn/graphql")
							.then().log().all()
								.assertThat()
									.statusCode(200)
										.and()
											.body("data.users[0].name", equalTo("tui.glen"));
	}
	
}
