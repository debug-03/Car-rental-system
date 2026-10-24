# Car Rental Service

A Java-based car rental management system developed as an Object-Oriented Programming (OOP) mini project. The application simulates the core operations of a vehicle rental agency, including vehicle management, customer registration, rental processing, returns, payment handling, and revenue calculation.

The project focuses on applying Java OOP concepts to a practical use case through a menu-driven console application.

## Overview

The Car Rental Service provides a structured way to manage a vehicle fleet and its rental operations. Users can view available vehicles, register and view customers, create rental records, process vehicle returns, and generate rental receipts.

The system supports multiple vehicle categories, including cars, bikes, and SUVs. Each vehicle type maintains its own rental rate, while a common vehicle structure handles shared properties and behaviour.

It also demonstrates how interfaces and inheritance can be used to manage different payment methods and vehicle categories within the same application.

## Features

- **Vehicle Management:** Maintain a fleet of cars, bikes, and SUVs with unique vehicle IDs, registration numbers, rental rates, and availability status.
- **Customer Management:** Register customers and maintain their contact details, driving licence information, and active rental count.
- **Rental Processing:** Rent available vehicles after checking vehicle availability and the customer's active rental limit.
- **Vehicle Returns:** Process returns using a rental ID, update vehicle availability, and maintain rental status.
- **Rental Cost Calculation:** Calculate the rental amount using the vehicle's daily rate and rental duration.
- **Payment Handling:** Support simulated cash and card payment methods through a common payment interface.
- **Rental Receipts:** Display rental details, duration, total amount, payment mode, and rental status.
- **Rental History:** View previous rental records and identify active and completed rentals.
- **Revenue Summary:** Calculate total revenue from completed rentals.
- **OOP Demonstration:** Demonstrate runtime polymorphism through different vehicle types and their rental rates.

## Technologies Used

- Java
- Object-Oriented Programming
- Java Collections Framework (`ArrayList`)
- Java Date and Time API (`LocalDate`, `ChronoUnit`)
- Java Utility Package (`Scanner`)

## Object-Oriented Programming Concepts

### 1. Abstraction

The abstract `Vehicle` class defines common vehicle properties and declares abstract methods such as `getRentalRatePerDay()` and `getVehicleType()`. The `Payment` interface defines a common contract for payment processing.

### 2. Encapsulation

Class data members are declared `private` to restrict direct access. Getter methods and specific operations are used to access information and update the state of vehicles, customers, and rentals.

### 3. Inheritance

The `Car`, `Bike`, and `SUV` classes extend the abstract `Vehicle` class. They reuse common vehicle functionality while implementing their own rental rates and vehicle types.

### 4. Polymorphism

Method overriding allows each vehicle subclass to provide its own implementation of the vehicle type and rental rate methods. The system uses `Vehicle` references to demonstrate runtime polymorphism.

### 5. Interfaces

The `Payment` interface is implemented by `CashPayment` and `CardPayment`. Both classes provide their own implementations of the payment methods, allowing the system to handle different payment modes through a common interface.

### 6. Association

The `Rental` class maintains references to a `Customer` and a `Vehicle`, connecting the customer, rented vehicle, and associated rental record.

## Project Structure

```text
Car-Rental-Service/
├── CarRentalService.java
└── README.md
```

The project is implemented in a single Java source file containing the interface, supporting classes, rental management logic, and main application entry point.

## Application Workflow

1. **Initialization:** The application loads six sample vehicles and two predefined customers.
2. **Vehicle Selection:** The user views the available fleet and selects a customer and vehicle.
3. **Rental Validation:** The system checks vehicle existence, availability, and the customer's active rental limit.
4. **Rental Creation:** A rental record is created with a unique rental ID and the current start date.
5. **Vehicle Return:** The user selects an active rental and chooses a payment mode.
6. **Billing:** The system calculates the rental amount based on the rental duration and daily rate.
7. **Record Update:** The vehicle is marked available, the customer's active rental count is updated, and the rental is marked as completed.
8. **Receipt and Reporting:** The system displays the rental receipt and updates the information used for rental history and revenue reporting.

## Sample Vehicle Fleet

The application starts with the following predefined vehicles:

| Vehicle | Model | Rental Rate per Day |
|---|---|---:|
| Maruti | Swift Dzire | Rs. 1,200 |
| Hyundai | Creta | Rs. 1,800 |
| Royal Enfield | Classic 350 | Rs. 600 |
| Honda | Activa 6G | Rs. 350 |
| Toyota | Fortuner | Rs. 3,500 |
| Mahindra | Scorpio N | Rs. 2,800 |

The initial customer records are also loaded automatically when the application starts.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed
- A terminal or Java-compatible IDE

### Compilation

Open the project directory in a terminal and compile the source file:

```bash
javac CarRentalService.java
```

### Execution

Run the compiled application using:

```bash
java CarRentalService
```

The main menu will appear in the terminal, allowing the user to access the available operations.

## Current Scope

This version is a console-based implementation intended to demonstrate Java OOP concepts and basic rental management logic.

- The application uses predefined sample data.
- Records are stored in memory and are not persisted after the program terminates.
- Payment processing is simulated and does not involve an actual payment gateway.
- The current interface supports a predefined set of customers and uses the system date for rental operations.

## Future Enhancements

The application can be extended with database integration, customer authentication, dynamic customer registration through the menu, custom rental dates, late-return charges, improved input validation, automated testing, and a graphical or web-based interface.

## Learning Outcomes

This project provides practical experience in designing classes and interfaces, implementing inheritance and runtime polymorphism, applying encapsulation, managing object relationships, working with Java collections, handling dates, and implementing business logic in a structured application.

## Author

Aakruti Agrawal

B.Tech, Information Technology

---

This project was developed as an academic mini project to apply Object-Oriented Programming principles in Java through a vehicle rental management system.
