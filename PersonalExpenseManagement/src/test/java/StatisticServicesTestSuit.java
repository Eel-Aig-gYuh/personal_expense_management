
import com.ghee.pojo.Budget;
import com.ghee.pojo.Category;
import com.ghee.pojo.Users;
import com.ghee.services.BudgetServices;
import com.ghee.services.StaticticsServices;
import com.ghee.services.UserServices;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
class StatisticServicesTestSuit {

    private StaticticsServices staticticsServices;    
    private UserServices userSebudgetrvices;
    private BudgetServices budgetServices;

    private static Users testUser;
    private static Category testExpenseCategory;
    private static Category testIncomeCategory;

    @BeforeEach
    void setUp() {
        staticticsServices = new StaticticsServices();
        budgetServices = new BudgetServices();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/spending_by_category_testcases.csv", numLinesToSkip = 1)
    @DisplayName("Test statSpendingByCategory with CSV data: {0}")
    void testStatSpendingByCategoryWithCsvData(
            String testCase,
            int userId,
            String startDate,
            String endDate,
            int expectedCategoryCount,
            double expectedAmount) throws Exception {

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
        assertEquals(expectedAmount, result.values().stream().mapToDouble(Double::doubleValue).sum(),
                "Amount should match expectation for: " + testCase);

        // Additional verification that all amounts are positive
        result.values().forEach(amount -> {
            assertTrue(amount >= 0, "Amount should be non-negative");
        });
    }
    
    @ParameterizedTest
    @CsvFileSource(resources = "/spending_by_budget_testcases.csv", numLinesToSkip = 1)
    @DisplayName("Test statSpendingByBudget with CSV data: {0}")
    void testStatSpendingByBudgetWithCsvData(
            String testCase,
            int userId,
            int categoryId,
            String startDateStr,
            String endDateStr,
            double expectedTotalAmount) throws Exception {

        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        List<Budget> budgets1 = budgetServices.getBudgetByUserIdAndDateRange(
            userId, 
            startDate, 
            endDate
        );
        
        budgets1.forEach(budget -> {
            try {
                if (budget == null) {
                    fail("Budget cannot be null");
                    return;
                }
                
                Map<LocalDate, Double> result = staticticsServices.statSpendingByBudget(budget);
                assertNotNull(result, "Result should not be null for budget: " + budget.getId());
                
                double expectedTotal = expectedTotalAmount;
                double actualTotal = result.values().stream().mapToDouble(Double::doubleValue).sum();
                
                assertEquals(expectedTotal, actualTotal, 0.01, "Total mismatch for budget: " + budget.getId());
                
            } catch (SQLException ex) {
                Logger.getLogger(StatisticServicesTestSuit.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
            
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/spending_by_date_testcases.csv", numLinesToSkip = 1)
    @DisplayName("Test statSpendingByDate with CSV data: {0}")
    void testStatSpendingByDateWithCsvData(
            String testCase,
            int userId,
            String startDateStr,
            String endDateStr,
            String expectedDatesStr,
            String expectedAmountsStr) throws Exception {

        // Setup
        Users user = new Users();
        user.setId(userId);

        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        // Execute
        Map<LocalDate, Double> result = staticticsServices.statTransactionByDay(user, startDate, endDate);

        // Verify
        assertNotNull(result, "Result should not be null for test case: " + testCase);

        String[] expectedDates = expectedDatesStr.split(";");
        String[] expectedAmounts = expectedAmountsStr.split(";");

        assertEquals(expectedDates.length, result.size(),
                "Expected number of dates to match for test case: " + testCase);

        for (int i = 0; i < expectedDates.length; i++) {
        String dateString = expectedDates[i];
        LocalDate date = LocalDate.parse(dateString);
        double expectedAmount = Double.parseDouble(expectedAmounts[i]);
        assertTrue(result.containsKey(date), "Result should contain date: " + date);
        assertEquals(expectedAmount, result.get(date), 0.01,
                "Amount mismatch for date " + date + " in test case: " + testCase);
    }
}

    @ParameterizedTest
    @CsvFileSource(resources = "/comparison_testcases.csv", numLinesToSkip = 1)
    @DisplayName("Test statComparison with CSV data: {0}")
    void testStatComparisonWithCsvData(
            String testCase,
            int userId,
            String preStart,
            String preEnd,
            String currStart,
            String currEnd,
            double expectedPreTotal,
            double expectedCurrTotal) throws Exception {

        // Setup
        Users user = new Users();
        user.setId(userId);

        LocalDate preStartDate = LocalDate.parse(preStart);
        LocalDate preEndDate = LocalDate.parse(preEnd);
        LocalDate currStartDate = LocalDate.parse(currStart);
        LocalDate currEndDate = LocalDate.parse(currEnd);

        // Execute
        Map<String, Double> result = staticticsServices.statComparison(user, currStartDate, currEndDate, preStartDate, preEndDate);

        // Verify
        assertNotNull(result, "Result should not be null for test case: " + testCase);
        assertEquals(2, result.size(), "Result should contain exactly 2 entries for 'Tháng trước' và 'Tháng này'");

        assertEquals(expectedPreTotal, result.get("Tháng trước"), 0.01,
                "Expected 'Tháng trước' to match for test case: " + testCase);
        assertEquals(expectedCurrTotal, result.get("Tháng này"), 0.01,
                "Expected 'Tháng này' to match for test case: " + testCase);
    }

}
