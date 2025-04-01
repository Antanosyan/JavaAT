package homework28_03;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Header {
    private final WebDriver driver;

    public Header(WebDriver driver) {
        this.driver = driver;
    }

    public SearchResultsPage selectHeaderCategory(String category) {
        driver.findElement(By.xpath(String.format("//div[@class='justify-content-end false  " +
                        "web-dropdown light navbar-collapse collapse']//div[text()='%s']", category)))
                .click();
        return new SearchResultsPage();
    }
}
