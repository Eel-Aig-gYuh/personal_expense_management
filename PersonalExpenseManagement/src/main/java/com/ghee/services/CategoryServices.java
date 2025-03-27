/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ghee.services;

import com.ghee.pojo.Category;
import com.ghee.pojo.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author giahu
 */
public class CategoryServices {
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
            String query = "SELECT * FROM category WHERE user_id = 1 OR user_id = ?";
            
            PreparedStatement stm = conn.prepareCall(query);
            
            stm.setInt(1, user_id);
            
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Category category = new Category(rs.getInt("id"), rs.getString("type"), rs.getString("name"));
                cates.add(category);
            }
        } catch (SQLException ex) {
            System.err.println("ERROR: Lỗi lấy danh sách cates dưới db.");
        }
        
        return cates;
    }
    
    
}
