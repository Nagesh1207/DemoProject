package files;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class OAthTestPojo {

	public static void main(String[] args) {
		String[] courseTites = {"Selenium Webdriver Java","Cypress","Protractor"};
		String response = given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust")
		.when().log().all()
		.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
		.asString();
		
		System.out.println(response);
		JsonPath jsonPath = new JsonPath(response);
		String accessToken = jsonPath.getString("access_token");
		
		GetCourse gc =given()
		.queryParam("access_token", accessToken)
		.when().log().all()
		.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);
		
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		List<Api>apiCourses=gc.getCourses().getApi();
		for(int i=0; i<apiCourses.size();i++) {
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(apiCourses.get(i).getPrice());
			}
			
		}
		ArrayList<String> s = new ArrayList<String>();
		
		List<WebAutomation> wa =gc.getCourses().getWebAutomation();
		for(int j=0; j<wa.size();j++) {
			s.add(wa.get(j).getCourseTitle());
			
		}
		List<String> expectedList=Arrays.asList(courseTites);
		Assert.assertTrue(s.equals(expectedList));
		 
		
	}

}
