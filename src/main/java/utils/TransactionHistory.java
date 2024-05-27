package utils;

/**
 * Represents a transaction history record with a type, amount, category, and timestamp.
 * This class provides methods to get these attributes and overrides the toString method for easy representation.
 *
 * @author Siyuan Lu
 */
public class TransactionHistory {
    private String type;
    private double amount;
    private String category;
    private String timestamp;

    /**
     * Constructs a new TransactionHistory with the specified type, amount, and category.
     * The timestamp is automatically set to the current date and time.
     *
     * @param type the type of the transaction (e.g., Deposit, Withdraw)
     * @param amount the amount of the transaction
     * @param category the category of the transaction (e.g., Balance, Savings)
     */
    public TransactionHistory(String type, double amount, String category) {
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    /**
     * Returns the type of the transaction.
     *
     * @return the type of the transaction
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the amount of the transaction.
     *
     * @return the amount of the transaction
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns the category of the transaction.
     *
     * @return the category of the transaction
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns the timestamp of the transaction.
     *
     * @return the timestamp of the transaction
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Returns a string representation of the TransactionHistory.
     *
     * @return a string representation of the TransactionHistory
     */
    @Override
    public String toString() {
        return "TransactionHistory{" +
                "type='" + type + '\'' +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
