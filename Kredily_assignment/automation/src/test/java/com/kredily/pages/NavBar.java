package com.kredily.pages;

import io.appium.java_client.android.AndroidDriver;

public class NavBar extends BasePage {
    public NavBar(AndroidDriver driver) { super(driver); }

    /** tab = Home | Approvals | Attendance | Directory | Profile */
    public void go(String tab) { tap(exactText(tab)); }
}
