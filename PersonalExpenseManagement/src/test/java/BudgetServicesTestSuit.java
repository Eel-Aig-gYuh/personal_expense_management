import com.ghee.config.AppConfigs;
import com.ghee.pojo.Budget;
import com.ghee.pojo.Category;
import com.ghee.pojo.Users;
import com.ghee.services.BudgetServices;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
@DisplayName("Budget Services Unit Tests")
class BudgetServicesTestSuit {

    private BudgetServices budgetServices;

    @BeforeEach
    void setUp() {
        budgetServices = new BudgetServices();
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(resources = "/budget_creation_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Create Budget - Parameterized Tests")
    @Tag("creation")
    void testCreateBudget(
            String testCase,
            int userId,
            int categoryId,
            double target,
            String startDate,
            String endDate,
            boolean expectedSuccess,
            @SuppressWarnings("unused") String ignoredMessage) throws SQLException, ParseException {
        
        if(expectedSuccess){
            List<Budget> budgets = budgetServices.getBudgetByUserIdAndDateRange(userId, LocalDate.parse(startDate), LocalDate.parse(endDate));
            budgets.forEach(budget -> {
                try {
                    budgetServices.deleteBudget(budget.getId());
                } catch (SQLException ex) {
                    Logger.getLogger(BudgetServicesTestSuit.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        Budget budget = new Budget();
        budget.setUserId(new Users(userId));
        budget.setCategoryId(new Category(categoryId));
        budget.setTarget(target);
        budget.setStartDate(java.sql.Date.valueOf(startDate));
        budget.setEndDate(java.sql.Date.valueOf(startDate));
        budget.setCreatedAt(new Date());

        Map<String, Object> result = budgetServices.createBudget(budget);

        assertEquals(expectedSuccess, result.get("success"),
                () -> "Test case '" + testCase + "' failed. Success expected: " + expectedSuccess);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(resources = "/update_budget_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Update Budget - Success Case")
    @Tag("update")
    void testUpdateBudget_Success(
            String testCase,
            int userId,
            int budgetId,
            int categoryId,
            String startDate,
            String endDate,
            double target,
            boolean expectedResult
    ) throws SQLException, ParseException {
        Budget budget = new Budget();
        budget.setId(budgetId);
        budget.setUserId(new Users(userId));
        budget.setCategoryId(new Category(categoryId));
        budget.setTarget(target);
        budget.setStartDate(java.sql.Date.valueOf(startDate));
        budget.setEndDate(java.sql.Date.valueOf(startDate));
        budget.setCreatedAt(new Date());
        
        Map<String, Object> res = budgetServices.updateBudget(budget);
        assertEquals(expectedResult, (boolean)res.get("success"));
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(resources = "/budget_query_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Query Budgets - Parameterized Tests")
    @Tag("query")
    void testGetBudgetsByDateRange(
            String testCase,
            int userId,
            String startDate,
            String endDate,
            int expectedCount,
            double expectedTotalBudget) throws SQLException {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        List<Budget> budgets = budgetServices.getBudgetByUserIdAndDateRange(userId, start, end);
        
        double actualTotal = budgets.stream()
                            .mapToDouble(Budget::getAmount)
                            .sum();

        assertAll(testCase,
            () -> assertEquals(expectedCount, budgets.size()),
            () -> assertEquals(expectedTotalBudget, actualTotal, 0.001)
        );
    }

    @Test
    @DisplayName("Get Total Spent - Basic Validation")
    @Tag("query")
    void testGetTotalSpent_Basic() throws SQLException {
        LocalDate end = LocalDate.now();
        LocalDate start = LocalDate.now().minusMonths(1);

        double totalSpent = budgetServices.getTotalSpent(1, start, end);

        assertTrue(totalSpent >= 0, "Total spent should not be negative");
    }
    
    @Test
    @DisplayName("Delete budget")
    void testDeleteBudget() throws SQLException{
        Budget budget = new Budget();
        budget.setUserId(new Users(40));
        budget.setCategoryId(new Category(2));
        budget.setTarget(500000);
        budget.setStartDate(java.sql.Date.valueOf("2025-06-01"));
        budget.setEndDate(java.sql.Date.valueOf("2025-06-20"));
        budget.setCreatedAt(new Date());

        Map<String, Object> result = budgetServices.createBudget(budget);
        List<Budget> budgets = budgetServices.getBudgetByUserIdAndDateRange(40, LocalDate.parse("2025-06-01"), LocalDate.parse("2025-06-20"));
        
        budgets.forEach(b -> {
            try {
                boolean actual = budgetServices.deleteBudget(b.getId());
                assertTrue(actual, "Test delete ");
            } catch (SQLException ex) {
                Logger.getLogger(BudgetServicesTestSuit.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}