package model;

import java.util.ArrayList;
import java.util.List;

public class AllocationEngine {
    private final AllocStrategy strategy;
    private final Building building;
    private final CompatibilityRegistry registry;

    public AllocationEngine(Building b, CompatibilityRegistry r, AllocStrategy s) {
        this.building = b;
        this.registry = r;
        this.strategy = s;
    }

    public List<Spot> getAvailableSpotsForVehicle(VehicleType vehicleType) {
        // 1. Get compatible types
        List<SpotType> allowedTypes = registry.getAllowedSpotTypes(vehicleType);
        
        List<Long> allCandidates = new ArrayList<>();

        // 2. Gather all candidate IDs from all floors in memory
        for (SpotType type : allowedTypes) {
            for (int i = 0; i < building.getTotalFloors(); i++) {
                Floor floor = building.getFloor(i);
                if (floor != null) {
                    allCandidates.addAll(floor.findAvailableSpot(type));
                }
            }
        }

        // 3. The strategy ranks them and gives the top 5
        List<Long> topSpotIds = strategy.sortAndLimit(allCandidates);
        
        // 4. Convert IDs to Spot objects
        List<Spot> spots = new ArrayList<>();
        for (Long spotId : topSpotIds) {
            for (int i = 0; i < building.getTotalFloors(); i++) {
                Floor floor = building.getFloor(i);
                if (floor != null) {
                    Spot spot = floor.getSpot(spotId);
                    if (spot != null) {
                        spots.add(spot);
                        break;
                    }
                }
            }
        }
        
        return spots;
    }
}
