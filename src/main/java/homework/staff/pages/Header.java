package homework.staff.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Header {
    private final String categoryXpath = "//a/div[text()='%s']";
    private final By headerRegisterLoc = By.xpath("//a[@href='/register']/div");

    private final WebDriver driver = DriverGenerator.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    public void selectHeaderCategory(String category) {
        WebElement waiting = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(categoryXpath, category))));
        waiting.click();
    }

    public RegisterPage hoverAndClick(String navbarCategory) {
        Actions actions = new Actions(driver);
        WebElement register = driver.findElement(By.xpath("//div[text()='" + navbarCategory + "']"));
        actions.moveToElement(register).perform();
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(headerRegisterLoc))).click();
        return new RegisterPage();
    }
}
