package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.AppiumDriver;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

public class Listeners extends AppiumUtils implements ITestListener {
    
    ExtentReports extent = ExtentReportNG.getReporterObject();
    ExtentTest test;
    AppiumDriver driver;

    @Override
    public void onTestStart(ITestResult result) {
        // Called when any test method starts
        test = extent.createTest(result.getMethod().getMethodName());
        
        // Get driver reference at test start
        try {
            driver = (AppiumDriver) result.getTestClass().getRealClass().getField("driver")
                    .get(result.getInstance());
        } catch (Exception e) {
            // Ignore any errors here, we'll try again in onTestFailure if needed
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Called when any test method succeeds
        test.log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Called when any test method fails
        test.fail(result.getThrowable());

        try {
            // If we don't have a driver reference, try to get it again
            if (driver == null) {
                driver = (AppiumDriver) result.getTestClass().getRealClass().getField("driver")
                        .get(result.getInstance());
            }
            
            if (driver != null) {
                // Take screenshot directly using driver
                File source = driver.getScreenshotAs(OutputType.FILE);
                String destinationFile = System.getProperty("user.dir") + "//reports//" + 
                    result.getMethod().getMethodName() + ".png";
                
                // Ensure reports directory exists
                new File(System.getProperty("user.dir") + "//reports").mkdirs();
                
                // Copy screenshot to destination
                FileUtils.copyFile(source, new File(destinationFile));
                
                // Add to report
                test.addScreenCaptureFromPath(destinationFile, result.getMethod().getMethodName());
                
                // Quit the driver after taking screenshot
                driver.quit();
                driver = null;
            } else {
                test.fail("Driver was null - unable to capture screenshot");
            }
        } catch (Exception e) {
            test.fail("Failed to capture screenshot: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Called when any test method is skipped
    }

    @Override
    public void onStart(ITestContext context) {
        // Called when any test starts
    }

    @Override
    public void onFinish(ITestContext context) {
        // Called when all tests are done
        extent.flush();
    }
}
