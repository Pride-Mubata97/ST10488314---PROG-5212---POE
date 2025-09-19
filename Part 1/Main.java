import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // This is a Scanner object to read input from the console
        Scanner scanner = new Scanner(System.in);

        // This is to prompt the user to enter their first name and read the input
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        // This is to prompt the user to enter their last name and read the input
        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        // This is a new LogIn object with the provided first and last names
        LogIn user = new LogIn(firstName, lastName);

        // This prompts the user to enter their registration details
        System.out.print("Register - Enter username: ");
        String regUsername = scanner.nextLine();

        System.out.print("Register - Enter password: ");
        String regPassword = scanner.nextLine();

        System.out.print("Register - Enter cell phone number (with international code, e.g. +27): ");
        String regCell = scanner.nextLine();

        // This is an attempt to register the user with the entered details
        String regResult = user.registerUser(regUsername, regPassword, regCell);

        // This Prints out the registration result message
        System.out.println(regResult);

        // If the registration fails this stops further execution
        if (!regResult.equals("User registered successfully.")) {
            return;
        }

        // Proceed to Login after successful registration
        System.out.println("\n-- Login -- ");

        // This prompts the user to enter their username for login
        System.out.print("Login - Enter username: ");
        String inputUsername = scanner.nextLine();

        // This prompts the user to enter their password for login
        System.out.print("Login - Enter password: ");
        String inputPassword = scanner.nextLine();

        // Attempt to log in the user with the entered credentials
        String loginStatus = user.loginUser(inputUsername, inputPassword);

        // Print the login status message
        System.out.println(loginStatus);
    }
}

