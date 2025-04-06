
import com.ghee.personalexpensemanagement.Utils;
import com.ghee.pojo.JdbcUtils;
import com.ghee.pojo.Wallet;
import com.ghee.pojo.Users;
import com.ghee.services.UserServices;
import com.ghee.services.WalletServices;
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
public class WalletServicesTestSuit {

    private WalletServices walletServices;
    private UserServices userServices;
    private Users testUser;
    private Wallet testWallet;

    @BeforeEach
    void setUp() throws SQLException {
        walletServices = new WalletServices();
        userServices = new UserServices();
        
        // Create and register a test user
        testUser = createTestUser();
        Map<String, Object> regResult = userServices.registerUser(testUser);
        assertTrue((boolean) regResult.get("success"), "Setup failed - could not register test user");
        
        // Login the user to set current user in Utils
        Users loggedInUser = userServices.loginUser(testUser.getUsername(), "TestPass123!");
        assertNotNull(loggedInUser);
        Utils.setCurrentUser(loggedInUser);
        
        // Get the wallet for testing
        testWallet = walletServices.getWalletById(loggedInUser.getId());
        assertNotNull(testWallet, "Setup failed - could not get wallet for test user");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/wallet_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Get Wallet Test: {0}")
    void testGetWalletByUserId(String testCase, int userId, boolean expectedSuccess) throws SQLException {
        Wallet result = walletServices.getWalletById(userId);
        
        if (expectedSuccess) {
            assertNotNull(result, "Should retrieve wallet for valid user ID: " + testCase);
            assertEquals(userId, result.getUsers().getId());
            assertTrue(result.getSoDu() >= 0);
            assertNotNull(result.getCreatedAt());
        } else {
            assertNull(result, "Should not retrieve wallet for invalid user ID: " + testCase);
        }
    }

    @Test
    @DisplayName("Test wallet properties")
    void testWalletProperties() throws SQLException {
        assertNotNull(testWallet.getId());
        assertTrue(testWallet.getId() > 0);
        assertNotNull(testWallet.getSoDu());
        assertTrue(testWallet.getSoDu() >= 0);
        assertNotNull(testWallet.getCreatedAt());
        assertNotNull(testWallet.getUsers());
        assertEquals(testUser.getId(), testWallet.getUsers().getId());
    }

    @Test
    @DisplayName("Test wallet-user association")
    void testWalletUserAssociation() throws SQLException {
        Users walletUser = testWallet.getUsers();
        assertNotNull(walletUser);
        assertEquals(testUser.getUsername(), walletUser.getUsername());
        assertEquals(testUser.getEmail(), walletUser.getEmail());
    }

    private Users createTestUser() {
        Users user = new Users();
        user.setUsername("walletTestUser_" + System.currentTimeMillis());
        user.setPassword("TestPass123!");
        user.setFirstName("Wallet");
        user.setLastName("Test");
        user.setAvatar("wallet_avatar.png");
        user.setEmail("wallet_test" + System.currentTimeMillis() + "@example.com");
        user.setRole("USER");
        user.setCreatedAt(new Date());
        return user;
    }
}