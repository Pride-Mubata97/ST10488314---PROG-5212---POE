package org.example;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LogIn {

    // Instance variables to store user information
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String cellNumber;

    // Constructor to initialize first and last name during object creation
    public LogIn(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Method to check if a username is valid: it should contain "_" and be at most 5 characters
    boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    // Method to validate password: password must be at least 8 characters, contain an uppercase letter, a number, and a special character
    boolean ValidPassword(String password) {
        return password != null &&
                password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*");
    }

    // Method to validate the cell phone number format: must start with '+' followed by digits
    boolean checkCellPhoneNumber(String cellNumber) {
        return cellNumber != null && cellNumber.matches("^\\+[0-9]{10,15}$");
    }

    // Method to register a user by validating username, password, and cell number
    public String registerUser(String username, String password, String cellNumber) {
        boolean isUsernameValid = checkUserName(username);
        boolean isPasswordValid = ValidPassword(password);
        boolean isCellNumberValid = checkCellPhoneNumber(cellNumber);

        // Validate username
        if (isUsernameValid) {
            System.out.println("Username successfully captured");
        } else {
            System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
        }

        // Validate password
        if (isPasswordValid) {
            System.out.println("Password successfully captured.");
        } else {
            System.out.println("Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        }

        // Validate cell number
        if (isCellNumberValid) {
            System.out.println("Cell number successfully added.");
        } else {
            System.out.println("Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.");
        }

        // Only store credentials if all validations pass
        if (isUsernameValid && isPasswordValid && isCellNumberValid) {
            this.username = username;
            this.password = password;
            this.cellNumber = cellNumber;
            return "User registered successfully.";
        } else {
            return "Registration failed: Please correct the validation errors above.";
        }
    }

    // Method to authenticate a user based on input username and password
    public String loginUser(String inputUsername, String inputPassword) {
        if (this.username == null || this.password == null) {
            return "Login failed: No registered user found.";
        }

        boolean isUsernameCorrect = this.username.equals(inputUsername);
        boolean isPasswordCorrect = this.password.equals(inputPassword);

        // Check username
        if (isUsernameCorrect) {
            System.out.println("Username is correct.");
        } else {
            System.out.println("Username is incorrect.");
        }

        // Check password
        if (isPasswordCorrect) {
            System.out.println("Password is correct.");
        } else {
            System.out.println("Password is incorrect.");
        }

        // If both username and password match, login is successful
        if (isUsernameCorrect && isPasswordCorrect) {
            return "Welcome " + firstName + " " + lastName + "! It's great to see you again.";
        } else {
            return "Login failed: username or password incorrect.";
        }
    }

    // Getter methods for testing
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCellNumber() {
        return cellNumber;
    }
}