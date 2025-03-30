package homework28_03;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SingleCompanyResult {

    private final WebDriver driver;
    private final By actualRes = By.xpath("//div[img[@alt=\"company-poster\"]]//following-sibling::div/div[3]");
    private final By nameOfCompany = By.xpath("//h1[@role=\"heading\"]");
    private final By nameOfIndustry = By.xpath("//div[text()=\"Industry\"]//following-sibling::div");

    public SingleCompanyResult(WebDriver driver) {
        this.driver = driver;
    }

    public String getActualResult() {
        String name = driver.findElement(nameOfCompany).getText().toLowerCase();
        String details = driver.findElement(actualRes).getText().replace("(", "").replace(")", "");
        return name + "\n" + details;
    }
    public String getActualNameOfIndustry(){
        return driver.findElement(nameOfIndustry).getText().toLowerCase();
    }
}
