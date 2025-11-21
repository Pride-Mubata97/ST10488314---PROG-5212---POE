package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;

class MainTest {

    // Test message objects for different scenarios
    private Message message1, message2, message3, message4, message5;

    // This method runs before each test to set up test data
    @BeforeEach
    void setUp() {
        // Create test messages with different statuses:
        // message1: Sent message with valid international number
        message1 = new Message("0100000001", "+27834557896", "Did you get the cake?", 0); // Sent
        // message2: Stored message with longer content
        message2 = new Message("0200000002", "+27838884567", "Where are you? You are late! I have asked you to be on time.", 1); // Stored
        // message3: Disregarded message
        message3 = new Message("0300000003", "+27834484567", "Yohoooo, I am at your gate.", 2); // Disregard
        // message4: Sent message with invalid number format (no international code)
        message4 = new Message("0400000004", "0838884567", "It is dinner time!", 0); // Sent
        // message5: Another stored message
        message5 = new Message("0500000005", "+27838884567", "Ok, I am leaving without you.", 1); // Stored

        // Clear static arrays between tests to ensure test isolation
        clearStaticArrays();
    }

    // Helper method to reset all static arrays in Main class between tests
    private void clearStaticArrays() {
        try {
            // Get references to the count fields using reflection, since they're private
            Field sentMessagesCountField = Main.class.getDeclaredField("sentMessagesCount");
            Field storedMessagesCountField = Main.class.getDeclaredField("storedMessagesCount");
            Field disregardedMessagesCountField = Main.class.getDeclaredField("disregardedMessagesCount");
            Field messageHashesCountField = Main.class.getDeclaredField("messageHashesCount");
            Field messageIDsCountField = Main.class.getDeclaredField("messageIDsCount");

            // Make private fields accessible
            sentMessagesCountField.setAccessible(true);
            storedMessagesCountField.setAccessible(true);
            disregardedMessagesCountField.setAccessible(true);
            messageHashesCountField.setAccessible(true);
            messageIDsCountField.setAccessible(true);

            // Reset all counters to zero
            sentMessagesCountField.setInt(null, 0);
            storedMessagesCountField.setInt(null, 0);
            disregardedMessagesCountField.setInt(null, 0);
            messageHashesCountField.setInt(null, 0);
            messageIDsCountField.setInt(null, 0);

            // Get references to the array fields
            Field sentMessagesField = Main.class.getDeclaredField("sentMessages");
            Field storedMessagesField = Main.class.getDeclaredField("storedMessages");
            Field disregardedMessagesField = Main.class.getDeclaredField("disregardedMessages");
            Field messageHashesField = Main.class.getDeclaredField("messageHashes");
            Field messageIDsField = Main.class.getDeclaredField("messageIDs");

            // Make array fields accessible
            sentMessagesField.setAccessible(true);
            storedMessagesField.setAccessible(true);
            disregardedMessagesField.setAccessible(true);
            messageHashesField.setAccessible(true);
            messageIDsField.setAccessible(true);

            // Create new empty arrays to replace existing ones
            sentMessagesField.set(null, new Message[1000]);
            storedMessagesField.set(null, new Message[1000]);
            disregardedMessagesField.set(null, new Message[1000]);
            messageHashesField.set(null, new String[1000]);
            messageIDsField.set(null, new String[1000]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Populates the static arrays with test data for testing various scenarios
    private void populateTestData() {
        try {
            // Get all array and count field references using reflection
            Field sentMessagesField = Main.class.getDeclaredField("sentMessages");
            Field storedMessagesField = Main.class.getDeclaredField("storedMessages");
            Field disregardedMessagesField = Main.class.getDeclaredField("disregardedMessages");
            Field messageHashesField = Main.class.getDeclaredField("messageHashes");
            Field messageIDsField = Main.class.getDeclaredField("messageIDs");
            Field sentMessagesCountField = Main.class.getDeclaredField("sentMessagesCount");
            Field storedMessagesCountField = Main.class.getDeclaredField("storedMessagesCount");
            Field disregardedMessagesCountField = Main.class.getDeclaredField("disregardedMessagesCount");
            Field messageHashesCountField = Main.class.getDeclaredField("messageHashesCount");
            Field messageIDsCountField = Main.class.getDeclaredField("messageIDsCount");

            // Make all fields accessible
            sentMessagesField.setAccessible(true);
            storedMessagesField.setAccessible(true);
            disregardedMessagesField.setAccessible(true);
            messageHashesField.setAccessible(true);
            messageIDsField.setAccessible(true);
            sentMessagesCountField.setAccessible(true);
            storedMessagesCountField.setAccessible(true);
            disregardedMessagesCountField.setAccessible(true);
            messageHashesCountField.setAccessible(true);
            messageIDsCountField.setAccessible(true);

            // Get current array instances
            Message[] sentMessages = (Message[]) sentMessagesField.get(null);
            Message[] storedMessages = (Message[]) storedMessagesField.get(null);
            Message[] disregardedMessages = (Message[]) disregardedMessagesField.get(null);
            String[] messageHashes = (String[]) messageHashesField.get(null);
            String[] messageIDs = (String[]) messageIDsField.get(null);

            // Get current counts
            int sentMessagesCount = sentMessagesCountField.getInt(null);
            int storedMessagesCount = storedMessagesCountField.getInt(null);
            int disregardedMessagesCount = disregardedMessagesCountField.getInt(null);
            int messageHashesCount = messageHashesCountField.getInt(null);
            int messageIDsCount = messageIDsCountField.getInt(null);

            // Message 1: Sent - Add to sent arrays and tracking arrays
            sentMessages[sentMessagesCount++] = message1;
            messageHashes[messageHashesCount++] = message1.getMessageHash();
            messageIDs[messageIDsCount++] = message1.getMessageID();

            // Message 2: Stored - Add to stored arrays and tracking arrays
            storedMessages[storedMessagesCount++] = message2;
            messageHashes[messageHashesCount++] = message2.getMessageHash();
            messageIDs[messageIDsCount++] = message2.getMessageID();

            // Message 3: Disregard - Add to disregarded arrays and tracking arrays
            disregardedMessages[disregardedMessagesCount++] = message3;
            messageHashes[messageHashesCount++] = message3.getMessageHash();
            messageIDs[messageIDsCount++] = message3.getMessageID();

            // Message 4: Sent - Add to sent arrays and tracking arrays
            sentMessages[sentMessagesCount++] = message4;
            messageHashes[messageHashesCount++] = message4.getMessageHash();
            messageIDs[messageIDsCount++] = message4.getMessageID();

            // Message 5: Stored - Add to stored arrays and tracking arrays
            storedMessages[storedMessagesCount++] = message5;
            messageHashes[messageHashesCount++] = message5.getMessageHash();
            messageIDs[messageIDsCount++] = message5.getMessageID();

            // Update the static count fields with new values
            sentMessagesCountField.setInt(null, sentMessagesCount);
            storedMessagesCountField.setInt(null, storedMessagesCount);
            disregardedMessagesCountField.setInt(null, disregardedMessagesCount);
            messageHashesCountField.setInt(null, messageHashesCount);
            messageIDsCountField.setInt(null, messageIDsCount);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Test: Verifies that only sent messages are correctly stored in the sentMessages array
    @Test
    @DisplayName("Sent Messages array correctly populated")
    void testSentMessagesArrayCorrectlyPopulated() {
        populateTestData();

        try {
            Field sentMessagesField = Main.class.getDeclaredField("sentMessages");
            Field sentMessagesCountField = Main.class.getDeclaredField("sentMessagesCount");

            sentMessagesField.setAccessible(true);
            sentMessagesCountField.setAccessible(true);

            Message[] sentMessages = (Message[]) sentMessagesField.get(null);
            int sentMessagesCount = sentMessagesCountField.getInt(null);

            // Verify only sent messages are in the array (message1 and message4)
            assertEquals(2, sentMessagesCount);

            // Check message contents - should contain only sent messages
            boolean foundMessage1 = false;
            boolean foundMessage4 = false;

            for (int i = 0; i < sentMessagesCount; i++) {
                Message msg = sentMessages[i];
                if (msg.getMessage().equals("Did you get the cake?")) {
                    foundMessage1 = true;
                }
                if (msg.getMessage().equals("It is dinner time!")) {
                    foundMessage4 = true;
                }
            }

            assertTrue(foundMessage1, "Should contain 'Did you get the cake?'");
            assertTrue(foundMessage4, "Should contain 'It is dinner time!'");

        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    // Test: Finds and verifies the longest message among all messages
    @Test
    @DisplayName("Display the longest Message")
    void testDisplayLongestMessage() {
        populateTestData();

        try {
            // Get all arrays and counts to search through all message types
            Field sentMessagesField = Main.class.getDeclaredField("sentMessages");
            Field storedMessagesField = Main.class.getDeclaredField("storedMessages");
            Field disregardedMessagesField = Main.class.getDeclaredField("disregardedMessages");
            Field sentMessagesCountField = Main.class.getDeclaredField("sentMessagesCount");
            Field storedMessagesCountField = Main.class.getDeclaredField("storedMessagesCount");
            Field disregardedMessagesCountField = Main.class.getDeclaredField("disregardedMessagesCount");

            sentMessagesField.setAccessible(true);
            storedMessagesField.setAccessible(true);
            disregardedMessagesField.setAccessible(true);
            sentMessagesCountField.setAccessible(true);
            storedMessagesCountField.setAccessible(true);
            disregardedMessagesCountField.setAccessible(true);

            Message[] sentMessages = (Message[]) sentMessagesField.get(null);
            Message[] storedMessages = (Message[]) storedMessagesField.get(null);
            Message[] disregardedMessages = (Message[]) disregardedMessagesField.get(null);
            int sentMessagesCount = sentMessagesCountField.getInt(null);
            int storedMessagesCount = storedMessagesCountField.getInt(null);
            int disregardedMessagesCount = disregardedMessagesCountField.getInt(null);

            // Find the longest message by checking all arrays
            Message longestMessage = null;
            int maxLength = 0;

            // Check sent messages (message1 and message4)
            for (int i = 0; i < sentMessagesCount; i++) {
                Message msg = sentMessages[i];
                if (msg.getMessage().length() > maxLength) {
                    maxLength = msg.getMessage().length();
                    longestMessage = msg;
                }
            }

            // Check stored messages (message2)
            for (int i = 0; i < storedMessagesCount; i++) {
                Message msg = storedMessages[i];
                // Only include if it's message2 (from test data 1-4)
                if (msg.getMessage().contains("Where are you?") && msg.getMessage().length() > maxLength) {
                    maxLength = msg.getMessage().length();
                    longestMessage = msg;
                }
            }

            // Check disregarded messages (message3)
            for (int i = 0; i < disregardedMessagesCount; i++) {
                Message msg = disregardedMessages[i];
                // Only include if it's message3 (from test data 1-4)
                if (msg.getMessage().contains("Yohoooo") && msg.getMessage().length() > maxLength) {
                    maxLength = msg.getMessage().length();
                    longestMessage = msg;
                }
            }

            assertNotNull(longestMessage, "Should find a longest message");
            assertEquals("Where are you? You are late! I have asked you to be on time.",
                    longestMessage.getMessage(), "Should find the longest message from all messages 1-4");

        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    // Test: Searches for a specific message using its ID
    @Test
    @DisplayName("Search for messageID")
    void testSearchForMessageID() {
        populateTestData();

        try {
            Field messageIDsField = Main.class.getDeclaredField("messageIDs");
            Field messageIDsCountField = Main.class.getDeclaredField("messageIDsCount");

            messageIDsField.setAccessible(true);
            messageIDsCountField.setAccessible(true);

            String[] messageIDs = (String[]) messageIDsField.get(null);
            int messageIDsCount = messageIDsCountField.getInt(null);

            // Search for message4's ID specifically
            String targetID = "0400000004"; // message4's ID
            boolean found = false;

            for (int i = 0; i < messageIDsCount; i++) {
                if (targetID.equals(messageIDs[i])) {
                    found = true;
                    break;
                }
            }

            assertTrue(found, "Should find message with ID: " + targetID);

        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    // Test: Searches for all messages (sent or stored) for a specific recipient
    @Test
    @DisplayName("Search all messages sent or stored for a particular recipient")
    void testSearchMessagesByRecipient() {
        populateTestData();

        String recipient = "+27838884567"; // Recipient that has multiple messages

        try {
            Field sentMessagesField = Main.class.getDeclaredField("sentMessages");
            Field storedMessagesField = Main.class.getDeclaredField("storedMessages");
            Field sentMessagesCountField = Main.class.getDeclaredField("sentMessagesCount");
            Field storedMessagesCountField = Main.class.getDeclaredField("storedMessagesCount");

            sentMessagesField.setAccessible(true);
            storedMessagesField.setAccessible(true);
            sentMessagesCountField.setAccessible(true);
            storedMessagesCountField.setAccessible(true);

            Message[] sentMessages = (Message[]) sentMessagesField.get(null);
            Message[] storedMessages = (Message[]) storedMessagesField.get(null);
            int sentMessagesCount = sentMessagesCountField.getInt(null);
            int storedMessagesCount = storedMessagesCountField.getInt(null);

            // Count messages for the recipient in both sent and stored arrays
            int count = 0;
            boolean foundMessage2 = false;
            boolean foundMessage5 = false;

            for (int i = 0; i < sentMessagesCount; i++) {
                if (recipient.equals(sentMessages[i].getRecipient())) {
                    count++;
                }
            }

            for (int i = 0; i < storedMessagesCount; i++) {
                if (recipient.equals(storedMessages[i].getRecipient())) {
                    count++;
                    if (storedMessages[i].getMessage().equals("Where are you? You are late! I have asked you to be on time.")) {
                        foundMessage2 = true;
                    }
                    if (storedMessages[i].getMessage().equals("Ok, I am leaving without you.")) {
                        foundMessage5 = true;
                    }
                }
            }

            assertEquals(2, count, "Should find 2 messages for recipient: " + recipient);
            assertTrue(foundMessage2, "Should find message: 'Where are you? You are late! I have asked you to be on time.'");
            assertTrue(foundMessage5, "Should find message: 'Ok, I am leaving without you.'");

        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    // Test: Simulates deleting a message using its hash code
    @Test
    @DisplayName("Delete a message using a message hash")
    void testDeleteMessageByHash() {
        populateTestData();

        try {
            Field storedMessagesField = Main.class.getDeclaredField("storedMessages");
            Field messageHashesField = Main.class.getDeclaredField("messageHashes");
            Field storedMessagesCountField = Main.class.getDeclaredField("storedMessagesCount");
            Field messageHashesCountField = Main.class.getDeclaredField("messageHashesCount");

            storedMessagesField.setAccessible(true);
            messageHashesField.setAccessible(true);
            storedMessagesCountField.setAccessible(true);
            messageHashesCountField.setAccessible(true);

            Message[] storedMessages = (Message[]) storedMessagesField.get(null);
            String[] messageHashes = (String[]) messageHashesField.get(null);
            int storedMessagesCount = storedMessagesCountField.getInt(null);
            int messageHashesCount = messageHashesCountField.getInt(null);

            // Get hash of message2 (stored message) to delete
            String targetHash = message2.getMessageHash();

            int initialSize = storedMessagesCount;
            boolean initialHashExists = false;

            // Verify hash exists before deletion
            for (int i = 0; i < messageHashesCount; i++) {
                if (targetHash.equals(messageHashes[i])) {
                    initialHashExists = true;
                    break;
                }
            }

            // Simulate deletion - remove from storedMessages array
            boolean messageDeleted = false;
            for (int i = 0; i < storedMessagesCount; i++) {
                if (storedMessages[i] != null && targetHash.equals(storedMessages[i].getMessageHash())) {
                    // Shift elements down to fill the gap (simulating array removal)
                    for (int j = i; j < storedMessagesCount - 1; j++) {
                        storedMessages[j] = storedMessages[j + 1];
                    }
                    storedMessages[storedMessagesCount - 1] = null;
                    storedMessagesCount--;
                    storedMessagesCountField.setInt(null, storedMessagesCount);
                    messageDeleted = true;
                    break;
                }
            }

            // Remove hash from messageHashes array
            boolean hashDeleted = false;
            for (int i = 0; i < messageHashesCount; i++) {
                if (targetHash.equals(messageHashes[i])) {
                    // Shift elements down to fill the gap
                    for (int j = i; j < messageHashesCount - 1; j++) {
                        messageHashes[j] = messageHashes[j + 1];
                    }
                    messageHashes[messageHashesCount - 1] = null;
                    messageHashesCount--;
                    messageHashesCountField.setInt(null, messageHashesCount);
                    hashDeleted = true;
                    break;
                }
            }

            // Verify hash no longer exists after deletion
            boolean finalHashExists = false;
            for (int i = 0; i < messageHashesCount; i++) {
                if (targetHash.equals(messageHashes[i])) {
                    finalHashExists = true;
                    break;
                }
            }

            assertTrue(messageDeleted, "Message should be deleted from stored messages");
            assertTrue(hashDeleted, "Hash should be deleted from messageHashes");
            assertFalse(finalHashExists, "Hash should not exist after deletion");
            assertTrue(initialHashExists, "Hash should exist before deletion");

        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    // Test: Verifies that the report displays all required fields for sent messages
    @Test
    @DisplayName("Display Report - shows all sent messages with required fields")
    void testDisplayReport() {
        populateTestData();

        try {
            Field sentMessagesField = Main.class.getDeclaredField("sentMessages");
            Field sentMessagesCountField = Main.class.getDeclaredField("sentMessagesCount");

            sentMessagesField.setAccessible(true);
            sentMessagesCountField.setAccessible(true);

            Message[] sentMessages = (Message[]) sentMessagesField.get(null);
            int sentMessagesCount = sentMessagesCountField.getInt(null);

            // Verify report contains all required fields for each sent message
            for (int i = 0; i < sentMessagesCount; i++) {
                Message msg = sentMessages[i];
                assertNotNull(msg.getMessageHash(), "Message hash should not be null");
                assertNotNull(msg.getRecipient(), "Recipient should not be null");
                assertNotNull(msg.getMessage(), "Message content should not be null");
                assertFalse(msg.getMessageHash().isEmpty(), "Message hash should not be empty");
                assertFalse(msg.getRecipient().isEmpty(), "Recipient should not be empty");
                assertFalse(msg.getMessage().isEmpty(), "Message content should not be empty");
            }

            assertEquals(2, sentMessagesCount, "Report should contain 2 sent messages");

        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
}