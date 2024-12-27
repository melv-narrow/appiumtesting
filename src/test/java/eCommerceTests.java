import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class eCommerceTests extends BaseTest {
    
    @Test
    public void FillForm() throws InterruptedException {
        WebElement toolbarTitle = manageWait().until(ExpectedConditions.presenceOfElementLocated(By.id("com.androidsample.generalstore:id/toolbar_title")));
        Assert.assertTrue(toolbarTitle.isDisplayed());
        driver.findElement(By.id("com.androidsample.generalstore:id/spinnerCountry")).click();
        scrollToText("South Africa");
        driver.findElement(By.xpath("//android.widget.TextView[@text='South Africa']")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Melvin");
        driver.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
    }

    @Test
    public void FillFormWithoutName() throws InterruptedException {
        WebElement toolbarTitle = manageWait().until(ExpectedConditions.presenceOfElementLocated(By.id("com.androidsample.generalstore:id/toolbar_title")));
        Assert.assertTrue(toolbarTitle.isDisplayed());
        driver.findElement(By.id("com.androidsample.generalstore:id/spinnerCountry")).click();
        scrollToText("South Africa");
        driver.findElement(By.xpath("//android.widget.TextView[@text='South Africa']")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        String toastText = driver.findElement(By.xpath("//android.widget.Toast[1]")).getText();
        Assert.assertEquals(toastText, "Please enter your name");
    }

    @Test
    public void ScrollToAddProductToCart() throws InterruptedException {
        WebElement toolbarTitle = manageWait().until(ExpectedConditions.presenceOfElementLocated(By.id("com.androidsample.generalstore:id/toolbar_title")));
        Assert.assertTrue(toolbarTitle.isDisplayed());
        
        // Select country
        driver.findElement(By.id("com.androidsample.generalstore:id/spinnerCountry")).click();
        scrollToText("Brazil");
        driver.findElement(By.xpath("//android.widget.TextView[@text='Brazil']")).click();
        
        // Fill form
        driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Melvin");
        driver.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        
        // Wait for products to load and scroll to target
        manageWait().until(ExpectedConditions.presenceOfElementLocated(By.id("com.androidsample.generalstore:id/productName")));
        scrollToText("Jordan 6 Rings");
        
        // Find the product and click Add to Cart
        int productCount = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).size();
        for (int i = 0; i < productCount; i++) {
            String productName = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).get(i).getText();
            if (productName.equals("Jordan 6 Rings")) {
                driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart")).get(i).click();
                break;
            }
        }
        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        String toolbarTitle2 = manageWait().until(ExpectedConditions.presenceOfElementLocated(By.id("com.androidsample.generalstore:id/toolbar_title"))).getText();
        Assert.assertEquals(toolbarTitle2, "Cart");
        String productName = driver.findElement(By.id("com.androidsample.generalstore:id/productName")).getText();
        Assert.assertEquals(productName, "Jordan 6 Rings");
    }

    @Test
    public void SumOfProductsInCart() throws InterruptedException {
        WebElement toolbarTitle = manageWait().until(ExpectedConditions.presenceOfElementLocated(By.id("com.androidsample.generalstore:id/toolbar_title")));
        Assert.assertTrue(toolbarTitle.isDisplayed());
        
        // Select country
        driver.findElement(By.id("com.androidsample.generalstore:id/spinnerCountry")).click();
        scrollToText("Brazil");
        driver.findElement(By.xpath("//android.widget.TextView[@text='Brazil']")).click();
        
        // Fill form
        driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Melvin");
        driver.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        
        // List of products to add
        String[] productsToAdd = {"Air Jordan 9 Retro", "Jordan 6 Rings"};
        
        // Add each product to cart
        for (String productToAdd : productsToAdd) {
            // Wait for products to load and scroll to target
            manageWait().until(ExpectedConditions.presenceOfElementLocated(By.id("com.androidsample.generalstore:id/productName")));
            scrollToText(productToAdd);
            
            // Find the product and click Add to Cart
            int productCount = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).size();
            for (int i = 0; i < productCount; i++) {
                String productName = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).get(i).getText();
                if (productName.equals(productToAdd)) {
                    driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart")).get(i).click();
                    break;
                }
            }
        }
        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        
        // Verify cart title
        String cartTitle = manageWait().until(ExpectedConditions.presenceOfElementLocated(By.id("com.androidsample.generalstore:id/toolbar_title"))).getText();
        Assert.assertEquals(cartTitle, "Cart");
        
        // Get products in cart
        List<WebElement> cartProducts = driver.findElements(By.id("com.androidsample.generalstore:id/productName"));
        Assert.assertEquals(cartProducts.get(0).getText(), "Air Jordan 9 Retro");
        Assert.assertEquals(cartProducts.get(1).getText(), "Jordan 6 Rings");

        List<WebElement> productPrices = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice"));
        int productCount = productPrices.size();
        double purchaseAmount = 0;
        for (int i = 0; i < productCount; i++) {
            String productPrice = productPrices.get(i).getText();
            Double price = Double.parseDouble(productPrice.substring(1));
            purchaseAmount = purchaseAmount + price;
        }
        String totalAmount = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
        Double totalPurchaseAmount = Double.parseDouble(totalAmount.substring(1));
        Assert.assertEquals(purchaseAmount, totalPurchaseAmount);
        WebElement terms = driver.findElement(By.id("com.androidsample.generalstore:id/termsButton"));
        longClickGesture(terms);
        String alertTitle = driver.findElement(By.id("com.androidsample.generalstore:id/alertTitle")).getText();
        Assert.assertEquals(alertTitle, "Terms Of Conditions");
        driver.findElement(By.id("android:id/button1")).click();
        driver.findElement(AppiumBy.className("android.widget.CheckBox")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
    }

    @Test
    public void PurchaseProductHybrid() throws InterruptedException {
        WebElement toolbarTitle = manageWait().until(ExpectedConditions.presenceOfElementLocated(By.id("com.androidsample.generalstore:id/toolbar_title")));
        Assert.assertTrue(toolbarTitle.isDisplayed());
        
        // Select country
        driver.findElement(By.id("com.androidsample.generalstore:id/spinnerCountry")).click();
        scrollToText("Brazil");
        driver.findElement(By.xpath("//android.widget.TextView[@text='Brazil']")).click();
        
        // Fill form
        driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Melvin");
        driver.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        
        // List of products to add
        String[] productsToAdd = {"Air Jordan 9 Retro", "Jordan 6 Rings"};
        
        // Add each product to cart
        for (String productToAdd : productsToAdd) {
            // Wait for products to load and scroll to target
            manageWait().until(ExpectedConditions.presenceOfElementLocated(By.id("com.androidsample.generalstore:id/productName")));
            scrollToText(productToAdd);
            
            // Find the product and click Add to Cart
            int productCount = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).size();
            for (int i = 0; i < productCount; i++) {
                String productName = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).get(i).getText();
                if (productName.equals(productToAdd)) {
                    driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart")).get(i).click();
                    break;
                }
            }
        }
        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        
        // Verify cart title
        String cartTitle = manageWait().until(ExpectedConditions.presenceOfElementLocated(By.id("com.androidsample.generalstore:id/toolbar_title"))).getText();
        Assert.assertEquals(cartTitle, "Cart");
        
        // Get products in cart
        List<WebElement> cartProducts = driver.findElements(By.id("com.androidsample.generalstore:id/productName"));
        Assert.assertEquals(cartProducts.get(0).getText(), "Air Jordan 9 Retro");
        Assert.assertEquals(cartProducts.get(1).getText(), "Jordan 6 Rings");

        List<WebElement> productPrices = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice"));
        int productCount = productPrices.size();
        double purchaseAmount = 0;
        for (int i = 0; i < productCount; i++) {
            String productPrice = productPrices.get(i).getText();
            Double price = Double.parseDouble(productPrice.substring(1));
            purchaseAmount = purchaseAmount + price;
        }
        String totalAmount = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
        Double totalPurchaseAmount = Double.parseDouble(totalAmount.substring(1));
        Assert.assertEquals(purchaseAmount, totalPurchaseAmount);
        WebElement terms = driver.findElement(By.id("com.androidsample.generalstore:id/termsButton"));
        longClickGesture(terms);
        String alertTitle = driver.findElement(By.id("com.androidsample.generalstore:id/alertTitle")).getText();
        Assert.assertEquals(alertTitle, "Terms Of Conditions");
        driver.findElement(By.id("android:id/button1")).click();
        driver.findElement(AppiumBy.className("android.widget.CheckBox")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
        Thread.sleep(6000);
        driver.context("WEBVIEW_com.androidsample.generalstore");
        WebElement searchField = driver.findElement(By.name("q"));
        Assert.assertTrue(searchField.isDisplayed());
        driver.context("NATIVE_APP");
    }
}
