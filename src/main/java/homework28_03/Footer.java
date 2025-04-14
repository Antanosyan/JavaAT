package homework28_03;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Footer {
    private final WebDriver driver;
    @FindBy(xpath = "(//a//div[text()='View all companies'])[2]")
    private WebElement viewAll;

    public Footer(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public SearchResultsPage selectFooterCategory() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOf(viewAll));
        actions.scrollToElement(viewAll)
                .moveToElement(viewAll)
                .click(viewAll)
                .build()
                .perform();
        return new SearchResultsPage();
    }
}
