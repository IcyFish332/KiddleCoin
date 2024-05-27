package utils;
/**
 * A utility class that provides common utility methods used across the application.
 *
 * @author Siyuan Lu
 */
public class Utils {
    /**
     * Formats a given amount as a currency string.
     *
     * @param amount the amount to format
     * @return the formatted currency string
     */
    public static String formatCurrency(double amount) {
        return String.format("$%.2f", amount);
    }

    // TODO: Add utility methods that might be useful across the application
}