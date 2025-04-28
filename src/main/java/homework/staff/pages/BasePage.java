package homework.staff.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;

    public Header header;
    public Footer footer;

    public BasePage() {
        this.driver = DriverGenerator.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        this.js = (JavascriptExecutor) driver;
        this.header = new Header(driver);
        this.footer = new Footer(driver);
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }
}