package BaseTest;

import homework.staff.pages.DriverGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {
    protected abstract String getUrl();

    @BeforeEach
    public void setUp() {
        DriverGenerator.initializeBrowser(getUrl());
    }

    @AfterEach
    public void tearDown() {
        DriverGenerator.quitDriver();
    }
}
