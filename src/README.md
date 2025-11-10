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
    1. **`testUserConstructorandGetters`**
         This is the "happy path" test, which verifies that a "User" object created with valid strings correctly stores and returns those strings via its getter methods.
    2. **`testUserWithNullValues`**
         This is the "error case" test[cite: 263]. It confirms that the "User" constructor correctly throws an "IllegalArgumentException" when "null" values are passed in.
    3. **`testUserEmptyConstructor`**
         This is an "edge case" test. It validates that the constructor correctly accepts empty strings ("''") without throwing an error.

# Relationships
This class directly tests the "User" class.


## Seat.java ##

# Functionality
This class models a seat by storing the User who reserveed it and an isOccupied boolean. The constructor sets the initial User and defaults it to false. It includes methods to get/set the user, check occupancy, and manually occupy() or free() seat. 

# Testing
See "SeatTest.java".

# Relationships
The class implements the "SeatGuide" interface. it also implements "java.io.Serializable" so that "Seat" objects can be saved as part of the "Table" object. "Seat" objects are stored in an array within the "Table" class. 

## SeatGuide.java ##

# Functionality 
THis is the interface for the "Seat" class. It ensures that any "Seat" object will have public methods for "getUser()", "setUser()", "getIsOccupied()", "occupy()", and "free()".

# Testing
No testing

# Relationships
Interface implemented by the "Seat" class. 


## SeatTest.java ##


# Functionality 
This class contains the comprehensive JUnit test case for the "Seat" class.

# Testing
The tests are designed to be comprehensive and cover the class's full functionality:
    1. **`testSeatConstructorAndGetters`**
        This test verifies that a  `Seat` object is correctly constructed, that it returns the correct `User`, and that its default `isOccupied` state is `false`.
    2. **`testOccupyAndFree`**
        This test checks the state-changing methods. It confirms that `occupy()` correctly sets the `isOccupied` flag to `true` and that `free()` correctly resets it to `false`.
    3. **`testSetUser`**
        This test validates that the `setUser()` method correctly updates the `User` object associated with the seat.

# Relationships
This class tests the `Seat` class. 


## Table.java ##


# Functionality
This class is a data model that represents a single table. It stores its location, price, and an array of `Seat` objects. The constructor validates the capacity (0-2) and initializes the `Seat` array, filling it with new `Seat` objects. 

# Testing 
See TableTest.java

# Relationships
The class implements the `TableGuide` interface  and java.io.Serializable. Table objects are contained within `Reservation` objects, and in turn, contain an array of `Seat` objects.


## TableGuide.java ##

# Functionality
This is the interface for the `Table` class. It ensures that any `Table` object will have public methods for `getTableRow()`, `getTableColumn`, `getSeats()`, and `getPrice()`.

# Testing
No testing

# Relationships
Interface implemented by the `Table` class. 