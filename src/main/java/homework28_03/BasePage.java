package homework28_03;

import org.openqa.selenium.WebDriver;


abstract class BasePage {
    protected WebDriver driver = DriverGenerator.getDriver();

    protected Footer footer = new Footer(driver);
    protected Header header = new Header(driver);
}
