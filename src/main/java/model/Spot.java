package model;

public class Spot {
    private long spotId;
    private String spotName;
    private SpotType spotType;
    private boolean isOccupied;
    private String currentVehicle;
    private boolean isClosed;
    private int floorNum;
    private int rowNum;
    private int spotNum;
    private Floor floor;

    public Spot(long spotId, SpotType spotType, boolean isOccup, String currV, 
                int floorN, int rowN, int spotN) {
        this.spotId = spotId;
        this.spotType = spotType;
        this.isOccupied = isOccup;
        this.currentVehicle = currV;
        this.floorNum = floorN;
        this.rowNum = rowN;
        this.spotNum = spotN;
        this.isClosed = false;
        this.spotName = String.format("F%d-R%d-S%d", floorN, rowN, spotN);
    }

    public long getSpotID() {
        return spotId;
    }

    public long getDBSpotID() {
        return spotId;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied, String currV) {
        this.isOccupied = occupied;
        this.currentVehicle = currV;
    }

    public boolean getStatus() {
        return isClosed;
    }

    public String getCurrentVehicle() {
        return currentVehicle;
    }

    public String getSpotName() {
        return spotName;
    }

    public void releaseVehicle() {
        this.isOccupied = false;
        this.currentVehicle = null;
    }

    public int getFloorNum() {
        return floorNum;
    }
    
    public void setFloor(Floor floor) {
        this.floor = floor;
    }
    
    public Floor getFloor() {
        return floor;
    }
}
