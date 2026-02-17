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

    public List<Long> getAvailableSpotsForVehicle(VehicleType vehicleType) {
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

        return strategy.sortAndLimit(allCandidates);

        // 3.the strategy rank them and give  the top 5
       /* List<Long>sortedSpots= strategy.sortAndLimit(allCandidates);

        List<Spot> sortedObj;

        //I need the SpotDAO to give me the actual objects , use a 
        //for loop to call the SPot findByID and add it to sortedObj

        return sortedObj;*/
    }
}

        /**public static Spot findById(long spotId) {
        String sql = "SELECT * FROM spots WHERE spot_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, spotId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return createSpotFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding spot by ID: " + e.getMessage());
        }
        return null;
     } */