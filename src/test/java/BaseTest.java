import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import pageobjects.FormPage;

public class BaseTest {
    public AndroidDriver driver;
    public AppiumDriverLocalService service;
    public FormPage formPage;
    public Properties prop;

    public BaseTest() {
        try {
            prop = new Properties();
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + 
                "/src/test/java/resources/data.properties");
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeClass(alwaysRun = true)
    public void startAppiumService() {
        // Start Appium server programmatically
        service = new AppiumServiceBuilder()
            .withAppiumJS(new File("C:\\Users\\melvi\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
            .withIPAddress(prop.getProperty("ipAddress"))
            .usingPort(Integer.parseInt(prop.getProperty("port")))
            .withArgument(() -> "--use-plugins", "execute-driver")
            .withArgument(() -> "--allow-insecure", "chromedriver_autodownload")
            .build();
        service.start();
    }

    @BeforeMethod(alwaysRun = true)
    public void configureAppium() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        
        // BrowserStack configuration
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("userName", "melvinmpolokeng_D3IbRG");
        browserstackOptions.put("accessKey", "MdvBsqYu5sRHDg3fp3pW");
        browserstackOptions.put("projectName", "Appium Android Project");
        browserstackOptions.put("buildName", "BrowserStack Build " + System.currentTimeMillis());
        browserstackOptions.put("sessionName", "Appium Test");
        browserstackOptions.put("local", "false");
        options.setCapability("bstack:options", browserstackOptions);
        
        // App and device configuration
        options.setCapability("platformName", "android");
        options.setCapability("platformVersion", "12.0");
        options.setCapability("deviceName", "Samsung Galaxy S22 Ultra");
        options.setCapability("app", "bs://2b41182cd53336130b04be1cd4f90cb8b4b63642");
        
        String username = "melvinmpolokeng_D3IbRG";
        String accessKey = "MdvBsqYu5sRHDg3fp3pW";
        String browserStackUrl = "https://" + username + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";
        
        driver = new AndroidDriver(new URL(browserStackUrl), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        formPage = new FormPage(driver);
    }

    public void longClickGesture(WebElement element) {
        ((JavascriptExecutor)driver).executeScript("mobile: longClickGesture", ImmutableMap.of(
            "elementId", ((RemoteWebElement) element).getId(),
            "duration", 2000
        ));
    }

    public void swipeGesture(WebElement element, String direction) {
        ((JavascriptExecutor)driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
            "elementId", ((RemoteWebElement) element).getId(),
            "direction", direction,
            "percent", 0.25
        ));
    }

    public void dragAndDrop(WebElement source, WebElement target) {
        Point sourceLocation = source.getLocation();
        Point targetLocation = target.getLocation();
        
        ((JavascriptExecutor)driver).executeScript("mobile: dragGesture", ImmutableMap.of(
            "elementId", ((RemoteWebElement) source).getId(),
            "startX", sourceLocation.getX(),
            "startY", sourceLocation.getY(),
            "endX", targetLocation.getX(),
            "endY", targetLocation.getY()
        ));
    }

    public void clickGesture(WebElement element, int x, int y) {
        ((JavascriptExecutor)driver).executeScript("mobile: clickGesture", ImmutableMap.of(
            "elementId", ((RemoteWebElement) element).getId(),
            "x", x,
            "y", y
        ));
    }

    public void clickGesture(WebElement element) {
        clickGesture(element, 600, 1000);
    }

    public void startPreferenceActivity() {
        ((JavascriptExecutor)driver).executeScript("mobile: startActivity", ImmutableMap.of(
            "intent", "io.appium.android.apis/io.appium.android.apis.preference.PreferenceDependencies"
        ));
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

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterClass(alwaysRun = true)
    public void stopAppiumService() {
        if (service != null) {
            service.stop();
        }
    }
}
