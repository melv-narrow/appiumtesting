package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.AndroidActions;

public class FormPage extends AndroidActions {
    
    private AndroidDriver driver;
    
    public FormPage(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    
    @AndroidFindBy(id = "com.androidsample.generalstore:id/toolbar_title")
    private WebElement toolbarTitle;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/spinnerCountry")
    private WebElement spinnerCountry;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
    private WebElement nameField;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/radioFemale")
    private WebElement radioFemale;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/radioMale")
    private WebElement radioMale;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
    private WebElement btnLetsShop;

    @AndroidFindBy(xpath = "//android.widget.Toast[1]")
    private WebElement toastMessage;

    public boolean toolbarTitleDisplayed() {
        try {
            manageWait().until(ExpectedConditions.visibilityOf(toolbarTitle));
            return toolbarTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getToolbarTitle() {
        return toolbarTitle.getText();
    }

    public void selectCountry(String countryName) {
        spinnerCountry.click();
        scrollToText(countryName);
        WebElement countryOption = driver.findElement(By.xpath("//android.widget.TextView[@text='" + countryName + "']"));
        countryOption.click();
    }

    public ProductPage fillForm(String name, String gender) {
        nameField.sendKeys(name);
        if (gender.contains("female")) {
            radioFemale.click();
        } else {
            radioMale.click();
        }
        btnLetsShop.click();
        return new ProductPage(driver);
    }

    public void fillFormWithoutName(String gender) {
        if (gender.contains("female")) {
            radioFemale.click();
        } else {
            radioMale.click();
        }
        btnLetsShop.click();
    }

    public String getToastMessage() {
        return toastMessage.getText();
    }
}
