import java.time.LocalDateTime;

/**
 * Ticket - Transaction & Billing Class (The "Audit")
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
 * 4. Used to calculate charges and generate Invoice
 * 5. Archived after exit for historical records
 * 
 * AUDIT ROLE:
 * The Ticket is the immutable record of when and where a vehicle parked.
 * It enables:
 * - Calculating parking duration
 * - Identifying which spot was used
 * - Tracking vehicle movements
 * - Resolving disputes
 * 
 * RELATIONSHIPS:
 * - Created by EntryGate
 * - Retrieved by ExitGate
 * - References Vehicle via licensePlate
 * - References Spot via spotID
 * - Used to generate Invoice
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
     * Assigned parking spot ID (foreign key to spot)
     */
    private int spotID;
    
    /**
     * Timestamp when vehicle entered and ticket was issued
     * Used for duration calculation at exit
     */
    private LocalDateTime entryTimestamp;
    
    /**
     * Constructor
     * 
     * @param licensePlate Vehicle's license plate
     * @param spotID Assigned spot identifier
     * 
     * TODO: Generate unique ticketID (UUID or sequential)
     * TODO: Set licensePlate
     * TODO: Set spotID
     * TODO: Record current timestamp as entryTimestamp
     * TODO: Validate parameters are not null/invalid
     */
    public Ticket(String licensePlate, int spotID) {
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
    public int getSpotID() {
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
