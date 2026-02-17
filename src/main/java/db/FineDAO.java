package db;

import java.sql.*;
import java.time.LocalDateTime;

public class FineDAO {

    public static void save(String licensePlate, double fineAmount, String reason) {
        String sql = "INSERT INTO fines (license_plate, fine_amount, reason, is_paid, created_at) " +
                     "VALUES (?, ?, ?, 0, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, licensePlate.toLowerCase());
            pstmt.setDouble(2, fineAmount);
            pstmt.setString(3, reason);
            pstmt.setString(4, LocalDateTime.now().toString());
            pstmt.executeUpdate();
            
            System.out.println("Fine saved successfully.");
            
        } catch (SQLException e) {
            System.err.println("Error saving fine: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static double getUnpaidFinesForVehicle(String licensePlate) {
        String sql = "SELECT SUM(fine_amount) as total FROM fines " +
                     "WHERE license_plate = ? AND is_paid = 0";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, licensePlate.toLowerCase());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting unpaid fines: " + e.getMessage());
        }
        return 0.0;
    }

    public static void markAsPaid(String licensePlate) {
        String sql = "UPDATE fines SET is_paid = 1, paid_at = ? " +
                     "WHERE license_plate = ? AND is_paid = 0";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, LocalDateTime.now().toString());
            pstmt.setString(2, licensePlate.toLowerCase());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error marking fines as paid: " + e.getMessage());
        }
    }

    public static String getActiveFineScheme() {
        String sql = "SELECT scheme_type FROM fine_schemes WHERE is_active = 1 LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getString("scheme_type");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting active fine scheme: " + e.getMessage());
        }
        return "FIXED";
    }

    public static void setActiveFineScheme(String schemeType) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Deactivate all schemes
            String deactivate = "UPDATE fine_schemes SET is_active = 0";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(deactivate);
            }

            // Activate selected scheme
            String activate = "UPDATE fine_schemes SET is_active = 1 WHERE scheme_type = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(activate)) {
                pstmt.setString(1, schemeType);
                pstmt.executeUpdate();
            }

            conn.commit();
            System.out.println("Fine scheme updated to: " + schemeType);

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error rolling back: " + ex.getMessage());
                }
            }
            System.err.println("Error setting fine scheme: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println("Error resetting auto-commit: " + e.getMessage());
                }
            }
        }
    }
}
