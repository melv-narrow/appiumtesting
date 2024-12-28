package pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import utils.AndroidActions;

public class ProductPage extends AndroidActions {
    private AndroidDriver driver;
    
    public ProductPage(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productName")
    private List<WebElement> productNames;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productAddCart")
    private List<WebElement> productAddCart;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/appbar_btn_cart")
    private WebElement appbarBtnCart;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productName")
    private WebElement cartProductName;

    public void addProductsToCart(String product1, String product2) {
        String[] productsToAdd = {product1, product2};
        for (String product : productsToAdd) {
            scrollToText(product);

            int productCount = productNames.size();
            for (int i = 0; i < productCount; i++) {
                String productName2 = productNames.get(i).getText();
                if (productName2.equals(product)) {
                    productAddCart.get(i).click();
                    break;
                }
            }
        }
    }

    public void scrollToAddProductToCart(String product) {
        manageWait().until(ExpectedConditions.visibilityOf(cartProductName));
        scrollToText(product);
    }

    public void addProductToCart(String product) {
        int productCount = productNames.size();
        for (int i = 0; i < productCount; i++) {
            String productName2 = productNames.get(i).getText();
            if (productName2.equals(product)) {
                productAddCart.get(i).click();
                break;
            }
        }
    }

    public CartPage goToCart() {
        appbarBtnCart.click();
        return new CartPage(driver);
    }
}
