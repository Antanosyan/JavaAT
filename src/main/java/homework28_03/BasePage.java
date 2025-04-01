package homework28_03;

import org.openqa.selenium.WebDriver;


abstract class BasePage {
    protected WebDriver driver = DriverGenerator.getDriver();

    private Header header = new Header();
    private Footer footer = new Footer();
}
