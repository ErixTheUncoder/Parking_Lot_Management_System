package db;

import model.SpotType;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PriceRegistry {
    private static Map<SpotType, Double> baseRates = new HashMap<>();
    private static String activeScheme = "Standard";

    public static void loadFromDatabase() {
        String sql = "SELECT spot_type, base_rate FROM price_registry";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            baseRates.clear();
            while (rs.next()) {
                SpotType type = SpotType.valueOf(rs.getString("spot_type"));
                double rate = rs.getDouble("base_rate");
                baseRates.put(type, rate);
            }
            
            System.out.println("Price registry loaded successfully.");
            
        } catch (SQLException e) {
            System.err.println("Error loading price registry: " + e.getMessage());
            // Load defaults
            loadDefaults();
        }
    }

    private static void loadDefaults() {
        baseRates.put(SpotType.COMPACT, 2.0);
        baseRates.put(SpotType.REGULAR, 5.0);
        baseRates.put(SpotType.HANDICAPPED, 2.0);
        baseRates.put(SpotType.RESERVED, 10.0);
        baseRates.put(SpotType.LARGE, 7.0);
    }

    public static Double getBaseRate(SpotType spotType) {
        if (baseRates.isEmpty()) {
            loadFromDatabase();
        }
        return baseRates.getOrDefault(spotType, 5.0);
    }

    public static void updateRate(SpotType spotType, Double rate) {
        if (rate < 0) {
            throw new IllegalArgumentException("Rate cannot be negative");
        }

        String sql = "UPDATE price_registry SET base_rate = ? WHERE spot_type = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, rate);
            pstmt.setString(2, spotType.name());
            pstmt.executeUpdate();
            
            baseRates.put(spotType, rate);
            System.out.println("Rate updated for " + spotType + ": RM " + rate);
            
        } catch (SQLException e) {
            System.err.println("Error updating rate: " + e.getMessage());
        }
    }

    public static String getActiveScheme() {
        return activeScheme;
    }

    public static void setActiveScheme(String scheme) {
        activeScheme = scheme;
        System.out.println("Active pricing scheme set to: " + scheme);
    }

    public static Map<SpotType, Double> getAllRates() {
        if (baseRates.isEmpty()) {
            loadFromDatabase();
        }
        return new HashMap<>(baseRates);
    }
}
