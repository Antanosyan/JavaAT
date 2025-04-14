package Base;

import homework.staff.pages.DriverGenerator;
import homework.staff.pages.Footer;
import homework.staff.pages.Header;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
   protected WebDriver driver = DriverGenerator.getDriver();
   protected WebDriverWait wait;
   JavascriptExecutor js = (JavascriptExecutor) driver;

    protected void click (WebElement webElement){
        js.executeScript("arguments[0].scrollIntoView(true);", webElement);
        js.executeScript("arguments[0].click();", webElement) ;
    }
   public BasePage() {
       this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
       PageFactory.initElements(driver, this);

   }

   public Footer footer = new Footer(driver);
   public Header header = new Header(driver);
}
