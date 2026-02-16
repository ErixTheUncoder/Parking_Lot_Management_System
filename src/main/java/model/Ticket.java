package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Ticket {
    private String ticketID;
    private String licensePlate;
    private LocalDateTime entryTimestamp;
    private long spotID;
    private boolean isPaid;
    private boolean isActive;
    private Spot spot;

    public Ticket(String licensePlate, long spotID, boolean activeS) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty.");
        }
        if (spotID <= 0) {
            throw new IllegalArgumentException("Spot ID must be positive.");
        }

        this.licensePlate = licensePlate.replaceAll("\\s+", "").toLowerCase();
        this.spotID = spotID;
        this.entryTimestamp = LocalDateTime.now();
        this.ticketID = "T-" + this.licensePlate + "-" + System.currentTimeMillis();
        this.isPaid = false;
        this.isActive = activeS;
    }

    public Ticket(String tickID, long spotID, String licenId, 
                  LocalDateTime entryT, boolean isPaid, boolean isActive) {
        if (tickID == null || tickID.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket ID cannot be null or empty.");
        }
        if (licenId == null || licenId.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty.");
        }
        if (spotID <= 0) {
            throw new IllegalArgumentException("Spot ID must be positive.");
        }
        if (entryT == null) {
            throw new IllegalArgumentException("Entry timestamp cannot be null.");
        }

        this.ticketID = tickID;
        this.spotID = spotID;
        this.licensePlate = licenId.replaceAll("\\s+", "").toLowerCase();
        this.entryTimestamp = entryT;
        this.isPaid = isPaid;
        this.isActive = isActive;
    }

    public String getTicketID() {
        return ticketID;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public long getSpotID() {
        return spotID;
    }

    public LocalDateTime getEntryTimestamp() {
        return entryTimestamp;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public void setPaid() {
        this.isPaid = true;
    }

    public boolean getStat() {
        return isActive;
    }

    public boolean changeStat(boolean newStat) {
        this.isActive = newStat;
        return this.isActive;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public Spot getSpot() {
        return spot;
    }

    public long getDurationHours() {
        LocalDateTime now = LocalDateTime.now();
        long hours = ChronoUnit.HOURS.between(entryTimestamp, now);
        long minutes = ChronoUnit.MINUTES.between(entryTimestamp, now) % 60;
        
        // Ceiling: if any minutes, round up to next hour
        if (minutes > 0) {
            hours++;
        }
        
        return hours;
    }

    public String getEntryTimeFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return entryTimestamp.format(formatter);
    }
}
