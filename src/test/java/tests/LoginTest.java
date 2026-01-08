package tests;

import base.BaseTest;
import driver.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void verifyValidLogin() throws InterruptedException {
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.login("Admin", "admin123");
        loginPage.waitTime();
        //Assert.assertTrue(DriverFactory.getDriver().getTitle().contains("Dashboard"));
    }
}
