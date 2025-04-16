package homework28_03;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

abstract class BasePage {
    protected WebDriver driver = DriverGenerator.getDriver();
    protected WebDriverWait wait;


    public BasePage() {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);

    }

    protected Footer footer = new Footer(driver);
    protected Header header = new Header(driver);
}
