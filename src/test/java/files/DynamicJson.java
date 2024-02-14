package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
public class DynamicJson {
	
	@Test(dataProvider="BooksData")
	public void addBook(String isbn,String aisle) {
		RestAssured.baseURI="http://216.10.245.166";
		String response =given().header("Content.Type","appication/json").
		body(payload.AddBook(isbn,aisle)).
		when()
		.post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(response);
		String id =js.get("ID");
		System.out.println(id);
		
		//delete method
		

	}
	
	//array=collection of elements
	//multidimensional array= collection of arrays
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		return new Object[][] {{"rubys","6827"},{"wfea","6482"},{"gdcv","1837"}};
	}
	
}
