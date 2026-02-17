

//The vehicle stays more than 24 hours in a parking lot
//RM 20 per hour for overstaying

public class HourlyFineStrategy implements FineStrategy {
    private static final double HOURLY_FINE_RATE = 20.0;    

    @Override
    public double calculateFine(long hoursParked) {
        if (hoursParked <= 24) {    //final check before calculating fine
            return 0.0;
        }
        
        long overstayHours = hoursParked - 24;
        return overstayHours * HOURLY_FINE_RATE;
    }

    @Override
    public String getStrategyName() {
        return "Hourly Fine (RM 20 per hour overstay)";
    }
}
