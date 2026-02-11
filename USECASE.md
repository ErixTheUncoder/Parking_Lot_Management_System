# CCP6224 OOAD: Parking Lot Management System Use Cases

## 1. Customer Use Cases
The Customer interacts with the Entry/Exit system to manage their vehicle's stay.

* **Park Vehicle:** The customer registers their vehicle type, views suitable spots, and selects an available one.
* **Receive Parking Ticket:** Upon entry, the system generates a ticket containing the spot ID and entry timestamp.
* **Request Exit:** The customer provides their license plate number to initiate the checkout and fee calculation.
* **Process Payment:** The customer settles the total amount due (parking fees + any fines) using cash or card.
* **Receive Exit Receipt:** The customer gets a breakdown of the duration, fee, payment method, and any remaining balance.

---

## 2. Admin Use Cases
The Admin manages the lot's configuration and monitors business operations.

* **Manage Parking Structure:** The admin defines the layout, including the number of floors, rows, and types of spots (Compact, Regular, etc.).
* **Monitor Occupancy:** The admin views real-time data on how many spots are filled versus available across all floors.
* **Configure Fine Schemes:** The admin selects which fine logic (Fixed, Progressive, or Hourly) to apply to future parking sessions.
* **Generate Revenue Reports:** The admin views the total income collected from both parking fees and fines.
* **View Outstanding Fines:** The admin monitors a list of unpaid fines linked to specific license plates.

---

## 3. System Use Cases
In Object-Oriented Analysis, the System actor handles automated logic and background updates.

* **Calculate Parking Fee:** The system automatically computes the cost based on the vehicle’s duration (using ceiling rounding) and spot type.
* **Detect Violations/Fines:** The system automatically identifies if a vehicle has stayed over 24 hours or occupied a reserved spot without permission.
* **Update Spot Status:** The system automatically flips a spot to "occupied" upon entry and "available" once payment is confirmed.