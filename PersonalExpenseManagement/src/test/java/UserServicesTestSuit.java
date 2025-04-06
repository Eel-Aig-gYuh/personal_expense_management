import com.ghee.pojo.Users;
import com.ghee.services.UserServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
public class UserServicesTestSuit {

    private UserServices userServices;
    private Users validUser;

    @BeforeEach
    void setUp() throws SQLException {
        userServices = new UserServices();
        // Create and register a valid test user
        validUser = createValidUser();
        Map<String, Object> regResult = userServices.registerUser(validUser);
        assertTrue((boolean) regResult.get("success"), "Setup failed - could not register test user");
    }

    // Registration Tests
    @ParameterizedTest
    @CsvFileSource(resources = "/registration_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Registration Test: {0}")
    void testRegistration(String testCase, String username, String password, 
                        String firstName, String lastName, 
                        String avatar, String email, 
                        boolean expectedResult) throws SQLException {
        
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAvatar(avatar);
        user.setEmail(email);
        user.setRole("USER");
        user.setCreatedAt(new Date());

        Map<String, Object> result = userServices.registerUser(user);
        assertEquals(expectedResult, result.get("success"), 
            "Test case: " + testCase + " - Message: " + result.get("message"));
    }

    // Login Tests
    @ParameterizedTest
    @CsvFileSource(resources = "/login_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Login Test: {0}")
    void testLogin(String testCase, String username, String password, 
                 boolean expectedSuccess) throws SQLException {
        
        Users result = userServices.loginUser(username, password);
        
        if (expectedSuccess) {
            assertNotNull(result, "Login should succeed for: " + testCase);
            assertEquals(username, result.getUsername());
        } else {
            assertNull(result, "Login should fail for: " + testCase);
        }
    }

    @Test
    @DisplayName("Test password hashing")
    void testPasswordHashing() throws SQLException {
        String plainPassword = "Test@1234";
        Users user = createValidUser();
        user.setPassword(plainPassword);
        
        Map<String, Object> regResult = userServices.registerUser(user);
        assertTrue((boolean) regResult.get("success"));
        
        Users loggedInUser = userServices.loginUser(user.getUsername(), plainPassword);
        assertNotNull(loggedInUser);
        assertNotEquals(plainPassword, loggedInUser.getPassword());
        assertTrue(loggedInUser.getPassword().startsWith("$2a$"));
    }

    private Users createValidUser() {
        Users user = new Users();
        user.setUsername("validUser_" + System.currentTimeMillis());
        user.setPassword("ValidPass123!");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setAvatar("avatar.png");
        user.setEmail("test" + System.currentTimeMillis() + "@example.com");
        user.setRole("USER");
        user.setCreatedAt(new Date());
        return user;
    }
}