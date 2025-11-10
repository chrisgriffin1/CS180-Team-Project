CS180 Team Project: Restaurant 
Phase 1: Reservation Manager

------------------------
1. Compiling and Running
------------------------

This project relies on Java and the JUnit 4 library for testing.

From the root directory of the project, you can compile all Java files. You must include the JUnit JAR files in your classpath.
(Assuming you have a `lib/` folder containing `junit-4.13.jar` and `hamcrest-core-1.3.jar`)

On macOS/Linux
javac -cp ".:lib/*" *.java

# On Windows
javac -cp ".;lib\*" *.java

# On macOS/Linux
java -cp ".:lib/*" org.junit.runner.JUnitCore DatabaseTest TableTest

# On Windows
java -cp ".;lib\*" org.junit.runner.JUnitCore DatabaseTest TableTest

------------------
2. Submission List
------------------

Ishaan Limaye 

Jaden Fang

Aiden Prananta

Christopher Griffin

---------------------
3. Class Descriptions
---------------------

## User.java ##

# Functionality
This class is a datamodel that stores information for a single user account. It contains a "username" and "password", which are set in the constructor. The constructor includes validation to throw an "IllegalArgumentException" if either the "userName" or "password" is "null".

# Testing
See "UserTest.java".

# Relationships:
The class implements the "IUser" interface. It also implements "java.io.Serializable" so that "User" objects can be save to a file by the "Database" class. "User" objects are stored in an "Array List" within the "Database" class and are also part of a "Reservation" object.

## IUser.java ##

# Functionality
This is the interface for the "User" class. It ensures that any "User" object will have public methods for "getUsername()" and "getPassword()".

# Testing
No testing

# Relationships
Implemented by "User" class. 

## UserTest.java ##

# Functionality 
This class contains the comprehensive JUnit test case for the "User" class.

# Testing
 The tests are designed to be comprehensive and cover all required scenarios:
    **testUserConstructorandGetters**
         This is the "happy path" test, which verifies that a "User" object created with valid strings correctly stores and returns those strings via its getter methods.
    **testUserWithNullValues**
         This is the "error case" test[cite: 263]. It confirms that the "User" constructor correctly throws an "IllegalArgumentException" when "null" values are passed in.
    **testUserEmptyConstructor**
         This is an "edge case" test. It validates that the constructor correctly accepts empty strings ("''") without throwing an error.

# Relationships
This class directly tests the "User" class.


## 