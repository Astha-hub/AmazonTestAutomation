package com.amazon.tests;

import java.io.InputStream;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.amazon.BaseTest;
import com.amazon.pages.HomePage;
import com.amazon.pages.SignInPage;
import com.amazon.utils.TestUtils;

public class ProductTest extends BaseTest {
	SignInPage signInPage;
	BaseTest base;
	JSONObject products;
	TestUtils testUtils;

	@BeforeClass
	public void beforeClass() throws Exception {
		InputStream datais = null;
		try {
			String dataFileName = "data/products.json";
			datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
			JSONTokener tokener = new JSONTokener(datais);
			products = new JSONObject(tokener);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (datais != null) {
				datais.close();
			}
		}
	}

	@BeforeMethod
	public void beforeMethod(Method m) {
		// TODO: Add loggers 
		System.out.println("starting test---" + m.getName());
		signInPage = new SignInPage();

	}

	@AfterMethod
	public void afterMethod() {
	}

	@AfterClass
	public void afterClass() {
	}

	@Test
	public void testValidateProductDetails() throws InterruptedException {

		HomePage homePage = signInPage.pressSkipSignIn();

		homePage.searchProduct(products.getJSONObject("Products").getString("productName"));
		homePage.selectProduct(products.getJSONObject("Products").getString("productName"));
		
		// TODO:  Validation for product title and Price


	}

}
