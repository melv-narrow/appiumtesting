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

public class DragAndDropTest extends BaseTest {

    @Test
    @Description("This is to test the drag and drop gesture")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Melvin Mpolokeng")
    public void dragAndDropTest() throws MalformedURLException {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.accessibilityId("Drag and Drop")).click();
        WebElement source = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_1"));
        WebElement target = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_2"));
        dragAndDrop(source, target);
        String result = driver.findElement(By.id("io.appium.android.apis:id/drag_result_text")).getText();
        Assert.assertEquals(result, "Dropped!");
    }
}
