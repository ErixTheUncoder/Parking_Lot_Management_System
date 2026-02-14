
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

    /**
     * Constructor*/
    public Vehicle(String licensePlate) {        //this constructor is used for Vehcile initialisation during exit by receiving only license plate number
        // TODO: Implementation
    }

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
        // TODO: Implementation
    }
    
    /**
     * Get the vehicle's license plate
     * 
     * @return The license plate string
     * 
     * TODO: Implement getter
     */
    public String getLicensePlate() {
        // TODO: Implementation
        return null;
    }
    
    /**
     * Get the vehicle's type
     * 
     * @return The VehicleType
     * 
     * TODO: Implement getter
     */
    public VehicleType getVehicleType() {
        // TODO: Implementation
        return null;   //<<<works as a fallback
    }

    public void setEntryTime( LocalDateTime entryT){   // <<<<
        // TODO: Implementation
        // set the exitTime to null
    }
   //public <choose a suitable data type to return> calculateDuration(LocalDateTime exitT){     // <<<<<<
   //  set the exitTime from NULL to currentTime
   // subtract the exitTime-entryTime
   // a critical thing to ponder on why not using INT: think the vehicle was parked 12th  Feb 11:30 and leaving 15th Feb 23:00 , we need a proper method to calculate and store the hour , minute , second
   //}

}
