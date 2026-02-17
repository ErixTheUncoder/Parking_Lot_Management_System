package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Vehicle {
    private String licensePlate;
    private VehicleType vehicleType;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Duration duration;

    public Vehicle(String licensePlate, VehicleType vehicleType) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty.");
        }
        if (vehicleType == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null.");
        }
        
        this.licensePlate = licensePlate.replaceAll("\\s+", "").toLowerCase();
        this.vehicleType = vehicleType;
        this.entryTime = null;
        this.exitTime = null;
        this.duration = null;
    }

    public Vehicle(String licensePlate, LocalDateTime entryTime, VehicleType vehicleType) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty.");
        }
        if (vehicleType == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null.");
        }
        if (entryTime == null) {
            throw new IllegalArgumentException("Entry time cannot be null.");
        }

        this.licensePlate = licensePlate.replaceAll("\\s+", "").toLowerCase();
        this.vehicleType = vehicleType;
        this.entryTime = entryTime;
        this.exitTime = null;
        this.duration = null;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        if (entryTime == null) {
            throw new IllegalArgumentException("Entry time cannot be null.");
        }
        this.entryTime = entryTime;
        this.exitTime = null;
        this.duration = null;
    }

    public Duration calculateDuration() {
        if (entryTime == null) {
            throw new IllegalStateException("Entry time not set.");
        }

        this.exitTime = LocalDateTime.now();
        this.duration = Duration.between(entryTime, exitTime);

        if (duration.isNegative()) {
            throw new IllegalStateException("Exit time cannot be before entry time.");
        }

        return duration;
    }
}
