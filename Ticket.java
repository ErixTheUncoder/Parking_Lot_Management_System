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
    private long DBspotID;    
    
    //the above is set by the entryGate to keep the track as a FOREIGN kEY IN THE DB. 

    // ---------------- NEW FIELDS ----------------
    private boolean isPaid;
    private boolean isActive;

    /**
     * Constructor
     */
    public Ticket(String licensePlate, long DBspotID, boolean activeS) {   
        
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty.");
        }

        if (DBspotID <= 0) {
            throw new IllegalArgumentException("Spot ID must be positive.");
        }

        this.licensePlate = licensePlate.replaceAll("\\s+", "").toLowerCase();
        this.DBspotID = DBspotID;
        this.entryTimestamp = LocalDateTime.now();

        // T-PLATE-TIMESTAMP format
        this.ticketID = "T-" + this.licensePlate + "-" + System.currentTimeMillis();

        this.isPaid = false;      // default unpaid
        this.isActive = activeS;  // set active state
    }

    //used by DBservice for remaking the object from database 
    public Ticket(String tickID, long spotID , String licenId, 
                  LocalDateTime entryT, boolean isPaid, boolean isActive){
        
        if (tickID == null || tickID.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket ID cannot be null or empty.");
        }

        if (licenId == null || licenId.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty.");
        }

        if (spotID <= 0) {
            throw new IllegalArgumentException("Spot ID must be positive.");
        }

        if (entryT == null) {
            throw new IllegalArgumentException("Entry timestamp cannot be null.");
        }

        this.ticketID = tickID;
        this.DBspotID = spotID;
        this.licensePlate = licenId.replaceAll("\\s+", "").toLowerCase();
        this.entryTimestamp = entryT;
        this.isPaid = isPaid;
        this.isActive = isActive;
    }
    
    /**
     * Get the ticket's unique identifier
     */
    public String getTicketID() {
        return ticketID;
    }
    
    /**
     * Get the vehicle's license plate
     */
    public String getLicensePlate() {
        return licensePlate;
    }
    
    /**
     * Get the assigned spot ID
     */
    public long getSpotID() {
        return DBspotID;
    }
    
    /**
     * Get the entry timestamp
     */
    public LocalDateTime getEntryTimestamp() {
        return entryTimestamp;
    }

    // ----------- NEW METHODS -----------

    public boolean getisPaid() {
        return isPaid;
    }

    // Original simple setter (always mark as paid)
    public void setPaid() {
        this.isPaid = true;
    }

    // New setter with parameter (as requested)
    public void setPaid(boolean newStat){
        this.isPaid = newStat;
    }

    public boolean getStat(){
        return isActive;
    }

    public boolean changeStat(boolean newStat){
        this.isActive = newStat;
        return this.isActive;
    }
}
