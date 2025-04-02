/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ghee.services;

import com.ghee.config.AppConfigs;
import com.ghee.pojo.Category;
import com.ghee.pojo.JdbcUtils;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author giahu
 */
public class CategoryServices {
    
    // ================= GET
    
    public Category getCategoryById(int category_id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String query = "SELECT * FROM category WHERE id = ?";
            
            PreparedStatement stm = conn.prepareCall(query);
            
            stm.setInt(1, category_id);
            
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Category category = new Category(rs.getInt("id"), rs.getString("type"), rs.getString("name"));
                return category;
            }
        }
        return null;
    }
    
    public List<Category> getCategoriesByUserId(int user_id) throws SQLException {
        List<Category> cates = new ArrayList<>();
  
        try (Connection conn = JdbcUtils.getConn()){
            String query = "SELECT * FROM category WHERE user_id = ?";
            
            PreparedStatement stm = conn.prepareCall(query);
            
            stm.setInt(1, user_id);
            
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Category category = new Category(rs.getInt("id"), rs.getString("type"), rs.getString("name"));
                cates.add(category);
            }
        } catch (SQLException ex) {
            System.err.println(AppConfigs.ERROR_DATABASE);
        }
        
        return cates;
    }
    
    // ==================== POST
    
    public Map<String, Object> createCategory (Category category) throws SQLException {
        Map<String, Object> results = new HashMap<>();
        
        try (Connection conn = JdbcUtils.getConn()) {
            String procedureCall = "{Call CreateCategory (?, ?, ?, ?, ?)}";
            CallableStatement callableStatement = conn.prepareCall(procedureCall);
            
            callableStatement.setInt(1, category.getUserId().getId()); // user_id
            callableStatement.setString(2, category.getType()); // type
            callableStatement.setString(3, category.getName()); // name
            
            callableStatement.registerOutParameter(4, Types.BOOLEAN);
            callableStatement.registerOutParameter(5, Types.VARCHAR);
            
            callableStatement.execute();
            
            boolean success = callableStatement.getBoolean(4);
            String message = callableStatement.getString(5);
            
            results.put("success", success);
            results.put("message", message);
        }
        
        return results;
    }
}
