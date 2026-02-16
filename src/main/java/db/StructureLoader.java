package db;

import java.sql.*;
import java.util.*;
import model.*;

public class StructureLoader {

    public static Building loadBuildingFromDatabase() {
        Building building = new Building();

        String floorSql = "SELECT * FROM floors ORDER BY floor_number";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(floorSql)) {

            while (rs.next()) {
                int floorNumber = rs.getInt("floor_number");
                int floorId = rs.getInt("floor_id");
                double extraCharge = rs.getDouble("extra_charge");

                // Load spots for this floor using a separate PreparedStatement
                List<List<Spot>> rows = loadSpotsForFloor(floorId, conn);

                Floor floor = new Floor(floorNumber, extraCharge, rows);
                building.addFloor(floor);

                System.out.println("Loaded Floor " + floorNumber + " with " +
                        floor.getTotalSpotCount() + " spots");
            }

        } catch (SQLException e) {
            System.err.println("Error loading building structure: " + e.getMessage());
            e.printStackTrace();
        }

        return building;
    }

    private static List<List<Spot>> loadSpotsForFloor(int floorId, Connection conn) {
        Map<Integer, List<Spot>> rowMap = new TreeMap<>();

        String spotSql = "SELECT * FROM spots WHERE floor_id = ? ORDER BY row_num, spot_num";
        try (PreparedStatement pstmt = conn.prepareStatement(spotSql)) {

            pstmt.setInt(1, floorId);
            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    long spotId = rs.getLong("spot_id"); // SQLite returns Long
                    SpotType spotType = SpotType.valueOf(rs.getString("spot_type"));
                    boolean isOccupied = rs.getInt("is_occupied") == 1;
                    String currentVehicle = rs.getString("current_vehicle");
                    int rowNum = rs.getInt("row_num");
                    int spotNum = rs.getInt("spot_num");

                    Spot spot = new Spot(spotId, spotType, isOccupied, currentVehicle,
                            floorId, rowNum, spotNum);

                    rowMap.putIfAbsent(rowNum, new ArrayList<>());
                    rowMap.get(rowNum).add(spot);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error loading spots for floor: " + e.getMessage());
        }

        return new ArrayList<>(rowMap.values());
    }
}
