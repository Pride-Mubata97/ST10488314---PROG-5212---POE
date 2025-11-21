 QuickChat - Java Messaging Application

Student: Pride Mubata ST10488314
Course:PROG5121 

 Project Description
QuickChat is a comprehensive Java messaging application that allows users to be able to send, store, as well as manage text messages with robust validation and tracking features. The application features a graphical user interface using Swing and includes message persistence, search functionality, and detailed reporting.

Features

 User Authentication
- User registration with validation
- Secure login system
- Input validation for username, password, and phone    numbers

 Message Management
- Send multiple messages in one session
- Store messages for later sending
- Disregard unwanted messages
- 250-character message limit with validation

 Search & Analytics
- Search messages by unique ID
- Find all messages for a specific recipient
- Display the longest message sent
- View sender/recipient information

 Reporting
- Recently sent messages display
- Full detailed message reports
- Session summaries
- Message deletion by hash code

Technologies Used
-Java (Core application logic)
-Swing (Graphical User Interface)
- File I/O (JSON message storage)
- Regex (Input validation)

Class Overview

Main.java
-Main application controller
-Handles user interaction through Swing dialogs
-Manages message arrays and session data
-Provides menu system for all features

Message.java
-Represents individual messages with unique IDs and hashes
-Validates message length and recipient format
-Handles message storage in JSON format
-Generates unique hash codes for message tracking

LogIn.java
-Manages user registration and authentication
-Validates username, password, and phone number formats
-Provides secure login functionality

Usage Instructions
-Registration: Enter your personal details and create account credentials
-Login: Authenticate with your username and password
-Send Messages: Choose recipients and compose messages (max 250 chars)
-Manage Messages: Store, send, or disregard messages as needed
-View Reports: Access various reports and search functionalities
-Exit: View session summary before quitting
