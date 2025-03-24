/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ghee.services;

import com.ghee.pojo.Category;
import com.ghee.pojo.JdbcUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author giahu
 */
public class CategoryServices {
    public List<Category> getCategories() throws SQLException {
        List<Category> cates = new ArrayList<>();
        
        try (Connection conn = JdbcUtils.getConn()){
            
        }
        
        return cates;
    }
}
