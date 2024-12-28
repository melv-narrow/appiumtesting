package utils;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class AndroidActions {

    private AndroidDriver driver;

    public AndroidActions(AndroidDriver driver) {
        this.driver = driver;
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
}
