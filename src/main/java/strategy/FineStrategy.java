package strategy;

public interface FineStrategy {
    double calculateFine(long hoursParked);
    String getStrategyName();
}
