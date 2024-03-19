package utils;
public class Utils {
    // TODO: Add utility methods that might be useful across the application
    public static String formatCurrency(double amount) {
        return String.format("$%.2f", amount);
    }
}