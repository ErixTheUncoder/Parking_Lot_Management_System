# PROJECT STRUCTURE

## Overview
This file structure implements the Parking Lot Management System for CCP6224 OOAD assignment with clear module separation for three team members.

## Directory Structure

```
Parking_Lot_Management_System/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── parkinglot/
│   │               ├── model/                     # Domain Models
│   │               │   ├── ParkingLot.java       [Member 1: Abid]
│   │               │   ├── Floor.java            [Member 1: Abid]
│   │               │   ├── ParkingSpot.java      [Member 1: Abid]
│   │               │   ├── SpotType.java         [Member 1: Abid]
│   │               │   ├── SpotStatus.java       [Member 1: Abid]
│   │               │   ├── Vehicle.java          [Member 2: Eric]
│   │               │   ├── Motorcycle.java       [Member 2: Eric]
│   │               │   ├── Car.java              [Member 2: Eric]
│   │               │   ├── Truck.java            [Member 2: Eric]
│   │               │   ├── HandicappedVehicle.java [Member 2: Eric]
│   │               │   ├── VehicleType.java      [Member 2: Eric]
│   │               │   ├── Ticket.java           [Member 2: Eric]
│   │               │   ├── Payment.java          [Member 3: Pavien+Abid]
│   │               │   ├── Receipt.java          [Member 3: Pavien+Abid]
│   │               │   ├── Fine.java             [Member 3: Pavien+Abid]
│   │               │   └── PaymentMethod.java    [Member 3: Pavien+Abid]
│   │               │
│   │               ├── strategy/                  # Design Pattern
│   │               │   ├── FineStrategy.java     [Member 3: Pavien+Abid]
│   │               │   ├── FixedFine.java        [Member 3: Pavien+Abid]
│   │               │   ├── ProgressiveFine.java  [Member 3: Pavien+Abid]
│   │               │   └── HourlyFine.java       [Member 3: Pavien+Abid]
│   │               │
│   │               ├── service/                   # Business Logic
│   │               │   ├── ParkingManagementService.java [Member 1: Abid]
│   │               │   ├── EntryService.java     [Member 2: Eric]
│   │               │   ├── ExitService.java      [Member 3: Pavien+Abid]
│   │               │   └── FineManagementService.java [Member 3: Pavien+Abid]
│   │               │
│   │               ├── gui/                       # User Interface
│   │               │   ├── MainFrame.java        [All Members]
│   │               │   ├── AdminPanel.java       [Member 1: Abid]
│   │               │   ├── EntryPanel.java       [Member 2: Eric]
│   │               │   ├── ExitPanel.java        [Member 3: Pavien+Abid]
│   │               │   ├── FineConfigPanel.java  [Member 3: Pavien+Abid]
│   │               │   ├── ReportsPanel.java     [Member 1 & 3]
│   │               │   ├── TicketDialog.java     [Member 2: Eric]
│   │               │   └── ReceiptDialog.java    [Member 3: Pavien+Abid]
│   │               │
│   │               ├── util/                      # Utilities
│   │               │   ├── DateTimeUtil.java
│   │               │   └── IdGenerator.java
│   │               │
│   │               └── ParkingLotApplication.java # Main Entry Point
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── parkinglot/
│                   ├── model/
│                   │   ├── ParkingLotTest.java   [Member 1: Abid]
│                   │   ├── ParkingSpotTest.java  [Member 1: Abid]
│                   │   ├── VehicleTest.java      [Member 2: Eric]
│                   │   └── TicketTest.java       [Member 2: Eric]
│                   ├── strategy/
│                   │   └── FineStrategyTest.java [Member 3: Pavien+Abid]
│                   └── service/
│                       ├── EntryServiceTest.java [Member 2: Eric]
│                       └── ExitServiceTest.java  [Member 3: Pavien+Abid]
│
├── resources/                                     # Resources (images, config)
│
├── .gitignore                                     # Git ignore rules
├── README.md                                      # Project documentation
├── USECASE.md                                     # Use case specifications
└── PROJECT_STRUCTURE.md                           # This file

```

## Module Responsibilities

### Member 1 (Abid): CORE PARKING STRUCTURE & SPOT MANAGEMENT
- **Model**: ParkingLot, Floor, ParkingSpot, SpotType, SpotStatus
- **Service**: ParkingManagementService
- **GUI**: AdminPanel, ReportsPanel (Occupancy)
- **Tests**: ParkingLotTest, ParkingSpotTest

### Member 2 (Eric): VEHICLE, ENTRY & TICKET SYSTEM
- **Model**: Vehicle hierarchy (Vehicle, Motorcycle, Car, Truck, HandicappedVehicle), VehicleType, Ticket
- **Service**: EntryService
- **GUI**: EntryPanel, TicketDialog
- **Tests**: VehicleTest, TicketTest, EntryServiceTest

### Member 3 (Pavien + Abid): PAYMENT & FINE SYSTEM + DESIGN PATTERN
- **Model**: Payment, Receipt, Fine, PaymentMethod
- **Strategy**: FineStrategy, FixedFine, ProgressiveFine, HourlyFine
- **Service**: ExitService, FineManagementService
- **GUI**: ExitPanel, FineConfigPanel, ReceiptDialog, ReportsPanel (Revenue)
- **Tests**: FineStrategyTest, ExitServiceTest

## Build Instructions

### Prerequisites
- Java JDK 11 or higher

### Compile
```bash
javac -d bin -sourcepath src/main/java src/main/java/com/parkinglot/**/*.java
```

### Run Application
```bash
java -cp bin com.parkinglot.ParkingLotApplication
```

## UML Diagrams Ownership

### Member 1 (Abid)
- Class Diagram (Structure section)
- Use Cases: View Spots, Manage Parking Structure

### Member 2 (Eric)
- Class Diagram (Vehicle hierarchy)
- Sequence Diagram (Vehicle Entry Scenario)

### Member 3 (Pavien + Abid)
- Class Diagram (Design Pattern section)
- Sequence Diagram (Exit + Payment Scenario)

## Interview Defense Topics

### Member 1 (Abid)
- Composition vs. Aggregation (Why ParkingLot owns Floors)
- How new spot types can be added to the structure

### Member 2 (Eric)
- Inheritance & Polymorphism usage
- How adding a new vehicle type (e.g., Bus) is supported
- Validation rules per vehicle type

### Member 3 (Pavien + Abid)
- Why Strategy Pattern fits this requirement
- How new fine schemes are added without refactoring (Open-Closed Principle)
