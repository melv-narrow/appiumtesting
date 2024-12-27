import java.net.MalformedURLException;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumBy;

public class ScrollTest extends BaseTest {

    @Test
    @Description("This is to test long press")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Melvin Mpolokeng")
    public void scrollTest() throws MalformedURLException {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textContains(\"WebView\"));"));
        WebElement webView = driver.findElement(AppiumBy.accessibilityId("WebView"));
        Assert.assertTrue(webView.isDisplayed());        
    }
}
