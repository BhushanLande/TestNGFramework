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
    By searchButton = By.xpath("//button[@type='submit']");
    By tableValues = By.xpath("//div[@role='cell'][4]/div");
    By dropdownValues = By.xpath("//div[@role='listbox']/div[@role='option']/span");
    By searchInput = By.xpath("//label[text()='Employee Name']/parent::div/following-sibling::div//input");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void clickAdminTab() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(adminButton)));
        driver.findElement(adminButton).click();
    }

    public void clickSearchButton() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(searchButton)));
        driver.findElement(searchButton).click();
    }
    public void performSearch(String searchTerm) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        driver.findElement(searchInput).clear();
        driver.findElement(searchInput).sendKeys(searchTerm);
        clickSearchButton();
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

    public void handleAutocompleteDropdown(String searchValue) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        driver.findElement(searchInput).clear();
        driver.findElement(searchInput).sendKeys(searchValue);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(dropdownValues)));

        List<WebElement> values = driver.findElements(dropdownValues);
        for(int i=0; i<values.size(); i++){
            if(values.get(i).getText().equalsIgnoreCase(searchValue)){
                values.get(i).click();
                break;
            }
        }

        clickSearchButton();
    }
}
