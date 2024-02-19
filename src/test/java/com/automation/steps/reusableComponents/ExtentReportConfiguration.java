package com.automation.steps.reusableComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportConfiguration {

    // Static ExtentReports object to hold the report configuration
    static ExtentReports extentReports;

    /**
     * Method to set up and configure ExtentReports.
     * @return Configured ExtentReports object.
     */
    public static ExtentReports extentReportSetUp(){

        // Initialize ExceptionHandler for handling exceptions
        ExceptionHandler handler = new ExceptionHandler();

        try {
            // Generate timestamp for report filename
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyy HH-mm-ss");
            Date date = new Date();
            String formattedDate = simpleDateFormat.format(date);

            // Define the path for the report file
            String reportPath = System.getProperty("user.dir") + "/reports/report-" + formattedDate + ".html";

            // Create ExtentSparkReporter for HTML report
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            // Initialize ExtentReports and attach ExtentSparkReporter
            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);

            // Retrieve project name and report name from properties file
            String sProjectName = PropertiesOperations.getValueByKey("projectName");
            String sReportName = PropertiesOperations.getValueByKey("reportName");

            // Configure ExtentSparkReporter settings
            sparkReporter.config().setDocumentTitle(sProjectName);
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setReportName(sReportName);

            // Set system information for ExtentReports
            try {
                extentReports.setSystemInfo("Executed on Browser", PropertiesOperations.getValueByKey("selectBrowser"));
                extentReports.setSystemInfo("Executed on Environment", PropertiesOperations.getValueByKey("browserUrl"));
            } catch (IOException e) {
                // Handle IOException if properties cannot be retrieved
                handler.handleIOException("extentReportSetUp", e);
            }

            // Set system information for OS and User
            try {
                extentReports.setSystemInfo("Executed on OS", System.getProperty("os.name"));
                extentReports.setSystemInfo("Executed on User", System.getProperty("user.name"));
            } catch (SecurityException e) {
                // Handle SecurityException if system properties cannot be retrieved
                handler.handleSecurityException("extentReportSetUp", e);
            }

        } catch (IOException e) {
            // Handle IOException if an error occurs during report setup
            handler.handleIOException("extentReportSetUp", e);
        }

        // Return the configured ExtentReports object
        return extentReports;
    }
}
