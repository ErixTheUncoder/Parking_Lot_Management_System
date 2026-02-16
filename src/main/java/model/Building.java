package model;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private List<Floor> floors;

    public Building(List<Floor> fList) {
        this.floors = new ArrayList<>(fList);
    }

    public Building() {
        this.floors = new ArrayList<>();
    }

    public Floor getFloor(int index) {
        if (index >= 0 && index < floors.size()) {
            return floors.get(index);
        } else {
            System.out.println("Error: Floor index " + index + " out of bounds.");
        }
        return null;
    }

    public int getTotalFloors() {
        return floors.size();
    }

    public void addFloor(Floor floor) {
        this.floors.add(floor);
    }
    
    public List<Floor> getAllFloors() {
        return new ArrayList<>(floors);
    }
}
