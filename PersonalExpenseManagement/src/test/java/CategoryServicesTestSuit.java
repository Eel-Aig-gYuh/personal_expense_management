
import com.ghee.pojo.Category;
import com.ghee.pojo.JdbcUtils;
import com.ghee.pojo.Users;
import com.ghee.services.CategoryServices;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
public class CategoryServicesTestSuit {

    private CategoryServices categoryServices;
    private Users testUser;

    @BeforeEach
    void setUp() {
        categoryServices = new CategoryServices();
        testUser = new Users();
        testUser.setId(70);
    }

    // ================= GET TESTS =================
    @ParameterizedTest
    @CsvFileSource(resources = "/get_category_by_id_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Test category existence: {0}")
    void testGetCategoryById(String testCase, int categoryId, boolean expectedExists,
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
        
        if (expectedSuccess) {
            List<Category> userCates = categoryServices.getCategoriesByUserId(userId);

            List<Category> filteredCategories = userCates.stream()
                .filter(c -> c.getName().equals(name))
                .collect(Collectors.toList());

            if (!filteredCategories.isEmpty()) {
                filteredCategories.forEach(c -> {
                    try {
                        categoryServices.deleteCategory(c.getId(), userId);
                    } catch (SQLException ex) {
                        Logger.getLogger(CategoryServicesTestSuit.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            } else {
                System.out.println("Không tìm thấy category để xóa");
            }
        }

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
    
    @ParameterizedTest
    @CsvFileSource(resources = "/delete_category_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Test delete category")
    void testDeleteCategory(
            String testCase,
            int categoryId,
            int userId,
            boolean expectedResult
    ) throws SQLException {
        if (expectedResult) {
            // Lấy list categories của user
            List<Category> userCategories = categoryServices.getCategoriesByUserId(userId);
            // Lấy phần tử đầu tiên nếu có, không thì null
            Category createdCate = userCategories.isEmpty() 
                ? null 
                : userCategories.get(0);

            // Nếu chưa có thì tạo mới và lấy lại
            if (createdCate == null) {
                Category category = new Category();
                category.setUserId(new Users(userId));
                category.setType("Chi");
                category.setName("Đi chơi");
                Map<String, Object> res = categoryServices.createCategory(category);

                if ((boolean) res.get("success")) {
                    userCategories = categoryServices.getCategoriesByUserId(userId);
                    createdCate = userCategories.isEmpty() 
                        ? null 
                        : userCategories.get(0);
                }
            }

            // Cuối cùng, nếu có createdCate thì lấy id
            if (createdCate != null) {
                categoryId = createdCate.getId();
            }
        }

        
        
        Map<String, Object> deleteResult = categoryServices.deleteCategory(categoryId, userId);
        assertTrue((boolean)deleteResult.get("success") == expectedResult);
    }
    

    @ParameterizedTest
    @CsvFileSource(resources = "/update_category_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Update category: {0}")
    void testUpdateCategory(String testCase, int categoryId, int userId, String name, String type, boolean expectedResult) throws SQLException {
        Category category = categoryServices.getCategoryById(categoryId);
        category.setName(name);
        category.setType(type);
        category.setUserId(new Users(userId));
        Map<String, Object> result = categoryServices.updateCategory(category);
        assertTrue((boolean)result.get("success") == expectedResult);
    }
    
}