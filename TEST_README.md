# EntryGate Test Suite - Running Instructions

## Overview
This test suite validates the complete vehicle entry flow in the parking system, ensuring that all components work together correctly.

## Test File
- **EntryGateTest.java** - Comprehensive test with 4 test cases

## What Gets Tested

### TEST 1: Vehicle Arrival
- Creates a new vehicle with license plate and vehicle type
- Verifies EntryGate.NewArrival() works correctly
- Confirms vehicle is stored in the system

### TEST 2: Get Available Spots
- Retrieves list of available parking spots for the registered vehicle
- Validates that the AllocationEngine correctly identifies compatible spots
- Verifies compatibility rules are applied (e.g., CAR can use COMPACT or REGULAR spots)

### TEST 3: Mark Spot and Get Ticket
- Selects an available spot and marks it as occupied
- Generates a parking ticket with:
  - Unique ticket ID
  - License plate reference
  - Spot ID assignment
  - Entry timestamp
  - Initial status (ACTIVE, UNPAID)
- Updates the Spot object to reflect occupancy

### TEST 4: State Validation
- Confirms that spot occupancy state is properly updated
- Verifies that unoccupied spots remain available
- Validates parking system state consistency

## How to Run

### Method 1: Command Line Compilation & Execution
```bash
# Navigate to the project directory
cd c:\Users\Hp\Downloads\final_OOAD

# Compile all Java files
javac *.java

# Run the test
java EntryGateTest
```

### Method 2: Using an IDE (IntelliJ IDEA / Eclipse / VS Code + Extension Pack for Java)
1. Open the project folder: `c:\Users\Hp\Downloads\final_OOAD`
2. Right-click on `EntryGateTest.java`
3. Select "Run" or "Run as Java Application"

### Method 3: Using Maven/Gradle (if configured)
```bash
mvn clean compile exec:java -Dexec.mainClass="EntryGateTest"
```

## Expected Output

When successful, the test will display:

```
========== ENTRY GATE TEST SUITE ==========

SETUP PHASE: Initializing parking system...
✓ Parking system initialized successfully

TEST 1: Vehicle Arrival
--------------------------------------------------
Creating vehicle with license plate: abc-123
Vehicle type: CAR
✓ Vehicle arrival processed successfully

TEST 2: Get Available Spots for Vehicle
--------------------------------------------------
Requesting available spots for registered vehicle...
✓ Available spots found: 5
  Spot IDs: [1001, 1002, 1003, 1004, 1005]

TEST 3: Mark Spot and Get Ticket
--------------------------------------------------
Marking spot 1001 and generating ticket...
  Selected spot: F0-R0-S0 (Type: COMPACT)
✓ Ticket generated successfully
  Ticket ID: T-abc-123-1708123456789
  License Plate: abc-123
  Spot ID: 1001
  Entry Time: 2026-02-17T14:30:45.123456
  Status: ACTIVE
  Paid: NO

TEST 4: Validate State Changes
--------------------------------------------------
Validating state changes in parking system...
Spot F0-R0-S0:
  Occupied: YES
  Current Vehicle: abc-123

✓ Spot state correctly updated to OCCUPIED

Verifying unoccupied spots remain available...
✓ Unoccupied spots on Floor 0: 9
✓ Total spots on Floor 0: 10

========== ALL TESTS COMPLETED ==========
```

## System Components Used

### Test Setup
- **Building**: 2-floor parking structure with 18 spots
  - Floor 0: 10 spots (5 COMPACT, 5 mixed REGULAR/COMPACT)
  - Floor 1: 8 spots (4 LARGE, 4 RESERVED)

- **CompatibilityRegistry**: Maps vehicle types to allowed spot types
  - CAR → [COMPACT, REGULAR]
  - MOTORCYCLE → [COMPACT]
  - SUV → [REGULAR]
  - etc.

- **AllocationEngine**: Finds available spots using Strategy pattern
  - Uses SimpleSorter to rank best spots (lower IDs first)
  - Returns top 5 candidates

- **TicketEngine**: Handles parking ticket lifecycle
  - Creates tickets on entry
  - Updates spot occupancy
  - Updates floor search maps (for O(1) lookups)

- **MockTicketDAO**: Simulates database operations without actual database

## Testing Flow

```
EntryGate.NewArrival("ABC-123", CAR)
    ↓
EntryGate.getSpotToSelect()
    ↓
AllocationEngine.getAvailableSpotsForVehicle()
    ↓
CompatibilityRegistry.getAllowedSpotTypes()
    ↓
Floor.findAvailableSpot()
    ↓
SimpleSorter.sortAndLimit() → Returns top 5 spot IDs
    ↓
EntryGate.MarkSpotAndGetTicket(selectedSpot)
    ↓
TicketEngine.processEntry()
    ├→ Create Ticket
    ├→ Update Spot.setOccupied()
    ├→ Update Floor.updateFlatSearchMap()
    └→ TicketDAO.saveNewTicketAndOccupySpot()
```

## Key Validations

✓ Vehicle creation with proper license plate normalization
✓ Spot availability filtering by vehicle type compatibility
✓ Ticket generation with unique ID and timestamp
✓ Spot occupancy state updates
✓ Floor search map maintenance for quick lookups
✓ Database persistence simulation

## Troubleshooting

### Issue: "Class not found" error
- **Solution**: Ensure all `.java` files are in the same directory
- Verify no `.class` files exist (delete them and recompile)

### Issue: "Spot state not updated"
- Check that Floor.updateFlatSearchMap() is being called correctly
- Verify Spot.setOccupied() updates are persisting

### Issue: "No available spots found"
- Verify test building creation in createTestBuilding()
- Check CompatibilityRegistry rules for vehicle type
- Ensure SpotType matches compatibility rules

## Bug Fixes Applied

1. **EntryGate.MarkSpotAndGetTicket()**: Fixed parameter reference
   - Changed `chosenSpot` to `selectedSpot` parameter
   
2. **TicketDAO method visibility**: Changed to `public` for proper inheritance
   - Allows MockTicketDAO to override methods

## Future Enhancements

- Add test cases for multiple vehicles in parallel
- Test edge cases (full parking lot, invalid vehicle types)
- Add performance benchmarks
- Test exit gate workflow
- Integration with actual database
