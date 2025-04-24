
import com.ghee.pojo.Budget;
import com.ghee.pojo.Category;
import com.ghee.pojo.Users;
import com.ghee.services.StaticticsServices;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StatisticServicesTestSuit {
    private StaticticsServices staticticsServices;
    private static Users testUser;
    private static Category testExpenseCategory;
    private static Category testIncomeCategory;

    @BeforeAll
    static void setUpAll() {
        // Setup test user (could be loaded from CSV or test database)
        testUser = new Users();
        testUser.setId(1);
        testUser.setUsername("testuser");
        
        // Setup test categories (should match your test database)
        testExpenseCategory = new Category();
        testExpenseCategory.setId(1);
        testExpenseCategory.setName("Food");
        testExpenseCategory.setType("Chi");
        
        testIncomeCategory = new Category();
        testIncomeCategory.setId(2);
        testIncomeCategory.setName("Salary");
        testIncomeCategory.setType("Thu");
    }

    @BeforeEach
    void setUp() {
        staticticsServices = new StaticticsServices();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/spending_by_category_testcases.csv", numLinesToSkip = 1)
    @DisplayName("Test statSpendingByCategory with CSV data: {0}")
    void testStatSpendingByCategoryWithCsvData(
            String testCase, 
            int userId, 
            String startDate, 
            String endDate, 
            int expectedCategoryCount) throws Exception {
        
        // Setup
        Users user = new Users();
        user.setId(userId);
        
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        
        // Execute
        Map<String, Double> result = staticticsServices.statSpendingByCategory(user, start, end);
        
        // Verify
        assertNotNull(result, "Result should not be null");
        assertEquals(expectedCategoryCount, result.size(), 
            "Number of categories should match expectation for: " + testCase);
        
        // Additional verification that all amounts are positive
        result.values().forEach(amount -> {
            assertTrue(amount >= 0, "Amount should be non-negative");
        });
    }

//    @Test
//    @DisplayName("Test statSpendingByCategory with invalid date range")
//    void testStatSpendingByCategoryInvalidDateRange() {
//        Users user = new Users();
//        user.setId(1);
//        
//        LocalDate start = LocalDate.of(2023, 1, 1);
//        LocalDate end = LocalDate.of(2022, 12, 31); // End before start
//        
//        assertThrows(IllegalArgumentException.class, () -> {
//            staticticsServices.statSpendingByCategory(user, start, end);
//        });
//    }

    @ParameterizedTest
    @CsvFileSource(resources = "/spending_by_budget_testcases.csv", numLinesToSkip = 1)
    @DisplayName("Test statSpendingByBudget with CSV data: {0}")
    void testStatSpendingByBudgetWithCsvData(
            String testCase, 
            int userId, 
            int categoryId, 
            String startDate, 
            String endDate, 
            int expectedDayCount) throws Exception {
        
        // Setup
        Budget budget = new Budget();
        budget.setUserId(new Users(userId));
        
        Category category = new Category();
        category.setId(categoryId);
        category.setType(categoryId == 1 ? "Chi" : "Thu"); // Assuming ID 1 is expense
        budget.setCategoryId(category);
        
        budget.setStartDate(Date.valueOf(LocalDate.parse(startDate)));
        budget.setEndDate(Date.valueOf(LocalDate.parse(endDate)));
        
        // Execute
        Map<LocalDate, Double> result = staticticsServices.statSpendingByBudget(budget);
        
        // Verify
        assertNotNull(result, "Result should not be null");
        assertEquals(expectedDayCount, result.size(), 
            "Number of days with transactions should match expectation for: " + testCase);
        
        // Verify all dates are within budget range
        LocalDate budgetStart = new java.util.Date(budget.getStartDate().getTime()).toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate();

        LocalDate budgetEnd = new java.util.Date(budget.getEndDate().getTime()).toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate();
        result.keySet().forEach(date -> {
            assertFalse(date.isBefore(budgetStart), "Date should not be before budget start");
            assertFalse(date.isAfter(budgetEnd), "Date should not be after budget end");
        });
    }

    @Test
    @DisplayName("Test statSpendingByBudget with income category (should return empty)")
    void testStatSpendingByBudgetWithIncomeCategory() throws Exception {
        Budget budget = new Budget();
        budget.setUserId(testUser);
        budget.setCategoryId(testIncomeCategory);
        budget.setStartDate(Date.valueOf(LocalDate.now().minusMonths(1)));
        budget.setEndDate(Date.valueOf(LocalDate.now()));
        
        Map<LocalDate, Double> result = staticticsServices.statSpendingByBudget(budget);
        
        assertNotNull(result);
        assertTrue(result.isEmpty(), "Should return empty map for income category");
    }
}