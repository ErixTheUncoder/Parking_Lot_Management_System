

//The vehicle stays more than 24 hours in a parking lot
/*
•First 24 hours: RM 50
•Hours 24-48: Additional RM 100
•Hours 48-72: Additional RM 150
•Above 72 hours: Additional RM 200
*/

public class ProgressiveFineStrategy implements FineStrategy {
    
    @Override
    public double calculateFine(long hoursParked) {
        if (hoursParked <= 24) {
            return 0.0;
        }
        
        double fine = 0.0;
        
        // First 24 hours overstay: RM 50
        if (hoursParked > 24) {
            fine += 50.0;
        }
        
        // Hours 24-48: Additional RM 100
        if (hoursParked > 48) {
            fine += 100.0;
        }
        
        // Hours 48-72: Additional RM 150
        if (hoursParked > 72) {
            fine += 150.0;
        }
        
        // Above 72 hours: Additional RM 200
        if (hoursParked > 96) {
            fine += 200.0;
        }
        
        return fine;
    }

    @Override
    public String getStrategyName() {
        return "Progressive Fine (24h: RM50, 48h: +RM100, 72h: +RM150, 96h+: +RM200)";
    }
}
