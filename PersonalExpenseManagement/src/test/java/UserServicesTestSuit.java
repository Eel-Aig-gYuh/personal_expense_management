import com.ghee.pojo.JdbcUtils;
import com.ghee.pojo.Users;
import com.ghee.services.UserServices;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mindrot.jbcrypt.BCrypt;

@ExtendWith(MockitoExtension.class)
class UserServicesTestSuit {
    
    @InjectMocks
    private UserServices userServices;

    @Mock
    private Connection mockConnection;
    
    @Mock
    private CallableStatement mockCallableStatement;
    
    @Mock
    private ResultSet mockResultSet;

    private Users testUser;

    @BeforeEach
    void setUp() throws SQLException {
        // Giả lập kết nối DB
        Mockito.mockStatic(JdbcUtils.class);
        when(JdbcUtils.getConn()).thenReturn(mockConnection);
        
        // Tạo user mẫu
        testUser = new Users();
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setEmail("john.doe@example.com");
        testUser.setAvatar("avatar.png");
        testUser.setRole("USER");
        testUser.setCreatedAt(new Date());

        // Giả lập CallableStatement
        when(mockConnection.prepareCall(anyString())).thenReturn(mockCallableStatement);
    }

    @Test
    @DisplayName("Test đăng ký user thành công")
    void testRegisterUser_Success() throws SQLException {
        when(mockCallableStatement.execute()).thenReturn(true);
        when(mockCallableStatement.getBoolean(9)).thenReturn(true);
        when(mockCallableStatement.getString(10)).thenReturn("Đăng ký thành công");

        boolean result = userServices.registerUser(testUser);

        
        assertTrue(result);
        verify(mockCallableStatement, times(1)).execute();
    }

    @Test
@DisplayName("Test đăng ký user thất bại")
void testRegisterUser_Failure() throws SQLException {
    when(mockCallableStatement.execute()).thenReturn(true);
    when(mockCallableStatement.getBoolean(9)).thenReturn(false);
    when(mockCallableStatement.getString(10)).thenReturn("Lỗi đăng ký");

    boolean result = userServices.registerUser(testUser);
    
    assertFalse(result);
    verify(mockCallableStatement).setString(eq(1), eq(testUser.getUsername()));
    verify(mockCallableStatement).setString(eq(2), anyString());
    verify(mockCallableStatement).execute();
}

    @Test
    @DisplayName("Test lỗi khi gọi stored procedure đăng ký")
    void testRegisterUser_SQLException() throws SQLException {
        when(mockConnection.prepareCall(anyString())).thenThrow(new SQLException("Database error"));

        Executable executable = () -> userServices.registerUser(testUser);
        
        assertThrows(SQLException.class, executable);
    }

    @Test
    @DisplayName("Test đăng nhập user thành công")
    void testLoginUser_Success() throws SQLException {
        String hashedPassword = BCrypt.hashpw("testPassword", BCrypt.gensalt(10));

        when(mockCallableStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("password")).thenReturn(hashedPassword);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("username")).thenReturn("testUser");

        Users resultUser = userServices.loginUser("testUser", "testPassword");
        
        assertNotNull(resultUser);
        assertEquals("testUser", resultUser.getUsername());
    }

    @Test
    @DisplayName("Test đăng nhập thất bại do sai mật khẩu")
    void testLoginUser_WrongPassword() throws SQLException {
        String hashedPassword = BCrypt.hashpw("correctPassword", BCrypt.gensalt(10));

        when(mockCallableStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("password")).thenReturn(hashedPassword);

        Users resultUser = userServices.loginUser("testUser", "wrongPassword");

        assertNull(resultUser);
    }

    @Test
    @DisplayName("Test đăng nhập thất bại do user không tồn tại")
    void testLoginUser_UserNotFound() throws SQLException {
        when(mockCallableStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Users resultUser = userServices.loginUser("unknownUser", "testPassword");
        
        assertNull(resultUser);
    }

    @Test
    @DisplayName("Test lỗi SQL khi đăng nhập")
    void testLoginUser_SQLException() throws SQLException {
        when(mockConnection.prepareCall(anyString())).thenThrow(new SQLException("Database error"));

        Executable executable = () -> userServices.loginUser("testUser", "testPassword");

        assertThrows(SQLException.class, executable);
    }
}
