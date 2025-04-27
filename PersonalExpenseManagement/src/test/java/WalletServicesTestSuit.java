
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
   
}