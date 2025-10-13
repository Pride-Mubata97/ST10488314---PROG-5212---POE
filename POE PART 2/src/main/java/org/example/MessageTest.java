package org.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Test class for Message class - verifies all message functionality works correctly
 */
class MessageTest {
    private Message message1;
    private Message message2;
    private static int initialMessageCount;

    // Runs before each test to set up fresh test data
    @BeforeEach
    void setUp() {
        // Get current message count before test starts
        initialMessageCount = Message.returnTotalMessages();

        // Create test messages with specific data from requirements
        message1 = new Message("QQ12345678", "+27718693002", "Hi Mike, can you join us for dinner tonight");
        message2 = new Message("MSG002", "08575975889", "Hi Keegan, did you receive the payment?");
    }

    // Test 1: Verify test data is properly set up with 2 messages
    @Test
    @DisplayName("Test Data Setup - 2 Messages")
    void testTwoMessagesSetup() {
        assertNotNull(message1, "Message 1 should be initialized");
        assertNotNull(message2, "Message 2 should be initialized");
    }

    // Test 2: Test sending message with valid international number format
    @Test
    @DisplayName("Test Message 1 - Send with International Number")
    void testMessage1SendWithInternationalNumber() {
        // Verify recipient and message content match test data
        assertEquals("+27718693002", message1.getRecipient());
        assertEquals("Hi Mike, can you join us for dinner tonight", message1.getMessage());

        // Test send operation and verify success message
        String sendResult = message1.sendMessage();
        assertEquals("Message successfully sent.", sendResult);

        // Verify total message count increased by 1
        assertEquals(initialMessageCount + 1, Message.returnTotalMessages());
    }

    // Test 3: Test discarding message with local number format (should fail validation)
    @Test
    @DisplayName("Test Message 2 - Discard with Local Number")
    void testMessage2DiscardWithLocalNumber() {
        // Verify test data matches requirements
        assertEquals("08575975889", message2.getRecipient());
        assertEquals("Hi Keegan, did you receive the payment?", message2.getMessage());

        // Test disregard operation and verify correct message
        String disregardResult = message2.disregardMessage();
        assertEquals("Press 0 to delete message.", disregardResult);

        // Verify message count unchanged when disregarding
        assertEquals(initialMessageCount, Message.returnTotalMessages());
    }

    // Test 4: Test message length validation with valid message (<= 250 chars)
    @Test
    @DisplayName("Message Length Validation - Success")
    void testMessageLengthValidationSuccess() {
        String validMessage = "Short valid message";
        Message validMsg = new Message("VALID001", "+27711234567", validMessage);

        String result = validMsg.validateMessageLength();
        assertEquals("Message ready to send.", result);
    }

    // Test 5: Test message length validation with invalid message (> 250 chars)
    @Test
    @DisplayName("Message Length Validation - Failure")
    void testMessageLengthValidationFailure() {
        // Create message that exceeds 250 character limit
        String longMessage = "A".repeat(255);
        Message longMsg = new Message("LONG001", "+27711234567", longMessage);

        String result = longMsg.validateMessageLength();
        // Verify error message shows correct excess character count
        assertEquals("Message exceeds 250 characters by 5, please reduce size.", result);
    }

    // Test 6: Test recipient number validation with valid international format
    @Test
    @DisplayName("Recipient Number Format - Success")
    void testRecipientNumberFormatSuccess() {
        String result = message1.validateRecipientNumber();
        assertEquals("Cell phone number successfully captured.", result);
    }

    // Test 7: Test recipient number validation with invalid local format
    @Test
    @DisplayName("Recipient Number Format - Failure")
    void testRecipientNumberFormatFailure() {
        String result = message2.validateRecipientNumber();
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }

    // Test 8: Test message hash generation matches expected format
    @Test
    @DisplayName("Test Message Hash is Correct - QQ:bHITONIGHT")
    void testMessageHashIsCorrect() {
        // Test with specific data from test case 1
        Message testMessage = new Message("QQ12345678", "+27718693002", "Hi Mike, can you join us for dinner tonight");
        String hash = testMessage.createMessageHash();

        // Verify hash format: firstTwo:msgNumber:firstWord:lastWord
        // Expected: QQ:1:HI:TONIGHT
        assertTrue(hash.startsWith("QQ:"), "Hash should start with message ID prefix");
        assertTrue(hash.contains(":HI:"), "Hash should contain first word 'HI'");
        assertTrue(hash.endsWith(":TONIGHT"), "Hash should end with last word 'TONIGHT'");
    }

    // Test 9: Verify message IDs are properly created and not empty
    @Test
    @DisplayName("Test Message ID is Created")
    void testMessageIDIsCreated() {
        assertNotNull(message1.getMessageID(), "Message ID should not be null");
        assertFalse(message1.getMessageID().isEmpty(), "Message ID should not be empty");

        assertNotNull(message2.getMessageID(), "Message ID should not be null");
        assertFalse(message2.getMessageID().isEmpty(), "Message ID should not be empty");
    }

    // Test 10: Test basic send message functionality
    @Test
    @DisplayName("Test Send Message Operation")
    void testSendMessageOperation() {
        String sendResult = message1.sendMessage();
        assertEquals("Message successfully sent.", sendResult);
    }

    // Test 11: Test discard message functionality
    @Test
    @DisplayName("Test Discard Message Operation")
    void testDiscardMessageOperation() {
        String disregardResult = message2.disregardMessage();
        assertEquals("Press 0 to delete message.", disregardResult);
        assertEquals(initialMessageCount, Message.returnTotalMessages());
    }

    // Test 12: Test storing message to file system
    @Test
    @DisplayName("Test Store Message Operation")
    void testStoreMessageOperation() {
        // Clean up any existing test file
        try {
            Files.deleteIfExists(Paths.get("stored_messages.txt"));
        } catch (IOException e) {
            // Ignore if file doesn't exist
        }

        // Test store operation
        String storeResult = message1.storeMessage();
        assertEquals("Message successfully stored.", storeResult);

        // Verify file was created
        File storedFile = new File("stored_messages.txt");
        assertTrue(storedFile.exists(), "Stored messages file should be created");
    }

    // Test 13: Verify auto-generated fields are created properly
    @Test
    @DisplayName("Test Auto-Generated Fields")
    void testAutoGeneratedFields() {
        String hash1 = message1.getMessageHash();
        String hash2 = message2.getMessageHash();

        assertNotNull(hash1, "Message hash should be auto-generated");
        assertNotNull(hash2, "Message hash should be auto-generated");
        assertNotNull(message1.getMessageID(), "Message ID should be auto-generated");
        assertNotNull(message2.getMessageID(), "Message ID should be auto-generated");
    }

    // Test 14: Test total message counter increments correctly
    @Test
    @DisplayName("Test Total Number Sent")
    void testTotalNumberSent() {
        int initialCount = Message.returnTotalMessages();

        // Send first message and verify count increases
        message1.sendMessage();
        assertEquals(initialCount + 1, Message.returnTotalMessages());

        // Send second message and verify count increases again
        Message message3 = new Message("MSG003", "+27711234567", "Test message");
        message3.sendMessage();
        assertEquals(initialCount + 2, Message.returnTotalMessages());
    }

    // Test 15: Test all three user action options (Send, Store, Disregard)
    @Test
    @DisplayName("Test All User Action Options")
    void testAllUserActionOptions() {
        // Test Send action
        String sendResult = message1.sendMessage();
        assertEquals("Message successfully sent.", sendResult);

        // Test Store action
        String storeResult = message2.storeMessage();
        assertEquals("Message successfully stored.", storeResult);

        // Test Disregard action
        String disregardResult = message1.disregardMessage();
        assertEquals("Press 0 to delete message.", disregardResult);
    }

    // Cleanup after each test - remove test files
    @AfterEach
    void tearDown() {
        try {
            Files.deleteIfExists(Paths.get("stored_messages.txt"));
        } catch (IOException e) {
            // Ignore if file doesn't exist
        }
    }
}