import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.CartPage;
import pageobjects.ProductPage;

public class eCommerceTests extends BaseTest {
    
    @Test(dataProvider = "getFormData")
    public void FillForm(String name, String gender, String country) throws InterruptedException {
        Assert.assertTrue(formPage.toolbarTitleDisplayed(), "Toolbar title is not displayed");
        formPage.selectCountry(country);
        formPage.fillForm(name, gender);
    }

    @Test(dataProvider = "getFormWithoutNameData")
    public void FillFormWithoutName(String gender, String country) throws InterruptedException {
        Assert.assertTrue(formPage.toolbarTitleDisplayed(), "Toolbar title is not displayed");
        formPage.selectCountry(country);
        formPage.fillFormWithoutName(gender);
        String toastText = formPage.getToastMessage();
        Assert.assertEquals(toastText, "Please enter your name");
    }

    @Test(dataProvider = "getFormData")
    public void ScrollToAddProductToCart(String name, String gender, String country) throws InterruptedException {
        Assert.assertTrue(formPage.toolbarTitleDisplayed(), "Toolbar title is not displayed");
        formPage.selectCountry(country);
        ProductPage productPage = formPage.fillForm(name, gender);
        
        productPage.scrollToAddProductToCart("Jordan 6 Rings");
        productPage.addProductToCart("Jordan 6 Rings");
        CartPage cartPage = productPage.goToCart();

        Assert.assertEquals(cartPage.getToolbarTitle(), "Cart", "Cart title verification failed");
        String productName = cartPage.getProductsInCartByIndex(0);
        Assert.assertEquals(productName, "Jordan 6 Rings");
    }

    @Test(dataProvider = "getProductsData")
    public void SumOfProductsInCart(String name, String gender, String country, String product1, String product2) throws InterruptedException {
        Assert.assertTrue(formPage.toolbarTitleDisplayed(), "Toolbar title is not displayed");
        formPage.selectCountry(country);

        ProductPage productPage = formPage.fillForm(name, gender);
        productPage.addProductsToCart(product1, product2);

        CartPage cartPage = productPage.goToCart();
        Assert.assertEquals(cartPage.getToolbarTitle(), "Cart", "Cart title verification failed");
        Assert.assertEquals(cartPage.getProductsInCartByIndex(0), product1);
        Assert.assertEquals(cartPage.getProductsInCartByIndex(1), product2);
        Assert.assertEquals(cartPage.getPurchaseAmount(), cartPage.getProductPrices(), "Purchase amounts do not match");
        cartPage.goToTermsAndConditions();
        Assert.assertEquals(cartPage.getAlertTitle(), "Terms Of Conditions");
        cartPage.closeAlert();
        cartPage.acceptTermsAndConditions();
        cartPage.goToCheckout();
        Thread.sleep(6000);
    }

    @Test(dataProvider = "getFormData")
    public void PurchaseProductHybrid(String name, String gender, String country) throws InterruptedException {
        Assert.assertTrue(formPage.toolbarTitleDisplayed(), "Toolbar title is not displayed");
        formPage.selectCountry(country);

        ProductPage productPage = formPage.fillForm(name, gender);
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

    @DataProvider
    public Object[][] getFormData() {
        return new Object[][] {
            { "Melvin", "male", "Brazil" },
            { "Melvin", "female", "Argentina" },
            // { "Melvin", "", "Australia" },
            // { "Melvin", "female", "Austria" },
            // { "Melvin", "female", "Brazil" }
        };
    }

    @DataProvider
    public Object[][] getFormWithoutNameData() {
        return new Object[][] {
            { "female", "Argentina" },
            { "male", "Brazil" }
        };
    }

    @DataProvider
    public Object[][] getProductsData() {
        return new Object[][] {
            { "Melvin", "male", "Brazil", "Air Jordan 9 Retro", "Jordan 6 Rings" },
            { "Melvin", "female", "Argentina", "Jordan 6 Rings", "Air Jordan 9 Retro" }
        };
    }
}