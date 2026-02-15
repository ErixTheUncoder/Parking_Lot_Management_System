

import java.util.ArrayList;
import java.util.List;

public class AdminWindow{
  private Building building;
  private Floor floor;
  private Spot spot;

  public AdminWindow(Building building, Floor floor, Spot spot){
    this.building = building;
    this.floor = floor;
    this.spot = spot;
  }

  public void viewAllFloor(){ //TODO: Change the print into GUI functions later
    int index = 0;
    int allFloor = 0;
    while(building.getFloor(index)!=null){
        allFloor++;
        index++;
    }
    System.out.println("There are " + allFloor + " Floor(s)");
  }
  
  /**
   * View all spots for a specific floor
   * @param floorNumber The floor number to view
   */
  public void viewAllSpot(int floorNumber) {
    Floor floor = building.getFloor(floorNumber);
    
    if (floor == null) {
      System.out.println("Floor " + floorNumber + " not found!");
      return;
    }
    
    System.out.println("\n╔════════════════════════════════════════════════╗");
    System.out.println("║     Floor " + floorNumber + " - Spot Statistics              ║");
    System.out.println("╚════════════════════════════════════════════════╝");
    
    // Overall statistics
    int totalSpots = floor.getTotalSpotCount();
    int availableSpots = floor.getAvailableSpotCount();
    int occupiedSpots = floor.getOccupiedSpotCount();
    
    System.out.println("\n  Total Spots:     " + totalSpots);
    System.out.println("  Available:       " + availableSpots);
    System.out.println("  Occupied:        " + occupiedSpots);
    
    // Statistics by spot type
    System.out.println("\n  Breakdown by Type:");
    System.out.println("  ┌──────────────┬───────┬───────────┬──────────┐");
    System.out.println("  │ Spot Type    │ Total │ Available │ Occupied │");
    System.out.println("  ├──────────────┼───────┼───────────┼──────────┤");
    
    // Get stats for each spot type
    for (SpotType type : SpotType.values()) {
      int total = floor.getSpotCountByType(type);
      if (total > 0) {  // Only show types that exist on this floor
        int available = floor.getAvailableSpotCountByType(type);
        int occupied = floor.getOccupiedSpotCountByType(type);
        System.out.printf("  │ %-12s │ %5d │ %9d │ %8d │%n", 
                         type, total, available, occupied);
      }
    }
    
    System.out.println("  └──────────────┴───────┴───────────┴──────────┘");
    System.out.println();
  }


  
  /**
   * View all spots for all floors in the building
   */
  public void viewAllSpotsAllFloors() {
    System.out.println("\n╔════════════════════════════════════════════════╗");
    System.out.println("║       Building-Wide Spot Statistics            ║");
    System.out.println("╚════════════════════════════════════════════════╝\n");
    
    int floorIndex = 0;
    int totalBuildingSpots = 0;
    int totalBuildingAvailable = 0;
    int totalBuildingOccupied = 0;
    
    while (building.getFloor(floorIndex) != null) {
      Floor floor = building.getFloor(floorIndex);
      int total = floor.getTotalSpotCount();
      int available = floor.getAvailableSpotCount();
      int occupied = floor.getOccupiedSpotCount();
      
      System.out.println("Floor " + floorIndex + ": Total=" + total + 
                        ", Available=" + available + ", Occupied=" + occupied);
      
      totalBuildingSpots += total;
      totalBuildingAvailable += available;
      totalBuildingOccupied += occupied;
      
      floorIndex++;
    }
    
    System.out.println("\n─────────────────────────────────────────────────");
    System.out.println("Building Total: " + totalBuildingSpots + " spots");
    System.out.println("  Available: " + totalBuildingAvailable);
    System.out.println("  Occupied:  " + totalBuildingOccupied);
    System.out.println();
  }

  public void viewAllParkedVehicle(){
    System.out.println("\n╔════════════════════════════════════════════════╗");
    System.out.println("║       All Parked Vehicles in Building         ║");
    System.out.println("╚════════════════════════════════════════════════╝\n");
    
    int totalParkedVehicles = 0;
    int floorIndex = 0;
    
    System.out.println("  ┌──────────────┬─────────┬────────────────┬──────────────┐");
    System.out.println("  │ License Plate│ Floor   │ Spot Name      │ Spot Type    │");
    System.out.println("  ├──────────────┼─────────┼────────────────┼──────────────┤");
    
    // Iterate through all floors
    while (building.getFloor(floorIndex) != null) {
      Floor floor = building.getFloor(floorIndex);
      List<Spot> allSpots = floor.getAllSpots();
      
      // Check each spot for occupied vehicles
      for (Spot spot : allSpots) {
        if (spot.isOccupied()) {
          String licensePlate = spot.getCurrentVehicle();
          String spotName = spot.getSpotName();
          String spotType = spot.getSpotType().toString();
          
          System.out.printf("  │ %-12s │ %7d │ %-14s │ %-12s │%n", 
                           licensePlate, floorIndex, spotName, spotType);
          totalParkedVehicles++;
        }
      }
      
      floorIndex++;
    }
    
    System.out.println("  └──────────────┴─────────┴────────────────┴──────────────┘");
    System.out.println("\n  Total Parked Vehicles: " + totalParkedVehicles);
    System.out.println();
  }
  
  /**
   * View parked vehicles on a specific floor
   * Overloaded method that accepts a floor number parameter
   * @param floorNumber The floor number to view
   */
  public void viewAllParkedVehicle(int floorNumber){
    Floor floor = building.getFloor(floorNumber);
    
    if (floor == null) {
      System.out.println("Floor " + floorNumber + " not found!");
      return;
    }
    
    System.out.println("\n╔════════════════════════════════════════════════╗");
    System.out.println("║   Parked Vehicles on Floor " + floorNumber + "                ║");
    System.out.println("╚════════════════════════════════════════════════╝\n");
    
    List<Spot> allSpots = floor.getAllSpots();
    int parkedCount = 0;
    
    System.out.println("  ┌──────────────┬────────────────┬──────────────┐");
    System.out.println("  │ License Plate│ Spot Name      │ Spot Type    │");
    System.out.println("  ├──────────────┼────────────────┼──────────────┤");
    
    for (Spot spot : allSpots) {
      if (spot.isOccupied()) {
        String licensePlate = spot.getCurrentVehicle();
        String spotName = spot.getSpotName();
        String spotType = spot.getSpotType().toString();
        
        System.out.printf("  │ %-12s │ %-14s │ %-12s │%n", 
                         licensePlate, spotName, spotType);
        parkedCount++;
      }
    }
    
    if (parkedCount == 0) {
      System.out.println("  │            No vehicles parked on this floor             │");
    }
    
    System.out.println("  └──────────────┴────────────────┴──────────────┘");
    System.out.println("\n  Parked Vehicles on Floor " + floorNumber + ": " + parkedCount);
    System.out.println();
  }

  //-----------------------------------------------------------------------------------------
  
  /**
   * Main method for testing AdminWindow functionality
   */
  public static void main(String[] args) {
    // Create dummy building for testing
    Building testBuilding = createTestBuilding();
    
    AdminWindow admin = new AdminWindow(testBuilding, null, null);
    
    // Test viewAllFloor
    admin.viewAllFloor();
    
    // Test viewAllSpot for Floor 0
    admin.viewAllSpot(0);
    
    // Test viewAllSpot for Floor 1
    admin.viewAllSpot(1);
    
    // Test viewAllSpotsAllFloors
    admin.viewAllSpotsAllFloors();
    
    // Test viewAllParkedVehicle - all vehicles in building
    admin.viewAllParkedVehicle();
    
    // Test viewAllParkedVehicle - specific floors (overloaded method)
    admin.viewAllParkedVehicle(0);
    admin.viewAllParkedVehicle(2);
  }

  /**
   * Helper method to create test building with dummy data
   */
  private static Building createTestBuilding() {
    List<Floor> floors = new ArrayList<>();
    
    // Create 3 floors
    for (int floorNum = 0; floorNum < 3; floorNum++) {
      List<List<Spot>> rows = new ArrayList<>();
      long spotIdCounter = (floorNum + 1) * 100L;
      
      // Each floor has 3 rows
      for (int rowNum = 1; rowNum <= 3; rowNum++) {
        List<Spot> row = new ArrayList<>();
        
        // Each row has 5 spots
        for (int spotNum = 1; spotNum <= 5; spotNum++) {
          SpotType type;
          if (spotNum == 1) {
            type = SpotType.COMPACT;
          } else if (spotNum == 2) {
            type = SpotType.REGULAR;
          } else if (spotNum == 3) {
            type = SpotType.LARGE;
          } else if (spotNum == 4) {
            type = SpotType.RESERVED;
          } else {
            type = SpotType.HANDICAPPED;
          }
          
          // Make some spots occupied
          boolean isOccupied = (spotNum == 2 || spotNum == 4);
          String vehicle = isOccupied ? "CAR-" + spotIdCounter : null;
          
          Spot spot = new Spot(spotIdCounter++, type, isOccupied, vehicle,
                              floorNum, rowNum, spotNum);
          row.add(spot);
        }
        rows.add(row);
      }
      
      Floor floor = new Floor(floorNum, rows);
      floors.add(floor);
    }
    
    return new Building(floors);
  }

}

