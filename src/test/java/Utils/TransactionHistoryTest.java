package Utils;

import org.junit.jupiter.api.Test;
import utils.TransactionHistory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransactionHistoryTest {

    @Test
    public void testTransactionHistoryCreation() {
        TransactionHistory transactionHistory = new TransactionHistory("Deposit", 100, "Balance");
        assertNotNull(transactionHistory);
        assertEquals("Deposit", transactionHistory.getType());
        assertEquals(100, transactionHistory.getAmount());
        assertEquals("Balance", transactionHistory.getCategory());
        assertNotNull(transactionHistory.getTimestamp());
    }

    @Test
    public void testTransactionHistoryToString() {
        TransactionHistory transactionHistory = new TransactionHistory("Withdraw", 50, "Expense");
        String expectedString = "TransactionHistory{" +
                "type='Withdraw', " +
                "amount=50.0, " +
                "category='Expense', " +
                "timestamp='" + transactionHistory.getTimestamp() + "'" +
                '}';
        assertEquals(expectedString, transactionHistory.toString());
    }

    @Test
    public void testTransactionHistoryBoundaryAmount() {
        // Test for zero amount
        TransactionHistory transactionHistory1 = new TransactionHistory("Deposit", 0, "Balance");
        assertEquals(0, transactionHistory1.getAmount());

        // Test for negative amount
        TransactionHistory transactionHistory2 = new TransactionHistory("Withdraw", -50, "Expense");
        assertEquals(-50, transactionHistory2.getAmount());
    }

    @Test
    public void testTransactionHistoryBoundaryCategory() {
        // Test for empty category
        TransactionHistory transactionHistory = new TransactionHistory("Withdraw", 50, "");
        assertEquals("", transactionHistory.getCategory());
    }

    @Test
    public void testTransactionHistoryBoundaryType() {
        // Test for null type
        TransactionHistory transactionHistory = new TransactionHistory(null, 50, "Expense");
        assertEquals(null, transactionHistory.getType());
    }
}
