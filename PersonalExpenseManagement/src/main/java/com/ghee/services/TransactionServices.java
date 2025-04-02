/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ghee.services;

import com.ghee.config.AppConfigs;
import com.ghee.personalexpensemanagement.Utils;
import com.ghee.pojo.JdbcUtils;
import com.ghee.pojo.Transaction;
import com.ghee.pojo.Users;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Alert;

/**
 *
 * @author giahu
 */
public class TransactionServices {

    public Map<String, Object> addTransaction(Transaction transaction) throws SQLException {
        Map<String, Object> results = new HashMap<>();
        
        try (Connection conn = JdbcUtils.getConn()) {
            String produceCall = "{Call CreateTransaction (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement callableStatement = conn.prepareCall(produceCall);
            
            callableStatement.setInt(1, transaction.getUserId().getId()); // user_id
            callableStatement.setInt(2, transaction.getCategoryId().getId()); // category_id
            callableStatement.setInt(3, transaction.getWalletId().getId()); // wallet_id
            callableStatement.setDouble(4, transaction.getAmount()); // amount
            callableStatement.setDate(5, new java.sql.Date(transaction.getTransactionDate().getTime())); // transaction_date
            callableStatement.setString(6, transaction.getDescription()); // description
            callableStatement.setString(7, transaction.getType()); // type
            callableStatement.setDate(8, new java.sql.Date(transaction.getCreatedAt().getTime())); // created_at
            
            callableStatement.registerOutParameter(9, Types.BOOLEAN);
            callableStatement.registerOutParameter(10, Types.VARCHAR);
            
            callableStatement.execute();
            
            boolean success = callableStatement.getBoolean(9);
            String message = callableStatement.getString(10);
            
            results.put("success", success);
            results.put("message", message);
           
        }
        catch (SQLException ex) {
            results.put("success", false);
            results.put("message", AppConfigs.ERROR_DATABASE);
        }
        
        return results;
    }
}
