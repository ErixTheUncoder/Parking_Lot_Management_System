package db;

import model.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    public static void save(Invoice invoice, String ticketId) {
        String sql = "INSERT INTO invoices (invoice_id, ticket_id, license_plate, base_price, " +
                     "floor_premium, discount_amount, fines, total_amount, invoice_timestamp) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, invoice.getInvoiceID());
            pstmt.setString(2, ticketId);
            pstmt.setString(3, invoice.getLicensePlate());
            pstmt.setDouble(4, invoice.getBasePrice());
            pstmt.setDouble(5, invoice.getFloorPremium());
            pstmt.setDouble(6, invoice.getDiscountAmount());
            pstmt.setDouble(7, invoice.getFines());
            pstmt.setDouble(8, invoice.getTotalAmount());
            pstmt.setString(9, invoice.getInvoiceTimestamp().toString());
            pstmt.executeUpdate();
            
            System.out.println("Invoice saved successfully.");
            
        } catch (SQLException e) {
            System.err.println("Error saving invoice: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static List<String> getUnpaidInvoices() {
        List<String> fines = new ArrayList<>();
        String sql = "SELECT f.license_plate, f.fine_amount, f.reason, f.created_at " +
                     "FROM fines f WHERE f.is_paid = 0 ORDER BY f.created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String info = String.format("%s - RM %.2f - %s - Date: %s",
                    rs.getString("license_plate"),
                    rs.getDouble("fine_amount"),
                    rs.getString("reason"),
                    rs.getString("created_at"));
                fines.add(info);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting unpaid fines: " + e.getMessage());
        }
        return fines;
    }

    public static double getTotalRevenue() {
        String sql = "SELECT SUM(total_amount) as revenue FROM invoices";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getDouble("revenue");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting total revenue: " + e.getMessage());
        }
        return 0.0;
    }

    public static double getRevenueBreakdown(String component) {
        String sql = "SELECT SUM(" + component + ") as total FROM invoices";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting revenue breakdown: " + e.getMessage());
        }
        return 0.0;
    }
}
