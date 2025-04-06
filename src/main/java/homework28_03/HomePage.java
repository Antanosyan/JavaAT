package homework28_03;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    private final By infTechnologies = By.xpath("//div[text()='Information technologies']");
    private final By categorySearchField = By.xpath("//input[@class='ant-select-selection-search-input']");
    private final By searchButton = By.xpath("//img[@alt='search-icon']");

    public HomePage selectCompaniesRadioButton(String radioButton) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format("(//div[text()='%s'])[2]", radioButton))))
                .click();
        return this;
    }

    public HomePage selectIndustry(String industry) {
        driver.findElement(categorySearchField).sendKeys(industry);
        driver.findElement(By.xpath(String.format("//div[text()='%s']", industry))).click();
        return this;
    }

    public SearchResultsPage clickSearchButton() {
        driver.findElement(searchButton).click();
        return new SearchResultsPage();
    }

    public String getExpectedIndustryName() {
        return driver.findElement(infTechnologies).getText().toLowerCase();
    }
}
