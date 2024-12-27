import java.net.MalformedURLException;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumBy;

public class LongPress extends BaseTest {

    @Test
    @Description("This is to test long press")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Melvin Mpolokeng")
    public void longPressTest() throws MalformedURLException {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.accessibilityId("Expandable Lists")).click();
        driver.findElement(AppiumBy.accessibilityId("1. Custom Adapter")).click();
        WebElement element = driver.findElement(By.xpath("//android.widget.TextView[@text='People Names']"));
        longClickGesture(element);
        String alertTitle = driver.findElement(By.xpath("//android.widget.TextView[@text='Sample menu']")).getText();
        Assert.assertEquals(alertTitle, "Sample menu");
        Assert.assertTrue(driver.findElement(By.xpath("//android.widget.TextView[@text='Sample menu']")).isDisplayed());
        driver.findElement(By.id("android:id/content")).click();
        String toastText = driver.findElement(By.xpath("//android.widget.Toast[@text='People Names: Group 0 clicked']")).getText();
        Assert.assertEquals(toastText, "People Names: Group 0 clicked");        
    }
}
