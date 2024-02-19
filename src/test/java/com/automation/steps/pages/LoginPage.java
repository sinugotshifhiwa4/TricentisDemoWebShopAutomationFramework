package com.automation.steps.pages;

import com.automation.steps.reusableComponents.ExceptionHandler;
import com.automation.steps.utils.DriverFactory;
import com.automation.steps.utils.ExtentFactory;
import com.automation.steps.utils.TestBase;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.Duration;

public class LoginPage extends TestBase {


    ExceptionHandler handler = new ExceptionHandler();

    // Locators for elements on the login page
    By navigateToLoginLink = By.xpath("//a[.='Log in']");
    By emailTextBoxField = By.xpath("//input[@id='Email']");
    By passwordTextBoxField = By.xpath("//input[@id='Password']");
    By loginBtn = By.xpath("//input[@class='button-1 login-button']");
    By userProfileByEmail = By.xpath("//a[@class='account' and contains(.,'@gmail.com')]");
    By errorMessageOnLogin = By.xpath("//div[@class='validation-summary-errors']");
    By welcomeMessageOnLogin = By.xpath("//h1[.='Welcome, Please Sign In!']");



    /**
     * Navigates to the login page.
     * This method clicks on the login link, waits for the email text box field to be visible, checks its presence,
     * captures a screenshot of the login page, and handles any exceptions that occur during the process.
     */
    public void navigateToLoginPage() {
        try {
            // Click on the link/button to navigate to the login page
            Thread.sleep(2000);
            clickObjects(DriverFactory.getInstance().getDriver().findElement(navigateToLoginLink), "NavigateToLoginLink");

            // Wait for the email text box field to be visible
            explicitlyWaitObjects(DriverFactory.getInstance().getDriver().findElement(welcomeMessageOnLogin), Duration.ofSeconds(10));

            // Check if the email text box field is present on the login page
            isElementPresentObjects(DriverFactory.getInstance().getDriver().findElement(welcomeMessageOnLogin), "LoginWelcomeMessage");

            // Introduce a delay to ensure the page elements are loaded before capturing the screenshot
            Thread.sleep(2000);

            // Capture a screenshot of the login page
            captureScreenshot("LoginWelcomeMessage");

        } catch (Exception e) {
            // Handle any exceptions that occur during the navigation process
            handler.handleException("navigateToLoginPage", e);
        }
    }

    /**
     * Signs in with provided email and password.
     * This method enters the email and password, clicks the login button,
     * validates the login status, captures a screenshot, and handles any exceptions.
     * @param email The email to sign in with.
     * @param password The password to sign in with.
     */
    public void signIn(String email, String password){

        try{
            clearObjects(DriverFactory.getInstance().getDriver().findElement(emailTextBoxField), "LoginEmailField");
            sendKeysObjects(DriverFactory.getInstance().getDriver().findElement(emailTextBoxField), "LoginEmailField", email);

            clearObjects(DriverFactory.getInstance().getDriver().findElement(passwordTextBoxField), "LoginPasswordField");
            sendKeysObjects(DriverFactory.getInstance().getDriver().findElement(passwordTextBoxField), "LoginPasswordField", password);

            clickObjects(DriverFactory.getInstance().getDriver().findElement(loginBtn), "LoginButton");

            // Check if login is successful
            if (isLoginSuccessful()) {
                validateLoginStatus(DriverFactory.getInstance().getDriver().findElement(userProfileByEmail), "UserProfileByEmail");
                Thread.sleep(2000);
                captureScreenshot("userProfileByEmail");
            } else {
                // Login failed
                ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Invalid credentials provided. Login failed.");
                // Handle the failure scenario, e.g., throw an exception or log an error
                Assert.fail("Invalid credentials provided. Login failed.");
            }

        } catch (Exception e) {
            handler.handleException("signIn", e);
        }
    }

    /**
     * Checks if the login is successful.
     * @return true if login is successful, false otherwise.
     */
    private boolean isLoginSuccessful() {
        // Assuming there's some element on the page that indicates successful login,
        // check if that element is present after clicking the login button.
        try {
            WebElement userProfileElement = DriverFactory.getInstance().getDriver().findElement(userProfileByEmail);
            return userProfileElement.isDisplayed();
        } catch (NoSuchElementException e) {
            return false; // User profile element not found, login unsuccessful
        }
    }
}

