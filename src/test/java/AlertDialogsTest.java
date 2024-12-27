import java.net.MalformedURLException;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class AlertDialogsTest extends BaseTest {

    @Test
    @Description("This is to test long press")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Melvin Mpolokeng")
    public void alertDialogsTest() throws MalformedURLException, InterruptedException {
        driver.findElement(AppiumBy.accessibilityId("App")).click();
        driver.findElement(AppiumBy.accessibilityId("Alert Dialogs")).click();

        driver.findElement(AppiumBy.accessibilityId("OK Cancel dialog with a message")).click();
        Thread.sleep(2000);
        String alertTitle = driver.findElement(By.id("android:id/alertTitle")).getText();
        Assert.assertTrue(true, alertTitle);
        driver.findElement(By.id("android:id/button1")).click();
        
        driver.findElement(AppiumBy.accessibilityId("OK Cancel dialog with a long message")).click();
        Thread.sleep(2000);
        String headerTitle = driver.findElement(By.id("android:id/alertTitle")).getText();
        Assert.assertEquals(headerTitle, "Header title");
        driver.findElement(By.id("android:id/button1")).click();

        driver.findElement(AppiumBy.accessibilityId("OK Cancel dialog with ultra long message")).click();
        Thread.sleep(2000);
        String headerTitle2 = driver.findElement(By.id("android:id/alertTitle")).getText();
        Assert.assertEquals(headerTitle2, "Header title");
        driver.findElement(By.id("android:id/button1")).click();

        driver.findElement(AppiumBy.accessibilityId("List dialog")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='Command one']")).click();
        String toastText = driver.findElement(By.id("android:id/message")).getText();
        Assert.assertEquals(toastText, "You selected: 0 , Command one");
        WebElement screen = driver.findElement(By.id("android:id/textSpacerNoTitle"));
        clickGesture(screen, 600, 1000);
    }
}
