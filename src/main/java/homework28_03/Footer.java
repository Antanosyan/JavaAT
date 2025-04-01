package homework28_03;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class Footer {
    private final WebDriver driver;
    private final By companyLocator = By.xpath("(//div[text()='View all companies'])[2]");

    public Footer(WebDriver driver) {
        this.driver = driver;
    }

    public SearchResultsPage selectFooterCategory() {
        Actions actions = new Actions(driver);
        actions.click(driver.findElement(companyLocator)).perform();
        return new SearchResultsPage();
    }
}
