package org.example;

import javax.swing.JOptionPane;
import java.util.*;
import java.io.*;
import java.nio.file.Files;

public class Message {
    // Message properties - each message has these attributes
    private String messageID;        // Unique identifier for the message
    private String messageHash;      // Hash code generated from message content
    private String recipient;        // Phone number of message receiver
    private String message;          // Actual message text
    private int messageNumber;       // number of the message

    // This is a constructor which creates a new Message object with provided details
    public Message(String messageID, String recipient, String message, int messageNumber) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.message = message;
        this.messageNumber = messageNumber;
        this.messageHash = createMessageHash(); // Generate hash when message is created
    }

    // This is to validate that message ID exists and is exactly 10 characters long
    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10;
    }

    // This is to validate the recipient's phone number format
    public int checkRecipientCell() {
        if (recipient == null) return 0; // Return 0 for null recipient

        // Checks if number starts with '+' (international format)
        if (!recipient.startsWith("+")) return 0;

        //This is going to extract the number part after '+'
        String numberPart = recipient.substring(1);

        // Validate: must contain only digits and be between 10-12 digits long
        boolean valid = numberPart.matches("\\d+") && numberPart.length() >= 10 && numberPart.length() <= 12;
        return valid ? 1 : 0; //and it returns 1 if valid, 0 if invalid
    }

    // Creates a unique hash for the message based on its content and metadata
    public String createMessageHash() {
        if (messageID == null || message == null) return "";

        // Gets the first 2 characters of message ID
        String firstTwoChars = messageID.substring(0, 2);

        // Use the message number
        int msgNum = this.messageNumber;

        // Split message into words and get the first and last words
        String[] words = message.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0].toUpperCase() : "";
        String lastWord = words.length > 1 ? words[words.length - 1].toUpperCase() : firstWord;

        // Combine words for hash
        String combinedWords = firstWord + lastWord;

        // This is the format which will be: [first2Chars]:[messageNumber]:[combinedWords]
        return firstTwoChars + ":" + msgNum + ":" + combinedWords;
    }

    // Confirms message was sent successfully
    public String sentMessage() {
        return "Message successfully sent.";
    }

    // Stores message data in a JSON file for persistence
    public String storeMessage() {
        try {
            // Create JSON formatted string with message data
            String jsonData = String.format(
                    "{\"messageID\": \"%s\", \"messageHash\": \"%s\", \"recipient\": \"%s\", \"message\": \"%s\", \"messageNumber\": %d}",
                    messageID, messageHash, recipient, message, messageNumber
            );

            // Write to file
            FileWriter writer = new FileWriter("stored_messages.json", true);
            writer.write(jsonData + "\n");
            writer.close();

            return "Message successfully stored.";
        } catch (IOException e) {
            return "Error storing message: " + e.getMessage();
        }
    }

    // Validates that the message length doesn't exceed 250 characters
    public String validateMessageLength() {
        if (message == null) return "Message is null";
        if (message.length() > 250) {
            return "Please enter a message of less than 250 characters.";
        }
        return "Message ready to send.";
    }

    // Provides user feedback on recipient number validation
    public String validateRecipientNumber() {
        if (checkRecipientCell() == 1) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Confirms message was not sent
    public String disregardMessage() {
        return "Message disregarded successfully.";
    }

    // Returns formatted string with all message details
    public String getFullDetails() {
        return "MessageID: " + messageID + "\n" +
                "Message Hash: " + messageHash + "\n" +
                "Recipient: " + recipient + "\n" +
                "Message: " + message + "\n";
    }

    // GETTER METHODS which will allow access to private fields from other classes

    // Returns the message's unique ID
    public String getMessageID() { return messageID; }

    // Returns the message's hash code
    public String getMessageHash() { return messageHash; }

    // Returns the recipient's phone number
    public String getRecipient() { return recipient; }

    // Returns the message text content
    public String getMessage() { return message; }

    // Returns the message sequence number
    public int getMessageNumber() { return messageNumber; }
}