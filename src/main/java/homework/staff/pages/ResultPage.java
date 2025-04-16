package homework.staff.pages;

import Base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ResultPage extends BasePage {

    private final String companyDetails = "//div[contains(text(),'%s')]/ancestor-or-self::div[4]";
    private final String industryNameXpath = "//span[text()='%s']";
    private final String filterNameXpath = "//span[text()='%s']";
    private final String filterTypeXpath = "//div[text()='%s']";
    private final By jobsLoc = By.xpath("//div[h1]/following-sibling::div//img[@alt='left-icon']");
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
    @FindBy(xpath = "//div[text()='Filter By Industry']//following-sibling::div[6]")
    private WebElement viewMore;
    @FindBy(xpath = "//ul[@class='pagination']//li/a")
    private WebElement pagination;
    @FindBy(xpath = "//li[@class='next']/preceding-sibling::li[1]/a")
    private WebElement lastPage;
    @FindBy(xpath = "(//div[.//a[text()=\"1\"]])[8]/div[1]")
    private WebElement firstCompany;


    public ResultPage enterSearchKeyword(String searchString) {
        wait.until(ExpectedConditions.elementToBeClickable(searchInput)).sendKeys(searchString);
        return this;
    }

    public void clickOnSearchButton() {
        submitSearchButton.click();
    }

    public ResultPage clearSearchField() {
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
                By.xpath(String.format(companyDetails, nameCompany)))).getText().toLowerCase();
    }

    public ResultPage openViewMoreSection() {
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

    public int getItemsCountOfOnePage() {
        return driver.findElements(jobsLoc).size();
    }

    public void clearFilter() {
        actions.moveToElement(clearFilter).perform();
        actions.click(clearFilter).perform();
    }

    public int getCountOfSelectedFilter(String filterType, String filterName) {
        return Integer.parseInt(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format("//div[text()= '%s']", filterType) + String.format("//following-sibling::div//span[text()=\"%s\"]/span", filterName)))).getText().replaceAll("\\D", ""));
    }

    public void clickViewMore(String filterType) {
        WebElement filterSection = driver.findElement(By.xpath("//div[text()='" + filterType + "']"));

        List<WebElement> list = filterSection.findElements(By.xpath("./following-sibling::div//div[text()='View more']"));
        if (!list.isEmpty()) {
            WebElement viewMore = list.getFirst();
            actions.moveToElement(viewMore).perform();
            actions.click(viewMore).perform();
        }
    }

    public void selectFilter(String filterType, String filterName) {
        String previousUrl = driver.getCurrentUrl();
        clickViewMore(filterType);
        String nameXpath = String.format(filterTypeXpath, filterType) + "//following-sibling::div" + String.format(filterNameXpath, filterName);
        WebElement filterElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(nameXpath)));
        actions.moveToElement(filterElement).perform();
        actions.click(filterElement).perform();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(previousUrl)));
    }


    public int getCompaniesCount() {
        try {
            int lastPageNumber = Integer.parseInt(wait.until(ExpectedConditions.elementToBeClickable(lastPage)).getText());
            WebElement jobsOfFirstPage = driver.findElement(jobsLoc);
            actions.moveToElement(lastPage).perform();
            actions.click(lastPage).perform();
            wait.until(ExpectedConditions.invisibilityOf(jobsOfFirstPage));
            int jobsCountOFLastPage = getItemsCountOfOnePage();
            return (lastPageNumber - 1) * 50 + jobsCountOFLastPage;
        } catch (TimeoutException e) {
            return getItemsCountOfOnePage();
        }
    }

    public String getRandomItem(String filterType) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='" + filterType + "']")));
            clickViewMore(filterType);
            String xpath = String.format("//div[text()='%s']//following-sibling::div//span[span]", filterType);
            List<WebElement> list = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
            List<String> cleaned = list.stream().map(el -> el.getText().replaceAll(" \\(\\d+\\)", "")).toList();

            if (!cleaned.isEmpty()) {
                Random rand = new Random();
                return cleaned.get(rand.nextInt(cleaned.size()));
            }
        } catch (NoSuchElementException e) {
            return e.getMessage();
        }
        return null;
    }
}


