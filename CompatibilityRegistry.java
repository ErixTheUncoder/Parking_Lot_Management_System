import java.util.List;
import java.util.Map;

/**
 * CompatibilityRegistry - Registry & Logic Class ("Expert")
 * 
 * DESCRIPTION:
 * Central Source of Truth for vehicle-to-spot compatibility rules.
 * Loaded from database at startup.
 * 
 * ARCHITECTURE:
 * - Map<VehicleType, List<SpotType>>: Maps each vehicle type to allowed spot types
 * - Enables dynamic rules: Easy to update compatibility without code changes
 * 
 * //============================================================
 *   RULES for now hardcoded:
    1.Motorcycle - Can park in Compact spots only
    2.Car - Can park in Compact or Regular spots
    3.SUV/Truck - Can park in Regular spots only
    4.Handicapped Vehicle - Can park in any spot
 * //=============================================================
 * 
 * DESIGN PRINCIPLE: FUTURE consideration
 * Allows the system to be dynamic. For example, if a "Small Car" is suddenly
 * allowed to park in a new spot named "EV" spot, we just update the database and reload
 * this registry. No code changes needed.
 *  it will be Database-backed: Loaded at startup, can be refreshed    
 
 * RELATIONSHIPS:
 * - Uses VehicleType enum as key
 * - Uses SpotType enum as values
 * - Used by EntryGate to determine allowed spot types for a vehicle
 */
public class CompatibilityRegistry {
    
    /**
     * Map storing compatibility rules
     * Key: VehicleType enum
     * Value: List of allowed SpotTypes for that vehicle
     */
     //"hardcoded config that shouldn't change"
    private final  Map<VehicleType, List<SpotType>> AllowedSpotType= Map.of(
    VehicleType.MOTORCYCLE, List.of(SpotType.COMPACT),
    VehicleType.CAR, List.of(SpotType.COMPACT, SpotType.REGULAR),
    VehicleType.SUV, List.of(SpotType.REGULAR),
    VehicleType.TRUCK, List.of(SpotType.REGULAR),
    VehicleType.VIP, List.of(SpotType.RESERVED),
    VehicleType.HANDICAPPED, List.of(SpotType.HANDICAPPED)
    );                   
    
    /**
     * Constructor : default 
     * 
     */
    
    /**
     * Get all allowed spot types for a specific vehicle type
     * 
     * @param vehicleType The type of vehicle
     * @return List of allowed SpotTypes, or empty list if not found
     * 
     */
   
    public List<SpotType> getAllowedSpotTypes(VehicleType vehicleType) {
        // 1. Handle the case where vehicleType might be null
        if (vehicleType == null) {
            return List.of(); // Return an empty unmodifiable list
        }
        // 2. Look up the list in your map
        List<SpotType> allowed = AllowedSpotType.get(vehicleType);

        // 3. Return the list, or an empty one if the key wasn't found // List.copyOf() provides that "defensive copy" that is also unmodifiable
        return (allowed != null) ? List.copyOf(allowed) : List.of();
    }
    
    /**
     * Check if a specific vehicle type can use a specific spot type
     * 
     * @param vehicleType The type of vehicle
     * @param spotType The type of spot
     * @return True if compatible, false otherwise
     * 
     */
    public boolean isCompatible(VehicleType vehicleType, SpotType spotType) {
        if (vehicleType == null || spotType == null) {
            return false;
        }
        
        // Reuse your previous method
        List<SpotType> allowed = getAllowedSpotTypes(vehicleType);
        
        // Check if the specific spotType is in that list
        return allowed.contains(spotType);
    }
}
