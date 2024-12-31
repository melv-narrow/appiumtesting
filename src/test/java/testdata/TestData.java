package testdata;


import org.testng.annotations.DataProvider;

public class TestData {
    
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
