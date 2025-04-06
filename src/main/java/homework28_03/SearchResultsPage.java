package homework28_03;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SearchResultsPage extends BasePage {

    private final By searchInput = By.xpath("//input[@placeholder='Enter keywords...']");
    private final By submitSearchButton = By.xpath("//div[text()='Search']");
    private final By resultItems = By.xpath("//div[img[@alt='company-logo']]/div/div/div/div[string-length(text()) > 0]");
    private final By viewMoreLocator = By.xpath("//div[text()='Filter By Industry']//following-sibling::div[6]");
    private final By hiringLocator = By.xpath("//div[text()='Hiring']");
    public static int randomCompanyIndex;
    public static List<WebElement> companies;

    public SearchResultsPage enterSearchKeyword(String searchString) {
        wait.until(ExpectedConditions.elementToBeClickable(searchInput))
                .sendKeys(searchString);
        return this;
    }

    public void clickOnSearchButton() {
        driver.findElement(submitSearchButton).click();
    }

    public SearchResultsPage clearSearchField() {
        driver.findElement(searchInput).clear();
        return this;
    }

    public List<String> getResultList() {
        List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(resultItems));
        return elements
                .stream()
                .map(el -> el.getText().trim().toLowerCase())
                .collect(Collectors.toList());
    }

    public String selectRandomItem() {
        companies = driver.findElements(By.xpath("//div[img[@alt='company-logo']]/div/div/div/div[string-length(text()) > 0]"));
        Random rand = new Random();
        randomCompanyIndex = rand.nextInt(companies.size());
        return getCompanyDetails(companies.get(randomCompanyIndex).getText());
    }

    public SingleCompanyResult clickRandomPage() {
        Actions actions = new Actions(driver);
        actions.click(companies.get(randomCompanyIndex)).perform();
        return new SingleCompanyResult();
    }

    public String getCompanyDetails(String nameCompany) {
        return driver.findElement(By.xpath(String.format("//div[contains(text(),'%s')]/ancestor-or-self::div[4]", nameCompany)))
                .getText().toLowerCase();

    }

    public SearchResultsPage openViewMoreSection() {
        wait.until(ExpectedConditions.elementToBeClickable(viewMoreLocator));
        WebElement viewMore = driver.findElement(viewMoreLocator);
        viewMore.click();
        return this;
    }

    public void selectIndustryFilter(String industryName) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[text()='Clear filters']")));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format("//span[text()='%s']", industryName))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public void enterHiring() {
        wait.until(ExpectedConditions.elementToBeClickable(hiringLocator));
        driver.findElement(hiringLocator).click();
    }

    public List<Company> getCompamyList() {
        WebElement sportFilterExpectedCount = driver.findElement(By.xpath("//span[text()='Sport']/span"));
        WebElement sportFilterActualCount = driver.findElement(By.xpath("//img[@alt=\"building\"]//following-sibling::div"));
        wait.until(dr -> {
            int first = Integer.parseInt(sportFilterActualCount.getText().trim());
            int second = Integer.parseInt(sportFilterExpectedCount.getText().trim().replaceAll("\\D", ""));
            return first == second;
        });
        List<String> companyNames = getResultList();
        return companyNames.stream().map(name -> new Company(name.toLowerCase())).collect(Collectors.toList());
    }

    public boolean isNoCompanyFoundMessageVisible() {
        WebElement noResults = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div//img[@alt='search-not-found']/following-sibling::div")));
        return noResults.isDisplayed();
    }
}