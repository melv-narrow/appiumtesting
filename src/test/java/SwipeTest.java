import java.net.MalformedURLException;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumBy;

public class SwipeTest extends BaseTest {

    @Test
    @Description("This is to test the swipe gesture")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Melvin Mpolokeng")
    public void swipeGestureTest() throws MalformedURLException {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.accessibilityId("Gallery")).click();
        driver.findElement(AppiumBy.accessibilityId("1. Photos")).click();
        WebElement firstImage = driver.findElement(By.xpath("//android.widget.ImageView[1]"));
        String isFocusable = firstImage.getAttribute("focusable");
        Assert.assertEquals(isFocusable, "true");

        //Swipe gesture
        swipeGesture(firstImage, "left");
        isFocusable = firstImage.getAttribute("focusable");
        Assert.assertEquals(isFocusable, "false");
    }
}
