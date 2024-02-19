package com.automation.steps.utils;

import com.aventstack.extentreports.ExtentTest;

public class ExtentFactory {

    // Singleton instance of ExtentFactory
    private static ExtentFactory instance = new ExtentFactory();

    // Private constructor to prevent instantiation from outside the class
    private ExtentFactory(){}

    /**
     * Method to retrieve the singleton instance of ExtentFactory.
     * @return The singleton instance of ExtentFactory.
     */
    public static ExtentFactory getInstance(){
        return instance;
    }

    // ThreadLocal variable to store ExtentTest instances per thread
    ThreadLocal<ExtentTest> extent = new ThreadLocal<>();

    /**
     * Method to set the ExtentTest instance for the current thread.
     * @param extentParam The ExtentTest instance to be set.
     */
    public void setExtent(ExtentTest extentParam){
        extent.set(extentParam);
    }

    /**
     * Method to retrieve the ExtentTest instance for the current thread.
     * @return The ExtentTest instance for the current thread.
     */
    public ExtentTest getExtent(){
        return extent.get();
    }

    /**
     * Method to remove the ExtentTest instance for the current thread.
     */
    public void removeExtentObject(){
        extent.remove();
    }
}
