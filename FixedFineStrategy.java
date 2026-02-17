

//The vehicle stays more than 24 hours in a parking lot
//Flat RM 50 fine for overstaying


public class FixedFineStrategy implements FineStrategy {
    private static final double FIXED_FINE = 50.0;

    @Override
    public double calculateFine(long hoursParked) {
        if (hoursParked > 24) {
            return FIXED_FINE;
        }
        return 0.0;                 //if the parking time is not over 24 hour , no fine charged
    }

    @Override
    public String getStrategyName() {
        return "Fixed Fine (RM 50)";
    }
}
