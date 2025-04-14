package homework28_03;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Header {
    private final String categoryXpath = "//a/div[text()='%s']";
    private final WebDriver driver;

    public Header(WebDriver driver) {
        this.driver = driver;
    }

    public void selectHeaderCategory(String category) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement waiting = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(categoryXpath, category))));
        waiting.click();
    }
}
