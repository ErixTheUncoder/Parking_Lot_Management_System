package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Invoice {
    private String invoiceID;
    private String licensePlate;
    private double basePrice;
    private double floorPremium;
    private double discountAmount;
    private double fines;
    private double totalAmount;
    private LocalDateTime invoiceTimestamp;

    public Invoice() {
        this.invoiceID = "INV-" + System.currentTimeMillis();
        this.invoiceTimestamp = LocalDateTime.now();
        this.basePrice = 0.0;
        this.floorPremium = 0.0;
        this.discountAmount = 0.0;
        this.fines = 0.0;
        this.totalAmount = 0.0;
    }

    public Invoice(String invoiceID, String licensePlate, double basePrice, 
                   double floorPremium, double discountAmount, double fines, 
                   double totalAmount, LocalDateTime timestamp) {
        this.invoiceID = invoiceID;
        this.licensePlate = licensePlate;
        this.basePrice = basePrice;
        this.floorPremium = floorPremium;
        this.discountAmount = discountAmount;
        this.fines = fines;
        this.totalAmount = totalAmount;
        this.invoiceTimestamp = timestamp;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setBasePrice(double basePrice) {
        if (basePrice < 0) {
            throw new IllegalArgumentException("Base price cannot be negative");
        }
        this.basePrice = basePrice;
    }

    public void setFloorPremium(double floorPremium) {
        if (floorPremium < 0) {
            throw new IllegalArgumentException("Floor premium cannot be negative");
        }
        this.floorPremium = floorPremium;
    }

    public void setDiscountAmount(double discountAmount) {
        if (discountAmount < 0) {
            throw new IllegalArgumentException("Discount amount cannot be negative");
        }
        double subtotal = basePrice + floorPremium;
        if (discountAmount > subtotal) {
            throw new IllegalArgumentException("Discount cannot exceed subtotal");
        }
        this.discountAmount = discountAmount;
    }

    public void setFines(double fines) {
        if (fines < 0) {
            throw new IllegalArgumentException("Fines cannot be negative");
        }
        this.fines = fines;
    }

    public double calculateTotal() {
        double subtotal = basePrice + floorPremium;
        double discountedSubtotal = subtotal - discountAmount;
        this.totalAmount = discountedSubtotal + fines;
        return this.totalAmount;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double getFloorPremium() {
        return floorPremium;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getFines() {
        return fines;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getInvoiceTimestamp() {
        return invoiceTimestamp;
    }

    public String getTimestampFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return invoiceTimestamp.format(formatter);
    }
}
