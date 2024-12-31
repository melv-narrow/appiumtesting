import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
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

import java.net.MalformedURLException;

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

    @BeforeClass
    public void startAppiumService() {
        // Start Appium server programmatically
        service = new AppiumServiceBuilder().withAppiumJS(new File("C:\\Users\\melvi\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
            .withIPAddress(prop.getProperty("ipAddress"))
            .usingPort(Integer.parseInt(prop.getProperty("port")))
            .build();
        service.start();
    }

    @BeforeMethod
    public void configureAppium() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(prop.getProperty("deviceName"));
        options.setChromedriverExecutable("C:\\Users\\melvi\\Coding Projects\\udemy-appium\\chromedriver_win32\\chromedriver.exe");
        options.setApp("C:\\Users\\melvi\\Coding Projects\\udemy-appium\\udemy-appium\\src\\main\\resources\\General-Store.apk");
        
        driver = new AndroidDriver(new URL("http://" + prop.getProperty("ipAddress") + ":" + prop.getProperty("port")), options);
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

    @AfterMethod
    public void tearDown() throws InterruptedException {
        if (driver != null) {
            Thread.sleep(5000);
            driver.quit();
        }
    }

    @AfterClass
    public void stopAppiumService() throws InterruptedException {
        if (service != null) {
            Thread.sleep(5000);
            service.stop();
        }
    }
}
