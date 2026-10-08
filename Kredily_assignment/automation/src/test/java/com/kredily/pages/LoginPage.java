package com.kredily.pages;

import com.kredily.config.Config;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
    private static final By FIELD = AppiumBy.className("android.widget.EditText");

    public LoginPage(AndroidDriver driver) { super(driver); }

    private void typeIntoField(String value) {
        WebElement el = waitFor(FIELD, WAIT);
        el.click();
        el.clear();
        el.sendKeys(value);
        if (driver.isKeyboardShown()) driver.hideKeyboard();
    }

    public void tapContinue() { tap(exactText("Continue")); }

    public void openPasswordScreen(String user) {
        typeIntoField(user);
        tap(text("Sign in with password"));
        if (!isVisible(text("Enter password"), 5)) {          // fallback flow: Continue first
            tapContinue();
            if (isVisible(text("Sign in with password"), 4)) tap(text("Sign in with password"));
        }
        if (!isVisible(text("Enter password"), 10))
            throw new AssertionError("Password screen not reached");
    }

    public void submitPassword(String pwd) {
        typeIntoField(pwd);
        tap(exactText("Sign in"));
    }

    public void login(String user, String pwd) {
        openPasswordScreen(user);
        submitPassword(pwd);
    }

    public void login() { login(Config.VALID_USER, Config.VALID_PASS); }
}
