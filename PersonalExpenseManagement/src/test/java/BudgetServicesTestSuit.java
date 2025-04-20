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
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@RunWith(JUnitPlatform.class)
@DisplayName("Budget Services Unit Tests")
class BudgetServicesTestSuit {

    private BudgetServices budgetServices;
    private static Budget validBudget;

    @BeforeAll
    static void setUpClass() {
        validBudget = new Budget();
        validBudget.setUserId(new Users(1));
        validBudget.setCategoryId(new Category(1));
        validBudget.setTarget(1000.00);
        validBudget.setStartDate(java.sql.Date.valueOf(LocalDate.now()));
        validBudget.setEndDate(java.sql.Date.valueOf(LocalDate.now().plusMonths(1)));
        validBudget.setCreatedAt(new Date());
    }

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
            @SuppressWarnings("unused") String ignoredMessage) throws SQLException {

        Budget budget = new Budget();
        budget.setUserId(new Users(userId));
        budget.setCategoryId(new Category(categoryId));
        budget.setTarget(target);
        budget.setStartDate(java.sql.Date.valueOf(LocalDate.parse(startDate)));
        budget.setEndDate(java.sql.Date.valueOf(LocalDate.parse(endDate)));
        budget.setCreatedAt(new Date());

        Map<String, Object> result = budgetServices.createBudget(budget);

        assertEquals(expectedSuccess, result.get("success"),
                () -> "Test case '" + testCase + "' failed. Success expected: " + expectedSuccess);
    }

    @Test
    @DisplayName("Update Budget - Success Case")
    @Tag("update")
    void testUpdateBudget_Success() throws SQLException {
        Map<String, Object> createResult = budgetServices.createBudget(validBudget);
        assumeTrue((boolean) createResult.get("success"));

        // Get the created budget ID
        List<Budget> budgets = budgetServices.getBudgetByUserIdAndDateRange(
                validBudget.getUserId().getId(),
                LocalDate.now(),
                LocalDate.now().plusMonths(1)
        );
        assumeTrue(!budgets.isEmpty());
        
        Budget createdBudget = budgets.get(0);
        createdBudget.setTarget(1500.00);
        
        Map<String, Object> updateResult = budgetServices.updateBudget(createdBudget);

        assertTrue((boolean) updateResult.get("success"));
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
        double totalBudget = budgetServices.getTotalBudget(userId, start, end);

        assertAll(testCase,
                () -> assertEquals(expectedCount, budgets.size()),
                () -> assertEquals(expectedTotalBudget, totalBudget, 0.001)
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
    @DisplayName("Create Budget - Invalid User")
    @Tag("exception")
    void testCreateBudget_InvalidUser() throws SQLException {
        Budget invalidBudget = new Budget();
        invalidBudget.setUserId(new Users(-1));
        invalidBudget.setCategoryId(new Category(1));
        invalidBudget.setTarget(1000.00);
        invalidBudget.setStartDate(java.sql.Date.valueOf(LocalDate.now()));
        invalidBudget.setEndDate(java.sql.Date.valueOf(LocalDate.now().plusMonths(1)));
        invalidBudget.setCreatedAt(new Date());

        Map<String, Object> result = budgetServices.createBudget(invalidBudget);
        assertFalse((boolean) result.get("success"));
    }

    @Test
    @DisplayName("Update Non-Existent Budget")
    @Tag("update")
    void testUpdateNonExistentBudget() throws SQLException {
        Budget nonExistentBudget = new Budget();
        nonExistentBudget.setId(-1);
        nonExistentBudget.setUserId(new Users(1));
        nonExistentBudget.setCategoryId(new Category(1));
        nonExistentBudget.setTarget(1000.00);
        nonExistentBudget.setStartDate(java.sql.Date.valueOf(LocalDate.now()));
        nonExistentBudget.setEndDate(java.sql.Date.valueOf(LocalDate.now().plusMonths(1)));
        nonExistentBudget.setCreatedAt(new Date());

        Map<String, Object> result = budgetServices.updateBudget(nonExistentBudget);

        assertFalse((boolean) result.get("success"));
    }

    @Test
    @DisplayName("Get Total Budget - No Budgets")
    @Tag("query")
    void testGetTotalBudget_NoBudgets() throws SQLException {
        double totalBudget = budgetServices.getTotalBudget(
                1, 
                LocalDate.now().minusYears(1), 
                LocalDate.now().minusYears(1).plusDays(30)
        );
        assertEquals(0.0, totalBudget, 0.001);
    }

    @Test
    @DisplayName("Get Budgets By Date Range - Edge Cases")
    @Tag("query")
    void testGetBudgetsByDateRange_EdgeCases() throws SQLException {
        // Test with date range that shouldn't match any budgets
        List<Budget> budgets = budgetServices.getBudgetByUserIdAndDateRange(
                1,
                LocalDate.now().minusYears(1),
                LocalDate.now().minusYears(1).plusDays(1)
        );
        assertTrue(budgets.isEmpty());
    }
}