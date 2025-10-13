package org.example;

import javax.swing.*;
import java.util.*;

public class Main {
    // Static variables to track application state
    private static LogIn currentUser = null;        // Currently logged-in user
    private static int messagesProcessed = 0;       // Counter for processed messages

    /**
     * Main method - Entry point of the QuickChat application
     * Handles the overall application flow from login to shutdown
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to QuickChat.");

        // Attempt to log in user; if successful, run the main application loop
        if (loginProcess(scanner)) {
            runApplicationLoop(scanner);
        } else {
            System.out.println("Login failed. Application exiting.");
        }

        scanner.close();  // Clean up scanner resource
    }

    /**
     * Handles the complete login process including registration and authentication
     * @param scanner Scanner object for user input
     * @return true if login successful, false otherwise
     */
    private static boolean loginProcess(Scanner scanner) {
        // Collect user personal information
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        // Create LogIn object with user's name
        currentUser = new LogIn(firstName, lastName);

        // Registration Phase - Collect and validate credentials
        System.out.print("Register - Enter username: ");
        String regUsername = scanner.nextLine();

        System.out.print("Register - Enter password: ");
        String regPassword = scanner.nextLine();

        System.out.print("Register - Enter cell phone number (with international code): ");
        String regCell = scanner.nextLine();

        // Attempt to register user with provided credentials
        String regResult = currentUser.registerUser(regUsername, regPassword, regCell);
        System.out.println(regResult);

        // If registration fails, exit login process
        if (!regResult.equals("User registered successfully.")) {
            return false;
        }

        // Login Phase - Authenticate with registered credentials
        System.out.println("\n-- Login --");
        System.out.print("Login - Enter username: ");
        String inputUsername = scanner.nextLine();

        System.out.print("Login - Enter password: ");
        String inputPassword = scanner.nextLine();

        // Attempt to log in with provided credentials
        String loginStatus = currentUser.loginUser(inputUsername, inputPassword);
        System.out.println(loginStatus);

        // Return true only if login was successful (contains welcome message)
        return loginStatus.contains("Welcome");
    }

    /**
     * Main application loop that displays menu and handles user choices
     * Uses WHILE LOOP to keep showing menu until user chooses to quit
     * @param scanner Scanner object for user input
     */
    private static void runApplicationLoop(Scanner scanner) {
        boolean running = true;  // Control variable for the main loop

        // WHILE LOOP: Continuously displays menu until user chooses to quit
        while (running) {
            displayMenu();  // Show the main menu options
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline character

            // Handle user's menu choice
            if (choice == 1) {
                sendMessages(scanner);  // Option 1: Send messages
            } else if (choice == 2) {
                // Option 2: Show recently sent messages (future feature)
                System.out.println("Coming Soon.");
            } else if (choice == 3) {
                // Option 3: Quit application
                running = false;  // Exit the while loop
                showFinalSummary();  // Display goodbye summary
            } else {
                System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }

        System.out.println("Thank you for using QuickChat. Goodbye!");
    }

    /**
     * Displays the main menu options to the user
     */
    private static void displayMenu() {
        System.out.println("\n=== QuickChat Menu ===");
        System.out.println("1) Send Message");
        System.out.println("2) Show recently sent messages");
        System.out.println("3) Quit");
        System.out.print("Select an option: ");
    }

    /**
     * Handles the process of sending multiple messages
     * Uses FOR LOOP to process the specified number of messages
     * @param scanner Scanner object for user input
     */
    private static void sendMessages(Scanner scanner) {
        // Get how many messages user wants to send
        System.out.print("How many messages do you wish to send? ");
        int messageCount = scanner.nextInt();
        scanner.nextLine();  // Consume newline character

        // Validate message count
        if (messageCount <= 0) {
            System.out.println("Number of messages must be at least 1. Setting to 1.");
            messageCount = 1;
        }

        // Lists to track different types of processed messages
        List<Message> sentMessages = new ArrayList<>();    // Messages that were sent
        List<Message> storedMessages = new ArrayList<>();  // Messages that were stored

        // FOR LOOP: Process each message one by one
        for (int i = 0; i < messageCount; i++) {
            System.out.println("\nCreating message " + (i + 1) + " of " + messageCount);

            // PHASE 1: Recipient Number Validation
            String recipient;
            boolean validRecipient = false;

            // Keep asking for recipient until valid or user chooses to skip
            do {
                System.out.print("Enter recipient number (with international code e.g., +27): ");
                recipient = scanner.nextLine();

                // Create temporary message to validate recipient number format
                Message tempMessage = new Message(generateMessageID(), recipient, "temp");
                String validationResult = tempMessage.validateRecipientNumber();

                if (validationResult.equals("Cell phone number successfully captured.")) {
                    // Valid recipient number - proceed
                    System.out.println(validationResult);
                    validRecipient = true;
                } else {
                    // Invalid recipient number - give user option to retry or continue
                    System.out.println(validationResult);
                    System.out.print("Do you want to try again? (y/n): ");
                    String tryAgain = scanner.nextLine();
                    if (!tryAgain.equalsIgnoreCase("y")) {
                        break;  // User chooses to continue with invalid number
                    }
                }
            } while (!validRecipient);

            // PHASE 2: Message Content Input and Validation
            System.out.print("Enter your message (max 250 characters): ");
            String messageText = scanner.nextLine();

            // Create the actual message object
            Message message = new Message(generateMessageID(), recipient, messageText);

            // Validate message length
            String lengthValidation = message.validateMessageLength();
            System.out.println(lengthValidation);

            // Only proceed to action selection if message length is valid
            if (lengthValidation.equals("Message ready to send.")) {
                // PHASE 3: Action Selection
                int action = showMessageActionOptionsTerminal();

                if (action == 1) {
                    // Send Message action
                    String sendResult = message.sendMessage();
                    sentMessages.add(message);
                    messagesProcessed++;
                    System.out.println(sendResult);
                } else if (action == 2) {
                    // Disregard Message action
                    String disregardResult = message.disregardMessage();
                    System.out.println(disregardResult);
                } else if (action == 3) {
                    // Store Message action
                    String storeResult = message.storeMessage();
                    storedMessages.add(message);
                    System.out.println(storeResult);
                }
            } else {
                // Message too long - cancel this message
                System.out.println("Message creation cancelled due to length validation failure.");
            }
        }

        // PHASE 4: Final Summary Display
        // Show JOptionPane with all sent messages if any were sent
        if (!sentMessages.isEmpty()) {
            showMessageSummary(sentMessages);
        }
    }

    /**
     * Displays action options for a message in the terminal
     * @return user's selected action (1, 2, or 3)
     */
    private static int showMessageActionOptionsTerminal() {
        System.out.println("\n=== Message Action ===");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message");
        System.out.print("Select an option for this message: ");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        return choice;
    }

    /**
     * Displays a summary of all sent messages in a JOptionPane dialog
     * Formats the output to match the required screenshot format
     * @param sentMessages List of messages that were sent
     */
    private static void showMessageSummary(List<Message> sentMessages) {
        StringBuilder summary = new StringBuilder();
        summary.append("Total messages sent: ").append(sentMessages.size()).append("\n\n");

        // Format each sent message according to requirements
        for (int i = 0; i < sentMessages.size(); i++) {
            Message msg = sentMessages.get(i);
            summary.append("# Messages: ").append(msg.getMessageID()).append("\n");
            summary.append("Message: ").append(msg.getMessageHash()).append("\n");
            summary.append("Recognize: ").append(msg.getRecipient()).append("\n");
            summary.append("Message: ").append(msg.getMessage()).append("\n");

            // Add separator between messages (except after the last one)
            if (i < sentMessages.size() - 1) {
                summary.append("\n---\n\n");
            }
        }

        // Display the formatted summary in a dialog box
        JOptionPane.showMessageDialog(
                null,
                summary.toString(),
                "Message Summary",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Displays final summary when user quits the application
     */
    private static void showFinalSummary() {
        String summary = "Session Summary:\n" +
                "Total messages processed: " + messagesProcessed + "\n" +
                "Thank you for using QuickChat!";

        JOptionPane.showMessageDialog(
                null,
                summary,
                "Goodbye",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Generates a random message ID
     * @return 10-digit string representing the message ID
     */
    private static String generateMessageID() {
        // Generate random number between 1,000,000,000 and 9,999,999,999
        long id = (long) (Math.random() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(id);
    }
}