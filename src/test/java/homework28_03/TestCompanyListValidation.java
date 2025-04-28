package homework28_03;

import homework.staff.pages.DriverGenerator;
import homework.staff.pages.SearchResultPage;
import homework.staff.tests.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestCompanyListValidation extends BaseTest {

    @Test
    public void testCompany() {
        DriverGenerator.getDriver().get("https://staff.am/");

        SearchResultPage searchResultsPage = new SearchResultPage();
        searchResultsPage.header.selectCategoryOfHeader("Companies");
        searchResultsPage.openViewMoreSection()
                .selectFilterIndustry("Sport");
        List<String> companyListAfterHeaderFiltering = searchResultsPage.getCompaniesList();
        searchResultsPage.enterHiring();
        List<String> companyListAfterHiringFilter = searchResultsPage.getCompaniesList();
        searchResultsPage.footer.selectFooterCategory()
                .selectFilterIndustry("Sport");
        List<String> companyListAfterFooterFiltering = searchResultsPage.getCompaniesList();
        searchResultsPage.enterHiring();
        List<String> companyListAfterHiringFilter2 = searchResultsPage.getCompaniesList();
        Assertions.assertEquals(companyListAfterHeaderFiltering, companyListAfterFooterFiltering,
                "all the objects details must be the same");
        Assertions.assertEquals(companyListAfterHiringFilter, companyListAfterHiringFilter2,
                "all the objects details must be the same");

    }
}
