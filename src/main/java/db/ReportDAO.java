package db;

import java.sql.*;
import java.time.LocalDate;

public class ReportDAO {

    public static int getTotalVehiclesToday() {
        String sql = "SELECT COUNT(*) as count FROM tickets " +
                     "WHERE DATE(entry_timestamp) = DATE('now')";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("count");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting today's vehicle count: " + e.getMessage());
        }
        return 0;
    }

    public static int getTotalVehiclesInDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT COUNT(*) as count FROM tickets " +
                     "WHERE DATE(entry_timestamp) BETWEEN ? AND ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, startDate.toString());
            pstmt.setString(2, endDate.toString());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting vehicle count for date range: " + e.getMessage());
        }
        return 0;
    }

    public static double getRevenueToday() {
        String sql = "SELECT SUM(total_amount) as revenue FROM invoices " +
                     "WHERE DATE(invoice_timestamp) = DATE('now')";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getDouble("revenue");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting today's revenue: " + e.getMessage());
        }
        return 0.0;
    }

    public static int getCurrentOccupancy() {
        String sql = "SELECT COUNT(*) as count FROM spots WHERE is_occupied = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("count");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting current occupancy: " + e.getMessage());
        }
        return 0;
    }
}
