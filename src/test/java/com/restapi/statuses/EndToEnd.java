package com.restapi.statuses;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.restapi.common.RestUtilities;
import com.restapi.constants.Auth;
import com.restapi.constants.EndPoints;
import com.restapi.constants.Path;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class EndToEnd {
	ResponseSpecification responseSpec;
	RequestSpecification requestSpec;
	public static String tweetID = "";
	
	@BeforeClass
	public void setup() {
	
	requestSpec = RestUtilities.getRequestSpecification();
	//requestSpec.queryParam("user_id", "selclass12");
	requestSpec.basePath(Path.STATUSES);
	
	responseSpec= RestUtilities.getResponseSpecification();
	
	
	}
	
	@Test
	public void postTweet() {
		Response response =
				given()
					.spec(RestUtilities.createQuaryParam(requestSpec, "status", "End to End Testing and will read and now destroy!"))
				.when()
					.post(EndPoints.STATUSES_TWEET_POST)
				.then()
					.spec(responseSpec)
					.extract().response();
			
			JsonPath jsonPath = RestUtilities.getJsonPath(response);
			tweetID = jsonPath.get("id_str");
			System.out.println("This is the : "+ tweetID);
		
//			tweetID = response.path("id_str");
//			System.out.println("This is the : "+ tweetID);
		}
	@Test (dependsOnMethods= {"postTweet"})
	public void readTweet() {
		RestUtilities.setEndPoint(EndPoints.STATUSES_TWEET_READ_SINGLE);
		Response response = RestUtilities.getResponse(RestUtilities.createQuaryParam(requestSpec, "id",tweetID),"get");
		String text = response.path("text");
		System.out.println("The tweet was : "+ text);
//		Response response =
//			given()
//				.spec(RestUtilities.createQuaryParam(requestSpec, "id", tweetID))
//			.when()
//				.get(EndPoints.STATUSES_TWEET_READ_SINGLE)
//				.then()
//					.spec(responseSpec)
//					.extract().response();
//		
//		String text = response.path("text");
//		System.out.println("The tweet was : "+ text);
	}
		
	@Test(dependsOnMethods= {"readTweet"})
	public void deleteATweet() {
			given()
				.spec(RestUtilities.createPathParam(requestSpec, "id", tweetID))
				//.spec(RestUtilities.createPathParam(requestSpec, "id",tweetID))
				.pathParam("id",tweetID) // using path parameter
			.when()
				.post(EndPoints.STATUSES_TWEET_DESTROY)
			.then()
				.spec(responseSpec);	
		
	}
	


}
