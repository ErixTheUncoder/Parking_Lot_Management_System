import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FineDAO {

    /**
     * Save a new fine to the database with merged reasons and status
     */
    public static void save(String licensePlate, double fineAmount, String mergedReasons, 
                           int schemeId, TransactionStatus status) {
        String sql = "INSERT INTO fines (license_plate, fine_amount, merged_reasons, scheme_id, status, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, licensePlate.toLowerCase());
            pstmt.setDouble(2, fineAmount);
            pstmt.setString(3, mergedReasons);
            pstmt.setInt(4, schemeId);
            pstmt.setString(5, status.toString());
            pstmt.setString(6, LocalDateTime.now().toString());
            pstmt.executeUpdate();
            
            System.out.println("Fine saved successfully for vehicle: " + licensePlate);
            
        } catch (SQLException e) {
            System.err.println("Error saving fine: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get the total amount of DUE fines for a vehicle
     */
    public static double getDueFinesTotalAmount(String licensePlate) {
        String sql = "SELECT SUM(fine_amount) as total FROM fines " +
                     "WHERE license_plate = ? AND status = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, licensePlate.toLowerCase());
            pstmt.setString(2, TransactionStatus.DUE.toString());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                double total = rs.getDouble("total");
                return Double.isNaN(total) ? 0.0 : total;
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting due fines total: " + e.getMessage());
        }
        return 0.0;
    }

    /**
     * Get all DUE fines for a vehicle as Fine objects
     */
    public static List<Fine> getDueFines(String licensePlate) {
        return getFinesByStatus(licensePlate, TransactionStatus.DUE);
    }

    /**
     * Get all fines for a vehicle filtered by status
     */
    public static List<Fine> getFinesByStatus(String licensePlate, TransactionStatus status) {
        List<Fine> fines = new ArrayList<>();
        String sql = "SELECT fine_id, license_plate, fine_amount, merged_reasons, scheme_id, status, created_at, paid_at " +
                     "FROM fines " +
                     "WHERE license_plate = ? AND status = ? " +
                     "ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, licensePlate.toLowerCase());
            pstmt.setString(2, status.toString());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Fine fine = new Fine(
                    rs.getInt("fine_id"),
                    rs.getString("license_plate"),
                    rs.getDouble("fine_amount"),
                    rs.getString("merged_reasons"),
                    rs.getInt("scheme_id"),
                    TransactionStatus.valueOf(rs.getString("status")),
                    rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                    rs.getTimestamp("paid_at") != null ? rs.getTimestamp("paid_at").toLocalDateTime() : null
                );
                fines.add(fine);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting fines by status: " + e.getMessage());
        }
        return fines;
    }

    /**
     * Update the status of a fine (DUE → PENDING → DONE)
     */
    public static void updateFineStatus(int fineId, TransactionStatus newStatus) {
        String sql;
        
        if (newStatus == TransactionStatus.DONE) {
            sql = "UPDATE fines SET status = ?, paid_at = ? WHERE fine_id = ?";
        } else {
            sql = "UPDATE fines SET status = ? WHERE fine_id = ?";
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, newStatus.toString());
            
            if (newStatus == TransactionStatus.DONE) {
                pstmt.setString(2, LocalDateTime.now().toString());
                pstmt.setInt(3, fineId);
            } else {
                pstmt.setInt(2, fineId);
            }
            
            pstmt.executeUpdate();
            System.out.println("Fine status updated to " + newStatus + " for fine_id: " + fineId);
            
        } catch (SQLException e) {
            System.err.println("Error updating fine status: " + e.getMessage());
        }
    }

    /**
     * Update all DUE fines for a vehicle to a new status
     */
    public static void updateAllDueFinesStatus(String licensePlate, TransactionStatus newStatus) {
        String sql = "UPDATE fines SET status = ? WHERE license_plate = ? AND status = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, newStatus.toString());
            pstmt.setString(2, licensePlate.toLowerCase());
            pstmt.setString(3, TransactionStatus.DUE.toString());
            
            int updated = pstmt.executeUpdate();
            System.out.println("Updated " + updated + " fines for vehicle: " + licensePlate);
            
        } catch (SQLException e) {
            System.err.println("Error updating all due fines: " + e.getMessage());
        }
    }

    /**
     * Get the applicable fine scheme based on entry time
     * Returns scheme_id that was active at the time of entry     //+++++++++++++++++++++++++++++++++++++++++
     */
    public static int getSchemeIdByEntryTime(LocalDateTime entryTime) {
        String sql = "SELECT scheme_id FROM fine_schemes " +
                     "WHERE applicable_from <= ? " +
                     "ORDER BY applicable_from DESC LIMIT 1";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, entryTime.toString());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("scheme_id");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting scheme by entry time: " + e.getMessage());
        }
        return 1; // Default to scheme_id 1
    }

    /**
     * Get the scheme type (FIXED, HOURLY, PROGRESSIVE) from scheme_id //++++++++++++++++++++++++++++++++++++++
     */
    public static String getSchemeType(int schemeId) {
        String sql = "SELECT scheme_type FROM fine_schemes WHERE scheme_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, schemeId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("scheme_type");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting scheme type: " + e.getMessage());
        }
        return "FIXED"; // Default fallback
    }
    
}

