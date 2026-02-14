import java.util.List;
import java.util.Map;

/**
 * Floor - Structural Class (The "Pillar")
 * 
 * DESCRIPTION:
 * Represents a single floor in the parking building using a Hybrid 2D ArrayList
 * of Rows (for physical mapping) 
 * A)>>>> [this is used for Admin view of a floor and row inside the floor and spot inside the row]
 * B)>>>>> a Flat Search Map for O(1) lookup.  [this is used for customer side search assign release!]
 * 
 * ARCHITECTURE:
 * - Hybrid Structure: Combines physical 2D layout with fast search capability
 * - 2D ArrayList: Represents physical rows and columns of parking spots
 * - Flat Search Map: Maps SpotType to List of available spot IDs for O(1) lookup
 * 
 * FINANCIAL LOGIC:
 * Holds an extraCharge variable (e.g., VIP Floor premium) to be added during
 * the exit phase for audit-ready billing.
 * 
 * 
 * RELATIONSHIPS:
 * - Contains multiple Spot objects (1-to-many composition)
 * - Part of Building (many-to-1) <<<[1 building holds many floors]
 * 
 * - Used by EntryGate to find available spots
 * - Used by ExitGate to calculate floor premium charges
 */
public class Floor {
    
    /**
     * The floor number identifier
     */
    private int floorNumber;
    
    /**
     * Extra charge for this floor (e.g., VIP floor premium)
     * This is added during the exit phase for audit-ready billing     <<guys I added it as an future extension!
     */
    private double extraCharge;
    
    /**
     * 2D ArrayList representing physical layout of parking spots
     * Organized as rows of spots for spatial mapping
     */
    private List<List<Spot>> rows;           //<<< floor holds a list of row , each row holds a list of <spot>
    
    /**
     * Flat Search Map for O(1) lookup
     * Maps SpotType to List of available spot IDs
     * Enables fast spot finding without iterating through 2D structure
     */
    private Map<SpotType, List<Integer>> flatSearchMap;            //<<<<<<<<<<<<<Integer may not be possible as assignment 
                                                                   //needs Alphanumeric!>>>>>>>>>>>>>>
    
    /**
     * Constructor
     * 
     * @param floorNumber The floor number
     * @param extraCharge The premium charge for this floor
     * 
     * TODO: Initialize the 2D rows structure
     * TODO: Initialize the flatSearchMap
     * TODO: Consider loading floor layout from database/configuration
     */
    public Floor(int floorNumber, double extraCharge) {
        // TODO: Implementation
    }
    
    /**
     * Get the extra charge for this floor
     * 
     * @return The floor's extra charge/premium
     * 
     * TODO: Implement getter
     */
    public double getExtraCharge() {
        // TODO: Implementation
        return 0.0;
    }
    
    /**
     * Find an available spot of the specified type using O(1) lookup
     * 
     * @param type The spot type to search for
     * @return The spot ID if available, null otherwise
     * 
     * TODO: Query flatSearchMap for the spot type
     * TODO: Return the first available spot ID from the list
     * TODO: Handle case when no spots of this type are available
     * TODO: Update flatSearchMap when spot is reserved (or do this atomically in DB)
     */
    public Integer findAvailableSpot(SpotType type) {                   //<<<this will be a list of spot IDS 
                                                                        //each spotID is unique in system and belongs to one floor only
        // TODO: Implementation
        return null;
    }
    
    /**
     * Get a specific spot by its ID
     * 
     * @param spotID The spot identifier
     * @return The Spot object, or null if not found
     * 
     * TODO: Search through the 2D rows structure
     * TODO: Consider maintaining a separate Map<Integer, Spot> for O(1) access by ID
     * TODO: Handle invalid spot IDs
     */
    public Spot getSpot(int spotID) {           //<<<<<< spotID will be not int Type!
        // TODO: Implementation
        return null;
    }
    
    /**
     * Update the occupancy status of a spot
     * 
     * @param spotID The spot to update
     * @param isOccupied The new occupancy status
     * 
     * TODO: Locate the spot by ID
     * TODO: Update the spot's occupancy status
     * TODO: Update the flatSearchMap to add/remove spot from available list
     * TODO: Consider making this thread-safe for concurrent gate operations
     */
    public void updateSpotOccupancy(int spotID, boolean isOccupied) {
        // TODO: Implementation
    }
}
