package org.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class LogInTest {

    // Test for username correctly formatted
    @Test
    public void testCheckUserNameCorrectlyFormatted() {
        LogIn login = new LogIn("John", "Doe");
        boolean result = login.checkUserName("kyL_1");
        assertTrue("Username should be valid with underscore and 5 characters", result);
    }

    // Test for username incorrectly formatted (no underscore)
    @Test
    public void testCheckUserNameIncorrectlyFormattedNoUnderscore() {
        LogIn login = new LogIn("John", "Doe");
        boolean result = login.checkUserName("kyle1");
        assertFalse("Username should be invalid without underscore", result);
    }

    // Test for username incorrectly formatted (too long)
    @Test
    public void testCheckUserNameIncorrectlyFormattedTooLong() {
        LogIn login = new LogIn("John", "Doe");
        boolean result = login.checkUserName("kyle_12345");
        assertFalse("Username should be invalid when longer than 5 characters", result);
    }

    // Test for null username
    @Test
    public void testCheckUserNameNull() {
        LogIn login = new LogIn("John", "Doe");
        boolean result = login.checkUserName(null);
        assertFalse("Username should be invalid when null", result);
    }

    // Test for password meets complexity requirements
    @Test
    public void testValidPasswordMeetsComplexity() {
        LogIn login = new LogIn("John", "Doe");
        boolean result = login.ValidPassword("Ch&&sec@ke99!");
        assertTrue("Password should be valid with all requirements met", result);
    }

    // Test for password does not meet complexity requirements
    @Test
    public void testPasswordDoesNotMeetComplexity() {
        LogIn login = new LogIn("John", "Doe");
        boolean result = login.ValidPassword("password");
        assertFalse("Password should be invalid without capital, number, and special character", result);
    }

    // Test for null password
    @Test
    public void testValidPasswordNull() {
        LogIn login = new LogIn("John", "Doe");
        boolean result = login.ValidPassword(null);
        assertFalse("Password should be invalid when null", result);
    }

    // Test for cell phone number correctly formatted
    @Test
    public void testCheckCellPhoneNumberCorrectlyFormatted() {
        LogIn login = new LogIn("John", "Doe");
        boolean result = login.checkCellPhoneNumber("+27838968976");
        assertTrue("Cell number should be valid with international format", result);
    }

    // Test for cell phone number incorrectly formatted (no plus)
    @Test
    public void testCheckCellPhoneNumberIncorrectlyFormattedNoPlus() {
        LogIn login = new LogIn("John", "Doe");
        boolean result = login.checkCellPhoneNumber("0838968976");
        assertFalse("Cell number should be invalid without international code", result);
    }

    // Test for cell phone number incorrectly formatted (too short)
    @Test
    public void testCheckCellPhoneNumberIncorrectlyFormattedTooShort() {
        LogIn login = new LogIn("John", "Doe");
        boolean result = login.checkCellPhoneNumber("+123");
        assertFalse("Cell number should be invalid when too short", result);
    }

    // Test for null cell number
    @Test
    public void testCheckCellPhoneNumberNull() {
        LogIn login = new LogIn("John", "Doe");
        boolean result = login.checkCellPhoneNumber(null);
        assertFalse("Cell number should be invalid when null", result);
    }

    // Test for successful registration with valid data
    @Test
    public void testRegisterUserSuccessful() {
        LogIn login = new LogIn("John", "Doe");
        String result = login.registerUser("kyL_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("User registered successfully.", result);
        assertEquals("kyL_1", login.getUsername());
        assertEquals("Ch&&sec@ke99!", login.getPassword());
        assertEquals("+27838968976", login.getCellNumber());
    }

    // Test for failed registration with invalid username
    @Test
    public void testRegisterUserFailedInvalidUsername() {
        LogIn login = new LogIn("John", "Doe");
        String result = login.registerUser("kyle1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Registration failed: Please correct the validation errors above.", result);
        assertNull("Username should not be set when validation fails", login.getUsername());
    }

    // Test for failed registration with invalid password
    @Test
    public void testRegisterUserFailedInvalidPassword() {
        LogIn login = new LogIn("John", "Doe");
        String result = login.registerUser("kyL_1", "password", "+27838968976");
        assertEquals("Registration failed: Please correct the validation errors above.", result);
        assertNull("Password should not be set when validation fails", login.getPassword());
    }

    // Test for failed registration with invalid cell number
    @Test
    public void testRegisterUserFailedInvalidCellNumber() {
        LogIn login = new LogIn("John", "Doe");
        String result = login.registerUser("kyL_1", "Ch&&sec@ke99!", "0838968976");
        assertEquals("Registration failed: Please correct the validation errors above.", result);
        assertNull("Cell number should not be set when validation fails", login.getCellNumber());
    }

    // Test for login successful
    @Test
    public void testLoginSuccessful() {
        LogIn login = new LogIn("John", "Doe");
        login.registerUser("kyL_1", "Ch&&sec@ke99!", "+27838968976");
        String result = login.loginUser("kyL_1", "Ch&&sec@ke99!");
        assertEquals("Welcome John Doe! It's great to see you again.", result);
    }

    // Test for login failed with wrong username
    @Test
    public void testLoginFailedWrongUsername() {
        LogIn login = new LogIn("John", "Doe");
        login.registerUser("kyL_1", "Ch&&sec@ke99!", "+27838968976");
        String result = login.loginUser("wrongUser", "Ch&&sec@ke99!");
        assertEquals("Login failed: username or password incorrect.", result);
    }

    // Test for login failed with wrong password
    @Test
    public void testLoginFailedWrongPassword() {
        LogIn login = new LogIn("John", "Doe");
        login.registerUser("kyL_1", "Ch&&sec@ke99!", "+27838968976");
        String result = login.loginUser("kyL_1", "wrongPass");
        assertEquals("Login failed: username or password incorrect.", result);
    }

    // Test for login when no user is registered
    @Test
    public void testLoginNoUserRegistered() {
        LogIn login = new LogIn("John", "Doe");
        String result = login.loginUser("kyL_1", "Ch&&sec@ke99!");
        assertEquals("Login failed: No registered user found.", result);
    }
}