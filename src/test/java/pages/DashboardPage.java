package pages;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DashboardPage {
    private WebDriver driver;

    By adminButton = By.xpath("//span[text()='Admin']/../../a");
    By tableValues = By.xpath("//div[@role='cell'][4]/div");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void clickAdminTab() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(adminButton)));
        driver.findElement(adminButton).click();
    }

    public boolean validateUser(String userName) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(tableValues)));
        boolean flag = false;
        List<WebElement> values = driver.findElements(tableValues);
        for(int i=0; i<values.size(); i++){
            if(values.get(i).getText().equalsIgnoreCase(userName)){
                flag = true;
            }
        }
        return flag;
    }
}
