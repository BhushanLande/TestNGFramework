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
    public void setupPages() {
        loginPage = new LoginPage(DriverFactory.getDriver());
        dashboardPage = new DashboardPage(DriverFactory.getDriver());
    }


    @Test
    public void verifyValidLogin() throws InterruptedException {
        loginPage.login("Admin", "admin123");
        loginPage.waitTime();
    }

    @Test
    public void verifyUser() throws InterruptedException {
        loginPage.login("Admin", "admin123");
        loginPage.waitTime();
        dashboardPage.clickAdminTab();
        Assert.assertTrue(dashboardPage.validateUser("Jobin Sam"));
    }
    @Test
    public void testInvalidUsername() throws InterruptedException {
        loginPage.enterUsername("InvalidUser");
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();
        loginPage.waitTime();
        Assert.assertTrue(loginPage.getErrorMessage().contains("Invalid credentials"));
    }

    @Test
    public void testAdminSearchFunctionality() throws InterruptedException {
        loginPage.login("Admin", "admin123");
        loginPage.waitTime();
        // Navigate to Admin Tab
        dashboardPage.clickAdminTab();

        // Search for a known user
        dashboardPage.handleAutocompleteDropdown("Ranga Akunuri");
        Assert.assertTrue(dashboardPage.validateUser("Ranga Akunuri"), "Search for 'Ranga Akunuri' failed");
    }
}
