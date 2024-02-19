package com.automation.steps.tests;

import com.automation.steps.pages.LoginPage;
import com.automation.steps.reusableComponents.ExcelConfiguration;
import com.automation.steps.reusableComponents.ExceptionHandler;
import com.automation.steps.reusableComponents.PropertiesOperations;
import com.automation.steps.reusableComponents.RerunFailedTestsConfiguration;
import com.automation.steps.utils.MyLogger;
import com.automation.steps.utils.TestBase;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class TestCases extends TestBase {

    LoginPage loginPage = new LoginPage();
    ExceptionHandler handler = new ExceptionHandler();
    ExcelConfiguration excelConfiguration = new ExcelConfiguration();


    /**
     * Test case to verify login with valid credentials.
     */
    @Test(retryAnalyzer = RerunFailedTestsConfiguration.class)
    public void loginWithValidCredentials(){

        try {
            // Read test data from Excel file
            String sExcelPath = PropertiesOperations.getValueByKey("excelPath");
            String sSheetName = PropertiesOperations.getValueByKey("sheetName");
            List<Map<String, String>> dataFromExcel = excelConfiguration.readExcelData(sExcelPath, sSheetName);

            // Iterate over test data and perform login
            for(Map<String, String> readData : dataFromExcel){
                loginPage.navigateToLoginPage();
                loginPage.signIn(
                        readData.get("ValidUsername"),
                        readData.get("ValidPassword")
                );
            }

            // Log test case result
            MyLogger.startTestCase(new Throwable().getStackTrace()[0].getMethodName());
            MyLogger.info("Login was successful");

        } catch (Exception e) {
            handler.handleException("loginWithValidCredentials", e);
        }
    }

    /**
     * Test case to verify login with invalid credentials.
     */
    //@Test(retryAnalyzer = RerunFailedTestsConfiguration.class)
    public void loginWithInvalidCredentials(){

        try {
            // Read test data from Excel file
            String sExcelPath = PropertiesOperations.getValueByKey("excelPath");
            String sSheetName = PropertiesOperations.getValueByKey("sheetName");
            List<Map<String, String>> dataFromExcel = excelConfiguration.readExcelData(sExcelPath, sSheetName);

            // Iterate over test data and perform login
            for(Map<String, String> readData : dataFromExcel){
                loginPage.navigateToLoginPage();
                loginPage.signIn(
                        readData.get("InvalidUsername"),
                        readData.get("InvalidPassword")
                );
            }

            // Log test case result
            MyLogger.startTestCase(new Throwable().getStackTrace()[0].getMethodName());
            MyLogger.info("Login was unsuccessful");

        } catch (Exception e) {
            handler.handleException("loginWithInvalidCredentials", e);
        }
    }
}
