import java.util.ArrayList;
import java.util.List;

public class AllocationEngine {
    private final AllocStrategy strategy;     //holding the reference to the strategy 
    private final Building building; // Access to floors using getFloor(int floorNumber), floor has a flat search map for finding spot
    private final CompatibilityRegistry registry;   // checking suitable spot types for a particular vehicle type 

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
                List<Long> spotsOfType = building.getFloor(i).findAvailableSpot(type);
                if (spotsOfType != null) {
                    allCandidates.addAll(spotsOfType);
                }
            }
        }

        // 3.the strategy rank them and give  the top 5
       List<Long>sortedSpots= strategy.sortAndLimit(allCandidates);
       List<Spot> sortedObj = new ArrayList<>();

       for (Long spotID : sortedSpots) {
        Spot spot = null;
            for (int i = 0; i < building.getTotalFloors(); i++) {
                Floor floor = building.getFloor(i);
                if (floor != null) {
                    spot = floor.getSpot(spotID);
                    if (spot != null) break;
                }
            }
            if (spot != null) {
                sortedObj.add(spot);}
        }
        return sortedObj;
    }
}

        