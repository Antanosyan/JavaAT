package homework.staff.tests;

;
import BaseTest.BaseTest;
import homework.staff.pages.JobsPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class TestFilterFields extends BaseTest {
    @Override
    protected String getUrl() {
        return "https://staff.am/jobs/";
    }

    @ParameterizedTest
    @CsvSource({
            "Job category, Legal, Human Resources",
            "Job special tag, Application without a CV, Doctoral degree",
            "Specialist level, Junior, Mid level",
            "Job salary, Not Mentioned, Mentioned",
            "Job types, Fixed term contract, Full time",
            "Job terms, Freelance, Permanent ",
            "By cities, Goris, Yerevan"
    })
    public void filtersValidation(String filterType, String filterName1, String filterName2) {
        JobsPage jobsPage = new JobsPage();

        jobsPage.selectFilter(filterType, filterName1);
        int exceptedCountAfterFirstFilter = jobsPage.getCountOfSelectedFilter(filterType, filterName1);
        int actualCountAfterFirstFilter = jobsPage.getCompaniesCount();
        System.out.println("All jobs expected count -> " + exceptedCountAfterFirstFilter);
        System.out.println("All jobs count after first filtering -> " + actualCountAfterFirstFilter);
        System.out.println("_________________________________________________________");
        Assertions.assertEquals(exceptedCountAfterFirstFilter, actualCountAfterFirstFilter
                , "Jobs count in filter section should be equals to count of jobs after first filtering");

        jobsPage.selectFilter(filterType, filterName2);
        int exceptedCountAfterSecondFilter = jobsPage.getCountOfSelectedFilter(filterType, filterName1)
                + jobsPage.getCountOfSelectedFilter(filterType, filterName2);
        int actualCountAfterSecondFilter = jobsPage.getCompaniesCount();
        System.out.println("All jobs expected count -> " + exceptedCountAfterSecondFilter);
        System.out.println("All jobs count after second filtering -> " + actualCountAfterSecondFilter);
        System.out.println("_________________________________________________________");
        Assertions.assertEquals(exceptedCountAfterSecondFilter, actualCountAfterSecondFilter
                , "Jobs count sum in filter section should be equals to count of jobs after both filtering");

        jobsPage.removeFilter(filterType, filterName1);
        int exceptedCountAfterRemoveFirstFilter = jobsPage.getCountOfSelectedFilter(filterType, filterName2);
        int actualCountAfterRemoveFirstFilter = jobsPage.getCompaniesCount();
        System.out.println("All jobs expected count -> " + exceptedCountAfterRemoveFirstFilter);
        System.out.println("All jobs count after first filter removing -> " + actualCountAfterRemoveFirstFilter);
        System.out.println("_________________________________________________________");
        Assertions.assertEquals(exceptedCountAfterRemoveFirstFilter, actualCountAfterRemoveFirstFilter
                , "Jobs count in filter section should be equals to count of jobs after unselected first");

        jobsPage.setClearFilter();
        System.out.println("All jobs count after removing filters -> " + jobsPage.getCompaniesCount());
        System.out.println("_________________________________________________________");
    }
}