
public enum TransactionStatus {
    DUE,      // Issued, not yet touched
    PENDING,  // Payment in progress/locked
    DONE      // Successfully settled
}