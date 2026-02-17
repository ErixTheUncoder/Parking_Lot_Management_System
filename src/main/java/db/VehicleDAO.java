package db;

import model.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    public static void save(Vehicle vehicle) {
        String sql = "INSERT OR REPLACE INTO vehicles (license_plate, vehicle_type, entry_time) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, vehicle.getLicensePlate());
            pstmt.setString(2, vehicle.getVehicleType().name());
            pstmt.setString(3, vehicle.getEntryTime() != null ? 
                vehicle.getEntryTime().toString() : null);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error saving vehicle: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Vehicle findByPlate(String licensePlate) {
        String sql = "SELECT * FROM vehicles WHERE license_plate = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, licensePlate.toLowerCase());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String plate = rs.getString("license_plate");
                VehicleType type = VehicleType.valueOf(rs.getString("vehicle_type"));
                String entryTimeStr = rs.getString("entry_time");
                
                if (entryTimeStr != null) {
                    LocalDateTime entryTime = LocalDateTime.parse(entryTimeStr);
                    return new Vehicle(plate, entryTime, type);
                } else {
                    return new Vehicle(plate, type);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding vehicle: " + e.getMessage());
        }
        return null;
    }

    public static List<String> getParkedVehicles() {
        List<String> vehicles = new ArrayList<>();
        String sql = "SELECT v.license_plate, v.vehicle_type, t.entry_timestamp, s.spot_name " +
                     "FROM vehicles v " +
                     "JOIN tickets t ON v.license_plate = t.license_plate " +
                     "JOIN spots s ON t.spot_id = s.spot_id " +
                     "WHERE t.is_active = 1";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String info = String.format("%s (%s) - Spot: %s - Entry: %s",
                    rs.getString("license_plate"),
                    rs.getString("vehicle_type"),
                    rs.getString("spot_name"),
                    rs.getString("entry_timestamp"));
                vehicles.add(info);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting parked vehicles: " + e.getMessage());
        }
        return vehicles;
    }

    public static void updateExitTime(String licensePlate, LocalDateTime exitTime) {
        String sql = "UPDATE vehicles SET exit_time = ? WHERE license_plate = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, exitTime.toString());
            pstmt.setString(2, licensePlate.toLowerCase());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error updating exit time: " + e.getMessage());
        }
    }
}
