package model;

import java.util.*;

public class Floor {
    private int floorNumber;
    private double extraCharge;
    private List<List<Spot>> rows;
    private Map<Long, Spot> flatAccess;
    private Map<SpotType, List<Long>> flatSearchMap;

    public Floor(int floorNumber, double extraCharge, List<List<Spot>> R) {
        this.floorNumber = floorNumber;
        this.extraCharge = extraCharge;
        this.rows = R;

        this.flatAccess = new HashMap<>();
        for (List<Spot> row : R) {
            for (Spot spot : row) {
                this.flatAccess.put(spot.getDBSpotID(), spot);
                spot.setFloor(this);
            }
        }

        this.flatSearchMap = new HashMap<>();
        for (List<Spot> row : R) {
            for (Spot spot : row) {
                if (!spot.isOccupied() && !spot.getStatus()) {
                    this.flatSearchMap.putIfAbsent(spot.getSpotType(), new ArrayList<>());
                    this.flatSearchMap.get(spot.getSpotType()).add(spot.getDBSpotID());
                }
            }
        }
    }

    public List<Long> findAvailableSpot(SpotType type) {
        List<Long> spots = flatSearchMap.get(type);
        return spots != null ? spots : new ArrayList<>();
    }

    public Spot getSpot(long spotId) {
        return flatAccess.get(spotId);
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public double getExtraCharge() {
        return extraCharge;
    }

    public List<Spot> getAllSpots() {
        return new ArrayList<>(flatAccess.values());
    }

    public int getTotalSpotCount() {
        return flatAccess.size();
    }

    public int getAvailableSpotCount() {
        int count = 0;
        for (Spot spot : flatAccess.values()) {
            if (!spot.isOccupied() && !spot.getStatus()) {
                count++;
            }
        }
        return count;
    }

    public int getOccupiedSpotCount() {
        int count = 0;
        for (Spot spot : flatAccess.values()) {
            if (spot.isOccupied()) {
                count++;
            }
        }
        return count;
    }

    public int getOccupiedSpotCountByType(SpotType type) {
        int count = 0;
        for (Spot spot : flatAccess.values()) {
            if (spot.getSpotType() == type && spot.isOccupied()) {
                count++;
            }
        }
        return count;
    }
    
    public int getSpotCountByType(SpotType type) {
        int count = 0;
        for (Spot spot : flatAccess.values()) {
            if (spot.getSpotType() == type) {
                count++;
            }
        }
        return count;
    }
    
    public int getAvailableSpotCountByType(SpotType type) {
        List<Long> availableSpots = flatSearchMap.get(type);
        return (availableSpots != null) ? availableSpots.size() : 0;
    }

    public void updateFlatSearchMap(Spot spot, boolean isEntryCall) {
        SpotType T = spot.getSpotType();
        Long spotI = spot.getDBSpotID();

        if (T == null || !flatSearchMap.containsKey(T)) {
            System.out.println("Spot type does not exist");
            return;
        }

        if (isEntryCall) {
            flatSearchMap.get(T).remove(spotI);
        } else {
            flatSearchMap.get(T).add(spotI);
        }
    }
}
