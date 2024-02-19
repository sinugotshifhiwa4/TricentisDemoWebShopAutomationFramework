package com.automation.steps.reusableComponents;

import com.automation.steps.utils.DriverFactory;
import com.automation.steps.utils.ExtentFactory;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Base64;

public class WebActions {

    public void sendKeysObjects(WebElement element, String fieldName, String valueToBeSent){
        try{
            element.sendKeys(valueToBeSent);
            // Log success message in extent report
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Successfully entered value '" + valueToBeSent + "' in the field: " + fieldName);
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Failed to enter value in the field: " + fieldName + ". Exception: " + e.getMessage());
        }
    }

    public void clickObjects(WebElement element, String fieldName){
        try{
            element.click();
            // Log success message in extent report
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Successfully clicked on '" + fieldName + "'");
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Failed to click on '" + fieldName + "'. Exception: " + e.getMessage());
        }
    }

    public void clearObjects(WebElement element, String fieldName){
        try{
            element.clear();
            // Log success message in extent report
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Data cleared successfully for field: '" + fieldName + "'");
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Unable to clear data for field: " + fieldName + "'. Exception: " + e.getMessage());
        }
    }

    public void moveToElementObjects(WebElement element, String fieldName){
        try {
            JavascriptExecutor executor = (JavascriptExecutor) DriverFactory.getInstance().getDriver();
            executor.executeScript("arguments[0].scrollIntoView(true);", element);
            Actions actions = new Actions(DriverFactory.getInstance().getDriver());
            actions.moveToElement(element).build().perform();
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Mouse hovered successfully on: '" + fieldName + "'");
            Thread.sleep(1000);
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Unable to hover mouse on field: " + fieldName + "'. Exception: " + e.getMessage());
        }
    }

    public boolean isElementPresentObjects(WebElement element, String fieldName){

        boolean flag = false;

        try{
            // Log success message in extent report
            flag = element.isDisplayed();
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Presence of field '" + fieldName + "' is: " + flag);
            return flag;

        } catch (NoSuchElementException e) {
            // Log if the element is not found
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element '" + fieldName + "' not found. Exception: " + e.getMessage());
        } catch (Exception e) {
            // Log any other exceptions
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Checking for presence of field: " + fieldName + "'. Exception: " + e.getMessage());
        }

        return flag;
    }

    public void validateLoginStatus(WebElement loginElement, String fieldName) {
        try {
            if (loginElement.isDisplayed()) {
                // Logged in
                ExtentFactory.getInstance().getExtent().log(Status.PASS, "User is logged in. Element '" + fieldName + "' is present.");
            } else {
                // Not logged in
                ExtentFactory.getInstance().getExtent().log(Status.FAIL, "User is not logged in. Element '" + fieldName + "' not found.");
            }
        } catch (Exception e) {
            // Error occurred while checking login status
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Error occurred while checking login status for field: " + fieldName + "'. Exception: " + e.getMessage());
        }
    }
    public void selectDropDownByVisibleTextObjects(WebElement element, String fieldName, String ddVisibleText) throws Throwable{
        try{
            Select select = new Select(element);
            select.selectByVisibleText(ddVisibleText);
            // Log success message in extent report
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Dropdown value selected by visible text for field: '" + fieldName + "'");
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Dropdown value not selected for field: " + fieldName + "'. Exception: " + e.getMessage());
        }
    }

    public void selectDropDownByValueObjects(WebElement element, String fieldName, String ddVisibleText) throws Throwable{
        try{
            Select select = new Select(element);
            select.selectByValue(ddVisibleText);
            // Log success message in extent report
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Dropdown value selected by value text for field: '" + fieldName + "'");
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Dropdown value not selected for field: " + fieldName + "'. Exception: " + e.getMessage());
        }
    }

    public void assertEqualsStringObjects(String expectedValue, String actualValue, String locatorName) throws Throwable{
        try{
            // Log success message in extent report
            Assert.assertEquals(expectedValue, actualValue);
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "String assertion is successful for field: '" + locatorName + "'");
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "String assertion failed for field: " + locatorName + "'. Exception: " + e.getMessage());
        }
    }

    public String getTextObjects(WebElement element, String fieldName){
        String text = "";
        try{
            // Log success message in extent report
            text = element.getText();
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Text retrieved for field: '" + fieldName + "' is: " + text);
            return text;
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Text not retrieved for field: " + fieldName + "'. Exception: " + e.getMessage());
            return text;
        }
    }

    public void explicitlyWaitObjects(WebElement element, Duration timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), timeoutInSeconds);
            wait.until(ExpectedConditions.visibilityOf(element));
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Element is visible after explicit wait");
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element is not visible after explicit wait. Exception: " + e.getMessage());
        }
    }

    public void waitForAllElementsToLoadObjects(Duration timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), timeoutInSeconds);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("*")));
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "All elements are loaded after explicit wait");
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "All elements are not loaded after explicit wait. Exception: " + e.getMessage());
        }
    }


    public void waitForElementToBeClickableObjects(WebElement element, Duration timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), timeoutInSeconds);
            wait.until(ExpectedConditions.elementToBeClickable(element));

            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Element is clickable after explicit wait");
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Element is not clickable after explicit wait. Exception: " + e.getMessage());
        }
    }

    public void captureScreenshot(String screenshotName) {
        try {

            File screenshot = ((TakesScreenshot) DriverFactory.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);

            byte [] fileScreenshot = Files.readAllBytes(screenshot.toPath());
            String stringEncoder = Base64.getEncoder().encodeToString(fileScreenshot);

            ExtentFactory.getInstance().getExtent().log(Status.INFO, "Screenshot captured: " + screenshotName, MediaEntityBuilder.createScreenCaptureFromBase64String(stringEncoder).build());
        } catch (IOException e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Failed to capture screenshot. Exception: " + e.getMessage());
        }
    }

    public void switchToFrame(WebElement frameElement) {
        try {
            DriverFactory.getInstance().getDriver().switchTo().frame(frameElement);
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Switched to frame successfully");
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Failed to switch to frame. Exception: " + e.getMessage());
        }
    }

    public void switchToDefaultContent() {
        try {
            DriverFactory.getInstance().getDriver().switchTo().defaultContent();
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Switched to default content successfully");
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Failed to switch to default content. Exception: " + e.getMessage());
        }
    }

    public void switchToWindow(String windowHandle) {
        try {
            DriverFactory.getInstance().getDriver().switchTo().window(windowHandle);
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Switched to window successfully");
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Failed to switch to window. Exception: " + e.getMessage());
        }
    }

    public void dragAndDrop(WebElement sourceElement, WebElement targetElement) {
        try {
            Actions actions = new Actions(DriverFactory.getInstance().getDriver());
            actions.dragAndDrop(sourceElement, targetElement).build().perform();
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Performed drag and drop successfully");
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Failed to perform drag and drop. Exception: " + e.getMessage());
        }
    }

    public void uploadFile(WebElement uploadElement, String filePath) {
        try {
            uploadElement.sendKeys(filePath);
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "File uploaded successfully");
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Failed to upload file. Exception: " + e.getMessage());
        }
    }

    public void scrollPageUp() {
        JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getInstance().getDriver();
        js.executeScript("window.scrollTo(0, 0)");
        ExtentFactory.getInstance().getExtent().log(Status.PASS, "Page scrolled up successfully");
    }

    public void scrollPageDown() {
        JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getInstance().getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        ExtentFactory.getInstance().getExtent().log(Status.PASS, "Page scrolled down successfully");
    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getInstance().getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        ExtentFactory.getInstance().getExtent().log(Status.PASS, "Page scrolled to element successfully");
    }

    public void acceptAlert() {
        try {
            Alert alert = DriverFactory.getInstance().getDriver().switchTo().alert();
            Thread.sleep(2000);
            alert.accept();
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Alert accepted successfully");
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Failed to accept alert. Exception: " + e.getMessage());
        }
    }

    public void dismissAlert() {
        try {
            Alert alert = DriverFactory.getInstance().getDriver().switchTo().alert();
            alert.dismiss();
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Alert dismissed successfully");
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Failed to dismiss alert. Exception: " + e.getMessage());
        }
    }

    public void getAlertText() {
        try {
            Alert alert = DriverFactory.getInstance().getDriver().switchTo().alert();
            String alertText = alert.getText();
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Alert text retrieved successfully: " + alertText);
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Failed to retrieve alert text. Exception: " + e.getMessage());
        }
    }

    public void navigateBack() {
        try {
            DriverFactory.getInstance().getDriver().navigate().back();
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Navigated back successfully");
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Failed to navigate back. Exception: " + e.getMessage());
        }
    }

    public void navigateForward() {
        try {
            DriverFactory.getInstance().getDriver().navigate().forward();
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Navigated forward successfully");
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Failed to navigate forward. Exception: " + e.getMessage());
        }
    }

    public void refreshPage() {
        try {
            DriverFactory.getInstance().getDriver().navigate().refresh();
            ExtentFactory.getInstance().getExtent().log(Status.PASS, "Page refreshed successfully");
        } catch (Exception e) {
            ExtentFactory.getInstance().getExtent().log(Status.FAIL, "Failed to refresh page. Exception: " + e.getMessage());
        }
    }
}
