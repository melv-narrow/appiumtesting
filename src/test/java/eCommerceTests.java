import org.testng.Assert;
import org.testng.annotations.Test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import pageobjects.CartPage;
import pageobjects.ProductPage;
import testdata.TestData;

@Epic("eCommerce Tests")
@Feature("Login and Product Purchase Flow")
public class eCommerceTests extends BaseTest {
    
    @Test(dataProvider = "getFormData", dataProviderClass = TestData.class, groups = { "Positive Tests" })
    @Story("Form Submission")
    public void FillForm(String name, String gender, String country) throws InterruptedException {
        Assert.assertTrue(formPage.toolbarTitleDisplayed(), "Toolbar title is not displayed");
        formPage.selectCountry(country);
        formPage.fillForm(name, gender);
    }

    @Test(dataProvider = "getFormWithoutNameData", dataProviderClass = TestData.class, groups = { "Negative Tests" })
    @Story("Form Submission wihtout a Name. Validation Test")
    public void FillFormWithoutName(String gender, String country) throws InterruptedException {
        Assert.assertTrue(formPage.toolbarTitleDisplayed(), "Toolbar title is not displayed");
        formPage.selectCountry(country);
        formPage.fillFormWithoutName(gender);
        String toastText = formPage.getToastMessage();
        Assert.assertEquals(toastText, "Please enter your name");
    }

    @Test(dataProvider = "getFormData", dataProviderClass = TestData.class, groups = { "Positive Tests" })
    @Story("Scroll to a product and add the product to Cart")
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

    @Test(dataProvider = "getProductsData", dataProviderClass = TestData.class, groups = { "Positive Tests" })
    @Story("Add multiple products to Cart and check against the total cart amount")
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

    @Test(dataProvider = "getProductsData", dataProviderClass = TestData.class, groups = { "Positive Tests" })
    @Story("Add multiple products to Cart and check against the total cart amount")
    public void PurchaseProductHybrid(String name, String gender, String country, String product1, String product2) throws InterruptedException {
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
        // driver.context("WEBVIEW_com.androidsample.generalstore");
        // WebElement searchField = driver.findElement(By.name("q"));
        // Assert.assertTrue(searchField.isDisplayed());
        // driver.context("NATIVE_APP");
    }
}