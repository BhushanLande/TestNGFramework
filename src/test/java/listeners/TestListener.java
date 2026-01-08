package listeners;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import driver.DriverFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentManager;
import utils.ScreenshotUtil;

import java.io.IOException;

public class TestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getExtent();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        test.set(extent.createTest(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test Passed");
        // 🔴 TAKE SCREENSHOT & ATTACH TO REPORT
        String path = null;
        try {
            path = ScreenshotUtil.takeScreenshot(
                    DriverFactory.getDriver(),
                    result.getMethod().getMethodName()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        test.get().addScreenCaptureFromPath(path);
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    @Override
    public void onTestFailure(ITestResult result)  {
        test.get().fail(result.getThrowable());

        // 🔴 TAKE SCREENSHOT & ATTACH TO REPORT
        String path = null;
        try {
            path = ScreenshotUtil.takeScreenshot(
                    DriverFactory.getDriver(),
                    result.getMethod().getMethodName()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        test.get().addScreenCaptureFromPath(path);
    }
}
