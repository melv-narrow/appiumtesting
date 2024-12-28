import org.testng.Assert;
import org.testng.annotations.Test;

import pageobjects.CartPage;
import pageobjects.ProductPage;

public class eCommerceTests extends BaseTest {
    
    @Test
    public void FillForm() throws InterruptedException {
        Assert.assertTrue(formPage.toolbarTitleDisplayed(), "Toolbar title is not displayed");
        formPage.selectCountry("Brazil");
        formPage.fillForm("Melvin", "");
    }

    @Test
    public void FillFormWithoutName() throws InterruptedException {
        Assert.assertTrue(formPage.toolbarTitleDisplayed(), "Toolbar title is not displayed");
        formPage.selectCountry("Argentina");
        formPage.fillFormWithoutName("female");
        String toastText = formPage.getToastMessage();
        Assert.assertEquals(toastText, "Please enter your name");
    }

    @Test
    public void ScrollToAddProductToCart() throws InterruptedException {
        Assert.assertTrue(formPage.toolbarTitleDisplayed(), "Toolbar title is not displayed");
        formPage.selectCountry("Brazil");
        ProductPage productPage = formPage.fillForm("Melvin", "");
        
        productPage.scrollToAddProductToCart("Jordan 6 Rings");
        productPage.addProductToCart("Jordan 6 Rings");
        CartPage cartPage = productPage.goToCart();

        Assert.assertEquals(cartPage.getToolbarTitle(), "Cart", "Cart title verification failed");
        String productName = cartPage.getProductsInCartByIndex(0);
        Assert.assertEquals(productName, "Jordan 6 Rings");
    }

    @Test
    public void SumOfProductsInCart() throws InterruptedException {
        Assert.assertTrue(formPage.toolbarTitleDisplayed(), "Toolbar title is not displayed");
        formPage.selectCountry("Australia");

        ProductPage productPage = formPage.fillForm("Melvin", "female");
        productPage.addProductsToCart("Air Jordan 9 Retro", "Jordan 6 Rings");

        CartPage cartPage = productPage.goToCart();
        Assert.assertEquals(cartPage.getToolbarTitle(), "Cart", "Cart title verification failed");
        Assert.assertEquals(cartPage.getProductsInCartByIndex(0), "Air Jordan 9 Retro");
        Assert.assertEquals(cartPage.getProductsInCartByIndex(1), "Jordan 6 Rings");
        Assert.assertEquals(cartPage.getPurchaseAmount(), cartPage.getProductPrices(), "Purchase amounts do not match");
        cartPage.goToTermsAndConditions();
        Assert.assertEquals(cartPage.getAlertTitle(), "Terms Of Conditions");
        cartPage.closeAlert();
        cartPage.acceptTermsAndConditions();
        cartPage.goToCheckout();

        Thread.sleep(6000);
    }

    @Test
    public void PurchaseProductHybrid() throws InterruptedException {
        Assert.assertTrue(formPage.toolbarTitleDisplayed(), "Toolbar title is not displayed");
        formPage.selectCountry("Austria");

        ProductPage productPage = formPage.fillForm("Melvin", "female");
        productPage.addProductsToCart("Air Jordan 9 Retro", "Jordan 6 Rings");

        CartPage cartPage = productPage.goToCart();
        Assert.assertEquals(cartPage.getToolbarTitle(), "Cart", "Cart title verification failed");
        Assert.assertEquals(cartPage.getProductsInCartByIndex(0), "Air Jordan 9 Retro");
        Assert.assertEquals(cartPage.getProductsInCartByIndex(1), "Jordan 6 Rings");
        Assert.assertEquals(cartPage.getPurchaseAmount(), cartPage.getProductPrices(), "Purchase amounts do not match");
        cartPage.goToTermsAndConditions();
        Assert.assertEquals(cartPage.getAlertTitle(), "Terms Of Conditions");
        cartPage.closeAlert();
        cartPage.acceptTermsAndConditions();
        cartPage.goToCheckout();

        Thread.sleep(6000);
        // driver.context("WEBVIEW_com.androidsample.generalstore");
        // WebElement searchField = driver.findElement(By.name("q"));
        // Assert.assertTrue(searchField.isDisplayed());
        // driver.context("NATIVE_APP");
    }
}