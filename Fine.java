
import java.time.LocalDateTime;

public class Fine {
    private int fineId;
    private String plate;
    private double amount;
    private String reasons; // merged string 
    private int schemeId;
    private TransactionStatus paymentStatus;
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;

    public Fine() {
    }

    public Fine(int fineId, String plate, double amount, String reasons, 
                int schemeId, TransactionStatus paymentStatus, 
                LocalDateTime createdAt, LocalDateTime paidAt) {
        this.fineId = fineId;
        this.plate = plate;
        this.amount = amount;
        this.reasons = reasons;
        this.schemeId = schemeId;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
        this.paidAt = paidAt;
    }

    // Getters and Setters
    public int getFineId() {
        return fineId;
    }

    //=======================================
    public void setFineId(int fineId) {
        this.fineId = fineId;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public int getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(int schemeId) {
        this.schemeId = schemeId;
    }

    public TransactionStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(TransactionStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }
}