package org.example;

import javax.swing.*;
import java.util.*;

public class Main {

    // Current user of the application
    private static LogIn currentUser = null;
    // Counter for total messages sent during session
    private static int totalMessagesSent = 0;

    // Arrays to store different types of messages
    private static Message[] sentMessages = new Message[1000];       // These are the successfully sent messages
    private static Message[] disregardedMessages = new Message[1000]; // These are messages that the user chose to ignore
    private static Message[] storedMessages = new Message[1000];     // These are the messages that are saved for later sending

    // Arrays to track message identifiers
    private static String[] messageHashes = new String[1000];        // Unique hash codes for messages
    private static String[] messageIDs = new String[1000];           // Unique IDs for messages

    // These are counters for each array to track how many items are stored
    private static int sentMessagesCount = 0;
    private static int disregardedMessagesCount = 0;
    private static int storedMessagesCount = 0;
    private static int messageHashesCount = 0;
    private static int messageIDsCount = 0;

    // This is the main method which is the entry point of the application
    public static void main(String[] args) {
        showInfo("Welcome to QuickChat.");

        // This is for the attempt to login and if successful, run the main application
        if (loginProcess()) {
            runApplicationLoop();
        } else {
            showError("Login failed. Application exiting.");
        }
    }

    // Handles user registration and login process
    private static boolean loginProcess() {
        // Get user's personal information
        String firstName = ask("Enter your first name:", "Registration");
        if (firstName == null) return false;

        String lastName = ask("Enter your last name:", "Registration");
        if (lastName == null) return false;

        // Create new user account
        currentUser = new LogIn(firstName, lastName);

        // Get account credentials
        String username = ask("Register - Enter username:", "Registration");
        String password = ask("Register - Enter password:", "Registration");
        String cell = ask("Register - Enter cell phone number (with international code):", "Registration");

        if (username == null || password == null || cell == null) return false;

        //This attempts to register user
        String regResult = currentUser.registerUser(username, password, cell);
        showInfo(regResult);

        if (!regResult.equals("User registered successfully.")) return false;

        // Verify login with registered credentials
        String inputUser = ask("Login - Enter username:", "Login");
        String inputPass = ask("Login - Enter password:", "Login");
        if (inputUser == null || inputPass == null) return false;

        String loginStatus = currentUser.loginUser(inputUser, inputPass);
        showInfo(loginStatus);

        // Return true only if login was successful
        return loginStatus.contains("Welcome");
    }

    // Main application loop that displays menu as well as handles user choices
    private static void runApplicationLoop() {
        boolean running = true;

        while (running) {
            // Display menu options to user
            int choice = askInt("""
                === QuickChat Menu ===
                1) Send Message
                2) Show recently sent messages
                3) Display Sender/Recipient of All Sent Messages
                4) Display Longest Sent Message
                5) Search Message by ID
                6) Search Messages by Recipient
                7) Delete Message by Hash
                8) Display Full Report
                9) Quit
                Select an option:
                """, "Menu");

            // Process user's menu choice
            if (choice == 1) {
                sendMessages();
            } else if (choice == 2) {
                showRecentlySentMessages();
            } else if (choice == 3) {
                displaySenderRecipientAllSent();
            } else if (choice == 4) {
                displayLongestSentMessage();
            } else if (choice == 5) {
                searchMessageByID();
            } else if (choice == 6) {
                searchMessagesByRecipient();
            } else if (choice == 7) {
                deleteMessageByHash();
            } else if (choice == 8) {
                displayFullReport();
            } else if (choice == 9) {
                showFinalSummary();
                running = false;
            } else {
                showError("Invalid choice");
            }
        }

        showInfo("Thank you for using QuickChat. Goodbye!");
    }

    // Handles sending multiple messages in one session
    private static void sendMessages() {
        // Ask how many messages user wants to send
        int count = askInt("How many messages do you wish to send?", "Send Messages");
        if (count <= 0) count = 1;

        // Array to track messages in current session
        Message[] session = new Message[count];
        int sessionCount = 0;

        // Process each message
        for (int i = 0; i < count; i++) {
            String r = ask("Enter recipient number (+27...):", "Message " + (i + 1));
            String txt = ask("Enter your message (max 250 chars):", "Message " + (i + 1));
            if (r == null || txt == null) continue;

            // Create new message with generated ID
            Message msg = new Message(generateMessageID(i), r, txt, i);

            // Validate message length
            String check = msg.validateMessageLength();
            if (!check.equals("Message ready to send.")) {
                showError(check);
                continue;
            }

            // Ask user what to do with the message they typed
            int action = askInt("""
                === Message Action ===
                1) Send Message
                2) Disregard Message
                3) Store Message to send later
                Select an option:
                """, "Message Action");

            handleMessageAction(msg, action, session, sessionCount);
            if (action == 1) {
                session[sessionCount] = msg;
                sessionCount++;
            }
        }

        // Show summary of sent messages in this session
        if (sessionCount > 0) {
            showScrollable(makeSessionSummary(session, sessionCount), "Session Sent Messages");
        }
    }

    // Processes user's choice for what to do with a message
    private static void handleMessageAction(Message msg, int action, Message[] session, int sessionCount) {
        if (action == 1) {
            // Send the message
            msg.sentMessage();
            totalMessagesSent++;

            // Add to sent messages array
            if (sentMessagesCount < sentMessages.length) {
                sentMessages[sentMessagesCount] = msg;
                sentMessagesCount++;
            }

            // Add to message IDs array
            if (messageIDsCount < messageIDs.length) {
                messageIDs[messageIDsCount] = msg.getMessageID();
                messageIDsCount++;
            }

            // Add to message hashes array
            if (messageHashesCount < messageHashes.length) {
                messageHashes[messageHashesCount] = msg.getMessageHash();
                messageHashesCount++;
            }

            showInfo("Message sent successfully!\n" + msg.getFullDetails());
        } else if (action == 2) {
            // Add to disregarded messages array
            if (disregardedMessagesCount < disregardedMessages.length) {
                disregardedMessages[disregardedMessagesCount] = msg;
                disregardedMessagesCount++;
            }
            showInfo(msg.disregardMessage());
        } else if (action == 3) {
            // Add to stored messages array
            if (storedMessagesCount < storedMessages.length) {
                storedMessages[storedMessagesCount] = msg;
                storedMessagesCount++;
            }
            showInfo(msg.storeMessage());
        } else {
            showError("Invalid action. Message cancelled.");
        }
    }

    // Displays all recently sent messages
    private static void showRecentlySentMessages() {
        if (sentMessagesCount == 0) {
            showInfo("No messages sent yet.");
            return;
        }

        StringBuilder sb = new StringBuilder("Recently Sent Messages:\n\n");
        for (int i = 0; i < sentMessagesCount; i++) {
            sb.append("Message ").append(i + 1).append(":\n")
                    .append("To: ").append(sentMessages[i].getRecipient()).append("\n")
                    .append("Message: ").append(sentMessages[i].getMessage()).append("\n\n");
        }

        showScrollable(sb.toString(), "Recently Sent Messages");
    }

    // Shows sender and recipient information for all sent messages
    private static void displaySenderRecipientAllSent() {
        if (sentMessagesCount == 0) {
            showInfo("No sent messages available.");
            return;
        }

        StringBuilder sb = new StringBuilder("Sender and Recipient of All Sent Messages:\n\n");
        for (int i = 0; i < sentMessagesCount; i++) {
            sb.append("To: ").append(sentMessages[i].getRecipient()).append("\n");
        }

        showScrollable(sb.toString(), "Sent Messages Overview");
    }

    // Finds and displays the longest message sent
    private static void displayLongestSentMessage() {
        if (sentMessagesCount == 0) {
            showInfo("No sent messages available.");
            return;
        }

        Message longest = sentMessages[0];
        for (int i = 1; i < sentMessagesCount; i++) {
            if (sentMessages[i].getMessage().length() > longest.getMessage().length()) {
                longest = sentMessages[i];
            }
        }

        showInfo("Longest Sent Message:\n\nRecipient: " + longest.getRecipient() +
                "\nMessage: " + longest.getMessage() +
                "\nLength: " + longest.getMessage().length() + " characters");
    }

    // Searches for a specific message using its ID
    private static void searchMessageByID() {
        String id = ask("Enter Message ID to search:", "Search Message");
        if (id == null || id.isBlank()) return;

        // Search through sent messages
        for (int i = 0; i < sentMessagesCount; i++) {
            if (sentMessages[i].getMessageID().equals(id)) {
                showInfo("Message Found:\n\nRecipient: " + sentMessages[i].getRecipient() +
                        "\nMessage: " + sentMessages[i].getMessage());
                return;
            }
        }

        showError("Message with ID '" + id + "' not found.");
    }

    // Finds all messages sent to a specific recipient
    private static void searchMessagesByRecipient() {
        String r = ask("Enter recipient number to search:", "Search by Recipient");
        if (r == null || r.isBlank()) return;

        StringBuilder sb = new StringBuilder("Messages for " + r + ":\n\n");
        boolean found = false;

        // Search sent messages
        for (int i = 0; i < sentMessagesCount; i++) {
            if (sentMessages[i].getRecipient().equals(r)) {
                sb.append("SENT: ").append(sentMessages[i].getMessage()).append("\n\n");
                found = true;
            }
        }

        // Search stored messages
        for (int i = 0; i < storedMessagesCount; i++) {
            if (storedMessages[i].getRecipient().equals(r)) {
                sb.append("STORED: ").append(storedMessages[i].getMessage()).append("\n\n");
                found = true;
            }
        }

        if (found) showScrollable(sb.toString(), "Messages for " + r);
        else showError("No messages found for recipient: " + r);
    }

    // Deletes a message using its unique hash
    private static void deleteMessageByHash() {
        String hash = ask("Enter Message Hash to delete:", "Delete Message");
        if (hash == null || hash.isBlank()) return;

        // Search and remove from sentMessages array
        for (int i = 0; i < sentMessagesCount; i++) {
            if (sentMessages[i].getMessageHash().equals(hash)) {
                // Shift elements to fill the gap
                for (int j = i; j < sentMessagesCount - 1; j++) {
                    sentMessages[j] = sentMessages[j + 1];
                }
                sentMessages[sentMessagesCount - 1] = null;
                sentMessagesCount--;

                showInfo("Message deleted:\n" + sentMessages[i].getMessage());
                return;
            }
        }

        showError("Message with hash '" + hash + "' not found.");
    }

    // Shows complete report of all sent messages with full details
    private static void displayFullReport() {
        if (sentMessagesCount == 0) {
            showInfo("No sent messages available for report.");
            return;
        }

        StringBuilder sb = new StringBuilder("Full Sent Messages Report:\n\n");
        for (int i = 0; i < sentMessagesCount; i++) {
            sb.append("Message ").append(i + 1).append(":\n")
                    .append("Message Hash: ").append(sentMessages[i].getMessageHash()).append("\n")
                    .append("Recipient: ").append(sentMessages[i].getRecipient()).append("\n")
                    .append("Message: ").append(sentMessages[i].getMessage()).append("\n")
                    .append("Message ID: ").append(sentMessages[i].getMessageID()).append("\n\n");
        }

        showScrollable(sb.toString(), "Full Messages Report");
    }

    // Displays final summary when user quits the application
    private static void showFinalSummary() {
        showInfo("Session Summary:\nTotal messages sent: " + totalMessagesSent +
                "\nTotal sent messages in system: " + sentMessagesCount +
                "\nThank you for using QuickChat!");
    }
    // Displays input dialog and returns user's text input
    private static String ask(String msg, String title) {
        return JOptionPane.showInputDialog(null, msg, title, JOptionPane.QUESTION_MESSAGE);
    }

    // Gets integer input from user with error handling
    private static int askInt(String msg, String title) {
        try {
            return Integer.parseInt(ask(msg, title));
        } catch (Exception e) {
            return 0; // Return 0 if input is invalid
        }
    }

    // Displays information message to user
    private static void showInfo(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    // Displays error message to user
    private static void showError(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Displays a scrollable text area for large amounts of text
    private static void showScrollable(String msg, String title) {
        JTextArea ta = new JTextArea(msg);
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);

        JScrollPane sp = new JScrollPane(ta);
        sp.setPreferredSize(new java.awt.Dimension(500, 400));

        JOptionPane.showMessageDialog(null, sp, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // Creates a summary of messages sent in current session
    private static String makeSessionSummary(Message[] messages, int count) {
        StringBuilder sb = new StringBuilder("Total messages sent in this session: ")
                .append(count).append("\n\n");

        for (int i = 0; i < count; i++) {
            sb.append("Message ").append(i + 1).append(":\n")
                    .append(messages[i].getFullDetails()).append("\n");
        }
        return sb.toString();
    }

    // Generates unique message ID combining counter and random number
    private static String generateMessageID(int num) {
        String counter = String.format("%02d", num);
        String randomPart = String.format("%08d", new Random().nextInt(100000000));
        return counter + randomPart;
    }
}