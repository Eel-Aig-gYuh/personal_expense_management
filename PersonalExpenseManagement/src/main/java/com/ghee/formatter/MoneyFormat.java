/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ghee.formatter;

import java.text.DecimalFormat;

/**
 *
 * @author giahu
 */
public class MoneyFormat {
    public static String moneyFormat(double value) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        
        return df.format(value);
    }
    
    public static String formatAmount(double amount) {
        if (amount >= 1000000){
            return String.format("%.1fM", amount/1000000);
        } else if (amount >= 1000) {
            return String.format("%.1fK", amount/1000);
        }
        else
            return String.format("%.0f", amount);
    }
}
