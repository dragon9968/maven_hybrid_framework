package com.nopcommerce.user;

import org.testng.annotations.Test;

import commons.BaseTest;
import pageObjects.nopCommerce.user.UserHomePageObject;
import pageObjects.nopCommerce.user.UserLoginPageObject;
import pageObjects.nopCommerce.user.UserRegisterPageObject;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Level_03_Page_Object_02_Login extends BaseTest {
	private WebDriver driver;
	private String existingEmail, invalidEmail, notFoundEmail, firstName, lastName;
	private String projectPath = System.getProperty("user.dir");
	private UserHomePageObject homePage;
	private UserRegisterPageObject registerPage;
	private UserLoginPageObject loginPage;
	
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browserName) {
		// Home Page
		driver = getBrowserName(browserName);
		homePage = new UserHomePageObject(driver);

		firstName = "long";
		lastName = "qa";
		existingEmail = "long" + generateRandomNumber() + "@qa.team";
		invalidEmail = "long@123@";
		notFoundEmail = "longdaica@qa.team";

		System.out.println("Pre-Condition - Step 01: Click to Register link");
		homePage.openRegisterPage();

		registerPage = new UserRegisterPageObject(driver);

		System.out.println("Pre-Condition - Step 02: Input to required fields");
		registerPage.inputToFirstnameTextbox("firstName");
		registerPage.inputToLastnameTextbox("lastName");
		registerPage.inputToEmailTextbox(existingEmail);
		registerPage.inputToPasswordTextbox("123456");
		registerPage.inputToConfirmPasswordTextbox("123456");
		System.out.println("Email registered is " + existingEmail);
		
		System.out.println("Pre-Condition - Step 03: Click Register button ");
		registerPage.clickToRegisterButton();

		System.out.println("Pre-Condition - Step 04: Verified success message displayed ");
		Assert.assertEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");

		sleepInSecond(2);
		
		System.out.println("Pre-Condition - Step 05: Click to Logout link");
		registerPage.clickToLogoutLink();

		// Click logout th?? bussiness n?? s??? quay v??? trang Home Page
		//homePage = new HomePageObject(driver);

	}

	@Test
	public void Login_01_Empty_Data() {
		homePage.openLoginPage();
		
		// T??? trang Home - Click Login link -> Qa trang Login
		loginPage = new UserLoginPageObject(driver);
		loginPage.clickToLoginButton();
		
		Assert.assertEquals(loginPage.getErrorMessageAtEmailTextbox(), "Please enter your email");

	}

	@Test
	public void Login_02_Invalid_Email() {
		homePage.openLoginPage();

		loginPage = new UserLoginPageObject(driver);
		loginPage.inputToEmailTexbox(invalidEmail);

     	loginPage.clickToLoginButton();

    	Assert.assertEquals(loginPage.getErrorMessageAtEmailTextbox(), "Wrong email");

	}

	@Test
	public void Login_03_Email_Not_Found() {
		homePage.openLoginPage();

		loginPage = new UserLoginPageObject(driver);

		loginPage.inputToEmailTexbox(notFoundEmail);
		
		loginPage.clickToLoginButton();

		Assert.assertEquals(loginPage.getUnsuccessfullErrorMessage(), "Login was unsuccessful. Please correct the errors and try again.\nNo customer account found");

	}

	@Test
	public void Login_04_Existing_Email_Empty_Password() {
		homePage.openLoginPage();

		loginPage = new UserLoginPageObject(driver);

		loginPage.inputToEmailTexbox(existingEmail);
		loginPage.inputToPasswordTexbox("");
		
		loginPage.clickToLoginButton();

		Assert.assertEquals(loginPage.getUnsuccessfullErrorMessage(), "Login was unsuccessful. Please correct the errors and try again.\nThe credentials provided are incorrect");

	}

	@Test
	public void Login_05_Existing_Email_Incorrect_Password() {
		homePage.openLoginPage();

		loginPage = new UserLoginPageObject(driver);

		loginPage.inputToEmailTexbox(existingEmail);
		loginPage.inputToPasswordTexbox("123457");
		
		loginPage.clickToLoginButton();

		Assert.assertEquals(loginPage.getUnsuccessfullErrorMessage(), "Login was unsuccessful. Please correct the errors and try again.\nThe credentials provided are incorrect");

	}

	@Test
	public void Login_06_Valid_Login() {
		homePage.openLoginPage();

		loginPage = new UserLoginPageObject(driver);

		loginPage.inputToEmailTexbox(existingEmail);
		loginPage.inputToPasswordTexbox("123456");
		
		loginPage.clickToLoginButton();
		
		//Login th??nh c??ng -> Home Page
		homePage = new UserHomePageObject(driver);
        Assert.assertTrue(homePage.isMyAccountLinkDisplay());		

	}
	

	@AfterClass
	public void afterClass() {
		// driver.quit();
	}

}
