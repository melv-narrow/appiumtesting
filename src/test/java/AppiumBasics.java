import java.net.MalformedURLException;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AppiumBasics extends BaseTest {

    @Test
    @Description("This is a test to configure wifi settings")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Melvin Mpolokeng")
    public void WifiSettingsTest() throws MalformedURLException {
        startPreferenceActivity();
        driver.findElement(By.id("android:id/checkbox")).click();        
        driver.findElement(By.xpath("(//android.widget.RelativeLayout)[2]")).click();
        String alertTitle = driver.findElement(By.id("android:id/alertTitle")).getText();
        Assert.assertEquals(alertTitle, "WiFi settings");
        driver.findElement(By.id("android:id/edit")).sendKeys("TestWifi");
        driver.findElement(By.id("android:id/button1")).click();
    }
}
