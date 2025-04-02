/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ghee.services;

import com.ghee.config.AppConfigs;
import com.ghee.pojo.JdbcUtils;
import com.ghee.pojo.Users;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author giahu
 */
public class UserServices {

    /**
     * hàm thêm user vào database.
     *
     * @param user
     * @throws SQLException
     */
    public Map<String, Object> registerUser(Users user) throws SQLException {
        Map<String, Object> results = new HashMap<>();
        
        try (Connection conn = JdbcUtils.getConn()) {
            String procedureCall = "{CALL RegisterUser(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement callableStatement = conn.prepareCall(procedureCall);

            // Mã hóa mật khẩu bằng BCrypt
            // 10 là số lần lặp (work factor)
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));

            // Thiết lập các tham số in.
            callableStatement.setString(1, user.getUsername());
            callableStatement.setString(2, hashedPassword);
            callableStatement.setString(3, user.getFirstName());
            callableStatement.setString(4, user.getLastName());
            callableStatement.setString(5, user.getAvatar());
            callableStatement.setString(6, user.getEmail());
            callableStatement.setString(7, user.getRole());
            callableStatement.setDate(8, new java.sql.Date(user.getCreatedAt().getTime()));

            // Thiết lập tham số out.
            callableStatement.registerOutParameter(9, Types.BOOLEAN);
            callableStatement.registerOutParameter(10, Types.VARCHAR);

            callableStatement.execute();

            boolean success = callableStatement.getBoolean(9);
            String message = callableStatement.getString(10);

            results.put("success", success);
            results.put("message", message);
            
            return results;
        }
    }

    /**
     * Hàm đăng nhập.
     *
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public Users loginUser(String username, String password) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            if (username.trim() == null) {
                return null;
            }

            String procedureCall = "{CALL LoginUser(?)}";
            CallableStatement callableStatement = conn.prepareCall(procedureCall);

            callableStatement.setString(1, username);

            ResultSet rs = callableStatement.executeQuery();

            if (rs.next()) {
                Users user = new Users();

                String hashedPassword = rs.getString("password");
                System.out.printf("Password trong DB: ", hashedPassword);

                // Kiểm tra mật khẩu
                if (hashedPassword != null && BCrypt.checkpw(password, hashedPassword)) {
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(hashedPassword); // Lưu mật khẩu đã mã hóa
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));
                    user.setCreatedAt(rs.getDate("created_at"));
                    return user;
                }
            }
        } catch (SQLException ex) {
            System.out.println(AppConfigs.ERROR_DATABASE);
        }
        return null;
    }
}
