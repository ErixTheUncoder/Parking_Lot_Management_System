import java.util.ArrayList;
import java.util.List;

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>an important note:this is fully loaded in the beginning of programme using another class StructureLoad 
// that class uses the constructor provided in this class

/**
 * Building - Structural Class ("Pillar")
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
 * exitGate directly access a spot 
 * entryGate also directly access a spot 
 */
public class Building {
    
    /**
     * Collection of all floors in the building
     */
    private List<Floor> floors;       //we might need to implement it with ArrayList[pre-allocate floor number to make it better] here !(optional)

    /**
     * Constructor
     * 
     */
    public Building(List<Floor> fList) {
        this.floors = new ArrayList<>(fList);         

    }
    //=======================================================================
    
    /**
     * Retrieve a specific floor by its number              //access existing floor [floor ID is simple INT]
     * 
     * @param floorNumber The floor number to retrieve
     * @return The Floor object, or null if not found           <<error handling logic!
     * 
     * TODO: Implement floor retrieval logic
     * TODO: Handle case when floor number doesn't exist
     */
    public Floor getFloor(int index) {            //<<getter method here! used from CONTROLLER 
        if (index >= 0 && index < floors.size()) {
        return floors.get(index);
        }else{
          System.out.println("Error: Floor index " + index + " out of bounds.");
        }
        return null;
    }

    public int getTotalFloors(){
        int numFloor = floors.size();
        return numFloor;
    }

}
