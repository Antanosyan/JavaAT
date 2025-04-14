package homework.staff.pages;

import Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.Objects;

public class JobsPage extends BasePage {
    private final String filterNameXpath = "//span[text()='%s']";
    private final String filterTypeXpath = "//div[text()='%s']";
    private final By jobsLoc = By.xpath("//div[h1]/following-sibling::div//img[@alt='left-icon']");
    String viewAll = "//div[text()='%s']//following-sibling::div[6]";

    @FindBy(xpath = "//div[text()='Clear filters']")
    private WebElement clearFilter;
    @FindBy(xpath = "//div[text()='Filter By Industry']//following-sibling::div[6]")
    private WebElement viewMore;
    @FindBy(xpath = "//ul[@class='pagination']//li/a")
    private WebElement pagination;
    @FindBy(xpath = "//li[@class='next']/preceding-sibling::li[1]/a")
    private WebElement lastPage;
    @FindBy(xpath = "(//div[.//a[text()=\"1\"]])[8]/div[1]")
    private WebElement firstCompany;

    public void selectFilter(String filterType, String filterName) {
        String nameXpath = String.format(filterTypeXpath, filterType) + "//following-sibling::div" + String.format(filterNameXpath, filterName);
        String previousUrl = driver.getCurrentUrl();
        try {
            click(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(viewAll, filterType)))));
            click(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(nameXpath))));
        } catch (TimeoutException e) {
            click(wait.until(ExpectedConditions.elementToBeClickable
                    (By.xpath(nameXpath))));
        }
        wait.until(d -> !Objects.equals(driver.getCurrentUrl(), previousUrl));
    }

    public int getItemsCountOfOnePage() {
        return driver.findElements(jobsLoc).size();
    }

    public int getCompaniesCount() {
        int lastPageNumber = 0;
        try {
            lastPageNumber = Integer.parseInt(wait.until(ExpectedConditions.elementToBeClickable(lastPage)).getText());
            click(lastPage);
            wait.until(driver1 -> driver1.findElements(jobsLoc).size() < 50);
            return (lastPageNumber - 1) * 50 + getItemsCountOfOnePage();
        } catch (TimeoutException e) {
            return getItemsCountOfOnePage() < 50 ? getItemsCountOfOnePage() : lastPageNumber * 50;
        }
    }

    public void setClearFilter() {
        click(wait.until(ExpectedConditions.elementToBeClickable(clearFilter)));
    }

    public int getCountOfSelectedFilter(String filterType, String filterName) {
        return Integer
                .parseInt(wait.until(ExpectedConditions
                                .visibilityOfElementLocated(By.xpath(String.format("//div[text()= '%s']", filterType)
                                        + String.format("//following-sibling::div//span[text()=\"%s\"]/span", filterName))))
                        .getText().replaceAll("\\D", ""));
    }

    public void removeFilter(String filterType, String filterName) {
        String nameXpath = String.format(filterTypeXpath, filterType) +
                "//following-sibling::div" + String.format(filterNameXpath, filterName);
        click(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(nameXpath))));
    }
}

