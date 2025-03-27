/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ghee.services;

import com.ghee.personalexpensemanagement.Utils;
import com.ghee.pojo.JdbcUtils;
import com.ghee.pojo.Transaction;
import com.ghee.pojo.Users;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author giahu
 */
public class TransactionServices {

    public boolean addTransaction(Transaction transaction) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String produceCall = "{Call CreateTransaction (?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement callableStatement = conn.prepareCall(produceCall);
            
            callableStatement.setInt(1, transaction.getUserId().getId()); // user_id
            callableStatement.setInt(2, transaction.getCategoryId().getId()); // category_id
            callableStatement.setInt(3, transaction.getWalletId().getId()); // wallet_id
            callableStatement.setDouble(4, transaction.getAmount()); // amount
            callableStatement.setDate(5, new java.sql.Date(transaction.getTransactionDate().getTime())); // transaction_date
            callableStatement.setString(6, transaction.getDescription()); // description
            callableStatement.setDate(7, new java.sql.Date(transaction.getCreatedAt().getTime())); // created_at
            
            callableStatement.registerOutParameter(8, Types.VARCHAR);
            callableStatement.registerOutParameter(9, Types.VARCHAR);
            
            callableStatement.execute();
            
            boolean success = callableStatement.getBoolean(8);
            String message = callableStatement.getString(9);
            
            System.out.println(success ? "SUCCESS: ": "FAILURE: " + message);
            
            return success;

        }
        catch (SQLException ex) {
            System.err.println("ERROR: " + ex.getMessage());
            return false;
        }
    }
}
