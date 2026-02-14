import java.time.LocalDateTime;

/**
 * Ticket -  Class for tracking & Audit
 * 
 * DESCRIPTION:
 * Acts as "The Bridge" connecting a vehicle's license plate to its assigned
 * parking spot. Represents the persistent "Contract" during the parking stay.
 * 
 * PURPOSE:
 * - Connects licensePlate (vehicle identifier) to spotID (location)
 * - Records entry timestamp for duration calculation
 * - Persists in database until vehicle exits
 * - Primary record for audit trail
 * 
 * LIFECYCLE:
 * 1. Created by EntryGate when vehicle enters
 * 2. Stored in database during parking stay
 * 3. Retrieved by ExitGate using license plate
 * 4. Used to calculate charges and generate Invoice by ExitGate
 * 5. Archived after exit for historical records
 * 
 * ADMIN Checking benefits :
 * The Ticket is the immutable record of when and where a vehicle parked.
 * It enables:
 * - Calculating parking duration
 * - Identifying which spot was used by exitGate
 * - Tracking vehicle movements
 * - Resolving disputes
 * 
 * RELATIONSHIPS:
 * - Created by EntryGate
 * - Retrieved by ExitGate
 * - References Vehicle via licensePlate
 * - References Spot via spotID
 * - Used to generate Invoice by exitGate
 */
public class Ticket {
    
    /**
     * Unique ticket identifier (primary key)
     */
    private String ticketID;
    
    /**
     * Vehicle license plate (foreign key to vehicle)
     * Acts as the primary lookup key for exit processing
     */
    private String licensePlate;
    
    /**
     * Timestamp when vehicle entered and ticket was issued
     * Used for duration calculation at exit
     */
    private LocalDateTime entryTimestamp;

 
   /**
     * Assigned parking spot ID (foreign key to spot)
    /*
    private String spotID;//<<<<< spotID is a string as it is made of format "F1-R2-S3"(floor1,row2,spot3)
    */ //[Note : this is not kept as it is hard to lookup in DB]

       /*****************************************************/
    private long DBspotID;    //<<<<this is used to make connection with datbase primary key which is fast working with 
    
    //the above is set by the entryGate to keep the track as a FOREIGN kEY IN THE DB. 

    /**
     * Constructor
     * 
     * @param licensePlate Vehicle's license plate
     * @param spotID Assigned spot identifier
     * 
     * TODO: Generate unique ticketID (UUID or sequential)
     * TODO: Set licensePlate
     * TODO: Set DBspotID
     * TODO: Record current timestamp as entryTimestamp
     * TODO: Validate parameters are not null/invalid
     */
    public Ticket(String licensePlate, long DBspotID) {   //long is used for having more range of number
        // TODO: Implementation
    }

    //used by DBservice for remaking the object from database 
    public Ticket(String tickID, long spotID , String licenId, LocalDateTime entryT){
        // TODO: Implementation
    }
    
    /**
     * Get the ticket's unique identifier
     * 
     * @return The ticket ID
     * 
     * TODO: Implement getter
     */
    public String getTicketID() {
        // TODO: Implementation
        return null;
    }
    
    /**
     * Get the vehicle's license plate
     * 
     * @return The license plate
     * 
     * TODO: Implement getter
     */
    public String getLicensePlate() {
        // TODO: Implementation
        return null;
    }
    
    /**
     * Get the assigned spot ID
     * 
     * @return The spot ID
     * 
     * TODO: Implement getter
     */
    public long getSpotID() {
        // TODO: Implementation
        return 0;
    }
    
    /**
     * Get the entry timestamp
     * 
     * @return The timestamp when ticket was issued
     * 
     * TODO: Implement getter
     */
    public LocalDateTime getEntryTimestamp() {
        // TODO: Implementation
        return null;
    }
}
