package homework28_03;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TestCompanySearchAndValidation extends BaseTest {

    @Test
    public void testCompanySearchAndValidation() throws Exception {
        HomePage homePage = new HomePage();
        SearchResultsPage searchResultsPage = new SearchResultsPage();
        SingleCompanyResult singleCompanyResult = new SingleCompanyResult();

        homePage.selectCompaniesRadioButton("Companies")
                .selectIndustry("Information technologies")
                .clickSearchButton();

        searchResultsPage.enterSearchKeyword(RandomStringUtils.randomAlphanumeric(8))
                .clickOnSearchButton();
        Assertions.assertTrue(searchResultsPage.getResultList().isEmpty(),
                "Result must be empty");

        searchResultsPage.clearSearchField()
                .enterSearchKeyword("ser")
                .clickOnSearchButton();

        Assertions.assertTrue(searchResultsPage.getResultList()
                        .stream()
                        .allMatch(name -> name.contains("ser")),
                "all products name must contain search keyword");

        String expectedDetails = searchResultsPage.selectRandomItem();
        SingleCompanyResult companyPage = searchResultsPage.clickRandomPage();
        String actualDetails = companyPage.getActualResult();
        Assertions.assertEquals(expectedDetails, actualDetails,
                "Company details in IndustriesResultPage should be equals company details in CompanyPage");

        String expectedIndustry = homePage.getExpectedIndustryName();
        String actualIndustry = companyPage.getActualNameOfIndustry();
        Assertions.assertEquals(expectedIndustry, actualIndustry,
                "Industry name of company should be the same as selected industries category");

    }
}
