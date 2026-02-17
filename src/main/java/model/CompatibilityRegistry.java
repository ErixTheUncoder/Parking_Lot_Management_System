package model;

import java.sql.*;
import java.util.*;
import db.DatabaseConnection;

public class CompatibilityRegistry {
    private Map<VehicleType, List<SpotType>> allowedSpotTypes;

    public CompatibilityRegistry() {
        this.allowedSpotTypes = new HashMap<>();
        loadFromDatabase();
    }

    public void loadDummy() {
        // Fallback hardcoded rules
        allowedSpotTypes.put(VehicleType.MOTORCYCLE, List.of(SpotType.COMPACT));
        allowedSpotTypes.put(VehicleType.CAR, List.of(SpotType.COMPACT, SpotType.REGULAR));
        allowedSpotTypes.put(VehicleType.SUV, List.of(SpotType.REGULAR, SpotType.LARGE));
        allowedSpotTypes.put(VehicleType.TRUCK, List.of(SpotType.REGULAR, SpotType.LARGE));
        allowedSpotTypes.put(VehicleType.VIP, List.of(SpotType.RESERVED));
        allowedSpotTypes.put(VehicleType.HANDICAPPED, 
            List.of(SpotType.HANDICAPPED, SpotType.REGULAR));
    }

    private void loadFromDatabase() {
        String sql = "SELECT vehicle_type, spot_type FROM compatibility_registry";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            allowedSpotTypes.clear();
            while (rs.next()) {
                VehicleType vType = VehicleType.valueOf(rs.getString("vehicle_type"));
                SpotType sType = SpotType.valueOf(rs.getString("spot_type"));
                
                allowedSpotTypes.putIfAbsent(vType, new ArrayList<>());
                allowedSpotTypes.get(vType).add(sType);
            }
            
            System.out.println("Compatibility registry loaded successfully.");
            
        } catch (SQLException e) {
            System.err.println("Error loading compatibility registry: " + e.getMessage());
            // Load hardcoded fallback
            loadDummy();
        }
    }

    public List<SpotType> getAllowedSpotTypes(VehicleType vehicleType) {
        List<SpotType> allowed = allowedSpotTypes.get(vehicleType);
        if (allowed == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(allowed);
    }

    public boolean isCompatible(VehicleType vehicleType, SpotType spotType) {
        List<SpotType> allowed = getAllowedSpotTypes(vehicleType);
        return allowed.contains(spotType);
    }
}
