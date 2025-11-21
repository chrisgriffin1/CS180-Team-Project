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
- Aiden Prananta - Submitted through Vocareum. 
- Christopher Griffin

## 3. Class Descriptions

### User.java

#### Functionality
Stores user account information including username and password. The constructor validates inputs and throws an `IllegalArgumentException` if either field is `null`.

#### Testing
See `UserTest.java`.

#### Relationships
Implements `IUser` interface and `java.io.Serializable` for file persistence. Stored in an `ArrayList` within `Database` and used in `Reservation` objects.

---

### IUser.java

#### Functionality
Interface for the `User` class. Requires `getUsername()` and `getPassword()` methods.

#### Testing
No testing

#### Relationships
Implemented by `User` class. 

---

### UserTest.java

#### Functionality 
JUnit tests for the `User` class.

#### Testing
Covers the main scenarios:

1. **`testUserConstructorAndGetters`**  
   Happy path test - checks that valid strings are stored and returned through getters.

2. **`testUserWithNullValues`**  
   Error case - ensures the constructor throws an `IllegalArgumentException` for `null` values.

3. **`testUserEmptyConstructor`**  
   Edge case - makes sure empty strings (`""`) are accepted without errors.

#### Relationships
Tests the `User` class.

---

### Seat.java

#### Functionality
Represents a seat with a `User` and `isOccupied` status. Constructor sets the initial user and defaults to unoccupied. Includes methods to get/set the user and manually `occupy()` or `free()` the seat. 

#### Testing
See `SeatTest.java`.

#### Relationships
Implements `SeatGuide` interface and `java.io.Serializable`. Stored in arrays within `Table` objects. 

---

### SeatGuide.java

#### Functionality 
Interface for the `Seat` class. Requires `getUser()`, `setUser()`, `getIsOccupied()`, `occupy()`, and `free()` methods.

#### Testing
No testing

#### Relationships
Implemented by the `Seat` class. 

---

### SeatTest.java

#### Functionality 
JUnit tests for the `Seat` class.

#### Testing
Covers the main functionality:

1. **`testSeatConstructorAndGetters`**  
   Checks that a `Seat` is constructed properly, returns the right `User`, and defaults to `isOccupied = false`.

2. **`testOccupyAndFree`**  
   Tests state changes - `occupy()` sets the flag to `true` and `free()` resets it to `false`.

3. **`testSetUser`**  
   Makes sure `setUser()` updates the associated `User` object.

#### Relationships
Tests the `Seat` class. 

---

### Table.java

#### Functionality
Represents a table with location, price, and an array of `Seat` objects. The constructor validates capacity (must be 0-2) and initializes the seat array. 

#### Testing 
See `TableTest.java`

#### Relationships
Implements `TableGuide` interface and `java.io.Serializable`. Contains an array of `Seat` objects and is used in `Reservation` objects.

---

### TableGuide.java

#### Functionality
Interface for the `Table` class. Requires `getTableRow()`, `getTableColumn()`, `getSeats()`, and `getPrice()` methods.

#### Testing
No testing

#### Relationships
Implemented by the `Table` class. 

---

### TableTest.java

#### Functionality
JUnit tests for the `Table` class.

#### Testing
Covers different scenarios:

1. **`testTableConstructorAndGetters`**  
   Happy path - checks that a table with valid inputs is created and getters work.

2. **`testTableWithZeroCapacity`**  
   Edge case - ensures a table can be created with capacity of 0.

3. **`testTableWithNegativeCapacity`**  
   Error case - makes sure the constructor throws an `IllegalArgumentException` for negative capacity.

4. **`testTableWithExcessiveCapacity`**  
   Error case - makes sure the constructor throws an `IllegalArgumentException` when capacity exceeds the limit (2).

---

### Reservation.java

#### Functionality
Represents a reservation with day, time, the booking user, party size, and the reserved table. Constructor validates that `user` and `day` aren't `null` and `partySize` isn't zero.

#### Testing
See `ReservationTest.java`

#### Relationships
Implements `ReservationGuide` interface and `java.io.Serializable`. Stored in an `ArrayList` in the `Database` class. Contains `User` and `Table` objects.

---

### ReservationGuide.java

#### Functionality
Interface for the `Reservation` class. Requires `getDay()`, `getTime()`, `getUser()`, `getTable()`, `getPartySize()`, and `isTableOccupied()` methods.

#### Testing
No testing. 

#### Relationships
Implemented by the `Reservation` class.

---

### ReservationTest.java

#### Functionality
JUnit tests for the `Reservation` class.

#### Testing
Covers various scenarios:

1. **`testReservationConstructorAndGetters`**  
   Happy path - checks that a reservation with valid inputs is created and getters work.

2. **`testReservationWithInvalidPartySize`**  
   Error case - tests that a reservation with negative party size is still created (logic flaw to fix later).

3. **`testReservationWithNullUser`**  
   Error case - ensures the constructor throws an `IllegalArgumentException` for null users.

4. **`testReservationWithNullDay`**  
   Error case - ensures the constructor throws an `IllegalArgumentException` for null days.

5. **`testReservationWithZeroPartySize`**  
   Error case - ensures the constructor throws an `IllegalArgumentException` when `partySize` is 0.

---

### Restaurant.java

#### Functionality
Manages the restaurant's seating plan. Holds the total capacity and a 2D array of tables. Includes `occupyReservation()` and `removeReservation()` methods to update seat states based on reservations.

#### Testing
See `RestaurantTest.java`.

#### Relationships
Implements `RestaurantGuide` interface. Contains a `Table[][]` array and works with `Reservation` and `Seat` objects.

---

### RestaurantGuide.java

#### Functionality
Interface for the `Restaurant` class. Requires `getCapacity()`, `getSeatingPlan()`, `occupyReservation()`, and `removeReservation()` methods.

#### Testing
No testing.

#### Relationships
Implemented by the `Restaurant` class.

---

### RestaurantTest.java

#### Functionality
JUnit tests for the `Restaurant` class.

#### Testing
Tests the main logic:

1. **`testGetters`**  
   Checks that `getCapacity()` and `getSeatingPlan()` return the values set in the constructor.

2. **`testOccupyReservation`**  
   Makes sure `occupyReservation()` updates `Seat` objects in the seating plan (sets user and occupies seat).

3. **`testRemoveReservation`**  
   Ensures `removeReservation()` reverts seat states (frees seat and clears user).

#### Relationships
Tests the `Restaurant` class.

---

### Database.java

#### Functionality
Central database managing persistence for users and reservations. Stores two `ArrayLists` - one for users, one for reservations. All methods are synchronized for thread safety. Writes to disk (`users.txt` and `reservations.txt`) on any modification and loads data on startup.

#### Testing
See `DatabaseTest.java`.

#### Relationships
Implements `DatabaseGuide` interface. Used by the server to manage all user and reservation data.

---

### DatabaseGuide.java

#### Functionality
Interface for the `Database` class. Defines methods like `makeNewUser()`, `deleteUser()`, `createReservation()`, `deleteReservation()`, `getUsers()`, and `getReservations()`.

#### Testing
No testing

#### Relationships
Implemented by `Database.java`.

---

### DatabaseTest.java

#### Functionality
JUnit tests for the `Database` class.

#### Testing
Tests persistence (round-trip testing):

1. **`testSaveAndReadUsers`**  
   Creates a database, saves new users (writes to file), then creates another database instance to check that users loaded from the file.

2. **`testReadUsersWhenFileMissing`**  
   Edge case - makes sure the constructor doesn't crash when `users.txt` is missing and returns an empty `ArrayList` instead.

#### Relationships
Tests the `Database` class.

---

### Client.java






---

### ClientGuide.java












---

### Server.java






---

### ServerGuide.java