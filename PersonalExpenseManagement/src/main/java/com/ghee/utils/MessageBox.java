/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ghee.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author giahu
 */
public class MessageBox {
    /**
     * Tạo hộp message thông báo.
     * @param content
     * @param types
     * @return 
     */
    public static Alert getAlert(String content, Alert.AlertType types){
        return new Alert(types, content, ButtonType.OK);
    }
    
    public static Alert getYesNoAlert(String content, Alert.AlertType types){
        return new Alert(types, content, ButtonType.OK, ButtonType.CANCEL);
    }
}
