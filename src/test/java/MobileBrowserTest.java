import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MobileBrowserTest extends BrowserBaseTest {
    @Test
    public void searchGoogleOnMobile() {
        driver.get("https://www.google.com");
        WebElement searchField = driver.findElement(By.name("q"));
        Assert.assertTrue(searchField.isDisplayed());
        searchField.sendKeys("Appium");
        searchField.sendKeys(Keys.ENTER);
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 600)", "");
        driver.findElement(By.cssSelector("a[href='https://github.com/appium/appium']")).click();
        String url = driver.getCurrentUrl();
        Assert.assertEquals(url, "https://github.com/appium/appium");
    }
}
