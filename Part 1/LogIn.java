public class LogIn {

    // Instance variables to store user information
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String cellNumber;

    // Here is a constructor to initialize first and last name during object creation
    public LogIn(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Here is a method to check if a username is valid: it should contain "_" and it should be at most 5 characters
    boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Method to validate password: password must be at least 8 characters, contain an uppercase letter, a number, and a special character
    boolean ValidPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*");
    }

    // Here is a method to validate the cell phone number format: must start with '+' followed by country code and number
    boolean checkCellPhoneNumber(String cellNumber) {
        return cellNumber.matches("^\\+\\d{1,3}\\d{1,10}$");
    }

    // Method to register a user by validating username, password, and cell number
    public String registerUser(String username, String password, String cellNumber) {
        // Validate username if it correct it outputs a certain message and if it is inccorect it outputs another message
        if (checkUserName(username)) {
            System.out.println("Username successfully captured");
        } else {
            System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
        }

        // Validate password
        if (ValidPassword(password)) {
            System.out.println("Password successfully captured.");
        } else {
            System.out.println("Password is not correctly formatted please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        }

        // Validate cell number
        if (checkCellPhoneNumber(cellNumber)) {
            System.out.println("Cell number successfully added.");
        } else {
            System.out.println("Cell number incorrectly formatted or does not contain international code.");
        }

        // This is to store the validated user credentials
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;

        return "User registered successfully.";
    }

    // Method to authenticate a user based on input username and password
    public String loginUser(String inputUsername, String inputPassword) {
        // This checks if the username matches
        if (this.username != null && this.username.equals(inputUsername)) {
            System.out.println("Username is correct.");
        } else {
            System.out.println("Username is incorrect.");
        }

        // This check if the password matches
        if (this.password != null && this.password.equals(inputPassword)) {
            System.out.println("Password is correct.");
        } else {
            System.out.println("Password is incorrect.");
        }

        // If both username and password match, login is successful
        if (this.username != null && this.username.equals(inputUsername) &&
                this.password != null && this.password.equals(inputPassword)) {
            return "Welcome " + firstName + " " + lastName + "! It’s great to see you again.";
        } else {
            return "Login failed: username or password incorrect.";
        }
    }
}