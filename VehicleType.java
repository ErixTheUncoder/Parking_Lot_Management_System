/**
 * VehicleType - Enumeration
 * 
 * DESCRIPTION:
 * Defines the different categories of vehicles that can use the parking system.
 * 
 * PURPOSE:
 * - Used by Vehicle to categorize vehicles
 * - Used by CompatibilityRegistry as key for allowed spot types
 * - Determines which parking spots a vehicle can occupy
 * 
 * DESIGN PRINCIPLE:
 * Centralized enum ensures consistency across the system.
 * Adding a new vehicle type requires:               <<for future system expansion , we are using this approach
 * 1. Adding enum value here
 * 2. Updating CompatibilityRegistry with allowed spot types
 * 
 * RELATIONSHIPS:
 * - Used by Vehicle class
 * - Key in CompatibilityRegistry Map
 */
public enum VehicleType {
    
    /**
     * Motorcycle - smallest vehicle type
     */
    MOTORCYCLE("Motorcycle"),
    
    /**
     * Standard car/sedan
     */
    CAR("Car"),
    
    /**
     * Sport Utility Vehicle - larger than standard car
     */
    SUV("SUV"),
    
    /**
     * Truck - largest regular vehicle type
     */
    TRUCK("Truck"),
    
    /**
     * Handicapped vehicle - special type of vehicle 
     */
    HANDICAPPED("Handicapped"),

     /**
     * VIP vehicle - premium customer's vehicle
     */
    VIP("VIP");
    
    // TODO:  add display names or descriptions
    // TODO:  add methods to get human-readable labels
    
    private final String displayName;
    
    VehicleType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}
