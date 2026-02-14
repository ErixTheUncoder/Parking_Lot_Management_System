import java.util.Map;

/**
 * PriceRegistry - Registry & Logic Class (The "Expert")
 * 
 * DESCRIPTION:
 * Central Source of Truth for parking spot pricing. Loaded from database at startup.
 * Ensures pricing consistency across the entire parking system.
 * 
 * ARCHITECTURE:
 * - Map<SpotType, Double>: Maps each spot type to its base hourly/per-unit rate
 * - Database-backed: Loaded at startup, can be refreshed
 * - Single source of truth: Prevents data inconsistency
 * 
 * DESIGN PRINCIPLE:
 * By centralizing pricing here (not in Spot objects), we ensure that every
 * EV spot in the mall charges the same rate. Changes to pricing only need
 * to be made in the database and this registry.
 * 
 * RELATIONSHIPS:
 * - Uses SpotType enum as key
 * - Used by EntryGate for initial pricing information
 * - Used by ExitGate to calculate base charges
 * - Loaded from database at system startup
 */
public class PriceRegistry {
    
    /**
     * Map storing base rates for each spot type
     * Key: SpotType enum
     * Value: Base rate (e.g., dollars per hour)
     */
    private Map<SpotType, Double> baseRates;
    
    /**
     * Constructor
     * 
     * TODO: Initialize the baseRates map
     * TODO: Consider calling loadFromDatabase() in constructor
     * TODO: Handle initialization errors gracefully
     */
    public PriceRegistry() {
        // TODO: Implementation
    }
    
    /**
     * Load pricing data from the database
     * Called at startup and when pricing needs to be refreshed
     * 
     * TODO: Query database for all spot type rates
     * TODO: Populate the baseRates map
     * TODO: Handle database connection errors
     * TODO: Consider caching strategy and refresh intervals
     * TODO: Log successful load and any missing spot types
     */
    public void loadFromDatabase() {
        // TODO: Implementation
    }
    
    /**
     * Get the base rate for a specific spot type
     * 
     * @param spotType The type of spot to get pricing for
     * @return The base rate, or null if not found
     * 
     * TODO: Look up rate in baseRates map
     * TODO: Handle case when spot type is not in registry
     * TODO: Consider throwing exception vs returning null for missing types
     * TODO: Consider logging accesses for auditing
     */
    public Double getBaseRate(SpotType spotType) {
        // TODO: Implementation
        return null;
    }
    
    /**
     * Update the rate for a specific spot type
     * Used for administrative pricing changes
     * 
     * @param spotType The spot type to update
     * @param rate The new rate
     * 
     * TODO: Update the baseRates map
     * TODO: Persist change to database
     * TODO: Consider transaction management
     * TODO: Validate that rate is positive
     * TODO: Log pricing changes for audit trail
     * TODO: Consider notifying other system components of price change
     */
    public void updateRate(SpotType spotType, Double rate) {
        // TODO: Implementation
    }
}
