/**
 * EntryGate - Registry & Logic Class (The "Expert" / "Craftsman")
 * 
 * DESCRIPTION:
 * Orchestrates the vehicle entry process. Acts as "The Handshake" between
 * a vehicle and the parking system.
 * 
 * RESPONSIBILITIES:
 * 1. Fetch allowed spot types from CompatibilityRegistry
 * 2. Query Floor(s) for available spots of allowed types
 * 3. Perform atomic DB update to prevent race conditions
 * 4. Generate and return Ticket
 * 
 * ARCHITECTURE - THE HANDSHAKE:
 * The EntryGate coordinates multiple components to safely assign a parking spot:
 * - CompatibilityRegistry: What spot types can this vehicle use?
 * - Building/Floor: Where are the available spots?
 * - Database: Atomic reservation to prevent double-booking
 * 
 * CONCURRENCY SAFETY:
 * Uses atomic DB update to prevent two gates from assigning the same spot
 * at the same millisecond (critical for multi-gate systems).
 * 
 * RELATIONSHIPS:
 * - Accesses Building to find floors and spots
 * - Uses CompatibilityRegistry to determine allowed spot types
 * - Uses PriceRegistry for pricing information (optional at entry)
 * - Processes Vehicle objects
 * - Creates Ticket objects
 */
public class EntryGate {
    
    /**
     * Unique identifier for this entry gate
     */
    private int gateID;
    
    /**
     * Reference to the building structure
     */
    private Building building;
    
    /**
     * Reference to compatibility rules registry
     */
    private CompatibilityRegistry compatibilityRegistry;
    
    /**
     * Reference to pricing registry
     */
    private PriceRegistry priceRegistry;
    
    /**
     * Constructor
     * 
     * @param gateID Unique gate identifier
     * @param building The building this gate serves
     * 
     * TODO: Initialize all fields
     * TODO: Load/inject CompatibilityRegistry
     * TODO: Load/inject PriceRegistry
     * TODO: Validate that building is not null
     */
    public EntryGate(int gateID, Building building) {
        // TODO: Implementation
    }
    
    /**
     * Process vehicle entry - THE HANDSHAKE
     * 
     * @param vehicle The vehicle attempting to enter
     * @return Ticket if successful, null if no spots available
     * 
     * WORKFLOW:
     * 1. Get allowed spot types from CompatibilityRegistry    
     * 2. Search for available spot (iterate floors if needed)
     * 3. Create Ticket with vehicle plate and spot ID
     * 4. Perform atomic DB update to reserve the spot
     * 5. Update in-memory Floor state
     * 6. Return Ticket to vehicle/driver
     * 
     * TODO: Implement the complete entry workflow
     * TODO: Handle case when no compatible spots are available
     * TODO: Handle database transaction failures (rollback)
     * TODO: Log entry events for monitoring
     * TODO: Consider retry logic for transient failures
     */
    public Ticket processEntry(Vehicle vehicle) {
        // TODO: Implementation
        return null;
    }
    
    /**
     * Find an available spot for the vehicle type
     * 
     * @param vehicleType The type of vehicle seeking a spot
     * @return Available Spot, or null if none found
     * 
     * TODO: Get allowed spot types from CompatibilityRegistry
     * TODO: Iterate through floors (or use priority logic)
     * TODO: For each floor, query for available spots of allowed types
     * TODO: Return first available spot found
     * TODO: Consider implementing spot assignment strategy:
     *       - Closest to entrance
     *       - Cheapest first
     *       - Load balancing across floors
     */
    public Spot findAvailableSpot(VehicleType vehicleType) {  //<<<This  will search by spotType  
        // TODO: Implementation
        return null;
    }
    
    /**
     * Perform atomic database update to reserve a spot
     * Prevents race condition where two gates try to assign same spot
     * 
     * @param ticket The ticket containing spot assignment
     * @return True if update successful, false if spot was taken
     * 
     * CRITICAL: This must be atomic to prevent double-booking
     * 
     * TODO: Use database transaction with appropriate isolation level
     * TODO: Check if spot is still available (SELECT FOR UPDATE)
     * TODO: Insert ticket record
     * TODO: Update spot occupancy status
     * TODO: Commit transaction if all successful
     * TODO: Rollback on any failure
     * TODO: Return false if spot was taken by another gate
     */
    public boolean performAtomicDBUpdate(Ticket ticket) {
        // TODO: Implementation
        return false;
    }
}


/*string licenseplate= vehicleA.getLicensePlate();

field A: license plate
field B : type 

//get selected type of parking the person wants 
>>> compact , regular 

 Vehicle a = new Vechicle(licen_plate,type);

 //==entry System

 A.processEntry(Vehicle a);*/





