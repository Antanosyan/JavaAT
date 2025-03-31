package homework28_03;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class HomePage {

    private final WebDriver driver;
    private final By companiesRadioButton = By.xpath("(//div[text()=\"Companies\"])[2]");;
    private final By infTechnologies = By.xpath("//div[text()=\"Information technologies\"]");
    private final By categorySearchField = By.xpath("//input[@class='ant-select-selection-search-input']");
    private final By searchButton = By.xpath("//img[@alt=\"search-icon\"]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectCompaniesRadioButton() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(companiesRadioButton).click();
        Thread.sleep(3000);
    }

    public void selectIndustry(String industry) throws InterruptedException {
        Actions act = new Actions(driver);
        driver.findElement(categorySearchField).click();
        Thread.sleep(2000);

        while (true) {
            WebElement highlightedOption = driver.findElement(By.xpath("//div[contains(@class, 'ant-select-item-option-active')]"));
            if (highlightedOption.getText().equals(industry)) {
                highlightedOption.click();
                break;
            }
            act.sendKeys(Keys.DOWN).perform();
            Thread.sleep(500);
        }
    }

    public void clickSearchButton() throws InterruptedException {
        driver.findElement(searchButton).click();
        Thread.sleep(3000);
    }
    public String getExpectedIndustryName(){
        return driver.findElement(infTechnologies).getText().toLowerCase();
    }
}
