
import java.util.List;

public class FineManager {
    private FineStrategy currentStrategy;
    private CompatibilityRegistry compatible_check;
    private final double compViolationAmount = 200.00;
    private final String CompViolationLog ="PARKED AT WRONG SPOT!";

    public FineManager() {
        loadStrategyFromDatabase();
    }

    /**
     * Load the currently active fine strategy from the database //it is done to keep null violation and fallback fine scheme
     */
    private void loadStrategyFromDatabase() {
        String schemeType = "FIXED";
        setStrategy(schemeType);
    }

    /**
     * Set the fine strategy based on scheme type
     */
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

    /**
     * Check if a fine is applicable based on parking duration
     * checks if duration > 24 hours (overstay condition)
     * 
     * Flow: First returns boolean if fine applies, then amount is asked separately
     * 
     * @param vehicle The parked vehicle
     * @param spot The parking spot (stub for compatibility check - handled elsewhere)
     * @return true if fine applies (overstay > 24 hours), false otherwise
     * flag_true starts as false , if true it is modified or it is sent as false indicating no fines applicable 
     */
    public boolean isFineApplicable(Vehicle vehicle, Spot spot) {
        long hoursParked = vehicle.calculateDuration();
        boolean flag_true = false;
        
        //CHECK 1: compatibality check
        if(compatible_check.isCompatible(vehicle.getVehicleType(), spot.getSpotType())){
                saveFine(vehicle,compViolationAmount,CompViolationLog);
                flag_true=true;
            System.out.println("Fine applicable: Vehicle " + vehicle.getLicensePlate() +   
                             " has parked at incompatible spot " + spot.getSpotType());
        }
        // CHECK 2: Duration check
        if (hoursParked > 24) {
            System.out.println("Fine applicable: Vehicle " + vehicle.getLicensePlate() +   
                             " has overstayed " + hoursParked + " hours (threshold: 24 hours)");
            flag_true=true;
        }
        
        System.out.println("No fine applicable: Vehicle " + vehicle.getLicensePlate() +   
                         " parked for " + hoursParked + " hours (within 24-hour limit)");
        return flag_true;
    }

    /**
     * Calculate the fine amount based on vehicle and applicable strategy
     * 
     * @param vehicle The parked vehicle
     * @return The calculated fine amount
     */
    public double calculateFineAmount(Vehicle vehicle) {
        long hoursParked = vehicle.calculateDuration();
        
        if (hoursParked <= 24) {
            return 0.0;
        }
        
        // Get applicable scheme based on entry time
        int schemeId = FineDAO.getSchemeIdByEntryTime(vehicle.getEntryTime());   //<<<<<<<<<<<<<<<<<<<<<
        String schemeType = FineDAO.getSchemeType(schemeId);
        
        // Set the appropriate strategy based on the scheme
        setStrategy(schemeType);
        
        // Calculate and return the fine
        double fineAmount = currentStrategy.calculateFine(hoursParked);
        System.out.println("Fine calculated using " + schemeType + " strategy: RM " + fineAmount);
        
        return fineAmount;
    }

    /**
     * Check if a vehicle has due fines (non-paid fines with DUE status)
     * 
     * @param vehicle The vehicle to check
     * @return true if there are due fines, false otherwise
     */
    public boolean isFineDue(Vehicle vehicle) {
        List<Fine> dueFines = FineDAO.getDueFines(vehicle.getLicensePlate());  
        boolean hasDueFines = !dueFines.isEmpty();
        
        if (hasDueFines) {
            System.out.println("Vehicle " + vehicle.getLicensePlate() +     
                             " has " + dueFines.size() + " due fine(s)");
        } else {
            System.out.println("No due fines for vehicle " + vehicle.getLicensePlate());  
        }
        
        return hasDueFines;
    }

    /**
     * Get all due fines for a vehicle
     * 
     * @param vehicle The vehicle to retrieve fines for
     * @return List of Fine objects with DUE status
     */
    public List<Fine> getDueFines(Vehicle vehicle) {
        return FineDAO.getDueFines(vehicle.getLicensePlate());
    }

    /**
     * Get the total amount of due fines for a vehicle
     * 
     * @param licensePlate The vehicle's license plate
     * @return Total amount of all DUE fines
     */
    public double getDueFinesTotalAmount(String licensePlate) {
        return FineDAO.getDueFinesTotalAmount(licensePlate);
    }

    /**
     * Save a new fine to the database
     * 
     * @param vehicle The vehicle to fine
     * @param fineAmount The calculated fine amount
     * @param mergedReasons Combined reason string
     */
    public void saveFine(Vehicle vehicle, double fineAmount, String mergedReasons) {
        if (fineAmount <= 0) {
            System.out.println("Fine amount must be positive. Not saving.");
            return;
        }
        
        int schemeId = FineDAO.getSchemeIdByEntryTime(vehicle.getEntryTime());
        FineDAO.save(vehicle.getLicensePlate(), fineAmount, mergedReasons, 
                     schemeId, TransactionStatus.DUE);
    }

    /**
     * Mark all DUE fines for a vehicle as PENDING (payment in progress)
     * This "locks" the fines so they can't be modified during payment
     * 
     * @param licensePlate The vehicle's license plate
     */
    public void initiateFinePayment(String licensePlate) {
        FineDAO.updateAllDueFinesStatus(licensePlate, TransactionStatus.PENDING);
        System.out.println("Fine payment initiated for vehicle: " + licensePlate + 
                         " (status: PENDING)");
    }

    /**
     * Confirm payment and mark all PENDING fines as DONE
     * 
     * @param licensePlate The vehicle's license plate
     */
    public void confirmFinePayment(String licensePlate) {
        List<Fine> pendingFines = FineDAO.getFinesByStatus(licensePlate, TransactionStatus.PENDING);
        
        for (Fine fine : pendingFines) {
            FineDAO.updateFineStatus(fine.getFineId(), TransactionStatus.DONE);
        }
        
        System.out.println("Fine payment confirmed for vehicle: " + licensePlate + 
                         " (" + pendingFines.size() + " fine(s) marked as DONE)");
    }

    /**
     * Get current strategy name (for informational purposes)
     */
    public String getCurrentStrategyName() {
        return currentStrategy.getStrategyName();
    }
}

