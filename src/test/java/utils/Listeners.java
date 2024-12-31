package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Listeners implements ITestListener {
    
    ExtentReports extent = ExtentReportNG.getReporterObject();
    ExtentTest test;

    @Override
    public void onTestStart(ITestResult result) {
        // Called when any test method starts
        test = extent.createTest(result.getMethod().getMethodName());
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
