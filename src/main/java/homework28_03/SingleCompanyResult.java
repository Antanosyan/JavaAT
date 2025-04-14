package homework28_03;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SingleCompanyResult extends BasePage {

    @FindBy(xpath = "//div[img[@alt='company-poster']]//following-sibling::div/div[3]")
    private WebElement actualRes;
    @FindBy(xpath = "//h1[@role='heading']")
    private WebElement nameOfCompany;
    @FindBy(xpath = "//div[text()='Industry']//following-sibling::div")
    private WebElement nameOfIndustry;

    public String getActualResult() {
        String name = nameOfCompany.getText().toLowerCase();
        String details = actualRes.getText().replace("(", "").replace(")", "");
        return name + "\n" + details;
    }

    public String getActualNameOfIndustry() {
        return nameOfIndustry.getText().toLowerCase();
    }
}
