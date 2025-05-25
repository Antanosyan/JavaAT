package homework.staff.tests;

import homework.staff.pages.DriverGenerator;
import homework.staff.pages.RegisterPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class RegistrationTest extends BaseTest {
    private final RegisterPage registerPage = new RegisterPage();

    @Test
    @Tag("ui")
    @DisplayName("Register with valid email after invalid input")
    void testRegistration() {
        DriverGenerator.getDriver().get("https://staff.am/");
        registerPage.selectCandidate("Candidate","Register")
                .setPersonalName("Vahe", "Antanosyan")
                .fillBirthday("1987", "July", "13");
        Assertions.assertTrue(registerPage.enterEmailAndCheckInvalidMessage("djhfjk", "The field must be a valid email address.")
                , "Expected an invalid email error message, but it was not displayed.");
        Assertions.assertTrue(registerPage.enterValidEmailAndVerifyNoError("dck87@gmail.com")
                , "Error message should not be displayed for a valid email");
        registerPage.setPassword();
        String expectedStyleOfRegisterButton = registerPage.getRegisterButtonStyle();
        registerPage.agreeToTerms();
        String actualStyleOfRegisterButton = registerPage.getRegisterButtonStyle();
        registerPage.submitRegister();
        Assertions.assertNotEquals(expectedStyleOfRegisterButton, actualStyleOfRegisterButton
                , "Register button style should not match expected value");
    }
}
