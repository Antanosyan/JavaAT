package Base;

import homework.staff.pages.DriverGenerator;
import homework.staff.pages.Footer;
import homework.staff.pages.Header;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver = DriverGenerator.getDriver();
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;

    public BasePage() {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    public Footer footer = new Footer(driver);
    public Header header = new Header(driver);
}
