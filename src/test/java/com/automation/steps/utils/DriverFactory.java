package com.automation.steps.utils;

import com.automation.steps.reusableComponents.ExceptionHandler;
import org.openqa.selenium.WebDriver;

public class DriverFactory {

    // Singleton instance of DriverFactory
    private static final DriverFactory instance = new DriverFactory();

    // Private constructor to prevent instantiation from outside the class
    private DriverFactory(){}

    /**
     * Method to retrieve the singleton instance of DriverFactory.
     * @return The singleton instance of DriverFactory.
     */
    public static DriverFactory getInstance(){
        return instance;
    }

    // ThreadLocal variable to store WebDriver instances per thread
    ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Method to set the WebDriver instance for the current thread.
     * @param driverTest The WebDriver instance to be set.
     */
    public void setDriver(WebDriver driverTest){
        driver.set(driverTest);
    }

    /**
     * Method to retrieve the WebDriver instance for the current thread.
     * @return The WebDriver instance for the current thread.
     */
    public WebDriver getDriver(){
        return driver.get();
    }

    /**
     * Method to close the browser and clean up resources.
     */
    public void closeBrowser(){
        // Initialize ExceptionHandler for handling exceptions
        ExceptionHandler handler = new ExceptionHandler();

        // Retrieve the WebDriver instance for the current thread
        WebDriver driverTest = driver.get();

        try{
            // Add a short delay before closing the browser
            Thread.sleep(4000);
            // Close the browser window
            driverTest.close();
            // Quit the WebDriver session
            driverTest.quit();
            // Remove the WebDriver instance from ThreadLocal
            driver.remove();
        } catch (Exception e) {
            // If an exception occurs during browser closure, handle it
            handler.handleException("closeBrowser", e);
        }
    }

}
