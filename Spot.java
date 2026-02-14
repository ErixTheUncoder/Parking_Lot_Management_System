/**
 * Spot - Structural/Data Class (The "Pillar")
 * 
 * DESCRIPTION:
 * Represents an individual parking spot. Self-aware of its identity but does
 * not store its price directly to avoid data inconsistency.
 * 
 * STATE:
 * - spotID: Unique identifier
 * - SpotType: The type/category of this spot (Enum)
 * - isOccupied: Boolean indicating current occupancy status
 * 
 * BEHAVIOR:
 * Acts as the anchor for the Vehicle Plate during the "Stay" period.
 * The spot knows what it is and whether it's occupied, but pricing comes
 * from PriceRegistry to maintain single source of truth.
 * 
 * DESIGN PRINCIPLE:
 * Does NOT store price to avoid data inconsistency. All pricing queries
 * go through PriceRegistry ensuring every EV spot in the mall charges the same.
 * 
 * RELATIONSHIPS:
 * - Contained by Floor (many-to-1)
 * - References SpotType enum
 * - Referenced by Ticket via spotID
 */
public class Spot {
    
    /**
     * Unique identifier for this spot
     */
    private int spotID;
    
    /**
     * The type/category of this parking spot
     * Determines compatibility and pricing (via PriceRegistry)
     */
    private SpotType spotType;
    
    /**
     * Current occupancy status
     * True if a vehicle is currently parked here
     */
    private boolean isOccupied;   //<<it will be asked from DB if a spot is till empty?
    
    private boolean isClosed;  /// <<< kept for future maintenance to close a spot!
    /**
     * Constructor
     * 
     * @param spotID Unique spot identifier
     * @param spotType The type of this spot
     * 
     * TODO: Initialize all fields
     * TODO: Validate that spotID is positive
     * TODO: Set initial occupancy to false
     */
    public Spot(int spotID, SpotType spotType) {
        // TODO: Implementation
    }
    
    /**
     * Get the spot's unique identifier
     * 
     * @return The spot ID
     * 
     * TODO: Implement getter
     */
    public int getSpotID() {
        // TODO: Implementation
        return 0;
    }
    
    /**
     * Get the spot's type
     * 
     * @return The SpotType
     * 
     * TODO: Implement getter
     */
    public SpotType getSpotType() {
        // TODO: Implementation
        return null;
    }
    
    /**
     * Check if the spot is currently occupied
     * 
     * @return True if occupied, false otherwise
     * 
     * TODO: Implement getter
     */
    public boolean isOccupied() {   /// >>> this will make the DB call to double sure!
        // TODO: Implementation
        return false;
    }
    
    /**
     * Set the occupancy status of this spot
     * 
     * @param occupied The new occupancy status
     * 
     * TODO: Implement setter
     * TODO: Consider adding validation or logging
     * TODO: Consider triggering events for monitoring systems
     */
    public void setOccupied(boolean occupied) {  ///>> set in the database it is occupied!!
        // TODO: Implementation
    }

    public boolean getStatus(){
        return  isClosed;
    }
}
