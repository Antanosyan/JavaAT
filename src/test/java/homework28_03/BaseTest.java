package homework28_03;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {
    @BeforeEach
    public void setUp() {
        DriverGenerator.getDriver();
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        DriverGenerator.quitDriver();
    }
}
