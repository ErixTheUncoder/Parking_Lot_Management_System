import java.util.List;
import java.util.Map;

/**
 * CompatibilityRegistry - Registry & Logic Class (The "Expert")
 * 
 * DESCRIPTION:
 * Central Source of Truth for vehicle-to-spot compatibility rules.
 * Loaded from database at startup.
 * 
 * ARCHITECTURE:
 * - Map<VehicleType, List<SpotType>>: Maps each vehicle type to allowed spot types
 * - Database-backed: Loaded at startup, can be refreshed
 * - Enables dynamic rules: Easy to update compatibility without code changes
 * 
 * DESIGN PRINCIPLE:
 * Allows the system to be dynamic. For example, if a "Small Car" is suddenly
 * allowed to park in an "EV" spot, we just update the database and reload
 * this registry. No code changes needed.
 * 
 * FLEXIBILITY EXAMPLES:
 * - Motorcycles can use Compact, Regular, or Motorcycle-specific spots
 * - Electric cars can use EV spots AND their size-appropriate regular spots
 * - Handicapped permits can use any spot type
 * 
 * RELATIONSHIPS:
 * - Uses VehicleType enum as key
 * - Uses SpotType enum as values
 * - Used by EntryGate to determine allowed spot types for a vehicle
 * - Loaded from database at system startup
 */
public class CompatibilityRegistry {
    
    /**
     * Map storing compatibility rules
     * Key: VehicleType enum
     * Value: List of allowed SpotTypes for that vehicle
     */
    private Map<VehicleType, List<SpotType>> compatibilityMap;
    
    /**
     * Constructor
     * 
     * TODO: Initialize the compatibilityMap
     * TODO: Consider calling loadFromDatabase() in constructor
     * TODO: Handle initialization errors gracefully
     */
    public CompatibilityRegistry() {
        // TODO: Implementation
    }
    
    /**
     * Load compatibility rules from the database
     * Called at startup and when rules need to be refreshed
     * 
     * TODO: Query database for all vehicle-spot compatibility rules
     * TODO: Populate the compatibilityMap
     * TODO: Handle database connection errors
     * TODO: Validate that all VehicleTypes have at least one allowed SpotType
     * TODO: Log successful load and any warnings
     */
    public void loadFromDatabase() {
        // TODO: Implementation
    }
    
    /**
     * Get all allowed spot types for a specific vehicle type
     * 
     * @param vehicleType The type of vehicle
     * @return List of allowed SpotTypes, or empty list if not found
     * 
     * TODO: Look up allowed types in compatibilityMap
     * TODO: Handle case when vehicle type is not in registry
     * TODO: Return defensive copy of list to prevent external modification
     * TODO: Consider sorting list by preference/priority
     */
    public List<SpotType> getAllowedSpotTypes(VehicleType vehicleType) {
        // TODO: Implementation
        return null;
    }
    
    /**
     * Check if a specific vehicle type can use a specific spot type
     * 
     * @param vehicleType The type of vehicle
     * @param spotType The type of spot
     * @return True if compatible, false otherwise
     * 
     * TODO: Get allowed types for the vehicle
     * TODO: Check if spotType is in the allowed list
     * TODO: Handle null parameters
     * TODO: Consider caching results for frequently checked combinations
     */
    public boolean isCompatible(VehicleType vehicleType, SpotType spotType) {
        // TODO: Implementation
        return false;
    }
}
