package homework.staff.tests;

import BaseTest.BaseTest;
import homework.staff.pages.DriverGenerator;
import homework.staff.pages.ResultPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class TestFilterFields extends FilterTest {

    @ParameterizedTest
    @CsvSource({
            "By cities",
            "Job category",
            "Job special tag",
            "Specialist level",
            "Job salary",
            "Job types",
            "Job terms",
    })
    public void filtersValidation(String filterType) {
        DriverGenerator.getDriver().get("https://staff.am/jobs");
        ResultPage jobsPage = new ResultPage();

        String filterName1 = jobsPage.getRandomItem(filterType);
        String filterName2 = jobsPage.getRandomItem(filterType);

        while (filterName1 != null && filterName1.equals(filterName2)) {
            filterName2 = jobsPage.getRandomItem(filterType);
        }
        // First filter
        jobsPage.selectFilter(filterType, filterName1);
        int expectedFirst = jobsPage.getCountOfSelectedFilter(filterType, filterName1);
        int actualFirst = jobsPage.getCompaniesCount();
        Assertions.assertEquals(expectedFirst, actualFirst,
                "Jobs count after applying first filter should match filter count");
        System.out.println("All jobs expected count -> " + expectedFirst);
        System.out.println("All jobs count after first filtering -> " + actualFirst);
        System.out.println("_________________________________________________________");
        // Second filter
        jobsPage.selectFilter(filterType, filterName2);
        int expectedSecond = jobsPage.getCountOfSelectedFilter(filterType, filterName1)
                + jobsPage.getCountOfSelectedFilter(filterType, filterName2);
        int actualSecond = jobsPage.getCompaniesCount();
        Assertions.assertEquals(expectedSecond, actualSecond,
                "Jobs count after applying both filters should match combined filter count");
        System.out.println("All jobs expected count -> " + expectedSecond);
        System.out.println("All jobs count after second filtering -> " + actualSecond);
        System.out.println("_________________________________________________________");
        // Remove first filter
        jobsPage.selectFilter(filterType, filterName1);
        int expectedAfterRemove = jobsPage.getCountOfSelectedFilter(filterType, filterName2);
        int actualAfterRemove = jobsPage.getCompaniesCount();
        Assertions.assertEquals(expectedAfterRemove, actualAfterRemove,
                "Jobs count after removing first filter should match remaining filter count");
        System.out.println("All jobs expected count -> " + expectedAfterRemove);
        System.out.println("All jobs count after first filter removing -> " + actualAfterRemove);
        System.out.println("_________________________________________________________");
        jobsPage.clearFilter();
        System.out.println("All filters cleared. Final count: " + jobsPage.getCompaniesCount());
    }
}

