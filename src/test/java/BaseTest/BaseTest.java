package BaseTest;

import homework.staff.pages.DriverGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {
<<<<<<< HEAD:src/test/java/BaseTest/BaseTest.java
=======

>>>>>>> main:src/test/java/homework28_03/BaseTest.java
    @BeforeEach
    public void setUp() {
        DriverGenerator.initializeBrowser("https://staff.am");
    }

    @AfterEach
    public void tearDown() {
        DriverGenerator.quitDriver();
    }
}
