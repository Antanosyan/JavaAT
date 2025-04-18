package BaseTest;

import homework.staff.pages.DriverGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {


    @BeforeEach
    public void setUp() {
        DriverGenerator.getDriver();
    }

    @AfterEach
    public void tearDown() {
        DriverGenerator.quitDriver();
    }
}
