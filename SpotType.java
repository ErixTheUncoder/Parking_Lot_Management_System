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
     */
    COMPACT,
    
    /**
     * Regular/standard parking spot - most common type
     */
    REGULAR,
    
    /**
     * Large parking spot - for bigger vehicles (SUVs, trucks)
     */
    LARGE,
    
    /**
     * Parking spot for  Vehicle with reservation 
     */
    RESERVED,
    
    /**
     * Handicapped/accessible parking spot
     */
    HANDICAPPED;
}
