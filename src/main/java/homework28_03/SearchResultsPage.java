package homework28_03;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SearchResultsPage extends BasePage {

    private final String companyDetails = "//div[contains(text(),'%s')]/ancestor-or-self::div[4]";
    private final String industryNameXpath = "//span[text()='%s']";
    private final By resultItemsLoc = By.xpath("//div[img[@alt='company-logo']]/div/div/div/div[string-length(text()) > 0]");
    public static int randomCompanyIndex;
    public static List<WebElement> companies;

    @FindBy(xpath = "//div[text()='Clear filters']")
    private WebElement clearFilter;
    @FindBy(xpath = "//input[@placeholder='Enter keywords...']")
    private WebElement searchInput;
    @FindBy(xpath = "//div[text()='Search']")
    private WebElement submitSearchButton;
    @FindBy(xpath = "//div[text()='Filter By Industry']//following-sibling::div[6]")
    private WebElement viewMoreLocator;
    @FindBy(xpath = "//div[text()='Hiring']")
    private WebElement hiringLocator;
    @FindBy(xpath = "//div//img[@alt='search-not-found']/following-sibling::div")
    private WebElement noCompanyMessageLoc;

    public SearchResultsPage enterSearchKeyword(String searchString) {
        wait.until(ExpectedConditions.elementToBeClickable(searchInput)).sendKeys(searchString);
        return this;
    }

    public void clickOnSearchButton() {
        submitSearchButton.click();
    }

    public SearchResultsPage clearSearchField() {
        searchInput.clear();
        return this;
    }

    public List<String> getResultList() {
        List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(resultItemsLoc));
        return elements
                .stream()
                .map(el -> el.getText().trim().toLowerCase())
                .collect(Collectors.toList());
    }

    public String selectRandomItem() {
        companies = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(resultItemsLoc));
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
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(String.format(companyDetails, nameCompany))))
                .getText().toLowerCase();
    }

    public SearchResultsPage openViewMoreSection() {
        WebElement viewMore = wait.until(ExpectedConditions.elementToBeClickable(viewMoreLocator));
        viewMore.click();
        return this;
    }

    public void selectIndustryFilter(String industryName) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.invisibilityOf(clearFilter));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(industryNameXpath, industryName))));
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        js.executeScript("arguments[0].click();", element);
    }

    public void enterHiring() {
        wait.until(ExpectedConditions.elementToBeClickable(hiringLocator)).click();
        wait.until(ExpectedConditions.urlContains("job"));
    }

    public List<String> getCompanyList() {
        WebElement sportFilterExpectedCount = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Sport']/span")));
        WebElement sportFilterActualCount = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@alt=\"building\"]//following-sibling::div")));
        wait.until(dr -> {
            int first = Integer.parseInt(sportFilterActualCount.getText().trim());
            int second = Integer.parseInt(sportFilterExpectedCount.getText().trim().replaceAll("\\D", ""));
            return first == second;
        });
        List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(resultItemsLoc));
        return elements
                .stream()
                .map(el -> el.getText().trim().toLowerCase())
                .collect(Collectors.toList());
    }

    public boolean isNoCompanyFoundMessageVisible() {
        WebElement noResults = wait.until(ExpectedConditions.visibilityOf(noCompanyMessageLoc));
        return noResults.isDisplayed();
    }
}