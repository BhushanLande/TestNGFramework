package driver;

import config.ConfigReader;
import healer.OpenAIHealer;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.XPathUtils;

import java.time.Duration;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private final boolean healingEnabled;
    private final OpenAIHealer healer;
    private final WebDriverWait wait;

    public DriverFactory(WebDriver driver) {
        this.healingEnabled = ConfigReader.isHealingEnabled();
        this.healer = new OpenAIHealer(ConfigReader.getOpenAIKey());
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static void initDriver() {
        String browser = ConfigReader.getProperty("browser");

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--ignore-certificate-errors");
            driver.set(new ChromeDriver());
            //driver.set(new ChromeDriver(options));
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver.set(new FirefoxDriver());
        }

        getDriver().manage().window().maximize();
        getDriver().get(ConfigReader.getProperty("url"));
    }

    public WebElement findElement(By locator) {

        try {
            // ✅ WAIT before finding
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        } catch (TimeoutException | NoSuchElementException e) {

            if (!healingEnabled) {
                throw e;
            }

            // 🔒 AI healing ONLY for XPath
            if (!locator.toString().startsWith("By.xpath:")) {
                System.out.println(
                        "Skipping AI healing (non-XPath): " + locator);
                throw e;
            }

            System.out.println("⚠ Broken XPath: " + locator);

            String brokenXpath = XPathUtils.extractXPath(locator);
            String pageSource = getDriver().getPageSource();

            By healed = healer.heal(brokenXpath, pageSource);

            // ✅ WAIT again for healed XPath
            return wait.until(
                    ExpectedConditions.presenceOfElementLocated(healed)
            );
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}