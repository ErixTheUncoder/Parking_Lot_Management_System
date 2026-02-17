package model;

public enum VehicleType {
    MOTORCYCLE("Motorcycle"),
    CAR("Car"),
    SUV("SUV"),
    TRUCK("Truck"),
    HANDICAPPED("Handicapped"),
    VIP("VIP");
    
    private final String displayName;
    
    VehicleType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}
