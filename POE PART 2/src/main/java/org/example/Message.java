package org.example;

import javax.swing.JOptionPane;
import java.util.*;
import java.io.*;

public class Message {
    // Instance variables to store message data
    private String messageID, messageHash, recipient, message;

    // Static variables to track all messages across all instances
    private static int totalMessages = 0;  // Counter for total messages sent
    private static List<Message> sentMessages = new ArrayList<>();  // List to store all sent messages

    // Here is a constructor to initialize a new Message object
    public Message(String messageID, String recipient, String message) {
        this.messageID = messageID;        // Unique identifier for the message
        this.recipient = recipient;        // Recipient's phone number
        this.message = message;            // The actual message content
        this.messageHash = createMessageHash();  // Generate hash automatically when message is created
    }

    /**
     * Validates if the message ID meets requirements
     * @return true if message ID is not null and the length is <= 10 characters
     */
    public boolean checkMessageID() {
        if (messageID == null) return false;
        return messageID.length() <= 10;
    }

    /**
     * Validates recipient cell phone number format
     * The requirements is that it must start with '+', followed by 10-12 digits
     * @return true if recipient number is properly formatted
     */
    public boolean checkRecipientCell() {
        if (recipient == null) return false;
        if (!recipient.startsWith("+")) return false;

        String numberPart = recipient.substring(1);  // Remove the '+' prefix
        return numberPart.length() >= 10 && numberPart.length() <= 12 && numberPart.matches("\\d+");
    }

    /**
     * This creates a unique hash for the message based on specific format
     * Format: First2CharsOfMessageID:MessageNumber:FirstWord:LastWord
     * Example: "QQ:1:HI:TONIGHT" for message "Hi Mike, can you join us for dinner tonight"
     * @return the generated message hash string
     */
    public String createMessageHash() {
        if (message == null || messageID == null) return "";

        // This get first two characters of message ID
        String firstTwo = messageID;
        if (messageID.length() > 2) {
            firstTwo = messageID.substring(0, 2);
        }

        // This get message number (incremental counter)
        int msgNumber = totalMessages + 1;

        // This get first and last words from the message
        String[] words = message.trim().split("\\s+");  // Split by whitespace
        String firstWord = "";
        String lastWord = "";

        if (words.length > 0) {
            firstWord = words[0].toUpperCase();  // Convert first word to uppercase
            if (words.length > 1) {
                lastWord = words[words.length - 1].toUpperCase();  // Convert last word to uppercase
            } else {
                lastWord = firstWord; // If only one word, use it for both first and last
            }
        }

        // This combine all parts into the final hash format
        return firstTwo + ":" + msgNumber + ":" + firstWord + ":" + lastWord;
    }

    /**
     * This sends the message by adding it to sent messages list and incrementing counter
     * @return success message string
     */
    public String sendMessage() {
        totalMessages++;  // Increment total messages counter
        sentMessages.add(this);  // Add this message to sent messages list
        return "Message successfully sent.";
    }

    /**
     * This stores the message to a text file for persistence
     * @return success or error message string
     */
    public String storeMessage() {
        try {
            // Append message details to file (creates file if it doesn't exist)
            FileWriter file = new FileWriter("stored_messages.txt", true);
            file.write("MessageID: " + messageID + ", Hash: " + messageHash + ", Recipient: " + recipient + ", Message: " + message + "\n");
            file.close();
            return "Message successfully stored.";
        } catch (IOException e) {
            return "Error storing message.";
        }
    }

    /**
     * This validates that the message length does not exceed 250 characters
     * @return appropriate validation message based on message length
     */
    public String validateMessageLength() {
        if (message == null) return "Message is null";
        if (message.length() <= 250) {
            return "Message ready to send.";  // Success case
        } else {
            int excessChars = message.length() - 250;  // Calculate how many characters over limit
            return "Message exceeds 250 characters by " + excessChars + ", please reduce size.";  // Failure case
        }
    }

    /**
     * This validates recipient phone number format and returns appropriate message
     * @return validation success or failure message
     */
    public String validateRecipientNumber() {
        if (checkRecipientCell()) {
            return "Cell phone number successfully captured.";  // Success case
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";  // Failure case
        }
    }

    /**
     * This handles message disregard action
     * @return instruction message for disregarding message
     */
    public String disregardMessage() {
        return "Press 0 to delete message.";
    }

    /**
     * This generates a formatted string of all sent messages
     * @return formatted string containing all sent message details
     */
    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages sent.";  // Return message if no messages have been sent
        }

        String result = "All Sent Messages:\n\n";
        for (int i = 0; i < sentMessages.size(); i++) {
            Message msg = sentMessages.get(i);
            result += "Message " + (i + 1) + ":\n" +
                    "MessageID: " + msg.messageID + "\n" +
                    "Message Hash: " + msg.messageHash + "\n" +
                    "Recipient: " + msg.recipient + "\n" +
                    "Message: " + msg.message + "\n\n";
        }
        return result;
    }

    /**
     *  This returns the total number of messages sent across all instances
     * @return count of total messages sent
     */
    public static int returnTotalMessages() {
        return totalMessages;
    }

    //These are GETTER METHODS which Provide access to private instance variables

    /**
     * @return the message ID
     */
    public String getMessageID() { return messageID; }

    /**
     * @return the message hash
     */
    public String getMessageHash() { return messageHash; }

    /**
     * @return the recipient phone number
     */
    public String getRecipient() { return recipient; }

    /**
     * @return the message content
     */
    public String getMessage() { return message; }
}