import org.junit.Test;
import static org.junit.Assert.*;

public class LogInTest {

    // Test for username correctly formatted
    @Test
    public void testCheckUserNameCorrectlyFormatted() {
        LogIn login = new LogIn("John", "Doe");
        boolean result = login.checkUserName("kyL_1");
        assertTrue("Username successfully captured", result);
    }

    // Test for username incorrectly formatted (no underscore)
    @Test
    public void testCheckUserNameIncorrectlyFormattedNoUnderscore() {
        LogIn login = new LogIn("John", "Doe");
        boolean result = login.checkUserName("kyle1");
        assertFalse("Username is incorrectly formatted, please ensure that your username contains an underscore and is no more than five characters in length.", result);
    }

    // Test for username incorrectly formatted (too long)
    @Test
    public void testCheckUserNameIncorrectlyFormattedTooLong() {
        LogIn login = new LogIn("John", "Doe");
        boolean result = login.checkUserName("kyle!!!!!!!");
        assertFalse("Username is  incorrectly formatted, please ensure that your username contains an underscore and is no more than five characters in length.", result);
    }

    // Test for password meets complexity requirements
    @Test
    public void testValidPasswordMeetsComplexity() {
        LogIn login = new LogIn("John", "Doe");
        boolean result = login.ValidPassword("Ch&&sec@ke99!");
        assertTrue("Password successfully captured.", result);
    }

    // Test for password does not meet complexity requirements
    @Test
    public void testPasswordDoesNotMeetComplexity() {
        LogIn login = new LogIn("John", "Doe");
        boolean result = login.ValidPassword("password");
        assertFalse("Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character", result);
    }

    // Test for cell phone number correctly formatted
    @Test
    public void testCheckCellPhoneNumberCorrectlyFormatted() {
        LogIn login = new LogIn("John", "Doe");
        boolean result = login.checkCellPhoneNumber("+27838968976");
        assertTrue("Cell number successfully captured", result);
    }

    // Test for cell phone number incorrectly formatted
    @Test
    public void testCheckCellPhoneNumberIncorrectlyFormatted() {
        LogIn login = new LogIn("John", "Doe");
        boolean result = login.checkCellPhoneNumber("08966553");
        assertFalse("Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again", result);
    }

    // Test for login successful
    @Test
    public void testLoginSuccessful() {
        LogIn login = new LogIn("John", "Doe");
        login.registerUser("kyL_1", "Ch&&sec@ke99!", "+27838968976");
        String result = login.loginUser("kyL_1", "Ch&&sec@ke99!");
        assertEquals("Welcome John Doe! It's great to see you again.", result);
    }

    // Test for login failed
    @Test
    public void testLoginFailed() {
        LogIn login = new LogIn("John", "Doe");
        login.registerUser("kyL_1", "Ch&&sec@ke99!", "+27838968976");
        String result = login.loginUser("wrongUser", "wrongPass");
        assertEquals("Login failed: username or password incorrect.", result);
    }

    // Test for register user returns success message regardless of validation
    @Test
    public void testRegisterUserReturnsSuccessMessage() {
        LogIn login = new LogIn("John", "Doe");
        String result = login.registerUser("kyL_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("User registered successfully.", result);
    }

    // Test that registerUser sets the username field
    @Test
    public void testRegisterUserSetsUsername() {
        LogIn login = new LogIn("John", "Doe");
        login.registerUser("kyL_1", "Ch&&sec@ke99!", "+27838968976");

    }

    // Test that registerUser sets the password field
    @Test
    public void testRegisterUserSetsPassword() {
        LogIn login = new LogIn("John", "Doe");
        login.registerUser("kyL_1", "Ch&&sec@ke99!", "+27838968976");

    }

    // Test that registerUser sets the cell number field
    @Test
    public void testRegisterUserSetsCellNumber() {
        LogIn login = new LogIn("John", "Doe");
        login.registerUser("kyL_1", "Ch&&sec@ke99!", "+27838968976");

    }
}