import java.util.HashMap;
import java.util.Map;

public class PriceRegistry {
    private static Map<SpotType, Double> baseRates = new HashMap<>();

    private static void loadDefaults() {
        baseRates.put(SpotType.COMPACT, 2.0);
        baseRates.put(SpotType.REGULAR, 5.0);
        baseRates.put(SpotType.HANDICAPPED, 2.0);
        baseRates.put(SpotType.RESERVED, 10.0);
        baseRates.put(SpotType.LARGE, 7.0);
    }


    public PriceRegistry(){
        loadDefaults();
    }

    public static Double getBaseRate(SpotType spotType) {
        return baseRates.getOrDefault(spotType, 5.0);
    }

    public static Map<SpotType, Double> getAllRates() {
        return new HashMap<>(baseRates);
    }
    //.....

    //IS discount be applied ? gets a discounted price of RM 2/hour for vehicleType is HANDICAPPED

    //expose method for checking discount 

    //if true calculate discount and send the update amount of parameter amount received >> in return call

    public boolean isDiscounted(VehicleType vehicleType){
      if(vehicleType == VehicleType.HANDICAPPED){
        return true;
      }
      return false;
    }

    /**
     * Applies discount to the amount if vehicle qualifies
     * Handicapped vehicles get RM 2.00 discount
     * @param amount Original parking amount
     * @param vehicleType Type of vehicle
     * @return Discounted amount if applicable, otherwise original amount
     */
    public double applyDiscountForDisabled(double amount, VehicleType vehicleType) {
        if (isDiscounted(vehicleType)) {
            return Math.max(0, amount - 2.0); // RM 2.00 discount for handicapped vehicles, minimum 0
        }
        return amount;
    }

    
}
