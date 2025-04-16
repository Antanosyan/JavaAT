package homework.staff.tests;

import BaseTest.BaseTest;
import homework.staff.pages.ResultPage;
import org.junit.jupiter.api.AfterEach;

public class FilterTest extends BaseTest {
    ResultPage resultPage = new ResultPage();

    @AfterEach
    public void clearFields() {
        resultPage.clearFilter();
    }
}
