package homework28_03;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {

    @BeforeEach
    public void setUp() {
        DriverGenerator.initializeBrowser("https://staff.am");
    }

    @AfterEach
    public void tearDown() {
        DriverGenerator.quitDriver();
    }
}
