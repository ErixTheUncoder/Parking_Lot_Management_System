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
     * 
     * TODO: Define this enum constant
     */
    MOTORCYCLE,
    
    /**
     * Standard car/sedan
     * 
     * TODO: Define this enum constant
     */
    CAR,
    
    /**
     * Sport Utility Vehicle - larger than standard car
     * 
     * TODO: Define this enum constant
     */
    SUV,
    
    /**
     * Truck - largest regular vehicle type
     * 
     * TODO: Define this enum constant
     */
    TRUCK,
    
    /**
     * Handicapped vehicle - special type of vehicle 
     * 
     * TODO: Define this enum constant
     */
    HANDICAPPED;
    
    // TODO: Consider adding display names or descriptions
    // TODO: Consider adding methods to get human-readable labels
}
