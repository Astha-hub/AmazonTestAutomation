package com.amazon.pages;

import com.amazon.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class SignInPage extends BaseTest {

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/skip_sign_in_button")
	private MobileElement skipSignInBtn;

	public HomePage pressSkipSignIn() {
		clickElement(skipSignInBtn);
		return new HomePage();
	}

}
