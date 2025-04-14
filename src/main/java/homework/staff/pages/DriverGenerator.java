package homework.staff.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverGenerator {
    public static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void initializeBrowser(String url) {
        getDriver().get(url);
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
