package homework.staff.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.RegistrationTestSteps;

public class RegistrationTest {
    RegistrationTestSteps steps = new RegistrationTestSteps();

    @Test
    @DisplayName("Register with valid email after invalid input")
    void testRegistration() {
        steps.openMainPage();
        steps.selectCandidate();
        steps.fillBirthday("1987", "July", "13");
        steps.setFirstName("Vahe");
        steps.setLastName("Antanosyan");
        Assertions.assertTrue(steps.enterEmailAndCheckInvalidMessage("d#4fj", "The field must be a valid email address.")
                , "Expected invalid email error message when attempt to enter password but it was not displayed.");
        Assertions.assertTrue(steps.enterValidEmailAndVerifyNoError("antanosyan1987@gmail.com")
                , "Error message should not be displayed for a valid email");
        steps.fillPasswords();
        String expectedStyleOfRegisterButton = steps.getExpectedRegisterButtonStyle();
        steps.agreeToTerms();
        String actualStyleOfRegisterButton = steps.getActualRegisterButtonStyle();
        steps.submitRegistration();
        Assertions.assertNotEquals(expectedStyleOfRegisterButton, actualStyleOfRegisterButton
                , "Register button style should not match expected value");
    }
}
