package UserAPIValidation.UserAPIAssignment;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

public class UserAPITest {
	  @BeforeTest
	  public void beforeTest() {
			RestAssured.baseURI ="https://reqres.in/api/users/";
			
	  }
	
	@Test()
	public void createUserAndReturnTheID() {

		//Save user information as a Json format
		JSONObject  userInfo= new JSONObject();
		userInfo.put("name", "NAME");
		userInfo.put("job", "JOB");
	
		
		RequestSpecification createUserRequest = RestAssured.given();
		createUserRequest.accept(ContentType.JSON);
		createUserRequest.contentType(ContentType.JSON);
		createUserRequest.body(userInfo.toString());
        
		Response response = createUserRequest.post("");
		
		//Get the id for the created user
		String id = response.jsonPath().get("id");
		System.out.println("The returned id is: " + id);
		
		//Get the status code
		int statusCode = response.getStatusCode();
		Assert.assertEquals(201,statusCode, "Request Failed, status code is: " + statusCode );
		
	}
	

		@Test()
		public void getUserData() {
		
	    String userID = "7";
		RequestSpecification getUserDataRequest = RestAssured.given();
		Response response = getUserDataRequest.get(userID);
		
		//Get the values inside data json.
		HashMap<Object, String> data = response.jsonPath().getJsonObject("data");

		String firstName = data.get("first_name");
		String lastName = data.get("last_name");

		System.out.println("The user with ID #" + userID + " is " + firstName + " " + lastName);
		
		//Get the status code
		int statusCode = response.getStatusCode();
		Assert.assertEquals(200,statusCode, "Request Failed, status code is: " + statusCode );
		}
//	
	
		@Test()
		public void negativeTestToGetNonExistingId() {
			String userID = "77";
			RequestSpecification getUserDataRequest = RestAssured.given();
			Response response = getUserDataRequest.get(userID);
			//Get the values inside data json.
			HashMap<Object, String> data = response.jsonPath().getJsonObject("data");
			
			Assert.assertNull(data, "User has data while it should not, user data: " + data );
			System.out.println("No Data retuned from this ID, as expected");
			//Get the status code
			int statusCode = response.getStatusCode();
			Assert.assertEquals(404,statusCode, "Request Passed while it should be failed, status code is: " + statusCode );
		}
}