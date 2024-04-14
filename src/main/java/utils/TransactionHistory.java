package utils;

public class TransactionHistory {
    private String type;
    private double amount;
    private String category;
    private String timestamp;

    public TransactionHistory(String type, double amount, String category) {
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    // 生成 getter 和 setter 方法

    @Override
    public String toString() {
        return "TransactionHistory{" +
                "type='" + type + '\'' +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getTimestamp() {
        return timestamp;
    }

}