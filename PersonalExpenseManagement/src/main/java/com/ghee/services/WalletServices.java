/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ghee.services;

import com.ghee.personalexpensemanagement.Utils;
import com.ghee.pojo.JdbcUtils;
import com.ghee.pojo.Wallet;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javafx.scene.control.Alert;

/**
 *
 * @author giahu
 */
public class WalletServices {
    public Wallet getWalletById(int userId) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String procedureCall = "{GetWalletByUserId (?, ?, ?)}";
            CallableStatement stm = conn.prepareCall(procedureCall);
            
            stm.setInt(1, userId);
            
            stm.registerOutParameter(2, Types.BOOLEAN);
            stm.registerOutParameter(3, Types.VARCHAR);
            
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
                Wallet wallet = new Wallet(rs.getInt("id"), rs.getDouble("so_du"), rs.getDate("created_at"));
                
                wallet.setUserId(Utils.getCurrentUser());
                
                return wallet;
            }
            
            // boolean success = stm.getBoolean(2);
            String message = stm.getString(3);
            
            System.out.println(message);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
}
