
import com.ghee.pojo.Wallet;
import com.ghee.pojo.Users;
import com.ghee.services.UserServices;
import com.ghee.services.WalletServices;
import com.ghee.utils.ManageUser;
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
//        testUser = createTestUser();
//        Map<String, Object> regResult = userServices.registerUser(testUser);
//        assertTrue((boolean) regResult.get("success"), "Setup failed - could not register test user");
//        
//        // Login the user to set current user in Utils
//        Users loggedInUser = userServices.loginUser(testUser.getUsername(), "TestPass123!");
//        assertNotNull(loggedInUser);
//        ManageUser.setCurrentUser(loggedInUser);
//        
//        // Get the wallet for testing
//        testWallet = walletServices.getWalletById(loggedInUser.getId());
//        assertNotNull(testWallet, "Setup failed - could not get wallet for test user");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/wallet_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Get Wallet Test: {0}")
    void testGetWalletByUserId(String testCase, String username, String password, boolean expectedSuccess) throws SQLException {
        Users user = userServices.loginUser(username, password);
        ManageUser.setCurrentUser(user);
        Wallet result = walletServices.getWalletById(user.getId());
        if (expectedSuccess) {
            assertNotNull(result, "Should retrieve wallet for valid user ID: " + testCase);
            assertEquals(user.getId(), result.getUsers().getId());
            assertTrue(result.getSoDu() >= 0);
            assertNotNull(result.getCreatedAt());
        } else {
            assertNull(result, "Should not retrieve wallet for invalid user ID: " + testCase);
        }
    }
    
    @ParameterizedTest
    @CsvFileSource(resources = "/wallet_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Test wallet properties: {0}")
    void testWalletProperties(String testCase, String username, String password, boolean expectedSuccess) throws SQLException {
        if (!expectedSuccess) return; // Skip if not expected to succeed
        
        Users user = userServices.loginUser(username, password);
        ManageUser.setCurrentUser(user);
        Wallet wallet = walletServices.getWalletById(user.getId());
        
        assertNotNull(wallet, "Wallet should exist for valid user");
        assertNotNull(wallet.getId(), "Wallet ID should not be null");
        assertTrue(wallet.getId() > 0, "Wallet ID should be positive");
        assertNotNull(wallet.getSoDu(), "Wallet balance should not be null");
        assertTrue(wallet.getSoDu() >= 0, "Wallet balance should be non-negative");
        assertNotNull(wallet.getCreatedAt(), "Created date should not be null");
        assertNotNull(wallet.getUsers(), "User association should not be null");
        assertEquals(user.getId(), wallet.getUsers().getId(), "Wallet should belong to the correct user");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/wallet_test_cases.csv", numLinesToSkip = 1)
    @DisplayName("Test wallet-user association: {0}")
    void testWalletUserAssociation(String testCase, String username, String password, boolean expectedSuccess) throws SQLException {
        if (!expectedSuccess) return; 
        
        Users user = userServices.loginUser(username, password);
        ManageUser.setCurrentUser(user);
        Wallet wallet = walletServices.getWalletById(user.getId());
        
        Users walletUser = wallet.getUsers();
        assertNotNull(walletUser, "Wallet should have an associated user");
        assertEquals(user.getUsername(), walletUser.getUsername(), "Username should match");
        assertEquals(user.getEmail(), walletUser.getEmail(), "Email should match");
        assertEquals(user.getFirstName(), walletUser.getFirstName(), "First name should match");
        assertEquals(user.getLastName(), walletUser.getLastName(), "Last name should match");
    }
}