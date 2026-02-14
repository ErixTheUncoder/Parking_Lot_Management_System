/**
 * SpotType - Enumeration
 * 
 * DESCRIPTION:
 * Defines the different categories of parking spots available in the system.
 * 
 * PURPOSE:
 * - Used by Spot to categorize parking spaces
 * - Used by PriceRegistry as key for base rates
 * - Used by CompatibilityRegistry to determine vehicle-spot compatibility
 * - Enables flexible pricing (different types have different rates)
 * 
 * DESIGN PRINCIPLE:
 * Centralized enum ensures consistency across the system.
 * Adding a new spot type requires:
 * 1. Adding enum value here
 * 2. Adding rate to PriceRegistry
 * 3. Updating CompatibilityRegistry for vehicle mappings
 * 
 * RELATIONSHIPS:
 * - Used by Spot class
 * - Key in PriceRegistry Map
 * - Value in CompatibilityRegistry Map
 */
public enum SpotType {
    
    /**
     * Compact parking spot - for smaller vehicles
     * 
     * TODO: Define this enum constant
     */
    COMPACT,
    
    /**
     * Regular/standard parking spot - most common type
     * 
     * TODO: Define this enum constant
     */
    REGULAR,
    
    /**
     * Large parking spot - for bigger vehicles (SUVs, trucks)
     * 
     * TODO: Define this enum constant
     */
    LARGE,
    
    /**
     * Electric Vehicle charging spot
     * 
     * TODO: Define this enum constant
     */
    EV,
    
    /**
     * Handicapped/accessible parking spot
     * 
     * TODO: Define this enum constant
     */
    HANDICAPPED;
    
    // TODO: Consider adding display names or descriptions
    // TODO: Consider adding methods to get human-readable labels
}
