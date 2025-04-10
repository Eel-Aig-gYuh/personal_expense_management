/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ghee.services;

import com.ghee.pojo.Budget;
import com.ghee.pojo.Category;
import com.ghee.pojo.JdbcUtils;
import com.ghee.pojo.Users;
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
public class BudgetServices {

    public double getTotalBudget(int userId, LocalDate startDate, LocalDate endDate) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String query = "SELECT COALESCE(SUM(target), 0) AS total_budget "
                    + "FROM budget "
                    + "WHERE user_id = ? AND start_date <= ? AND end_date >= ? ";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setInt(1, userId);
            stm.setDate(2, java.sql.Date.valueOf(endDate));
            stm.setDate(3, java.sql.Date.valueOf(startDate));

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_budget");
            }
        }
        return 0.0;
    }

    public double getTotalSpent(int userId, LocalDate startDate, LocalDate endDate) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String query = "SELECT COALESCE(SUM(amount), 0) AS total_spent "
                    + "FROM transaction t "
                    + "JOIN category c ON t.category_id = c.id "
                    + "WHERE t.user_id = ? AND c.type = 'Chi' AND t.transaction_date BETWEEN ? AND ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setInt(1, userId);
            stm.setDate(2, java.sql.Date.valueOf(startDate));
            stm.setDate(3, java.sql.Date.valueOf(endDate));

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_spent");
            }
        }
        return 0.0;
    }

    public List<Budget> getBudgetByUserIdAndDateRange(int userId, LocalDate startDate, LocalDate endDate) throws SQLException {
        List<Budget> budgets = new ArrayList<>();
        CategoryServices categoryServices = new CategoryServices();

        try (Connection conn = JdbcUtils.getConn()) {
            String query = " SELECT * FROM budget "
                    + " WHERE user_id = ? AND start_date <= ? AND end_date >= ? ";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setInt(1, userId);
            stm.setDate(2, java.sql.Date.valueOf(endDate));
            stm.setDate(3, java.sql.Date.valueOf(startDate));

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Budget budget = new Budget();
                budget.setId(rs.getInt("id"));

                Users user = new Users(rs.getInt("user_id"));
                budget.setUserId(user);

                Category category = categoryServices.getCategoryById(rs.getInt("category_id"));
                budget.setCategoryId(category);

                budget.setAmount(rs.getDouble("amount"));
                budget.setTarget(rs.getDouble("target"));
                budget.setStartDate(rs.getDate("start_date"));
                budget.setEndDate(rs.getDate("end_date"));
                budget.setCreatedAt(rs.getDate("created_at"));

                budgets.add(budget);
            }
        }
        return budgets;
    }

    /**
     * Tạo ngân sách mới
     *
     * @param budget
     * @return
     * @throws SQLException
     */
    public Map<String, Object> createBudget(Budget budget) throws SQLException {
        Map<String, Object> results = new HashMap<>();

        try (Connection conn = JdbcUtils.getConn()) {
            String produceCall = "{Call CreateBudget (?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement callableStatement = conn.prepareCall(produceCall);

            callableStatement.setInt(1, budget.getUserId().getId()); // user_id
            callableStatement.setInt(2, budget.getCategoryId().getId()); // category_id
            callableStatement.setDouble(3, budget.getTarget()); // target
            callableStatement.setDate(4, new java.sql.Date(budget.getStartDate().getTime())); // start_date
            callableStatement.setDate(5, new java.sql.Date(budget.getEndDate().getTime())); // end_date
            callableStatement.setDate(6, new java.sql.Date(budget.getCreatedAt().getTime())); // created_at

            callableStatement.registerOutParameter(7, Types.BOOLEAN);
            callableStatement.registerOutParameter(8, Types.VARCHAR);

            callableStatement.execute();

            boolean success = callableStatement.getBoolean(7);
            String message = callableStatement.getString(8);

            results.put("success", success);
            results.put("message", message);

        }
        return results;
    }

    /**
     * Cập nhật lại ngân sách
     *
     * @param budget
     * @return
     * @throws SQLException
     */
    public Map<String, Object> updateBudget(Budget budget) throws SQLException {
        Map<String, Object> results = new HashMap<>();

        try (Connection conn = JdbcUtils.getConn()) {

            String produceCall = "{Call UpdateBudget (?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement callableStatement = conn.prepareCall(produceCall);

            callableStatement.setInt(1, budget.getId()); // budget_id
            callableStatement.setInt(2, budget.getCategoryId().getId()); // category_id
            callableStatement.setDouble(3, budget.getTarget()); // target
            callableStatement.setDate(4, new java.sql.Date(budget.getStartDate().getTime())); // start_date
            callableStatement.setDate(5, new java.sql.Date(budget.getEndDate().getTime())); // end_date

            callableStatement.registerOutParameter(6, Types.BOOLEAN);
            callableStatement.registerOutParameter(7, Types.VARCHAR);

            callableStatement.executeUpdate();

            boolean success = callableStatement.getBoolean(6);
            String message = callableStatement.getString(7);

            results.put("success", success);
            results.put("message", message);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return results;
    }

    /**
     * Xoá ngân sách.
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public boolean deleteBudget(int id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM budget WHERE id = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, id);

            return stm.executeUpdate() > 0;
        }
    }
}
