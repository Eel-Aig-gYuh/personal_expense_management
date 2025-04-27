/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ghee.services;

import com.ghee.config.AppConfigs;
import com.ghee.pojo.Category;
import com.ghee.pojo.JdbcUtils;
import com.ghee.pojo.Transaction;
import com.ghee.pojo.Users;
import com.ghee.pojo.Wallet;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author giahu
 */
public class TransactionServices {

    /**
     * Lấy danh sách các giao dịch trong khoảng thời gian.
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     * @throws SQLException 
     */
    public List<Transaction> getTransactionByUserIdAdnDateRange(int userId, LocalDate startDate, LocalDate endDate) throws SQLException{
        List<Transaction> transactions = new ArrayList<>();
        CategoryServices categoryServices = new CategoryServices();
        
        try (Connection conn = JdbcUtils.getConn()) {
            String query = "SELECT t.*, c.name " + 
                           "FROM transaction t " +
                           "JOIN category c ON t.category_id = c.id " +
                           "WHERE t.user_id = ? AND t.transaction_date BETWEEN ? AND ? " + 
                           "ORDER BY t.id DESC";
            
            PreparedStatement stm = conn.prepareCall(query);
            stm.setInt(1, userId);
            stm.setDate(2, java.sql.Date.valueOf(startDate));
            stm.setDate(3, java.sql.Date.valueOf(endDate));
            
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                
                transaction.setId(rs.getInt("id"));
                
                Users user = new Users(rs.getInt("user_id"));
                transaction.setUserId(user);
                
                Category category = categoryServices.getCategoryById(rs.getInt("category_id"));
                transaction.setCategoryId(category);
                
                Wallet wallet = new Wallet(rs.getInt("wallet_id"));
                transaction.setWalletId(wallet);
                
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setTransactionDate(rs.getDate("transaction_date"));
                transaction.setDescription(rs.getString("description"));
                transaction.setCreatedAt(rs.getDate("created_at"));
                
                transactions.add(transaction);
            }
        }
        return transactions;
    }
    
    /**
     * Tính số dư đầu vào - tổng số tiền trước start_date
     * @param userId
     * @param startDate
     * @return
     * @throws SQLException 
     */
    public double getOpeningBalance(int userId, LocalDate startDate) throws SQLException{
        
        try (Connection conn = JdbcUtils.getConn()) {
            String query = "SELECT SUM(amount) AS opening_balance " + 
                           "FROM transaction " + 
                           "WHERE user_id = ? AND transaction_date < ?";
            PreparedStatement stm = conn.prepareCall(query);
            
            stm.setInt(1, userId);
            stm.setDate(2, java.sql.Date.valueOf(startDate));
            
            ResultSet rs = stm.executeQuery();
            if (rs.next())
                return rs.getDouble("opening_balance");
        }
        return 0.0;
    }
    
    /**
     * Tính số dư cuối - tổng số tiền từ đầu đến endDate.
     * @param userId
     * @param endDate
     * @return
     * @throws SQLException 
     */
    public double getClosingBalance(int userId, LocalDate endDate) throws SQLException {
        
        try (Connection conn = JdbcUtils.getConn()) {
            String query = "SELECT SUM(amount) AS closing_balance " + 
                           "FROM transaction " + 
                           "WHERE user_id = ? AND transaction_date <= ?";
            PreparedStatement stm = conn.prepareCall(query);
            
            stm.setInt(1, userId);
            stm.setDate(2, java.sql.Date.valueOf(endDate));
            
            ResultSet rs = stm.executeQuery();
            if (rs.next())
                return rs.getDouble("closing_balance");
        }
        return 0.0;
    }
    
    /**
     * Xoá giao dịch.
     * @param transaction
     * 
     * @return
     * @throws SQLException 
     */
    public boolean deleteTransaction(Transaction transaction) throws SQLException{
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "{CALL DeleteTransaction (?, ?, ?, ?)}";
            CallableStatement callableStatement = conn.prepareCall(sql);
            callableStatement.setInt(1, transaction.getId());
            callableStatement.setInt(2, transaction.getUserId().getId());
            
            callableStatement.registerOutParameter(3, Types.BOOLEAN);
            callableStatement.registerOutParameter(4, Types.VARCHAR);

            callableStatement.execute();
            
            boolean success = callableStatement.getBoolean(3);
            String message = callableStatement.getString(4);
            
            return success;
        }
    }
    
    /**
     * Cập nhật lại giao dịch.
     * @param transaction
     * @return 
     */
    public Map<String, Object> updateTransaction (Transaction transaction) throws SQLException {
        Map<String, Object> results = new HashMap<>();
        
        try (Connection conn = JdbcUtils.getConn()) {
            String produceCall = "{Call UpdateTransaction (?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement callableStatement = conn.prepareCall(produceCall);
            
            callableStatement.setInt(1, transaction.getId()); // transaction_id
            callableStatement.setInt(2, transaction.getUserId().getId()); // user_id
            callableStatement.setInt(3, transaction.getCategoryId().getId()); // category_id
            callableStatement.setDouble(4, transaction.getWalletId().getId()); // wallet_id
            callableStatement.setDouble(5, transaction.getAmount()); // amount
            callableStatement.setDate(6, new java.sql.Date(transaction.getTransactionDate().getTime())); // transaction_date
            callableStatement.setString(7, transaction.getDescription()); // description
            
            callableStatement.registerOutParameter(8, Types.BOOLEAN);
            callableStatement.registerOutParameter(9, Types.VARCHAR);
            
            callableStatement.execute();
            
            boolean success = callableStatement.getBoolean(8);
            String message = callableStatement.getString(9);
            
            results.put("success", success);
            results.put("message", message);
            
            // System.err.println(callableStatement.getString(9));
        }
        return results;
    }
        
    /**
     * Thêm giao dịch.
     * @param transaction
     * @return
     * @throws SQLException 
     */
    public Map<String, Object> addTransaction(Transaction transaction) throws SQLException {
        Map<String, Object> results = new HashMap<>();
        
        try (Connection conn = JdbcUtils.getConn()) {
            String produceCall = "{Call CreateTransaction (?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement callableStatement = conn.prepareCall(produceCall);
            
            callableStatement.setInt(1, transaction.getUserId().getId()); // user_id
            callableStatement.setInt(2, transaction.getCategoryId().getId()); // category_id
            callableStatement.setInt(3, transaction.getWalletId().getId()); // wallet_id
            callableStatement.setDouble(4, transaction.getAmount()); // amount
            callableStatement.setDate(5, new java.sql.Date(transaction.getTransactionDate().getTime())); // transaction_date
            callableStatement.setString(6, transaction.getDescription()); // description
            callableStatement.setDate(7, new java.sql.Date(transaction.getCreatedAt().getTime())); // created_at
            
            callableStatement.registerOutParameter(8, Types.BOOLEAN);
            callableStatement.registerOutParameter(9, Types.VARCHAR);
            
            callableStatement.execute();
            
            boolean success = callableStatement.getBoolean(8);
            String message = callableStatement.getString(9);
            
            results.put("success", success);
            results.put("message", message);
            
            // System.err.println(callableStatement.getString(9));
           
        }
        catch (SQLException ex) {
            results.put("success", false);
            results.put("message", AppConfigs.ERROR_DATABASE);
        }
        
        return results;
    }
}
