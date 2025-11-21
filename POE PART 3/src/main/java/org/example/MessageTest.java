package org.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.nio.file.Files;

class MessageTest {
    // Test message objects for different test scenarios
    private Message message1;
    private Message message2;

    // This method runs before each test to set up fresh test data
    @BeforeEach
    void setUp() {
        // Create message1 with valid international number format
        message1 = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight", 0);
        // Create message2 with invalid local number format (no international code)
        message2 = new Message("0112345678", "08575975889", "Hi Keegan, did you receive the payment?", 1);
    }

    // Test: Verifies the message hash generation follows the expected format
    @Test
    @DisplayName("Test Message Hash Format - 00:0:HITONIGHT")
    void testMessageHashFormat() {
        // Calculate hash for message1
        String hash = message1.createMessageHash();
        // Expected format: [first 2 chars of messageID]:[messageNumber]:[firstWord + lastWord in uppercase]
        // "0012345678" -> "00", message number 0, "Hi" + "tonight" -> "HITONIGHT"
        assertEquals("00:0:HITONIGHT", hash);
    }

    // Test: Verifies that message IDs are exactly 10 characters long
    @Test
    @DisplayName("Test Message ID is 10 characters")
    void testMessageIDLength() {
        // Check if message1 has valid ID format
        assertTrue(message1.checkMessageID());
        // Verify the actual length is exactly 10 characters
        assertEquals(10, message1.getMessageID().length());
    }

    // Test: Validates that international phone numbers (starting with +) are accepted
    @Test
    @DisplayName("Test Valid International Number")
    void testValidInternationalNumber() {
        // checkRecipientCell() returns 1 for valid international numbers
        assertEquals(1, message1.checkRecipientCell());
        // validateRecipientNumber() returns success message for valid numbers
        assertEquals("Cell phone number successfully captured.", message1.validateRecipientNumber());
    }

    // Test: Validates that local phone numbers (without +) are rejected
    @Test
    @DisplayName("Test Invalid Local Number")
    void testInvalidLocalNumber() {
        // checkRecipientCell() returns 0 for invalid/local numbers
        assertEquals(0, message2.checkRecipientCell());
        // validateRecipientNumber() returns error message for invalid numbers
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                message2.validateRecipientNumber());
    }

    // Test: Verifies that messages within the 250-character limit are accepted
    @Test
    @DisplayName("Test Message Length Validation - Success")
    void testMessageLengthSuccess() {
        // message1 has less than 250 characters, so validation should pass
        String result = message1.validateMessageLength();
        assertEquals("Message ready to send.", result);
    }

    // Test: Verifies that messages exceeding 250 characters are rejected
    @Test
    @DisplayName("Test Message Length Validation - Failure")
    void testMessageLengthFailure() {
        // Create a message with 255 characters (exceeds 250 limit)
        String longMessage = "A".repeat(255);
        Message longMsg = new Message("0212345678", "+27123456789", longMessage, 2);
        // Validation should fail with error message
        String result = longMsg.validateMessageLength();
        assertEquals("Please enter a message of less than 250 characters.", result);
    }

    // Test: Verifies the message sending operation returns correct success message
    @Test
    @DisplayName("Test Send Message Operation")
    void testSendMessage() {
        // sentMessage() should return success confirmation
        String result = message1.sentMessage();
        assertEquals("Message successfully sent.", result);
    }

    // Test: Verifies that messages can be stored to JSON file successfully
    @Test
    @DisplayName("Test Store Message Operation")
    void testStoreMessage() {
        // Clean up any existing test file before test
        try {
            Files.deleteIfExists(new File("stored_messages.json").toPath());
        } catch (IOException e) {
            // Ignore if file doesn't exist
        }

        // Store message1 to JSON file
        String result = message1.storeMessage();
        // Verify storage was successful
        assertEquals("Message successfully stored.", result);
        // Verify the JSON file was created
        assertTrue(new File("stored_messages.json").exists());
    }

    // Test: Verifies the message disregard operation returns correct message
    @Test
    @DisplayName("Test Disregard Message")
    void testDisregardMessage() {
        // disregardMessage() should return disregard confirmation
        String result = message1.disregardMessage();
        assertEquals("Message disregarded successfully.", result);
    }

    // Test: Verifies that message numbers are correctly assigned from loop counters
    @Test
    @DisplayName("Test Message Number from Loop Counter")
    void testMessageNumberFromLoopCounter() {
        // message1 was created with messageNumber 0 (from setUp)
        assertEquals(0, message1.getMessageNumber());
        // message2 was created with messageNumber 1 (from setUp)
        assertEquals(1, message2.getMessageNumber());
    }

    // This method runs after each test to clean up test files
    @AfterEach
    void tearDown() {
        // Delete the test JSON file if it exists to ensure clean state for next test
        try {
            Files.deleteIfExists(new File("stored_messages.json").toPath());
        } catch (IOException e) {
            // Ignore deletion errors - file might not exist
        }
    }
}