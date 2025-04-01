package homework28_03;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SearchResultsPage extends BasePage {
    List<WebElement> list;

    private final By searchInput = By.xpath("//input[@placeholder=\"Enter keywords...\"]");
    private final By submitSearchButton = By.xpath("//div[text()=\"Search\"]");
    private final By resultItems = By.xpath("//div[img[@alt=\"company-logo\"]]/div/div/div/div[string-length(text()) > 0]");
    public static int randomCompanyIndex;
    public static List<WebElement> companies;

    public SearchResultsPage enterSearchKeyword(String searchString) throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(searchInput).sendKeys(searchString);
        return this;
    }

    public void clickOnSearchButton() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(submitSearchButton).click();
    }

    public SearchResultsPage clearSearchField() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(searchInput).clear();
        return this;
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

    public String selectRandomItem() throws Exception {
        companies = driver.findElements(By.xpath("//div[img[@alt='company-logo']]/div/div/div/div[string-length(text()) > 0]"));
        Thread.sleep(5000);
        Random rand = new Random();
        randomCompanyIndex = rand.nextInt(companies.size());
        return getCompanyDetails(companies.get(randomCompanyIndex).getText());
    }

    public SingleCompanyResult clickRandomPage() throws InterruptedException {
        Actions actions = new Actions(driver);
        actions.click(companies.get(randomCompanyIndex)).perform();
        Thread.sleep(3000);
        return new SingleCompanyResult();
    }

    public String getCompanyDetails(String nameCompany) {
        return driver.findElement(By.xpath(String.format("//div[contains(text(),'%s')]/ancestor-or-self::div[4]", nameCompany)))
                .getText().toLowerCase();

    }
}
