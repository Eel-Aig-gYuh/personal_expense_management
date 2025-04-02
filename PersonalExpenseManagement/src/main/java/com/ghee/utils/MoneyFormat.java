/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ghee.utils;

import java.text.DecimalFormat;

/**
 *
 * @author giahu
 */
public class MoneyFormat {
    public static String moneyFormat(double value) {
        DecimalFormat df = new DecimalFormat("#,###.0");
        
        return df.format(value);
    }
}
