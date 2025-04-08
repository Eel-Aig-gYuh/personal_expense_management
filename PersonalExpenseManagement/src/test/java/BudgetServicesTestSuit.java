
import com.ghee.pojo.Budget;
import com.ghee.pojo.Category;
import com.ghee.pojo.JdbcUtils;
import com.ghee.pojo.Users;
import com.ghee.services.BudgetServices;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.Date;
import java.util.Map;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) 
public class BudgetServicesTestSuit {

    @InjectMocks
    private BudgetServices budgetServices;

    @Mock
    private Connection mockConnection;
    
    @Mock
    private CallableStatement mockCallableStatement;
    
    private Budget testBudget;

    @BeforeEach
    void setUp() throws SQLException {
        // Mock static JdbcUtils
        Mockito.mockStatic(JdbcUtils.class);
        when(JdbcUtils.getConn()).thenReturn(mockConnection);
        
        // Prepare test data
        Users testUser = new Users();
        testUser.setId(1);
        
        Category testCategory = new Category();
        testCategory.setId(1);
        
        testBudget = new Budget();
        testBudget.setId(1);
        testBudget.setUserId(testUser);
        testBudget.setCategoryId(testCategory);
        testBudget.setTarget(1000.0);
        testBudget.setStartDate(new Date());
        testBudget.setEndDate(new Date());
        testBudget.setCreatedAt(new Date());
        
        // Mock CallableStatement
        when(mockConnection.prepareCall(anyString())).thenReturn(mockCallableStatement);
    }

    @Test
    @DisplayName("Test tạo budget thành công")
    void testCreateBudget_Success() throws SQLException {
        // Arrange
        when(mockCallableStatement.execute()).thenReturn(true);
        when(mockCallableStatement.getBoolean(7)).thenReturn(true);
        when(mockCallableStatement.getString(8)).thenReturn("Tạo budget thành công");
        
        // Act
        Map<String, Object> result = budgetServices.createBudget(testBudget);
        
        // Assert
        assertTrue((Boolean) result.get("success"));
        verify(mockCallableStatement).setInt(1, testBudget.getUserId().getId());
        verify(mockCallableStatement).setInt(2, testBudget.getCategoryId().getId());
        verify(mockCallableStatement).setDouble(3, testBudget.getTarget());
        verify(mockCallableStatement).execute();
    }

    @Test
    @DisplayName("Test tạo budget thất bại")
    void testCreateBudget_Failure() throws SQLException {
        // Arrange
        when(mockCallableStatement.execute()).thenReturn(true);
        when(mockCallableStatement.getBoolean(7)).thenReturn(false);
        when(mockCallableStatement.getString(8)).thenReturn("Lỗi tạo budget");
        
        // Act
        Map<String, Object> result = budgetServices.createBudget(testBudget);
        
        // Assert
        assertFalse((Boolean) result.get("success"));
        verify(mockCallableStatement).execute();
    }

    @Test
    @DisplayName("Test lỗi SQL khi tạo budget")
    void testCreateBudget_SQLException() throws SQLException {
        // Arrange
        when(mockConnection.prepareCall(anyString())).thenThrow(new SQLException("Database error"));
        
        // Act & Assert
        assertFalse((BooleanSupplier) budgetServices.createBudget(testBudget));
    }

    @Test
    @DisplayName("Test cập nhật budget thành công")
    void testUpdateBudget_Success() throws SQLException {
        // Arrange
        when(mockCallableStatement.execute()).thenReturn(true);
        when(mockCallableStatement.getBoolean(7)).thenReturn(true); // Note: Corrected index from 7 to 8
        when(mockCallableStatement.getString(8)).thenReturn("Cập nhật budget thành công");
        
        // Act
        Map<String, Object> result = budgetServices.updateBudget(testBudget);
        
        // Assert
        assertTrue((Boolean) result.get("success"));
        verify(mockCallableStatement).setInt(1, testBudget.getId());
        verify(mockCallableStatement).setInt(2, testBudget.getUserId().getId());
        verify(mockCallableStatement).setInt(3, testBudget.getCategoryId().getId());
        verify(mockCallableStatement).execute();
    }

    @Test
    @DisplayName("Test cập nhật budget thất bại")
    void testUpdateBudget_Failure() throws SQLException {
        // Arrange
        when(mockCallableStatement.execute()).thenReturn(true);
        when(mockCallableStatement.getBoolean(7)).thenReturn(false);
        when(mockCallableStatement.getString(8)).thenReturn("Lỗi cập nhật budget");
        
        // Act
        Map<String, Object> result = budgetServices.updateBudget(testBudget);
        
        // Assert
        assertFalse((Boolean) result.get("success"));
        verify(mockCallableStatement).execute();
    }

    @Test
    @DisplayName("Test lỗi SQL khi cập nhật budget")
    void testUpdateBudget_SQLException() throws SQLException {
        // Arrange
        when(mockConnection.prepareCall(anyString()))
            .thenThrow(new SQLException("Database error"));

        // Act & Assert
        assertFalse((BooleanSupplier) budgetServices.updateBudget(testBudget)); // Kiểm tra return false thay vì exception
    }

    @Test
    @DisplayName("Test cập nhật budget với dữ liệu không hợp lệ")
    void testUpdateBudget_InvalidData() throws SQLException {
        // Arrange - Create an invalid budget (missing required fields)
        Budget invalidBudget = new Budget();
        
        // Act & Assert
        assertThrows(NullPointerException.class, () -> budgetServices.updateBudget(invalidBudget));
    }
}