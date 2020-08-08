package com.amazon;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.amazon.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseTest {
	protected  static AppiumDriver<MobileElement> driver;

	protected static Properties props;
	protected static ThreadLocal<HashMap<String, String>> strings = new ThreadLocal<HashMap<String, String>>();

	InputStream inputStream;
	InputStream stringsis;
	protected static ThreadLocal<String> dateTime = new ThreadLocal<String>();
	TestUtils utils = new TestUtils();

	public BaseTest() {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	public void setDateTime(String date) {
		dateTime.set(date);
	}

	public HashMap<String, String> getStrings() {
		return strings.get();
	}

	public void setStrings(HashMap<String, String> strings2) {
		strings.set(strings2);
	}

	@Parameters({ "platformName", "platformVersion" })
	@BeforeTest
	public void initializeDriver(String platformName, String platformVersion) throws Exception {
		setDateTime(utils.getDateTime());
		try {
			props = new Properties();
			String propFileNmae = "config.properties";
			String xmlFileName = "strings/strings.xml";
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileNmae);
			props.load(inputStream);

			stringsis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
			setStrings(utils.parseStringXML(stringsis));

			DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
			desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
			desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, props.getProperty("androidDeviceName"));
			File classpathRoot = new File(System.getProperty("user.dir"));

			File appDir = new File(classpathRoot, props.getProperty("androidAppLocation"));
			desiredCapabilities.setCapability(MobileCapabilityType.APP, appDir.getAbsolutePath());
			desiredCapabilities.setCapability("appActivity", props.getProperty("androidAppActivity"));
			desiredCapabilities.setCapability("appPackage", props.getProperty("androidAppPackage"));
			desiredCapabilities.setCapability("automationName", props.getProperty("androidAutomationName"));

			URL url = new URL(props.getProperty("appiumURL"));

			driver = new AndroidDriver(url, desiredCapabilities);
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}

		}
	}

	public String getDateTime() {
		return dateTime.get();
	}

	public void waitForVisibilty(MobileElement e) {
		WebDriverWait wait = new WebDriverWait(driver, TestUtils.WAIT);
		wait.until(ExpectedConditions.visibilityOf(e));
	}

	public void clickElement(MobileElement e) {
		waitForVisibilty(e);
		e.click();
	}

	public void sendKeys(MobileElement e, String strValue) {
		waitForVisibilty(e);
		e.sendKeys(strValue);
	}

	public void clear(MobileElement e) {
		waitForVisibilty(e);
		e.clear();
	}

	public String getAttribute(MobileElement e, String strAttribute) {
		waitForVisibilty(e);
		return e.getAttribute(strAttribute);
	}

	public void quitDriver() {
		driver.quit();
	}

	public void closeApp() {
		((InteractsWithApps) driver).closeApp();
	}

	public void launchApp() {
		((InteractsWithApps) driver).launchApp();
	}

	public MobileElement scrollToElement() {
		return (MobileElement) ((FindsByAndroidUIAutomator<?>) driver)
				.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
						+ ".scrollable(true)).scrollIntoView(" + "new UiSelector().description(\"test-Price\"));");
	}

	@AfterTest
	public void afterTest() {
		quitDriver();
	}

}
