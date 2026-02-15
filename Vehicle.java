import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Vehicle - Structural Class (The "Pillar")
 * 
 * DESCRIPTION:
 * Represents a vehicle entering/exiting the parking system.
 * Acts as the primary key for the system's main functionality.
 * 
 * PAYLOAD:
 * - licensePlate: Unique identifier for the vehicle
 * - VehicleType: Category/type of the vehicle (Enum)
 * 
 * ROLE:
 * The license plate serves as the primary key throughout the parking lifecycle:
 * - Entry: Associated with a Ticket
 * - Staying in the parkingLot(building): Linked to a Spot
 * - Exit: Used to retrieve Ticket and generate Invoice
 * 
 * RELATIONSHIPS:
 * - References VehicleType enum
 * - Processed by EntryGate
 * - Referenced by Ticket via licensePlate
 * - Used in ExitGate lookup
 */
public class Vehicle {
    
    /**
     * License plate - the unique identifier and primary key
     * for tracking this vehicle throughout the system
     */
    private String licensePlate;
    
    /**
     * The type/category of this vehicle
     * Determines which spot types it can use (via CompatibilityRegistry)
     */
    private VehicleType vehicleType;

    //++++++++++++++++++++++++++++++++++++++
    private LocalDateTime entryTime;
    //when the vehicle enters the system it is marked by the EntryGate[only when the vehicle is allocated a spot]

    private LocalDateTime exitTime;   //<<<set to NULL during entry

/* >>>>   private select a suitable dataType here for storing duration which can be hour-minute-second <<variable name: duration>>>;*/
    private Duration duration;

    //+++++++++++++++++++++++++++++++++++++++++
    
    /**
     * Constructor
     * 
     * @param licensePlate The vehicle's license plate number        <<<<in future Swing will pass the parameter[taken as input] to this constructor and make the vehicle opbject.
     * @param vehicleType The type of vehicle
     * 
     * TODO: Initialize all fields
     * TODO: Validate licensePlate is not null/empty
     * TODO: Consider normalizing license plate format (uppercase, remove spaces, etc.)         <<lowercase+number format is preferred
     */
    public Vehicle(String licensePlate, VehicleType vehicleType) {

        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty.");
        }

        if (vehicleType == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null.");
        }

        // normalize: remove spaces and convert to lowercase
        this.licensePlate = licensePlate.replaceAll("\\s+", "").toLowerCase();
        this.vehicleType = vehicleType;

        this.entryTime = null;
        this.exitTime = null;
        this.duration = null;
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // Additional constructor for DatabaseService loading
    // Used when loading existing parked vehicle from database
    public Vehicle(String licensePlate, LocalDateTime entryTime, VehicleType vehicleType) {

        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty.");
        }

        if (vehicleType == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null.");
        }

        if (entryTime == null) {
            throw new IllegalArgumentException("Entry time cannot be null.");
        }

        this.licensePlate = licensePlate.replaceAll("\\s+", "").toLowerCase();
        this.vehicleType = vehicleType;
        this.entryTime = entryTime;
        this.exitTime = null;
        this.duration = null;
    }
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    
    /**
     * Get the vehicle's license plate
     * 
     * @return The license plate string
     * 
     * TODO: Implement getter
     */
    public String getLicensePlate() {
        return licensePlate;
    }
    
    /**
     * Get the vehicle's type
     * 
     * @return The VehicleType
     * 
     * TODO: Implement getter
     */
    public VehicleType getVehicleType() {
        return vehicleType;   //<<<works as a fallback
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setEntryTime(LocalDateTime entryTime){
        if (entryTime == null) {
            throw new IllegalArgumentException("Entry time cannot be null.");
        }
        this.entryTime = entryTime;
        this.exitTime = null;   // set the exitTime to null
        this.duration = null;
    }

   // public <choose a suitable data type to return> calculateDuration{
   //  set the exitTime from NULL to currentTime
   // subtract the exitTime-entryTime
   // a critical thing to ponder on why not using INT: think the vehicle was parked 12th  Feb 11:30 and leaving 15th Feb 23:00 , we need a proper method to calculate and store the hour , minute , second
   //}
    
    public Duration calculateDuration() {

        if (entryTime == null) {
            throw new IllegalStateException("Entry time not set.");
        }

        this.exitTime = LocalDateTime.now();
        this.duration = Duration.between(entryTime, exitTime);

        if (duration.isNegative()) {
            throw new IllegalStateException("Exit time cannot be before entry time.");
        }

        return duration;
    }
}
