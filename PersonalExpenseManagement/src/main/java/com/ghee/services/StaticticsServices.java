/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ghee.services;

import com.ghee.pojo.Budget;
import com.ghee.pojo.Category;
import com.ghee.pojo.JdbcUtils;
import com.ghee.pojo.Users;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author giahu
 */
public class StaticticsServices {
    
    /**
     * thống kê, báo cáo chi tiêu theo danh mục
     * @param userId: người dùng hiện tại.
     * @param startDate : ngày bắt đầu.
     * @param endDate : ngày kết thúc.
     */
    public Map<String, Double> statSpendingByCategory(Users userId, LocalDate startDate, LocalDate endDate) throws SQLException {
        Map<String, Double> spendingByCategoryData = new HashMap<>();
        
        try (Connection conn = JdbcUtils.getConn()) {
            String query = "SELECT c.name, SUM(t.amount) AS total_amount " + 
                           "FROM transaction t " + 
                           "JOIN category c ON c.id = t.category_id " + 
                           "WHERE t.user_id = ? " +
                                "AND t.transaction_date BETWEEN ? AND ? " +
                                "AND c.type = 'Chi' "+ 
                           "GROUP BY c.id, c.name";
            PreparedStatement stm = conn.prepareCall(query);
            
            stm.setInt(1, userId.getId());
            stm.setDate(2, Date.valueOf(startDate));
            stm.setDate(3, Date.valueOf(endDate));
            
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
                String categoryName = rs.getString("name");
                double totalAmount = rs.getDouble("total_amount");
                spendingByCategoryData.put(categoryName, totalAmount);
            }
        }
        
        return spendingByCategoryData;
    }
    
    /**
     * thống kê, báo cáo chi tiêu cho một ngân sách cụ thể.
     * @param budget : ngân sách muốn được báo cáo trong detail budget.
     * @return 
     */
    public Map<LocalDate, Double> statSpendingByBudget(Budget budget) throws SQLException {
        Map<LocalDate, Double> spendingByBudgetData = new HashMap<>();
        
        try (Connection conn = JdbcUtils.getConn()) {
            String query = "SELECT t.transaction_date, SUM(t.amount) AS total_amount " + 
                           "FROM transaction t " + 
                           "JOIN category c ON c.id = t.category_id " + 
                           "WHERE t.user_id = ? AND t.category_id = ? " +
                                "AND t.transaction_date BETWEEN ? AND ? " +
                                "AND c.type = 'Chi' "+ 
                           "GROUP BY t.transaction_date";
            PreparedStatement stm = conn.prepareCall(query);
            
            stm.setInt(1, budget.getUserId().getId());
            stm.setInt(2, budget.getCategoryId().getId());
            stm.setDate(3, Date.valueOf(budget.getStartDate().toString()));
            stm.setDate(4, Date.valueOf(budget.getEndDate().toString()));
            
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
                LocalDate date = rs.getDate("transaction_date").toLocalDate();
                double totalAmount = rs.getDouble("total_amount");
                spendingByBudgetData.put(date, totalAmount);
            }
        }
        
        return spendingByBudgetData;
    }
    
    /**
     * Thống kê chi theo ngày trong tháng.
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     * @throws SQLException 
     */
    public Map<LocalDate, Double> statTransactionByDay(Users userId, LocalDate startDate, LocalDate endDate) throws SQLException {
        Map<LocalDate, Double> transactionByDay = new HashMap<>();
        
        try (Connection conn = JdbcUtils.getConn()) {
            String query = "SELECT t.transaction_date, SUM(t.amount) AS total_amount " + 
                           "FROM transaction t " +
                           "JOIN category c ON t.category_id = c.id " +
                           "WHERE t.user_id = ? " + 
                           "AND t.transaction_date BETWEEN ? AND ? " + 
                           "AND c.type = 'Chi' " + 
                           "GROUP BY t.transaction_date";
            PreparedStatement stm = conn.prepareCall(query);
            
            stm.setInt(1, userId.getId());
            stm.setDate(2, Date.valueOf(startDate));
            stm.setDate(3, Date.valueOf(endDate));
            
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
                LocalDate date = rs.getDate("transaction_date").toLocalDate();
                double totalAmount = rs.getDouble("total_amount");
                transactionByDay.put(date, totalAmount);
            }
            
            // Điền giá trị 0 cho các ngày không có giao dịch trong khoảng thời gian
            LocalDate currentDate = startDate;
            while (!currentDate.isAfter(endDate)) {
                transactionByDay.putIfAbsent(currentDate, 0.0);
                currentDate = currentDate.plusDays(1);
            }
        }
        
        return transactionByDay;
    }
    
    public Map<String, Double> statComparison(Users userId, LocalDate currentDateStart, LocalDate currentDateEnd, 
                                                            LocalDate preDateStart, LocalDate preDateEnd) throws SQLException {
        Map<String, Double> comparisonDatas = new HashMap<>();
        
        try (Connection conn = JdbcUtils.getConn()) {
            String query = "SELECT SUM(t.amount) AS total_amount " + 
                           "FROM transaction t " + 
                           "JOIN category c ON c.id = t.category_id " +
                           "WHERE t.user_id = ? " + 
                           "AND t.transaction_date BETWEEN ? AND ? " + 
                           "AND c.type = 'Chi'";
            PreparedStatement stm = conn.prepareCall(query);
            
            // thang truoc
            stm.setInt(1, userId.getId());
            stm.setDate(2, Date.valueOf(preDateStart));
            stm.setDate(3, Date.valueOf(preDateEnd));
            
            ResultSet rs = stm.executeQuery();
            
            Double preTotal = rs.next() ? rs.getDouble("total_amount") : 0.0;
            comparisonDatas.put("Tháng trước", preTotal);
            
            // thang nay
            stm.setDate(2, Date.valueOf(currentDateStart));
            stm.setDate(3, Date.valueOf(currentDateEnd));
            
            rs = stm.executeQuery();
            
            Double currTotal = rs.next() ? rs.getDouble("total_amount") : 0.0;
            comparisonDatas.put("Tháng này", currTotal);
        }
        
        return comparisonDatas;
    }
}
