import java.io.File;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.net.MalformedURLException;

public class BrowserBaseTest {
    public AndroidDriver driver;
    public AppiumDriverLocalService service;

    @BeforeClass
    public void ConfigureAppium() throws MalformedURLException {
        // Start Appium server programmatically
        service = new AppiumServiceBuilder().withAppiumJS(new File("C:\\Users\\melvi\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
            .withIPAddress("127.0.0.1").usingPort(4723).build();
        service.start();

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Pixel 9 Pro API 34");
        options.setChromedriverExecutable("C:\\Users\\melvi\\Coding Projects\\udemy-appium\\chromedriver_win32\\chromedriver.exe");
        options.setCapability("browserName", "Chrome");
        
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
    }

    public WebDriverWait manageWait() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait;
    }

    public void scrollToText(String text) {
        // Simple scroll and search using Android UIAutomator
        String uiAutomatorString = String.format(
            "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"%s\"))", 
            text);
        driver.findElement(AppiumBy.androidUIAutomator(uiAutomatorString));
    }

    public void scrollToXPath(String xpath) {
        ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
            "left", 100, "top", 100, "width", 600, "height", 600,
            "direction", "down",
            "percent", 0.75
        ));
    }

    public void scrollToElement(WebElement element) {
        Point location = element.getLocation();
        ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
            "left", location.getX(), "top", location.getY(),
            "width", 600, "height", 600,
            "direction", "down",
            "percent", 0.75
        ));
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        service.stop();
    }
}
