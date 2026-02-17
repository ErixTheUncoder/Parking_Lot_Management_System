package db;

import model.*;
import java.sql.*;
import java.time.LocalDateTime;

public class TicketDAO {

    public static void saveNewTicketAndOccupySpot(Ticket ticket, Spot spot) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Insert ticket
            String ticketSql = "INSERT INTO tickets (ticket_id, license_plate, spot_id, " +
                              "entry_timestamp, is_paid, is_active) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(ticketSql)) {
                pstmt.setString(1, ticket.getTicketID());
                pstmt.setString(2, ticket.getLicensePlate());
                pstmt.setLong(3, ticket.getSpotID());
                pstmt.setString(4, ticket.getEntryTimestamp().toString());
                pstmt.setInt(5, 0);
                pstmt.setInt(6, 1);
                pstmt.executeUpdate();
            }

            // Update spot
            String spotSql = "UPDATE spots SET is_occupied = ?, current_vehicle = ? WHERE spot_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(spotSql)) {
                pstmt.setInt(1, 1);
                pstmt.setString(2, ticket.getLicensePlate());
                pstmt.setLong(3, spot.getSpotID());
                pstmt.executeUpdate();
            }

            conn.commit();
            System.out.println("Ticket saved and spot occupied successfully.");

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println("Transaction rolled back due to error.");
                } catch (SQLException ex) {
                    System.err.println("Error rolling back transaction: " + ex.getMessage());
                }
            }
            System.err.println("Error saving ticket: " + e.getMessage());
            e.printStackTrace();
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

    public static void closeTicketAndFreeSpot(Ticket ticket, Spot spot) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Update ticket
            String ticketSql = "UPDATE tickets SET is_active = ?, is_paid = ? WHERE ticket_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(ticketSql)) {
                pstmt.setInt(1, 0);
                pstmt.setInt(2, 1);
                pstmt.setString(3, ticket.getTicketID());
                pstmt.executeUpdate();
            }

            // Update spot
            String spotSql = "UPDATE spots SET is_occupied = ?, current_vehicle = ? WHERE spot_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(spotSql)) {
                pstmt.setInt(1, 0);
                pstmt.setString(2, null);
                pstmt.setLong(3, spot.getSpotID());
                pstmt.executeUpdate();
            }

            conn.commit();
            System.out.println("Ticket closed and spot freed successfully.");

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println("Transaction rolled back due to error.");
                } catch (SQLException ex) {
                    System.err.println("Error rolling back transaction: " + ex.getMessage());
                }
            }
            System.err.println("Error closing ticket: " + e.getMessage());
            e.printStackTrace();
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

    public static Ticket findActiveByLicensePlate(String licensePlate) {
        String sql = "SELECT * FROM tickets WHERE license_plate = ? AND is_active = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, licensePlate.toLowerCase());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String ticketId = rs.getString("ticket_id");
                long spotId = rs.getLong("spot_id");
                String plate = rs.getString("license_plate");
                LocalDateTime entryTime = LocalDateTime.parse(rs.getString("entry_timestamp"));
                boolean isPaid = rs.getInt("is_paid") == 1;
                boolean isActive = rs.getInt("is_active") == 1;
                
                return new Ticket(ticketId, spotId, plate, entryTime, isPaid, isActive);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding active ticket: " + e.getMessage());
        }
        return null;
    }
}
