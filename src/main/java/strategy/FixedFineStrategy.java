package strategy;

public class FixedFineStrategy implements FineStrategy {
    private static final double FIXED_FINE = 50.0;

    @Override
    public double calculateFine(long hoursParked) {
        if (hoursParked > 24) {
            return FIXED_FINE;
        }
        return 0.0;
    }

    @Override
    public String getStrategyName() {
        return "Fixed Fine (RM 50)";
    }
}
