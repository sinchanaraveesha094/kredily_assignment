package com.kredily.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/** Locators use the visible text seen on screen. If Appium Inspector shows resource-ids /
 *  accessibility ids, prefer those - they are more stable than text. */
public class BasePage {
    protected final AndroidDriver driver;
    protected static final int WAIT = 20;

    public BasePage(AndroidDriver driver) { this.driver = driver; }

    public static By text(String t)      { return AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + t + "\")"); }
    public static By exactText(String t) { return AppiumBy.androidUIAutomator("new UiSelector().text(\"" + t + "\")"); }

    protected WebElement waitFor(By by, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.presenceOfElementLocated(by));
    }

    protected void tap(By by) { waitFor(by, WAIT).click(); }

    public boolean isVisible(By by, int seconds) {
        try { waitFor(by, seconds); return true; }
        catch (TimeoutException e) { return false; }
    }

    public String textOf(By by) { return waitFor(by, WAIT).getText(); }
}
