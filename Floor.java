import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Floor - Structural Class ("Pillar")
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
 * 
 * 
 * RELATIONSHIPS:
 * - Contains multiple Spot objects (1-to-many composition)
 * - Part of Building (many-to-1) <<<[1 building holds many floors]
 * 
 * - Used by searchEngine to find available spots
 */
public class Floor {

    //=====================
        Map<Long, Spot> flatAccess; //used for O(1) access by DBspotID       //DBspotID name is strange but it is used so 
    //=======================
    
    /**
     * The floor number identifier
     */
    private int floorNumber;
    
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
    private Map<SpotType, List<Long>> flatSearchMap;       

    //the Long is used here for a reason, 
    //each spot has an ID which is string but Database uses a long as a PK for faster lookup, though original name of spot can be found from the spot class
    
    /**
     * Constructor
     * 
     * @param floorNumber The floor number
     * @param List<List<Spot>>R the 2D structure
     * 
     * TODO: Initialize the 2D rows structure
     * TODO: Initialize the flatSearchMap
     * loading floor layout from database/configuration(loader class) >>use the below constructor to initialise Floor obj
     */
    public Floor(int floorNumber , List<List<Spot>>R) {
        this.floorNumber = floorNumber;
        this.rows = R;

        // Initialize flatAccess map
        this.flatAccess = new HashMap<>();
        for (List<Spot> row : R) {
            for (Spot spot : row) {
                this.flatAccess.put(spot.getDBSpotID(), spot);
            }
        } // Close first for loop

        // Initialize flatSearchMap
        this.flatSearchMap = new HashMap<>();

        // Populate flatSearchMap with available spots
        for (List<Spot> row : R) {
            for (Spot spot : row) {
                if (!spot.isOccupied() && !spot.getStatus()) {  // Available spots only
                    this.flatSearchMap.putIfAbsent(spot.getSpotType(), new ArrayList<>());
                    this.flatSearchMap.get(spot.getSpotType()).add(spot.getDBSpotID());
                }
            }
        }
    }
    
    /**
     * Find a list of available spot of the specified type using O(1) lookup
     * 
     * @param type The spot type to search for
     * @return The spot IDs if available, null otherwise
     * 
     * TODO: Query flatSearchMap for the spot type
     * TODO: Return the available spot IDs from the list 
     * TODO: Handle case when no spots of this type are available
     */
    public List<Long> findAvailableSpot(SpotType type) {                   //<<<this will be a list of spot IDS 
                                                                        //each spotID is unique in system and belongs to one floor only
        try {
            switch(type){
            case RESERVED -> { //
                List<Long> reservedSpotIDs = flatSearchMap.get(SpotType.RESERVED);
                return reservedSpotIDs;
                }

            case COMPACT -> {
                List<Long> compactSpotIDs = flatSearchMap.get(SpotType.COMPACT);
                return compactSpotIDs;
                }

            case HANDICAPPED -> {
                List<Long> handicapSpotIDs = flatSearchMap.get(SpotType.HANDICAPPED);          
                return handicapSpotIDs;
                }

            case LARGE -> {
                List<Long> compactSpotIDs = flatSearchMap.get(SpotType.LARGE);
                return compactSpotIDs;
                    }
            
            case REGULAR -> {
                List<Long> compactSpotIDs = flatSearchMap.get(SpotType.REGULAR);
                return compactSpotIDs;
                    }
            
            default  -> {
                System.out.println("Unable to find Spot for this type");
                break;
                }
            }
        } catch (Exception e) {
          System.out.println(e);
        }

        return null;
    }
    
    //==============================================
     // arrow definition can not have SpotType.x notiation
    //=============================================


    /**
     * Get a specific spot by its ID
     * 
     * @param DBspotID The spot identifier[DB name used here as this id is same as database]<<<<<<<<<<<<<<<
     * @return The Spot object, or null if not found
     * 
     * TODO: Search through the 2D rows structure
     * TODO: Consider maintaining a separate Map<long, Spot> for O(1) access by ID  <<<<<<<<
     * TODO: Handle invalid spot IDs
     */
    public Spot getSpot(long DBspotID) {              //this is used by exitGate or Admin to get a specific spot only
                                                    //this is used by entryGate to access the selected spot by customer and modify it      
        return flatAccess.get(DBspotID);  // O(1) lookup, returns null if not found
    }
    
    /**
     * Get the floor number
     * 
     * @return The floor number identifier
     */
    public int getFloorNumber() {
        return floorNumber;
    }
    
    /**
     * Get all spots on this floor
     * 
     * @return Collection of all spots
     */
    public List<Spot> getAllSpots() {
        return new ArrayList<>(flatAccess.values());
    }
    
    /**
     * Get total number of spots on this floor
     * 
     * @return Total spot count
     */
    public int getTotalSpotCount() {
        return flatAccess.size();
    }
    
    /**
     * Get number of available spots on this floor
     * 
     * @return Available spot count
     */
    public int getAvailableSpotCount() {
        int count = 0;
        for (Spot spot : flatAccess.values()) {
            if (!spot.isOccupied() && !spot.getStatus()) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Get number of occupied spots on this floor
     * 
     * @return Occupied spot count
     */
    public int getOccupiedSpotCount() {
        int count = 0;
        for (Spot spot : flatAccess.values()) {
            if (spot.isOccupied()) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Get count of spots by type (all spots, not just available)
     * 
     * @param type The spot type to count
     * @return Number of spots of that type
     */
    public int getSpotCountByType(SpotType type) {
        int count = 0;
        for (Spot spot : flatAccess.values()) {
            if (spot.getSpotType() == type) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Get count of available spots by type
     * 
     * @param type The spot type to count
     * @return Number of available spots of that type
     */
    public int getAvailableSpotCountByType(SpotType type) {
        List<Long> availableSpots = flatSearchMap.get(type);
        return (availableSpots != null) ? availableSpots.size() : 0;
    }
    
    /**
     * Get count of occupied spots by type
     * 
     * @param type The spot type to count
     * @return Number of occupied spots of that type
     */
    public int getOccupiedSpotCountByType(SpotType type) {
        int count = 0;
        for (Spot spot : flatAccess.values()) {
            if (spot.getSpotType() == type && spot.isOccupied()) {
                count++;
            }
        }
        return count;
    }
    //====================================== to keep the flatSearchMap updated ==============[during entry , exit ]













}
