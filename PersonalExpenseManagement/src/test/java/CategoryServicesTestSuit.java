
import com.ghee.pojo.Category;
import com.ghee.pojo.JdbcUtils;
import com.ghee.pojo.Users;
import com.ghee.services.CategoryServices;
import java.sql.Connection;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
public class CategoryServicesTestSuit {

    private CategoryServices categoryServices;
    private Users testUser;

//    @BeforeAll
//    static void initTestData() throws SQLException {
//        try (Connection conn = JdbcUtils.getConn()) {
//            // Sửa câu lệnh SQL đúng chuẩn
//            Statement stmt = conn.createStatement();
//
//            // Tạo bảng nếu chưa tồn tại
//            stmt.execute("CREATE TABLE IF NOT EXISTS category ("
//                    + "id INT PRIMARY KEY AUTO_INCREMENT,"
//                    + "type VARCHAR(20) NOT NULL,"
//                    + "name VARCHAR(100) NOT NULL,"
//                    + "user_id INT NOT NULL)");
//
//            // Thêm dữ liệu test
//            stmt.execute("INSERT INTO category (type, name, user_id) VALUES "
//                    + "('EXPENSE', 'Test Category 1', 1), "
//                    + "('INCOME', 'Test Category 2', 1)");
//        }
//    }

    @BeforeEach
    void setUp() {
        categoryServices = new CategoryServices();
        testUser = new Users();
        testUser.setId(1); // Giả định user có ID = 1 tồn tại trong DB
    }

    // ================= GET TESTS =================
    @ParameterizedTest
    @CsvFileSource(resources = "/category_existence_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Test category existence: {0}")
    void testCategoryExistence(String testCase, int categoryId, boolean expectedExists,
            String expectedName, String expectedType) throws SQLException {
        Category result = categoryServices.getCategoryById(categoryId);

        if (expectedExists) {
            assertNotNull(result, "Category should exist for test case: " + testCase);
            assertEquals(expectedName, result.getName());
            assertEquals(expectedType, result.getType());
        } else {
            assertNull(result, "Category should not exist for test case: " + testCase);
        }
    }

    // Xử lý trường hợp ID không hợp lệ
    @ParameterizedTest
    @CsvFileSource(resources = "/category_existence_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Test invalid category IDs: {0}")
    void testInvalidCategoryIds(String testCase, String categoryIdInput,
            boolean expectedExists, String expectedName,
            String expectedType) {
        try {
            // Chuyển đổi từ String sang int (xử lý trường hợp không phải số)
            int categoryId = Integer.parseInt(categoryIdInput);
            Category result = categoryServices.getCategoryById(categoryId);

            if (expectedExists) {
                assertNotNull(result);
            } else {
                assertNull(result);
            }
        } catch (NumberFormatException e) {
            // Kiểm tra xem test case có mong đợi lỗi không
            if (!testCase.contains("invalid")) {
                fail("Unexpected NumberFormatException for test case: " + testCase);
            }
            // Nếu test case mong đợi lỗi thì coi như pass
        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/category_user_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Get categories by user ID: {0}")
    void testGetCategoriesByUserId(String testCase, int userId, int expectedCount) throws SQLException {
        List<Category> result = categoryServices.getCategoriesByUserId(userId);

        if (expectedCount > 0) {
            assertNotNull(result);
            assertEquals(expectedCount, result.size(),
                    "Test case: " + testCase + " - Should return correct number of categories");
        } else {
            assertTrue(result.isEmpty(),
                    "Test case: " + testCase + " - Should return empty list for user with no categories");
        }
    }

    // ================= POST TESTS =================
    @ParameterizedTest
    @CsvFileSource(resources = "/category_creation_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Create category: {0}")
    void testCreateCategory(String testCase, int userId, String type,
            String name, boolean expectedSuccess) throws SQLException {

        Category category = new Category();
        Users user = new Users();
        user.setId(userId);
        category.setUserId(user);
        category.setType(type);
        category.setName(name);

        Map<String, Object> result = categoryServices.createCategory(category);

        assertEquals(expectedSuccess, result.get("success"),
                "Test case: " + testCase + " - Message: " + result.get("message"));

        if (expectedSuccess) {
            // Verify the category was actually created
            List<Category> userCategories = categoryServices.getCategoriesByUserId(userId);
            assertTrue(userCategories.stream().anyMatch(c
                    -> c.getName().equals(name) && c.getType().equals(type)),
                    "Created category should appear in user's category list");
        }
    }

    @Test
    @DisplayName("Create multiple categories with same name for same user - ALLOWED by business rule")
    void testCreateDuplicateCategoryAllowed() throws SQLException {
        // First creation - should succeed
        Category category1 = createTestCategory();
        Map<String, Object> firstResult = categoryServices.createCategory(category1);
        assertTrue((boolean) firstResult.get("success"), "First creation should succeed");

        // Second creation with same name - should also succeed (business rule change)
        Category category2 = createTestCategory(); // Same name
        Map<String, Object> secondResult = categoryServices.createCategory(category2);

        assertTrue((boolean) secondResult.get("success"),
                "Second creation with same name should succeed according to business rule");
        assertFalse(((String) secondResult.get("message")).contains("already exists"),
                "Message should not indicate duplicate error");

        // Verify both exist
        List<Category> userCategories = categoryServices.getCategoriesByUserId(testUser.getId());
        long count = userCategories.stream()
                .filter(c -> c.getName().equals(category1.getName()))
                .count();
        assertTrue(count >= 2, "Should find at least 2 categories with same name");
    }

    // Helper method
    private Category createTestCategory() {
        Category category = new Category();
        category.setUserId(testUser);
        category.setType("EXPENSE");
        category.setName("Test Category " + System.currentTimeMillis());
        return category;
    }
}
