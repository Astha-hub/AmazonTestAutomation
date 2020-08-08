package com.amazon.pages;

/**
 * Page Object for Home Page (SignIn--> Home Page).
 */
import java.util.List;

import com.amazon.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class HomePage extends BaseTest {

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/rs_search_src_text")
	private MobileElement searchProductField;

	@AndroidFindBy(xpath = "//android.widget.ListView[@resource-id='com.amazon.mShop.android.shopping:id/iss_search_suggestions_list_view']//android.widget.TextView")
	private List<MobileElement> lstProduct;


	public void searchProduct(String strValue) {
		sendKeys(searchProductField, strValue);
	}

	public void selectProduct(String strValue) {
		for (MobileElement product : lstProduct) {
			if (product.getText().equalsIgnoreCase(strValue))
				clickElement(product);
			break;
		}
	}

}
