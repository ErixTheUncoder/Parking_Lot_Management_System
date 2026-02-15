/**
 * Spot - Structural Class ("Pillar")
 * 
 * DESCRIPTION:
 * Represents an individual parking spot. Self-aware of its identity but does
 * not store its price directly to avoid data inconsistency.
 * 
 * STATE:
 * - spotID: Unique identifier [same as Database]
 * - spotName : (e.g., "F1-R1-S1" = Floor 1, Row 1, Spot 1)<<<<<for printing and display 
 * - SpotType: The type/category of this spot (Enum)
 * - isOccupied: Boolean indicating current occupancy status
 * - currentVehicle: the licensePlate no(String) of the currently occupied vehicle {used for Admin panel to see cureently parked vehicle} 
 * - isClosed : boolean logic for maintenance.
 * 
 * BEHAVIOR:
 * Acts as the anchor for the Vehicle Plate during the "Stay" period.
 * The spot knows what it is and whether it's occupied, but pricing comes
 * from PriceRegistry to maintain single source of truth.
 * 
 * DESIGN PRINCIPLE:
 * Does NOT store price to avoid data inconsistency. All pricing queries
 * go through PriceRegistry ensuring every spot of same kind in the mall charges the same 
 * single update in the priceRegistry changes all price of same type of spots 
 * 
 * RELATIONSHIPS:
 * - Contained by Floor (many-to-1)
 * - References SpotType enum
 * - Referenced by Ticket via spotID
 */
public class Spot {

//===================================structural data
    private int floorNum;
    private int rowNum;
    private int spotNum;

    //these are used to make up the spotName<<<<<
    //these are loaded from database 

//===================================+
    /**
     * Unique identifier for this spot[same as Database id]
     */
    private long  DBspotID;

    private String spotName; //<<<<<for printing and display  (e.g., "F1-R1-S1" = Floor 1, Row 1, Spot 1)
    
    /**
     * The type/category of this parking spot
     * Determines compatibility and pricing (via PriceRegistry)
     */
    private SpotType spotType;
    
    /**
     * Current occupancy status
     * True if a vehicle is currently parked here
     */
    private boolean isOccupied;   

    private String currentVehicle; // the licensePlate no(String) of the currently occupied vehicle {used for Admin panel to see cureently parked vehicle} 
    
    private boolean isClosed;  /// <<< kept for future maintenance to close a spot!
    /**
     * Constructor
     * 
     * @param DBspotID Unique spot identifier
     * @param spotType The type of this spot
     * 
     * TODO: Initialize all fields
     * TODO: Validate that spotID is positive
     * TODO: set the occupancy and current vehicle[NULL value set if empty]
     */
    public Spot(long DBspotID, SpotType spotType , boolean isOccup , String currV, int floorN , int rowN , int spotN) {
        //concatenate the spotName usinf floorN,rowN,spotN<<<<<<<<<<<<<<<<
        // TODO: Implementation
        this.DBspotID = DBspotID;
        this.spotType = spotType;
        this.isOccupied = isOccup;
        this.currentVehicle = currV;
        this.floorNum = floorN;
        this.rowNum = rowN;
        this.spotNum = spotN;
        this.isClosed = false;

        this.spotName = String.format("F%d-R%d-S%d",floorN,rowN,spotN);

    }
    
    /**
     * Get the spot's unique identifier
     * 
     * @return The spot ID
     * 
     * TODO: Implement getter
     */
    public long getDBSpotID() {
        return DBspotID;
    }
    
    /**
     * Get the spot's type
     * 
     * @return The SpotType
     * 
     * TODO: Implement getter
     */
    public SpotType getSpotType() {
        return spotType;
    }
    
    /**
     * Check if the spot is currently occupied
     * 
     * @return True if occupied, false otherwise
     * 
     * TODO: Implement getter
     */
    public boolean isOccupied() {   
        return isOccupied;
    }
    
    /**
     * Set the occupancy status of this spot
     * 
     * @param occupied The new occupancy status
     * @param currV is the license plate number
     * 
     * TODO: Implement setter
     * TODO: Consider adding validation or logging
     */
    public void setOccupied(boolean occupied , String currV) {  
        this.isOccupied = occupied;
        this.currentVehicle = currV;
    }

    public boolean getStatus(){ 
        return isClosed;
    }
    public String getCurrentVehicle(){
        //error handling 
        return currentVehicle;
    }
    public String getSpotName(){
     ///this is used by GUI
        return spotName;
    }
    public void releaseVehicle(){
        this.isOccupied=false;
        this.currentVehicle=null;
    }
}