# ST10488314---PROG-5212---POE
Pride Mubata ST10488314
User Registration and Login System
A Java-based user authentication system that provides user registration with validation and secure login functionality.

Features
User Registration: Captures username, password, and phone number with comprehensive validation

Input Validation: Enforces specific formatting rules for all user inputs

User Authentication: Allows users to log in with their registered credentials

Welcome Message: Displays a personalized greeting after successful login

Validation Rules
Username
Must contain an underscore (_)

Must be no more than 5 characters in length

Example: kyL_1 ✓ | kyle1 ✗ | kyle!!!!!!! ✗

Password
Must be at least 8 characters long

Must contain at least one uppercase letter

Must contain at least one number

Must contain at least one special character

Example: Ch&&sec@ke99! ✓ | password ✗

Phone Number
Must start with '+' followed by country code

Must contain only digits after the country code

Must be properly formatted with international code

Example: +27838968976 ✓ | 08966553 ✗

Project Structure
text
src/
├── Main.java          # Application entry point
├── LogIn.java         # Core login functionality
└── LogInTest.java     # JUnit tests for validation
Getting Started
Prerequisites
Java JDK 8 or higher

JUnit 4 (for testing)

Installation
Clone the repository

Compile the Java files:
bash
javac Main.java LogIn.java LogInTest.java

Running the Application
bash
java Main
Running Tests
bash
java -cp .;junit-4.13.2.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore LogInTest
Usage
Run the application

Enter your first and last name when prompted

Register with a username, password, and cell phone number that meet the validation requirements

Login with your registered credentials

Receive a personalized welcome message upon successful authentication

Code Overview
Main Class
Handles user input and application flow through the console interface.

LogIn Class
Contains the core functionality:

User registration with validation

Credential storage

User authentication

Validation methods for username, password, and phone number

LogInTest Class
Comprehensive JUnit tests covering:

Username validation (correct and incorrect formats)

Password complexity requirements

Phone number formatting

Successful and failed login scenarios

User registration functionality

Example Usage
text
Enter your first name: John
Enter your last name: Doe
Register - Enter username: kyL_1
Username successfully captured
Register - Enter password: Ch&&sec@ke99!
Password successfully captured.
Register - Enter cell phone number (with international code, e.g. +27): +27838968976
Cell number successfully added.
User registered successfully.

-- Login --
Login - Enter username: kyL_1
Login - Enter password: Ch&&sec@ke99!
Welcome John Doe! It's great to see you again.
Technologies Used
Java

JUnit 4 (for testing)

Regular expressions for input validation

Author
Developed as a Java application demonstrating object-oriented programming principles and input validation techniques.
