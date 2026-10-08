package com.kredily.tests;

import com.kredily.base.BaseTest;
import com.kredily.config.Config;
import com.kredily.pages.BasePage;
import com.kredily.pages.LoginPage;
import com.kredily.pages.NavBar;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * J1 valid login | J2 invalid login | J3 dashboard validation
 * J4 attendance team view | J5 directory + profile
 * Plus a known-bug probe for BUG-01 (group "known-bug", excluded from the default run).
 */
public class KredilyJourneyTests extends BaseTest {

    @Test(groups = "journey", description = "J1 - valid login lands on Home")
    public void j1_validLogin() {
        new LoginPage(driver).login();
        Assert.assertTrue(new BasePage(driver).isVisible(BasePage.text("Shift"), 25),
                "Home dashboard not shown after login");
    }

    @Test(groups = "journey", description = "J2a - wrong password shows error and stays on password screen")
    public void j2a_invalidPassword() {
        LoginPage login = new LoginPage(driver);
        login.login(Config.VALID_USER, Config.WRONG_PASS);
        Assert.assertTrue(login.isVisible(BasePage.text("Incorrect password"), 10), "No error for wrong password");
        Assert.assertTrue(login.isVisible(BasePage.text("Enter password"), 5), "Should stay on password screen");
    }

    @Test(groups = "journey", description = "J2b - empty identity shows validation")
    public void j2b_emptyIdentity() {
        LoginPage login = new LoginPage(driver);
        login.tapContinue();
        Assert.assertTrue(login.isVisible(BasePage.text("Enter your email or mobile number"), 8),
                "No validation message");
    }

    @Test(groups = "journey", description = "J3 - Home sections are present")
    public void j3_dashboardValidation() {
        new LoginPage(driver).login();
        BasePage page = new BasePage(driver);
        for (String label : new String[]{"Shift", "Needs you", "Team today", "This week"}) {
            Assert.assertTrue(page.isVisible(BasePage.text(label), 15), "Home section missing: " + label);
        }
        Assert.assertTrue(page.isVisible(BasePage.text("Clock In"), 5)
                || page.isVisible(BasePage.text("Clock Out"), 5), "No clock action shown");
    }

    @Test(groups = "journey", description = "J4 - Attendance team view summary tiles")
    public void j4_attendanceTeamView() {
        new LoginPage(driver).login();
        new NavBar(driver).go("Attendance");
        BasePage page = new BasePage(driver);
        Assert.assertTrue(page.isVisible(BasePage.text("attendance"), 15), "Attendance screen not shown");
        for (String label : new String[]{"In", "Late", "Leave", "Absent"}) {
            Assert.assertTrue(page.isVisible(BasePage.exactText(label), 8), "Summary tile missing: " + label);
        }
        Assert.assertTrue(page.isVisible(BasePage.exactText("Team"), 5)
                && page.isVisible(BasePage.exactText("Me"), 5), "Team/Me toggle missing");
    }

    @Test(groups = "journey", description = "J5 - Directory then Profile")
    public void j5_directoryAndProfile() {
        new LoginPage(driver).login();
        NavBar nav = new NavBar(driver);
        BasePage page = new BasePage(driver);
        nav.go("Directory");
        Assert.assertTrue(page.isVisible(BasePage.exactText("Directory"), 15), "Directory not shown");
        Assert.assertTrue(page.isVisible(BasePage.text("people"), 8), "People-count chip missing");
        nav.go("Profile");
        Assert.assertTrue(page.isVisible(BasePage.text("My info"), 15), "Profile not shown");
        Assert.assertTrue(page.isVisible(BasePage.text("Personal"), 5)
                && page.isVisible(BasePage.text("Work"), 5), "Profile sections missing");
    }

    /** Fails today because of BUG-01 (Home 8 vs badge/Reg. tab 5). Run: mvn test -Dgroups=known-bug */
    @Test(groups = "known-bug", description = "Probe BUG-01 - pending count consistent")
    public void probe_bug01_pendingCountConsistent() {
        new LoginPage(driver).login();
        BasePage page = new BasePage(driver);
        int homeCount = firstNumber(page.textOf(BasePage.text("approvals waiting")));
        new NavBar(driver).go("Approvals");
        page.isVisible(BasePage.text("Reg."), 10);
        int regCount = firstNumber(page.textOf(BasePage.text("Reg.")));
        Assert.assertEquals(homeCount, regCount, "BUG-01: Home vs Reg. tab count");
    }

    private static int firstNumber(String s) {
        Matcher m = Pattern.compile("(\\d+)").matcher(s);
        if (!m.find()) throw new AssertionError("No number in: " + s);
        return Integer.parseInt(m.group(1));
    }
}
