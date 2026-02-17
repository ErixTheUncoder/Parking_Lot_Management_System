package service;

import strategy.*;
import db.FineDAO;

public class FineManager {
    private FineStrategy currentStrategy;

    public FineManager() {
        loadStrategyFromDatabase();
    }

    private void loadStrategyFromDatabase() {
        String schemeType = FineDAO.getActiveFineScheme();
        setStrategy(schemeType);
    }

    public void setStrategy(String schemeType) {
        switch (schemeType) {
            case "FIXED":
                this.currentStrategy = new FixedFineStrategy();
                break;
            case "PROGRESSIVE":
                this.currentStrategy = new ProgressiveFineStrategy();
                break;
            case "HOURLY":
                this.currentStrategy = new HourlyFineStrategy();
                break;
            default:
                this.currentStrategy = new FixedFineStrategy();
        }
        System.out.println("Fine strategy set to: " + currentStrategy.getStrategyName());
    }

    public double calculateCurrentFine(long hoursParked) {
        return currentStrategy.calculateFine(hoursParked);
    }

    public double getUnpaidFines(String licensePlate) {
        return FineDAO.getUnpaidFinesForVehicle(licensePlate);
    }

    public double getTotalFines(String licensePlate, long hoursParked) {
        double currentFine = calculateCurrentFine(hoursParked);
        double unpaidFines = getUnpaidFines(licensePlate);
        
        // Save current fine if applicable
        if (currentFine > 0) {
            String reason = String.format("Overstay fine: %d hours parked", hoursParked);
            FineDAO.save(licensePlate, currentFine, reason);
        }
        
        return currentFine + unpaidFines;
    }

    public void markFinesAsPaid(String licensePlate) {
        FineDAO.markAsPaid(licensePlate);
    }

    public String getCurrentStrategyName() {
        return currentStrategy.getStrategyName();
    }
}
