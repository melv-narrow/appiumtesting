package pageobjects;

import utils.AndroidActions;

import java.util.List;

import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CartPage extends AndroidActions {
    
    public CartPage(AndroidDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(id = "com.androidsample.generalstore:id/toolbar_title")
    private WebElement toolbarTitle;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productName")
    private List<WebElement> cartProducts;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productPrice")
    private List<WebElement> productPrices;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/totalAmountLbl")
    private WebElement totalAmount;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/termsButton")
    private WebElement termsButton;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/alertTitle")
    private WebElement alertTitle;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement closeAlert;

    @AndroidFindBy(className = "android.widget.CheckBox")
    private WebElement checkBox;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnProceed")
    private WebElement btnProceed;

    public String getToolbarTitle() {
        return toolbarTitle.getText();
    }

    public String getProductsInCartByIndex(int index) {
        return cartProducts.get(index).getText();
    }

    public double getProductPrices() {
        int productCount = productPrices.size();
        double purchaseAmount = 0;
        for (int i = 0; i < productCount; i++) {
            String productPrice = productPrices.get(i).getText();
            Double price = Double.parseDouble(productPrice.substring(1));
            purchaseAmount = purchaseAmount + price;
        }
        return purchaseAmount;
    }

    public double getPurchaseAmount() {
        Double totalPurchaseAmount = Double.parseDouble(totalAmount.getText().substring(1));
        return totalPurchaseAmount;
    }

    public void goToTermsAndConditions() {
        longClickGesture(termsButton);
    }

    public String getAlertTitle() {
        return alertTitle.getText();
    }

    public void closeAlert() {
        closeAlert.click();
    }

    public void acceptTermsAndConditions() {
        checkBox.click();
    }

    public void goToCheckout() {
        btnProceed.click();
    }
}
