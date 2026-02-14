import java.util.List;

/**
 * Building - Structural Class (The "Pillar")
 * 
 * DESCRIPTION:
 * Represents the entire parking building structure. Contains multiple floors
 * and provides access to individual floors.
 * 
 * ROLE:
 * Acts as the top-level container for the parking system's physical structure.
 * 
 * RELATIONSHIPS:
 * - Contains multiple Floor objects (1-to-many composition)
 * - Accessed by EntryGate and ExitGate to find and manage parking spots
 */
public class Building {
    
    /**
     * Collection of all floors in the building
     */
    private List<Floor> floors;       //I am not sure on using ArrayList here !
    
    /**
     * Constructor
     * 
     * TODO: Initialize the floors list
     * TODO: loading building configuration from database
     */
    public Building() {
        // TODO: Implementation
    }
    
    /**
     * Add a floor to the building
     * 
     * @param floor The floor to add            //it will receive floor Object and add it to the building, for ADMIN<<<<<<<<<<<<<<
     *                                          //error handling >> can not add existing floor [floor ID is simple INT]
     * TODO: Implement floor addition logic
     * TODO: Validate floor number uniqueness
     * TODO: Consider maintaining sorted order by floor number       
     */
    public void addFloor(Floor floor) {
        // TODO: Implementation
    }
    
    /**
     * Retrieve a specific floor by its number              //access existing floor [floor ID is simple INT]
     * 
     * @param floorNumber The floor number to retrieve
     * @return The Floor object, or null if not found           <<error handlind logic!
     * 
     * TODO: Implement floor retrieval logic
     * TODO: Handle case when floor number doesn't exist
     */
    public Floor getFloor(int floorNumber) {            //<<getter method here! used from CONTROLLER 
        // TODO: Implementation
        return null;
    }
}
