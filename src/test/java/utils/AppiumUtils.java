package utils;

import org.openqa.selenium.OutputType;
import org.apache.commons.io.FileUtils;
import io.appium.java_client.AppiumDriver;

import java.io.File;
import java.io.IOException;

public class AppiumUtils {

    public String getScreenshotPath(String testCaseName, AppiumDriver driver) throws IOException {
        File source = driver.getScreenshotAs(OutputType.FILE);
        String reportsDir = System.getProperty("user.dir") + "//reports";
        // Create reports directory if it doesn't exist
        new File(reportsDir).mkdirs();
        String destinationFile = reportsDir + "//" + testCaseName + ".png";
        FileUtils.copyFile(source, new File(destinationFile));
        return destinationFile;
    };
};
