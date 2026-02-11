package com.parkinglot.strategy;

/**
 * Member 3 (Pavien + Abid): PAYMENT & FINE SYSTEM + DESIGN PATTERN
 * Fixed fine amount regardless of hours
 */
public class FixedFine implements FineStrategy {
    // TODO: Implement fixed fine calculation
    
    @Override
    public double calculateFine(long hoursOverstay) {
        // TODO: Return fixed fine amount
        return 0.0;
    }
}
