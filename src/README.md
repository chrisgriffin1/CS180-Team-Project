# CS180 Team Project: Restaurant 
## Phase 1

## 1. Compiling and Running

This project relies on Java and the JUnit 4 library for testing.

From the root directory of the project, you can compile all Java files. You must include the JUnit JAR files in your classpath.
(Assuming you have a `lib/` folder containing `junit-4.13.jar` and `hamcrest-core-1.3.jar`)

### On macOS/Linux
```bash
javac -cp ".:lib/*" *.java
java -cp ".:lib/*" org.junit.runner.JUnitCore DatabaseTest TableTest
```

### On Windows
```bash
javac -cp ".;lib\*" *.java
java -cp ".;lib\*" org.junit.runner.JUnitCore DatabaseTest TableTest
```

## 2. Submission List

- Ishaan Limaye 
- Jaden Fang
- Aiden Prananta
- Christopher Griffin

## 3. Class Descriptions

### User.java

#### Functionality
This class is a datamodel that stores information for a single user account. It contains a `username` and `password`, which are set in the constructor. The constructor includes validation to throw an `IllegalArgumentException` if either the `userName` or `password` is `null`.

#### Testing
See `UserTest.java`.

#### Relationships
The class implements the `IUser` interface. It also implements `java.io.Serializable` so that `User` objects can be saved to a file by the `Database` class. `User` objects are stored in an `ArrayList` within the `Database` class and are also part of a `Reservation` object.

---

### IUser.java

#### Functionality
This is the interface for the `User` class. It ensures that any `User` object will have public methods for `getUsername()` and `getPassword()`.

#### Testing
No testing

#### Relationships
Implemented by `User` class. 

---

### UserTest.java

#### Functionality 
This class contains the comprehensive JUnit test case for the `User` class.

#### Testing
The tests are designed to be comprehensive and cover all required scenarios:

1. **`testUserConstructorAndGetters`**  
   This is the "happy path" test, which verifies that a `User` object created with valid strings correctly stores and returns those strings via its getter methods.

2. **`testUserWithNullValues`**  
   This is the "error case" test. It confirms that the `User` constructor correctly throws an `IllegalArgumentException` when `null` values are passed in.

3. **`testUserEmptyConstructor`**  
   This is an "edge case" test. It validates that the constructor correctly accepts empty strings (`""`) without throwing an error.

#### Relationships
This class directly tests the `User` class.

---

### Seat.java

#### Functionality
This class models a seat by storing the User who reserved it and an `isOccupied` boolean. The constructor sets the initial User and defaults it to false. It includes methods to get/set the user, check occupancy, and manually `occupy()` or `free()` the seat. 

#### Testing
See `SeatTest.java`.

#### Relationships
The class implements the `SeatGuide` interface. It also implements `java.io.Serializable` so that `Seat` objects can be saved as part of the `Table` object. `Seat` objects are stored in an array within the `Table` class. 

---

### SeatGuide.java

#### Functionality 
This is the interface for the `Seat` class. It ensures that any `Seat` object will have public methods for `getUser()`, `setUser()`, `getIsOccupied()`, `occupy()`, and `free()`.

#### Testing
No testing

#### Relationships
Interface implemented by the `Seat` class. 

---

### SeatTest.java

#### Functionality 
This class contains the comprehensive JUnit test case for the `Seat` class.

#### Testing
The tests are designed to be comprehensive and cover the class's full functionality:

1. **`testSeatConstructorAndGetters`**  
   This test verifies that a `Seat` object is correctly constructed, that it returns the correct `User`, and that its default `isOccupied` state is `false`.

2. **`testOccupyAndFree`**  
   This test checks the state-changing methods. It confirms that `occupy()` correctly sets the `isOccupied` flag to `true` and that `free()` correctly resets it to `false`.

3. **`testSetUser`**  
   This test validates that the `setUser()` method correctly updates the `User` object associated with the seat.

#### Relationships
This class tests the `Seat` class. 

---

### Table.java

#### Functionality
This class is a data model that represents a single table. It stores its location, price, and an array of `Seat` objects. The constructor validates the capacity (0-2) and initializes the `Seat` array, filling it with new `Seat` objects. 

#### Testing 
See `TableTest.java`

#### Relationships
The class implements the `TableGuide` interface and `java.io.Serializable`. Table objects are contained within `Reservation` objects, and in turn, contain an array of `Seat` objects.

---

### TableGuide.java

#### Functionality
This is the interface for the `Table` class. It ensures that any `Table` object will have public methods for `getTableRow()`, `getTableColumn()`, `getSeats()`, and `getPrice()`.

#### Testing
No testing

#### Relationships
Interface implemented by the `Table` class. 

---

### TableTest.java

#### Functionality
Contains the comprehensive JUnit test case for the `Table` class.

#### Testing
The tests are designed to be comprehensive and cover all required scenarios:

1. **`testTableConstructorAndGetters`**  
   This "happy path" test verifies that a Table object with valid inputs is created correctly and its getters return the proper values.

2. **`testTableWithZeroCapacity`**  
   This "edge case" test validates that a table can be created with a capacity of 0.

3. **`testTableWithNegativeCapacity`**  
   This "error case" test confirms that the constructor correctly throws an `IllegalArgumentException` when a negative capacity is used.

4. **`testTableWithExcessiveCapacity`**  
   This "error case" test confirms that the constructor correctly throws an `IllegalArgumentException` when a capacity greater than the limit (2) is used.

---

### Reservation.java

#### Functionality
This class is a data model that represents a single reservation. It stores the `day`, `time`, the `User` who made the booking, the `partySize`, and the `Table` that was reserved. The constructor includes validation to throw an `IllegalArgumentException` if the `user` or `day` are `null`, or if the `partySize` is zero.

#### Testing
See `ReservationTest.java`

#### Relationships
This class implements the `ReservationGuide` interface and `java.io.Serializable`. `Reservation` objects are stored in an `ArrayList` within the `Database` class. It also contains `User` and `Table` objects.

---

### ReservationGuide.java

#### Functionality
This is the dedicated interface for the `Reservation` class. It ensures that any `Reservation` object will have public methods for `getDay()`, `getTime()`, `getUser()`, `getTable()`, `getPartySize()`, and `isTableOccupied()`.

#### Testing
No testing. 

#### Relationships
Interface implemented by the `Reservation` class

---

### ReservationTest.java

#### Functionality
This class contains the JUnit test case for the `Reservation` class.

#### Testing
The tests are designed to be comprehensive and cover all required scenarios:

1. **`testReservationConstructorAndGetters`**
        This "happy path" test verifies that a Reservation object with valid inputs is created correctly and its getters return the proper values.

2. **`testReservationWithInvalidPartySize`**
    This "error case" test checks that a reservation with a negative party size is still created (revealing a potential logic flaw to be fixed in a future phase).

3. **`testReservationWithNullUser`**
    This "error case" test confirms that the constructor correctly throws an IllegalArgumentException when a null User is used.

4. **`testReservationWithNullDay`**
    This "error case" test confirms that the constructor correctly throws an IllegalArgumentException when a null day is used.

5. **`testReservationWithZeroPartySize`**
    This "error case" test confirms that the constructor correctly throws an IllegalArgumentException when partySize is 0.

---

### Restaurant.java


#### Functionality
This class acts as the main manager for the restaurant's seating plan. It holds the total `capacity` and the `seatingPlan`. Its constructor initializes the restaurant's state. It includes methods like `occupyReservation()` and `removeReservation()` to change the state of seats within the `seatingPlan` based on a reservation.

#### Testing
See RestaurantTest.java.

#### Relationships
This class implements the RestaurantGuide interface. It holds a Table[][] array and interacts with Reservation and Seat objects to manage bookings

---


#### RestaurantGuide.java


### Functionality
This is the dedicated interface for the `Restaurant` class. It ensures that any `Restaurant` object will have public methods for `getCapacity()`, `getSeatingPlan()`, `occupyReservation()`, and `removeReservation()`.

#### Testing
No test.

#### Relationship
Interface is implemented by the Restaurant class.

### RestaurantTest.java
Functionality: This class contains the comprehensive JUnit test case for the Restaurant class.

Testing: The tests are designed to be comprehensive and cover the class's main logic:

testGetters: This test verifies that the getCapacity() and getSeatingPlan() methods return the correct values set in the constructor.

testOccupyReservation: This test checks that the occupyReservation() method correctly modifies the state of Seat objects within the seatingPlan (setting the user and occupying the seat).

testRemoveReservation: This test validates that the removeReservation() method correctly reverts the state of Seat objects, freeing the seat and setting its user to null.

Relationships: This class directly tests the Restaurant class.