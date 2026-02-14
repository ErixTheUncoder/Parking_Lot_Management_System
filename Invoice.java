import java.time.LocalDateTime;

/**
 * Invoice - Transaction & Billing Class (The "Audit" / DTO)
 * 
 * DESCRIPTION:
 * Acts as "The Staging Area" for exit billing. A Data Transfer Object (DTO)
 * that holds all financial components separately for audit-ready billing.
 * 
 * PURPOSE:
 * Created in memory at the ExitGate, this object provides complete financial
 * transparency by separating each charge component. After calculation, it's
 * written to the Payment table for auditing and reporting.
 * 
 * FINANCIAL TRANSPARENCY:
 * Separate fields for each charge component allow for:
 * - Clear audit trails
 * - Easy dispute resolution
 * - Accurate financial reporting
 * - Tax/accounting compliance
 * 
 * CHARGE COMPONENTS:
 * 1. BasePrice: Time × Rate (from PriceRegistry)
 * 2. FloorPremium: Extra charge specific to the floor (e.g., VIP floor)
 * 3. DiscountAmount: Applied to subtotal (base + premium), not to fines
 * 4. Fines: Calculated or retrieved from previous unpaid debt
 * 5. TotalAmount: Sum of all components after discounts
 * 
 * LIFECYCLE:
 * 1. Created by ExitGate during exit processing
 * 2. Components calculated and populated in sequence
 * 3. Total calculated using calculateTotal()
 * 4. Written to Payment table in database
 * 5. Retained for historical records and reporting
 * 
 * RELATIONSHIPS:
 * - Created by ExitGate
 * - Generated from Ticket data
 * - Uses PriceRegistry rates
 * - Uses Floor's extraCharge
 * - Written to database Payment table
 */
public class Invoice {
    
    /**
     * Unique invoice identifier (primary key)
     */
    private String invoiceID;
    
    /**
     * Vehicle license plate (links to ticket/vehicle)
     */
    private String licensePlate;
    
    /**
     * Base parking charge: Duration × Base Rate
     * Calculated from time parked and SpotType rate
     */
    private double basePrice;
    
    /**
     * Floor-specific premium charge
     * E.g., VIP floor extra charge, ground floor convenience fee
     */
    private double floorPremium;
    
    /**
     * Discount applied to subtotal (base + premium)
     * Note: Discounts do NOT apply to fines
     */
    private double discountAmount;
    
    /**
     * Fines and penalties
     * Includes current violations AND historical unpaid debt
     * Never discounted
     */
    private double fines;
    
    /**
     * Total amount due
     * Formula: (basePrice + floorPremium - discountAmount) + fines
     */
    private double totalAmount;
    
    /**
     * Timestamp when invoice was generated
     */
    private LocalDateTime invoiceTimestamp;
    
    /**
     * Constructor
     * 
     * TODO: Generate unique invoiceID (UUID or sequential)
     * TODO: Initialize all numeric fields to 0.0
     * TODO: Set invoiceTimestamp to current time
     */
    public Invoice() {
        // TODO: Implementation
    }
    
    /**
     * Set the base parking price
     * 
     * @param basePrice The base charge (duration × rate)
     * 
     * TODO: Validate basePrice is non-negative
     * TODO: Set the field
     * TODO: Consider recalculating total automatically
     */
    public void setBasePrice(double basePrice) {
        // TODO: Implementation
    }
    
    /**
     * Set the floor premium charge
     * 
     * @param floorPremium The floor-specific extra charge
     * 
     * TODO: Validate floorPremium is non-negative
     * TODO: Set the field
     * TODO: Consider recalculating total automatically
     */
    public void setFloorPremium(double floorPremium) {
        // TODO: Implementation
    }
    
    /**
     * Set the discount amount
     * 
     * @param discountAmount The discount to apply (positive number)
     * 
     * TODO: Validate discountAmount is non-negative
     * TODO: Ensure discount doesn't exceed (basePrice + floorPremium)
     * TODO: Set the field
     * TODO: Consider recalculating total automatically
     */
    public void setDiscountAmount(double discountAmount) {
        // TODO: Implementation
    }
    
    /**
     * Set the fines amount
     * 
     * @param fines Total fines including current and historical debt
     * 
     * TODO: Validate fines is non-negative
     * TODO: Set the field
     * TODO: Consider recalculating total automatically
     */
    public void setFines(double fines) {
        // TODO: Implementation
    }
    
    /**
     * Calculate and return the total amount due
     * 
     * @return The total amount
     * 
     * FORMULA: (basePrice + floorPremium - discountAmount) + fines
     * 
     * IMPORTANT: Discount applies to parking charges only, NOT to fines
     * 
     * TODO: Calculate subtotal (basePrice + floorPremium)
     * TODO: Apply discount to subtotal
     * TODO: Add fines (not discounted)
     * TODO: Set totalAmount field
     * TODO: Return totalAmount
     */
    public double calculateTotal() {
        // TODO: Implementation
        return 0.0;
    }
    
    /**
     * Get the invoice ID
     * 
     * @return The invoice identifier
     * 
     * TODO: Implement getter
     */
    public String getInvoiceID() {
        // TODO: Implementation
        return null;
    }
    
    /**
     * Get the total amount
     * 
     * @return The total amount due
     * 
     * TODO: Implement getter
     */
    public double getTotalAmount() {
        // TODO: Implementation
        return 0.0;
    }
}
