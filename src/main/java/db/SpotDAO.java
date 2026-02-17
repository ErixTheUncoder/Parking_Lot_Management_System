package db;

import model.*;
import java.sql.*;
import java.util.*;

public class SpotDAO {

    public static void updateOccupancy(long spotId, boolean isOccupied, String licensePlate) {
        String sql = "UPDATE spots SET is_occupied = ?, current_vehicle = ? WHERE spot_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, isOccupied ? 1 : 0);
            pstmt.setString(2, licensePlate);
            pstmt.setLong(3, spotId);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error updating spot occupancy: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static List<Spot> findAvailableByType(SpotType spotType) {
        List<Spot> spots = new ArrayList<>();
        String sql = "SELECT * FROM spots WHERE spot_type = ? AND is_occupied = 0 AND is_closed = 0";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, spotType.name());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Spot spot = createSpotFromResultSet(rs);
                spots.add(spot);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding available spots: " + e.getMessage());
        }
        return spots;
    }

    public static Spot findById(long spotId) {
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
    }

    public static Map<Integer, List<Spot>> loadAllSpots() {
        Map<Integer, List<Spot>> floorSpots = new HashMap<>();
        String sql = "SELECT s.*, f.floor_number FROM spots s " +
                     "JOIN floors f ON s.floor_id = f.floor_id " +
                     "ORDER BY f.floor_number, s.row_num, s.spot_num";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int floorNumber = rs.getInt("floor_number");
                Spot spot = createSpotFromResultSet(rs);
                
                floorSpots.putIfAbsent(floorNumber, new ArrayList<>());
                floorSpots.get(floorNumber).add(spot);
            }
            
        } catch (SQLException e) {
            System.err.println("Error loading all spots: " + e.getMessage());
        }
        return floorSpots;
    }

    private static Spot createSpotFromResultSet(ResultSet rs) throws SQLException {
        long spotId = rs.getLong("spot_id");
        SpotType spotType = SpotType.valueOf(rs.getString("spot_type"));
        boolean isOccupied = rs.getInt("is_occupied") == 1;
        String currentVehicle = rs.getString("current_vehicle");
        int floorNum = rs.getInt("floor_id");
        int rowNum = rs.getInt("row_num");
        int spotNum = rs.getInt("spot_num");
        
        return new Spot(spotId, spotType, isOccupied, currentVehicle, 
                       floorNum, rowNum, spotNum);
    }

    public static int getTotalCapacity() {
        String sql = "SELECT COUNT(*) as total FROM spots WHERE is_closed = 0";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting total capacity: " + e.getMessage());
        }
        return 0;
    }
}
