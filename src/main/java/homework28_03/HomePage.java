package homework28_03;

import org.openqa.selenium.*;

public class HomePage extends BasePage{

    private final By infTechnologies = By.xpath("//div[text()='Information technologies']");
    private final By categorySearchField = By.xpath("//input[@class='ant-select-selection-search-input']");
    private final By searchButton = By.xpath("//img[@alt=\"search-icon\"]");

    public HomePage selectCompaniesRadioButton(String radioButton) throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(By.xpath(String.format("(//div[text()='%s'])[2]", radioButton))).click();
        Thread.sleep(3000);
        return this;
    }

    public HomePage selectIndustry(String industry) throws InterruptedException {
        driver.findElement(categorySearchField).sendKeys(industry);
        Thread.sleep(3000);
        driver.findElement(By.xpath(String.format("//div[text()='%s']", industry))).click();
        return this;
    }

    public SearchResultsPage clickSearchButton() throws InterruptedException {
        driver.findElement(searchButton).click();
        Thread.sleep(3000);
        return new SearchResultsPage();
    }
    public String getExpectedIndustryName() throws InterruptedException {
        Thread.sleep(3000);
        return driver.findElement(infTechnologies).getText().toLowerCase();
    }
}
