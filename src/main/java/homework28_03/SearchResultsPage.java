package homework28_03;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SearchResultsPage extends BasePage {

    private final By searchInput = By.xpath("//input[@placeholder='Enter keywords...']");
    private final By submitSearchButton = By.xpath("//div[text()='Search']");
    private final By resultItems = By.xpath("//div[img[@alt='company-logo']]/div/div/div/div[string-length(text()) > 0]");
    private final By viewMoreLocator = By.xpath("(//div[@tabindex]/div[text()= 'View more'])[1]");
    private final By hiringLocator = By.xpath("//div[text()='Hiring']");
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

    public SearchResultsPage openViewMoreSection() throws InterruptedException {
        Thread.sleep(3000);
        Actions actions = new Actions(driver);
        actions.click(driver.findElement(viewMoreLocator)).perform();

        return this;
    }

    public SearchResultsPage selectIndustryFilter(String industryName) throws InterruptedException {
        Actions actions = new Actions(driver);
        Thread.sleep(5000);
        actions.click(driver.findElement(By.xpath(String.format("//span[text()='%s']", industryName)))).perform();
        Thread.sleep(5000);
        return this;
    }

    public SearchResultsPage enterHiring() {
        driver.findElement(hiringLocator).click();
        return this;
    }

    public List<Company> getCompamyList() throws InterruptedException {
        List<Company> productList = new ArrayList<>();
        List<String> companies = getResultList();
        Thread.sleep(5000);
        for (String company : companies) {
            productList.add(new Company(company));
        }
        return productList;
    }
}