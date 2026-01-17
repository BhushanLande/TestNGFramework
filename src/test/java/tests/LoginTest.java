package tests;

import base.BaseTest;
import driver.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;

public class LoginTest extends BaseTest {
    LoginPage loginPage;
    DashboardPage dashboardPage;

    @BeforeMethod
    public void setupPages() throws InterruptedException {
        loginPage = new LoginPage(DriverFactory.getDriver());
        dashboardPage = new DashboardPage(DriverFactory.getDriver());
        loginPage.login("Admin", "admin123");
        loginPage.waitTime();
    }


    @Test
    public void verifyValidLogin() throws InterruptedException {
        loginPage.login("Admin", "admin123");
        loginPage.waitTime();
    }

    @Test
    public void verifyUser() throws InterruptedException {
        dashboardPage.clickAdminTab();
        Assert.assertTrue(dashboardPage.validateUser("Jobin Sam"));
    }
}
