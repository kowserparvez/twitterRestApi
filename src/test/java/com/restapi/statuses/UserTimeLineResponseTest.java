package com.restapi.statuses;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.lessThan;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.restapi.common.RestUtilities;
import com.restapi.constants.EndPoints;
import com.restapi.constants.Path;

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class UserTimeLineResponseTest {
	
	
	
	ResponseSpecification responseSpec;
	RequestSpecification requestSpec;
	
	@BeforeClass
	public void setup() {
	requestSpec = RestUtilities.getRequestSpecification();
	requestSpec.queryParam("user_id", "selclass12");
	requestSpec.basePath(Path.STATUSES);
	
	responseSpec= RestUtilities.getResponseSpecification();
	
	
	}
	
	@Test(enabled = true)
	public void readTweets() {
			given()
				.spec(RestUtilities.createQuaryParam(requestSpec, "count", "1"))
			.when()
				.get(EndPoints.STATUSES_USER_TIMELINE)
			.then()
			//	.log().all()
				.spec(responseSpec)
				.body("user.screen_name",hasItem("selclass12"));
	
	}
	@Test // same as before test.
	public void resposeTweet() {
		RestUtilities.setEndPoint(EndPoints.STATUSES_USER_TIMELINE);
		Response res = RestUtilities.getResponse(
				(RestUtilities.createQuaryParam(requestSpec, "count", "1")), "get");
		ArrayList<String> screenNameList = res.path("user.screen_name");
		Assert.assertTrue(screenNameList.contains("selclass12"));
	}

}
