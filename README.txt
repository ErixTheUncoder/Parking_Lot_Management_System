# Parking Management System

# Compile
javac -d bin -cp "lib/*" src/main/java/model/*.java src/main/java/strategy/*.java src/main/java/db/*.java src/main/java/service/*.java src/main/java/gui/*.java

# Run
java -cp "bin;lib/*" gui.AppGUI


### Login

**Admin Login:**
- Username: `admin`
- Password: `1234`

**User Mode:**
- Select "User" role
- Click "Continue" (no login required)

### User Flow (Parking a Vehicle)

1. **Vehicle Registration**:
   - Enter license plate (e.g., "ABC123")
   - Select vehicle type
   - Click "Proceed"

2. **Entry Process**:
   - View available spots for your vehicle type
   - Select a spot from dropdown
   - Click "Reserve Spot & Generate Ticket"
   - Note your ticket ID and spot location

3. **Exit Process**:
   - Switch to "Exit Process" tab
   - System loads your ticket automatically
   - Review parking details:
     - Hours parked
     - Base fee
     - Floor premium
     - Discounts (if any)
     - Fines (if overstay > 24h)
     - Total due
   - Select payment method (Cash/Card)
   - Click "Pay & Exit"
   - Download receipt

### Admin Flow

1. **Login** with admin credentials

2. **Admin Panel**:
   - View total system capacity
   - Check current occupancy rate
   - See list of parked vehicles
   - Review unpaid fines
   - **Change fine scheme**:
     - Select from: FIXED, PROGRESSIVE, HOURLY
     - Click "Apply"

3. **Reporting Panel**:
   - Today's vehicle entries
   - Currently parked vehicles
   - Unpaid fines report
   - Total revenue collected

## Configuration

### Pricing

Edit prices in database or via code:
```java
PriceRegistry.updateRate(SpotType.COMPACT, 3.0);  // RM 3 per hour
```

Default rates:
- COMPACT: RM 2/hour
- REGULAR: RM 5/hour
- HANDICAPPED: RM 2/hour (FREE with handicapped card)
- RESERVED: RM 10/hour
- LARGE: RM 7/hour

### Fine Schemes

Change active scheme via Admin Panel or:
```java
FineDAO.setActiveFineScheme("PROGRESSIVE");
```

### Adding Floors/Spots

Modify `schema.sql` before first run, or insert directly into database:
```sql
INSERT INTO floors (floor_number, extra_charge, total_spots) VALUES (4, 3.0, 20);
INSERT INTO spots (spot_name, spot_type, floor_id, row_num, spot_num) 
VALUES ('F4-R1-S1', 'REGULAR', 4, 1, 1);
```

## Database Schema

**Key Tables:**
- `floors` - Floor information and premiums
- `spots` - Parking spot details
- `vehicles` - Vehicle registrations
- `tickets` - Active and historical parking tickets
- `invoices` - Payment records
- `fines` - Fine tracking
- `price_registry` - Spot type pricing
- `fine_schemes` - Available fine calculation methods
- `compatibility_registry` - Vehicle-to-spot compatibility rules

## Architecture

**Layered Architecture:**
1. **Presentation (GUI)**: Swing-based user interface
2. **Service Layer**: Business logic (FineManager)
3. **Model Layer**: Domain objects (Vehicle, Spot, Building, Gates)
4. **Strategy Layer**: Pluggable algorithms (Fine calculations)
5. **Data Access (DAO)**: Database operations with atomic transactions

**Design Patterns Used:**
- **Strategy Pattern**: Fine calculation strategies
- **DAO Pattern**: Database access abstraction
- **Facade Pattern**: EntryGate and ExitGate simplify complex operations
- **Singleton**: DatabaseConnection
- **Registry**: PriceRegistry, CompatibilityRegistry for centralized configuration


## Sample Test Scenarios

### Scenario 1: Normal Parking (1 hour)
1. Register vehicle: "CAR123", type: CAR
2. Select spot: F1-R1-S4 (REGULAR, RM 5/hour)
3. Park for 1 hour
4. Exit: Base RM 5 + Floor RM 0 = **Total: RM 5**

### Scenario 2: Overstay with Fixed Fine
1. Admin: Set fine scheme to FIXED
2. Register vehicle: "TRUCK789", type: TRUCK
3. Park for 30 hours
4. Exit: Base RM 150 (30h × RM 5) + Fine RM 50 = **Total: RM 200**

### Scenario 3: Handicapped with Unpaid Fine
1. Vehicle "HAND456" parks, overstays (gets fine)
2. Exits without paying fine
3. Returns next day
4. Exit: Current charges + Previous unpaid fine = **Combined total**
