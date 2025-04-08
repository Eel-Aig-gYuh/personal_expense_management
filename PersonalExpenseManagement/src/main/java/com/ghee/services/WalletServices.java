/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ghee.services;

import com.ghee.pojo.JdbcUtils;
import com.ghee.pojo.Wallet;
import com.ghee.utils.ManageUser;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author giahu
 */
public class WalletServices {
    public Wallet getWalletById(int userId) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String procedureCall = "{Call GetWalletByUserId (?, ?)}";
            CallableStatement callableStatement = conn.prepareCall(procedureCall);
            
            callableStatement.setInt(1, userId);
            
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            
            callableStatement.execute();
            
            ResultSet rs = callableStatement.getResultSet();
            
            if (rs.next()) {
                Wallet wallet = new Wallet(rs.getInt("id"), rs.getDouble("so_du"), rs.getDate("created_at"));
                
                wallet.setUsers(ManageUser.getCurrentUser());
                
                return wallet;
            }
            
            String message = callableStatement.getString(2);
            
            System.out.println(message);
        } catch (SQLException ex) {
            System.err.println("ERROR: " + ex.getMessage());
        }
        return null;
    }
}
