package com.parkinglot.strategy;

/**
 * Member 3 (Pavien + Abid): PAYMENT & FINE SYSTEM + DESIGN PATTERN
 * Strategy Pattern Interface for Fine Calculation
 * Implements Open-Closed Principle
 */
public interface FineStrategy {
    double calculateFine(long hoursOverstay);
}
