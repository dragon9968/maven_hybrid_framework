package pageObjects.nopCommerce.user;

import org.openqa.selenium.WebDriver;
import commons.BasePage;
import commons.PageGeneratorManager;
import io.qameta.allure.Step;
import pageUIs.nopCommerce.user.CustomerInfoPageUI;
import pageUIs.nopCommerce.user.HomePageUI;
import pageUIs.nopCommerce.user.LoginPageUI;

public class UserLoginPageObject extends BasePage {
	private WebDriver driver;

	public UserLoginPageObject(WebDriver driver) {
		this.driver = driver;
	}
	
	
//Có return class object để apply cho Page_Generator 2,3
	@Step("Click to Login button")
	public  UserHomePageObject clickToLoginButton() {
		waitForElementClickable(driver, LoginPageUI.LOGIN_BUTTON);
		clickToElement(driver, LoginPageUI.LOGIN_BUTTON);
		return PageGeneratorManager.getHomePage(driver);
	}

	@Step("Input to Email textbox with value is {0}")
	public void inputToEmailTexbox(String Email) {
		waitForElementVisible(driver, LoginPageUI.EMAIL_TEXTBOX);
		sendkeyToElement(driver, LoginPageUI.EMAIL_TEXTBOX, Email);
	}

	@Step("Input to Password textbox with value is {0}")
	public void inputToPasswordTexbox(String Password) {
		waitForElementVisible(driver, LoginPageUI.PASSWORD_TEXTBOX);
		sendkeyToElement(driver, LoginPageUI.PASSWORD_TEXTBOX, Password);
	}

	
	public String getUnsuccessfullErrorMessage() {
		waitForElementVisible(driver, LoginPageUI.LOGIN_UNSUCCESSFULL_ERROR_MESSAGE);
		return getElementText(driver, LoginPageUI.LOGIN_UNSUCCESSFULL_ERROR_MESSAGE);
	}

	public String getErrorMessageAtEmailTextbox() {
		waitForElementVisible(driver, LoginPageUI.EMAIL_ERROR_MESSAGE);
		return getElementText(driver, LoginPageUI.EMAIL_ERROR_MESSAGE);
	}

	public UserHomePageObject loginAsUser(String emailAddress , String password) {
		inputToEmailTexbox(emailAddress);
		inputToPasswordTexbox(password);
		clickToLoginButton();
		return PageGeneratorManager.getHomePage(driver);
	}
}
