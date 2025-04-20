
import com.ghee.pojo.Transaction;
import com.ghee.pojo.Users;
import com.ghee.pojo.Category;
import com.ghee.pojo.Wallet;
import com.ghee.services.TransactionServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
public class TransactionServiceTestSuit {

    private TransactionServices transactionServices;
    private Users testUser;
    private Category testCategory;
    private Wallet testWallet;

    @BeforeEach
    void setUp() throws SQLException {
        transactionServices = new TransactionServices();

        // Create a test user (mock or real)
        testUser = new Users();
        testUser.setId(1); // Assuming this is a valid test user ID

        // Create a test category (mock or real)
        testCategory = new Category();
        testCategory.setId(1); // Assuming this is a valid test category ID

        // Create a test wallet (mock or real)
        testWallet = new Wallet();
        testWallet.setId(1); // Assuming this is a valid test wallet ID
    }

    // Test for adding transactions
    @ParameterizedTest
    @CsvFileSource(resources = "/add_transaction_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Add Transaction Test: {0}")
    void testAddTransaction(String testCase,
            int userId,
            int categoryId,
            int walletId,
            double amount,
            String transactionDate,
            String description,
            boolean expectedResult) throws SQLException {

        Transaction transaction = new Transaction();
        transaction.setUserId(new Users(userId));
        transaction.setCategoryId(new Category(categoryId));
        transaction.setWalletId(new Wallet(walletId));
        transaction.setAmount(amount);
        transaction.setTransactionDate(java.sql.Date.valueOf(transactionDate));
        transaction.setDescription(description);
        transaction.setCreatedAt(new Date());

        Map<String, Object> result = transactionServices.addTransaction(transaction);
        assertEquals(expectedResult, result.get("success"),
                "Test case: " + testCase + " - Message: " + result.get("message"));
    }

    // Test for updating transactions
    @ParameterizedTest
    @CsvFileSource(resources = "/update_transaction_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Update Transaction Test: {0}")
    void testUpdateTransaction(String testCase,
            int transactionId,
            int userId,
            int categoryId,
            int walletId,
            double amount,
            String transactionDate,
            String description,
            boolean expectedResult) throws SQLException {

        Transaction transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setUserId(new Users(userId));
        transaction.setCategoryId(new Category(categoryId));
        transaction.setWalletId(new Wallet(walletId));
        transaction.setAmount(amount);
        transaction.setTransactionDate(java.sql.Date.valueOf(transactionDate));
        transaction.setDescription(description);

        Map<String, Object> result = transactionServices.updateTransaction(transaction);
        assertEquals(expectedResult, result.get("success"),
                "Test case: " + testCase + " - Message: " + result.get("message"));
    }

    // Test for deleting transactions
    @Test
    @DisplayName("Test delete transaction")
    void testDeleteTransaction() throws SQLException {
        // First add a transaction to delete
        Transaction transaction = createValidTransaction();
        Map<String, Object> addResult = transactionServices.addTransaction(transaction);
        assertTrue((boolean) addResult.get("success"));

        // Now delete it
        boolean deleteResult = transactionServices.deleteTransaction(transaction.getId());
        assertTrue(deleteResult);
    }

    // Test for getting transactions by date range
    @ParameterizedTest
    @CsvFileSource(resources = "/transaction_date_range_test_cases.csv", numLinesToSkip = 1)
    void testGetTransactionsByDateRange(String testCase,
            String startDateStr,
            String endDateStr,
            int expectedCount,
            String description) throws SQLException {

        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        List<Transaction> transactions = transactionServices.getTransactionByUserIdAdnDateRange(
                2, // user_id = 2 từ bảng dữ liệu
                startDate,
                endDate);

        assertEquals(expectedCount, transactions.size(),
                "Failed test case: " + testCase + " - " + description);

        // Có thể thêm kiểm tra tổng số tiền nếu cần
        if (expectedCount > 0) {
            double totalAmount = transactions.stream()
                    .mapToDouble(Transaction::getAmount)
                    .sum();
            System.out.println("Total amount for " + testCase + ": " + totalAmount);
        }
    }

    // Test for opening balance calculation
    @Test
    @DisplayName("Test opening balance calculation")
    void testOpeningBalance() throws SQLException {
        LocalDate testDate = LocalDate.now();
        double balance = transactionServices.getOpeningBalance(testUser.getId(), testDate);

        // Just verify the method runs without error
        // Actual balance depends on test data
        assertTrue(balance >= 0);
    }

    // Test for closing balance calculation
    @Test
    @DisplayName("Test closing balance calculation")
    void testClosingBalance() throws SQLException {
        LocalDate testDate = LocalDate.now();
        double balance = transactionServices.getClosingBalance(testUser.getId(), testDate);

        // Just verify the method runs without error
        // Actual balance depends on test data
        assertTrue(balance >= 0);
    }

    private Transaction createValidTransaction() {
        Transaction transaction = new Transaction();
        transaction.setUserId(testUser);
        transaction.setCategoryId(testCategory);
        transaction.setWalletId(testWallet);
        transaction.setAmount(100.0);
        transaction.setTransactionDate(new Date());
        transaction.setDescription("Test transaction");
        transaction.setCreatedAt(new Date());
        return transaction;
    }
}
