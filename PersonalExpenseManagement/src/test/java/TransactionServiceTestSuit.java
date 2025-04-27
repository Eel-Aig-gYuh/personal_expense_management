
import com.ghee.pojo.Transaction;
import com.ghee.pojo.Users;
import com.ghee.pojo.Category;
import com.ghee.pojo.Wallet;
import com.ghee.services.BudgetServices;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
public class TransactionServiceTestSuit {

    private TransactionServices transactionServices;
    private BudgetServices budgetServices;
    private Users testUser;

    @BeforeEach
    void setUp() throws SQLException {
        transactionServices = new TransactionServices();
        budgetServices = new BudgetServices();

        // Create a test user (mock or real)
        testUser = new Users();
        testUser.setId(1); // Assuming this is a valid test user ID

    }

    // Test for adding transactions
    @ParameterizedTest
    @CsvFileSource(resources = "/add_transaction_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Add Transaction Test: {0}")
    void testAddTransaction(String testCase,
            int userId,
            int categoryId,
            double amount,
            String transactionDate,
            String description,
            boolean expectedResult,
            double expectedAmount,
            String startDate,
            String endDate) throws SQLException {
        
        List<Transaction> userTrans = transactionServices.getTransactionByUserIdAdnDateRange(userId, LocalDate.parse(startDate), LocalDate.parse(endDate));
        userTrans.stream().forEach(t -> {
            try {
                transactionServices.deleteTransaction(t);
            } catch (SQLException ex) {
                Logger.getLogger(TransactionServiceTestSuit.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        Transaction transaction = new Transaction();
        transaction.setUserId(new Users(userId));
        transaction.setCategoryId(new Category(categoryId));
        transaction.setWalletId(new Wallet(userId));
        transaction.setAmount(amount);
        transaction.setTransactionDate(java.sql.Date.valueOf(transactionDate));
        transaction.setDescription(description);
        transaction.setCreatedAt(new Date());

        Map<String, Object> result = transactionServices.addTransaction(transaction);
        assertEquals(expectedResult, result.get("success"),
                "Test case: " + testCase + " - Message: " + result.get("message"));
        
        assertEquals(expectedAmount, budgetServices.getBudgetByUserIdAndDateRange(userId, LocalDate.parse(startDate), LocalDate.parse(endDate)).get(0).getAmount());
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

    @ParameterizedTest
    @CsvFileSource(resources = "/delete_transaction_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Test delete transaction")
    void testDeleteTransaction() throws SQLException {
        
    }

    // Test for getting transactions by date range
    @ParameterizedTest
    @CsvFileSource(resources = "/transaction_date_range_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("get transaction by date : {0}")
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
}
