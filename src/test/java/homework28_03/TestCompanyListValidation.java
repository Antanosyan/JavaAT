package homework28_03;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestCompanyListValidation extends BaseTest {

    @Test
    public void testCompany() throws Exception {
        //step1-2
        SearchResultsPage searchResultsPage = new SearchResultsPage();
        searchResultsPage.header.selectHeaderCategory("Companies")
                .openViewMoreSection()
                .selectIndustryFilter("Sport");
        Thread.sleep(5000);
        //step3
        List<Company> companyListAfterHeaderFiltering = searchResultsPage.getCompamyList();
        System.out.println(companyListAfterHeaderFiltering);
        //step4
        searchResultsPage.enterHiring();
        Thread.sleep(5000);

        List<Company> companyListAfterHiringFilter = searchResultsPage.getCompamyList();
        System.out.println(companyListAfterHiringFilter);
        Thread.sleep(5000);
        //step5-6  (2-4)
        searchResultsPage.footer.selectFooterCategory()
                .selectIndustryFilter("Sport");
        List<Company> companyListAfterFooterFiltering = searchResultsPage.getCompamyList();
        System.out.println(companyListAfterFooterFiltering);

        searchResultsPage.enterHiring();
        Thread.sleep(5000);
        List<Company> companyListAfterHiringFilter2 = searchResultsPage.getCompamyList();
        System.out.println(companyListAfterHiringFilter2);

        Assertions.assertEquals(companyListAfterHeaderFiltering, companyListAfterFooterFiltering,
                "all the objects details must be the same");
        Assertions.assertEquals(companyListAfterHiringFilter, companyListAfterHiringFilter2,
                "all the objects details must be the same");

    }
}
