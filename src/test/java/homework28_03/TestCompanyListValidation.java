package homework28_03;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestCompanyListValidation extends BaseTest {

    @Test
    public void testCompany() {
        //step1-2
        SearchResultsPage searchResultsPage = new SearchResultsPage();
        searchResultsPage.header.selectHeaderCategory("Companies");
        searchResultsPage.openViewMoreSection()
                .selectIndustryFilter("Sport");
        //step3
        List<Company> companyListAfterHeaderFiltering = searchResultsPage.getCompamyList();
        //step4
        searchResultsPage.enterHiring();
        List<Company> companyListAfterHiringFilter = searchResultsPage.getCompamyList();
        //step5-6  (2-4)
        searchResultsPage.footer.selectFooterCategory()
                .selectIndustryFilter("Sport");
        List<Company> companyListAfterFooterFiltering = searchResultsPage.getCompamyList();
        searchResultsPage.enterHiring();
        List<Company> companyListAfterHiringFilter2 = searchResultsPage.getCompamyList();

        Assertions.assertEquals(companyListAfterHeaderFiltering, companyListAfterFooterFiltering,
                "all the objects details must be the same");
        Assertions.assertEquals(companyListAfterHiringFilter, companyListAfterHiringFilter2,
                "all the objects details must be the same");

    }
}
