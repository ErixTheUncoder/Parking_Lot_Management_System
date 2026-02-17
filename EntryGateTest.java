import java.util.ArrayList;
import java.util.List;

/**
 * EntryGateTest - Comprehensive test for EntryGate functionality
 * 
 * PURPOSE:
 * Tests the complete flow of vehicle entry into the parking system:
 * 1. Vehicle arrival (NewArrival)
 * 2. Spot selection (getSpotToSelect)
 * 3. Spot allocation and ticket generation (MarkSpotAndGetTicket)
 * 4. Validation of state changes in Spot and Ticket
 */
public class EntryGateTest {
    
    public static void main(String[] args) {
        System.out.println("========== ENTRY GATE TEST SUITE ==========\n");
            // Setup phase
            System.out.println("SETUP PHASE: Initializing parking system...");
            Building building = createTestBuilding();
            CompatibilityRegistry registry = new CompatibilityRegistry();
            AllocStrategy strategy = new SimpleSorter();
            TicketDAO mockDAO = new MockTicketDAO();
            
            // Create engines
            AllocationEngine allocEngine = new AllocationEngine(building, registry, strategy);
            TicketEngine ticketEngine = new TicketEngine(mockDAO, building);

        for(int i=0;i<5;i++){
            try {
            // Create entry gate
            EntryGate entryGate = new EntryGate(1, building, allocEngine, ticketEngine);
            System.out.println("✓ Parking system initialized successfully\n");
            
            // Test 1: Vehicle Arrival
            System.out.println("TEST 1: Vehicle Arrival");
            System.out.println("-".repeat(50));
            testVehicleArrival(entryGate);
            
            // Test 2: Get Available Spots
            System.out.println("\nTEST 2: Get Available Spots for Vehicle");
            System.out.println("-".repeat(50));
            List<Spot> availableSpots = testGetAvailableSpots(entryGate);
            
            // Test 3: Mark Spot and Get Ticket
            System.out.println("\nTEST 3: Mark Spot and Get Ticket");
            System.out.println("-".repeat(50));
            if (!availableSpots.isEmpty()) {
                testMarkSpotAndGetTicket(entryGate,availableSpots.get(0));
            } else {
                System.out.println("✗ No available spots found. Skipping allocation test.");
            }
            
            // Test 4: Validation of State Changes
            System.out.println("\nTEST 4: Validate State Changes");
            System.out.println("-".repeat(50));
            testStateValidation(entryGate, building, availableSpots);
            
            System.out.println("\n========== ALL TESTS COMPLETED ==========\n");
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    } 
}
    
    /**
     * TEST 1: Create a vehicle with NewArrival and verify it's stored
     */
    private static void testVehicleArrival(EntryGate entryGate) {
        String licensePlate = "ABC-123";
        VehicleType vehicleType = VehicleType.HANDICAPPED;
        
        System.out.println("Creating vehicle with license plate: " + licensePlate);
        System.out.println("Vehicle type: " + vehicleType);
        
        try {
            entryGate.NewArrival(licensePlate, vehicleType);
            System.out.println("✓ Vehicle arrival processed successfully");
        } catch (Exception e) {
            System.out.println("✗ Vehicle arrival failed: " + e.getMessage());
        }
    }
    
    /**
     * TEST 2: Get available spots for the registered vehicle
     */
    private static List<Spot> testGetAvailableSpots(EntryGate entryGate) {
        System.out.println("Requesting available spots for registered vehicle...");
        
        try {
            List<Spot> spots = entryGate.getSpotToSelect();
            
            if (spots.isEmpty()) {
                System.out.println("⚠ No available spots returned");
            } else {
                System.out.println("✓ Available spots found: " + spots.size());
                System.out.println("  Spot IDs: " + spots.get(0).getDBSpotID());
            }
            
            return spots;
        } catch (Exception e) {
            System.out.println("✗ Failed to get available spots: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * TEST 3: Mark a spot and retrieve the ticket
     */
    private static void testMarkSpotAndGetTicket(EntryGate entryGate, Spot selectedSpot) {
        System.out.println("Marking spot " + selectedSpot.getDBSpotID() + " and generating ticket...");
        
        try {
            if (selectedSpot == null) {
                System.out.println("✗ Spot not found in building structure");
                return;
            }
            
            System.out.println("  Selected spot: " + selectedSpot.getSpotName() + 
                             " (Type: " + selectedSpot.getSpotType() + ")");
            
            // Mark spot and get ticket
            Ticket ticket = entryGate.MarkSpotAndGetTicket(selectedSpot);
            
            if (ticket == null) {
                System.out.println("✗ Ticket creation failed");
                return;
            }
            
            System.out.println("✓ Ticket generated successfully");
            System.out.println("  Ticket ID: " + ticket.getTicketID());
            System.out.println("  License Plate: " + ticket.getLicensePlate());
            System.out.println("  Spot ID: " + ticket.getSpotID());
            System.out.println("  Entry Time: " + ticket.getEntryTimestamp());
            System.out.println("  Status: " + (ticket.getStat() ? "ACTIVE" : "INACTIVE"));
            System.out.println("  Paid: " + (ticket.getisPaid() ? "YES" : "NO"));
            
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Invalid spot selection: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Ticket generation failed: " + e.getMessage());
        }
    }
    
    /**
     * TEST 4: Validate that state changes propagate correctly
     */
    private static void testStateValidation(EntryGate entryGate, Building building, List<Spot> testedSpots) {
        System.out.println("Validating state changes in parking system...");
        
        try {
            // Check if the assigned spot is now occupied
            if (!testedSpots.isEmpty()) {
                Spot spot = testedSpots.get(0);
                
                if (spot != null) {
                    System.out.println("Spot " + spot.getSpotName() + ":");
                    System.out.println("  Occupied: " + (spot.isOccupied() ? "YES" : "NO"));
                    System.out.println("  Current Vehicle: " + (spot.getCurrentVehicle().isEmpty() ? 
                                     "EMPTY" : spot.getCurrentVehicle()));
                    
                    if (spot.isOccupied()) {
                        System.out.println("✓ Spot state correctly updated to OCCUPIED");
                    }
                }
            }
            
            // Verify other spots remain unoccupied
            System.out.println("\nVerifying unoccupied spots remain available...");
            Floor floor = building.getFloor(1);
            
            if (floor != null) {
                List<Spot> allSpots = floor.getAllSpots();
                int unoccupiedCount = 0;
                for (Spot spot : allSpots) {
                    if (!spot.isOccupied()) {
                        unoccupiedCount++;
                    }
                }
                System.out.println("✓ Unoccupied spots on Floor 0: " + unoccupiedCount);
                System.out.println("✓ Total spots on Floor 0: " + floor.getTotalSpotCount());
            }
            
        } catch (Exception e) {
            System.out.println("✗ State validation failed: " + e.getMessage());
        }
    }
    
    /**
     * Helper: Create a test building with multiple floors and spots
     */
    private static Building createTestBuilding() {
        List<Floor> floors = new ArrayList<>();
        
        // Create Floor 0 with 10 spots
        List<List<Spot>> rows0 = new ArrayList<>();
        List<Spot> row0 = new ArrayList<>();
        
        // Create 10 test spots (2 rows, 5 spots each)
        long spotID = 1001;
        for (int i = 0; i < 5; i++) {
            row0.add(new Spot(spotID++, SpotType.COMPACT, false, null, 0, 0, i));
        }
        rows0.add(row0);
        
        List<Spot> row1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            row1.add(new Spot(spotID++, i % 2 == 0 ? SpotType.REGULAR : SpotType.COMPACT, 
                            false, null, 0, 1, i));
        }
        rows0.add(row1);
        
        Floor floor0 = new Floor(0, rows0);
        floors.add(floor0);
        
        // Create Floor 1 with 8 spots
        List<List<Spot>> rows1 = new ArrayList<>();
        List<Spot> row2 = new ArrayList<>();
        
        for (int i = 0; i < 4; i++) {
            row2.add(new Spot(spotID++, SpotType.HANDICAPPED, false, null, 1, 0, i));
        }
        rows1.add(row2);
        
        List<Spot> row3 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            row3.add(new Spot(spotID++, SpotType.RESERVED, false, null, 1, 1, i));
        }
        rows1.add(row3);
        
        Floor floor1 = new Floor(1, rows1);
        floors.add(floor1);
        
        return new Building(floors);
    }
}

/**
 * MockTicketDAO - Mock implementation for testing
 * Simulates database operations without actual database
 */
class MockTicketDAO extends TicketDAO {
    
    @Override
    public void saveNewTicketAndOccupySpot(Ticket t, Spot s, Vehicle v) {
        System.out.println("  [MockDAO] Saving ticket " + t.getTicketID() + 
                         " for vehicle " + v.getLicensePlate() + 
                         " in spot " + s.getDBSpotID());
    }
    
    @Override
    public void closeTicketAndFreeSpot(Ticket t, Spot s) {
        System.out.println("  [MockDAO] Closing ticket " + t.getTicketID() + 
                         " and freeing spot " + s.getDBSpotID());
    }
}
