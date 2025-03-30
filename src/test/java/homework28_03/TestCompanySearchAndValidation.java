package homework28_03;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class TestCompanySearchAndValidation {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://staff.am");
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        if (driver != null) {
            Thread.sleep(3000);
            driver.quit();
        }
    }

    @Test
    public void testCompanySearchAndValidation() throws Exception {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        SingleCompanyResult singleCompanyResult = new SingleCompanyResult(driver);

        homePage.selectCompaniesRadioButton();
        homePage.selectIndustry("Information technologies");
        homePage.enterSearch();
        homePage.clickSearchButton();

        searchResultsPage.enterSearchKeyword(Helper.generateRandomString(8));
        searchResultsPage.clickOnSearchButton();
        Assertions.assertTrue(searchResultsPage.getResultList().isEmpty(), "no such item");

        searchResultsPage.clearSearchField();
        searchResultsPage.enterSearchKeyword("ser");
        searchResultsPage.clickOnSearchButton();

        boolean isContainKeyword = Helper.areAllNamesContainingSearch(searchResultsPage.getResultList(), "sEr");
        Assertions.assertTrue(isContainKeyword, "all products name must contain keyword");

        String expectedDetails = searchResultsPage.expectedCompanyValid("ServiceTitan");
        searchResultsPage.selectRandomItem("ServiceTitan");
        String actualDetails = singleCompanyResult.getActualResult();
        Assertions.assertEquals(expectedDetails, actualDetails);

        String expectedIndustryName = homePage.getExpectedIndustryName();
        String actualIndustryName = singleCompanyResult.getActualNameOfIndustry();
        Assertions.assertEquals(expectedIndustryName, actualIndustryName);

    }
}
