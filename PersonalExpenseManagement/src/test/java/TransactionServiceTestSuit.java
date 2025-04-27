
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
            int userId,
            int categoryId,
            double amount,
            String transactionDate,
            String description,
            boolean expectedResult,
            double expectedAmount,
            String startDate,
            String endDate) throws SQLException {

        Transaction transToUpdate = transactionServices.getTransactionByUserIdAdnDateRange(userId, LocalDate.parse(startDate), LocalDate.parse(endDate)).get(0);
        
        Transaction cloneTrans = new Transaction();
        cloneTrans.setId(transToUpdate.getId());
        cloneTrans.setUserId(transToUpdate.getUserId());
        cloneTrans.setCategoryId(transToUpdate.getCategoryId());
        cloneTrans.setWalletId(new Wallet(userId));
        cloneTrans.setAmount(transToUpdate.getAmount());
        cloneTrans.setTransactionDate(transToUpdate.getTransactionDate());
        cloneTrans.setDescription(transToUpdate.getDescription());
        cloneTrans.setCreatedAt(new Date());
        
        transToUpdate.setUserId(new Users(userId));
        transToUpdate.setCategoryId(new Category(categoryId));
        transToUpdate.setWalletId(new Wallet(userId));
        transToUpdate.setAmount(amount);
        transToUpdate.setTransactionDate(java.sql.Date.valueOf(transactionDate));
        transToUpdate.setDescription(description);
        transToUpdate.setCreatedAt(new Date());

        Map<String, Object> result = transactionServices.updateTransaction(transToUpdate);
        assertEquals(expectedResult, result.get("success"),
                "Test case: " + testCase + " - Message: " + result.get("message"));
        assertEquals(expectedAmount, budgetServices.getBudgetByUserIdAndDateRange(userId, LocalDate.parse(startDate), LocalDate.parse(endDate)).get(0).getAmount());
//        if((boolean)result.get("success")){
            transactionServices.updateTransaction(cloneTrans);
//        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/delete_transaction_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Test delete transaction")
    void testDeleteTransaction(
            String testCase,
            int userId,
            int transactionId,
            boolean expectedResult,
            double expectedAmount,
            String startDate,
            String endDate
    ) throws SQLException {
        Transaction createdTrans;
        Transaction InvalidTransaction = null;
        if (expectedResult) {
            List<Transaction> userTrans = transactionServices.getTransactionByUserIdAdnDateRange(userId, LocalDate.parse(startDate), LocalDate.parse(endDate));
            createdTrans = userTrans.isEmpty() 
                ? null 
                : userTrans.get(0);

            if (createdTrans == null) {
                Transaction transaction = new Transaction();
                transaction.setUserId(new Users(userId));
                transaction.setCategoryId(new Category(39));
                transaction.setWalletId(new Wallet(userId));
                transaction.setAmount(100000);
                transaction.setTransactionDate(java.sql.Date.valueOf("2025-04-15"));
                transaction.setDescription("");
                transaction.setCreatedAt(new Date());
                Map<String, Object> res = transactionServices.addTransaction(transaction);

                if ((boolean) res.get("success")) {
                    userTrans = transactionServices.getTransactionByUserIdAdnDateRange(userId, LocalDate.parse(startDate), LocalDate.parse(endDate));
                    createdTrans = userTrans.isEmpty() 
                        ? null 
                        : userTrans.get(0);
                }
            }
        }
        else
        {
            InvalidTransaction = new Transaction();
            InvalidTransaction.setId(transactionId);
            InvalidTransaction.setUserId(new Users(userId));
            InvalidTransaction.setCategoryId(new Category(39));
            InvalidTransaction.setWalletId(new Wallet(userId));
            InvalidTransaction.setAmount(100000);
            InvalidTransaction.setTransactionDate(java.sql.Date.valueOf("2025-04-15"));
            InvalidTransaction.setDescription("");
            InvalidTransaction.setCreatedAt(new Date());
                
            createdTrans = InvalidTransaction;
                
        }

        
        
        boolean deleteResult = transactionServices.deleteTransaction( expectedResult ? createdTrans : InvalidTransaction);
        assertTrue(deleteResult == expectedResult);
        assertEquals(expectedAmount, budgetServices.getBudgetByUserIdAndDateRange(userId, LocalDate.parse(startDate), LocalDate.parse(endDate)).get(0).getAmount());
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
    @ParameterizedTest
    @CsvFileSource(resources = "/opening_balance_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Test opening balance calculation")
    void testOpeningBalance(
            String testCase,
            int userId,
            String startDate,
            double expectedAmount
    ) throws SQLException {
        LocalDate testDate = LocalDate.parse(startDate);
        double balance = transactionServices.getOpeningBalance(userId, testDate);

        assertEquals(expectedAmount, balance);
    }

    // Test for closing balance calculation
    @ParameterizedTest
    @CsvFileSource(resources = "/closing_balance_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Test closing balance calculation")
    void testClosingBalance(
            String testCase,
            int userId,
            String endDate,
            double expectedAmount
    ) throws SQLException {
        LocalDate testDate = LocalDate.parse(endDate);
        double balance = transactionServices.getOpeningBalance(userId, testDate);

        assertEquals(expectedAmount, balance);
    }
}
