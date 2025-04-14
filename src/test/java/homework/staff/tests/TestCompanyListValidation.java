package homework.staff.tests;

import BaseTest.BaseTest;
import homework.staff.pages.SearchResultsPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestCompanyListValidation extends BaseTest {
    @Override
    protected String getUrl() {
        return "https://staff.am";
    }
    @Test
    public void testCompany() {
        SearchResultsPage searchResultsPage = new SearchResultsPage();
        searchResultsPage.header.selectHeaderCategory("Companies");
        searchResultsPage.openViewMoreSection()
                .selectIndustryFilter("Sport");
        List<String> companyListAfterHeaderFiltering = searchResultsPage.getCompanyList();
        searchResultsPage.enterHiring();
        List<String> companyListAfterHiringFilter = searchResultsPage.getCompanyList();
        searchResultsPage.footer.selectFooterCategory()
                .selectIndustryFilter("Sport");
        List<String> companyListAfterFooterFiltering = searchResultsPage.getCompanyList();
        searchResultsPage.enterHiring();
        List<String> companyListAfterHiringFilter2 = searchResultsPage.getCompanyList();
        Assertions.assertEquals(companyListAfterHeaderFiltering, companyListAfterFooterFiltering,
                "all the objects details must be the same");
        Assertions.assertEquals(companyListAfterHiringFilter, companyListAfterHiringFilter2,
                "all the objects details must be the same");

    }
}
