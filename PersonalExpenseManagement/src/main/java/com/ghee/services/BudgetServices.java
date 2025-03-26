/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ghee.services;

import com.ghee.pojo.Budget;
import com.ghee.pojo.JdbcUtils;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author giahu
 */
public class BudgetServices {
    public List<Budget> getBudgets (String kw) throws SQLException{
        List<Budget> budgets = new ArrayList<>();
        
        try (Connection conn = JdbcUtils.getConn()) {
            String query = "SELECT * FROM budget";
        }
        
        return budgets;
    }
    
    public boolean createBudget(Budget budget) {
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
            
            System.out.println(success? "SUCCESS: ": "FAILURE: " + message);
            
            return success;

        } catch (SQLException ex) {
            System.err.println("ERROR: " + ex.getMessage());
            return false;
        }
    }
    
    public boolean updateBudget(Budget budget) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            
            String produceCall = "{Call UpdateBudget (?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement callableStatement = conn.prepareCall(produceCall);

            callableStatement.setInt(1, budget.getId()); // budget_id
            callableStatement.setInt(2, budget.getUserId().getId()); // user_id
            callableStatement.setInt(3, budget.getCategoryId().getId()); // category_id
            callableStatement.setDouble(4, budget.getTarget()); // target
            callableStatement.setDate(5, new java.sql.Date(budget.getStartDate().getTime())); // start_date
            callableStatement.setDate(6, new java.sql.Date(budget.getEndDate().getTime())); // end_date
            callableStatement.setDate(7, new java.sql.Date(budget.getCreatedAt().getTime())); // created_at
            
            callableStatement.registerOutParameter(8, Types.BOOLEAN);
            callableStatement.registerOutParameter(9, Types.VARCHAR);
            
            callableStatement.execute();
            
            boolean success = callableStatement.getBoolean(7);
            String message = callableStatement.getString(8);
            
            System.out.println(success? "SUCCESS: ": "FAILURE: " + message);
            
            return success;
            
            
        } catch (SQLException ex) {
            System.err.println("ERROR: " + ex.getMessage());
            return false;
        }
    }
}
