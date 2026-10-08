package com.kredily.base;

import com.kredily.config.Config;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.net.URL;
import java.time.Duration;

public abstract class BaseTest {
    protected AndroidDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        UiAutomator2Options o = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName(Config.DEVICE_NAME)
                .setNoReset(false)                       // fresh, logged-out state per test
                .setNewCommandTimeout(Duration.ofSeconds(120));
        if (!Config.APP_PACKAGE.isEmpty()) {             // app already installed
            o.setAppPackage(Config.APP_PACKAGE);
            if (!Config.APP_ACTIVITY.isEmpty()) o.setAppActivity(Config.APP_ACTIVITY);
        } else {
            o.setApp(Config.APK_PATH);
        }
        driver = new AndroidDriver(new URL(Config.APPIUM_URL), o);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) throws Exception {
        if (driver == null) return;
        if (result.getStatus() == ITestResult.FAILURE) {
            File shot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path dest = Path.of("reports", "screens", result.getName() + ".png");
            Files.createDirectories(dest.getParent());
            Files.copy(shot.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
        }
        driver.quit();
    }
}
