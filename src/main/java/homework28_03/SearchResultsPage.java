package homework28_03;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import java.util.List;
import java.util.stream.Collectors;

public class SearchResultsPage {

    private final WebDriver driver;
    private final By searchInput = By.xpath("//input[@placeholder=\"Enter keywords...\"]");
    private final By submitSearchButton = By.xpath("//div[text()=\"Search\"]");
    private final By resultItems = By.xpath("//div[img[@alt=\"company-logo\"]]/div/div/div/div[string-length(text()) > 0]");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterSearchKeyword(String searchString) throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(searchInput).sendKeys(searchString);
    }

    public void clickOnSearchButton() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(submitSearchButton).click();
    }

    public void clearSearchField() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(searchInput).sendKeys(Keys.CONTROL + "a");
        driver.findElement(searchInput).sendKeys(Keys.BACK_SPACE);
    }

    public List<String> getResultList() throws InterruptedException {
        Thread.sleep(3000);
        List<String> names;
        names = driver.findElements(resultItems)
                .stream()
                .map(el -> el.getText()
                        .toLowerCase())
                .collect(Collectors.toList());
        return names;
    }

    public void selectRandomItem(String companyName) throws Exception {
        Actions actions = new Actions(driver);
        Thread.sleep(3000);
        WebElement element = driver.findElement(By.xpath("//div[contains(text(), '" + companyName + "')]"));
        Thread.sleep(3000);
        actions.click(element).perform();
    }

    public String expectedCompanyValid(String companyName) {
        return driver.findElement(By.xpath("//div[contains(text(),'" + companyName + "')]/ancestor-or-self::div[4]")).getText().toLowerCase();
    }
}
