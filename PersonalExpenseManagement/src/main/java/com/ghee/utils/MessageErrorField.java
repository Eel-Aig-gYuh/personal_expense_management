/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ghee.utils;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author giahu
 */
public class MessageErrorField {
    public static void ErrorFieldHbox(TextField txt, String errMsg){
        txt.setStyle("-fx-border-color: red; -fx-border-width: 2px");

        Label errorMsg = new Label(errMsg);
        try {
            errorMsg.setId(txt.toString() + "error");
            System.out.println(txt.getId() + "error");
            errorMsg.setStyle("-fx-text-fill: #FF6B6B; -fx-font-weight: bold; -fx-margin-top: 5px");

            HBox parent = (HBox) txt.getParent();
            int index = parent.getChildren().indexOf(txt);
            VBox vbox = new VBox();
            vbox.getChildren().addAll(txt, errorMsg);
            parent.getChildren().add(index, vbox);
        } catch (Exception ex) {
            return;
        }
    }
    
    public static void ErrorFieldHbox(TextArea txt, String errMsg){
        txt.setStyle("-fx-border-color: red; -fx-border-width: 2px");

        Label errorMsg = new Label(errMsg);
        try {
            errorMsg.setId(txt.toString() + "error");
            System.out.println(txt.getId() + "error");
            errorMsg.setStyle("-fx-text-fill: #FF6B6B; -fx-font-weight: bold; -fx-margin-top: 5px");

            HBox parent = (HBox) txt.getParent();
            int index = parent.getChildren().indexOf(txt);
            VBox vbox = new VBox();
            vbox.getChildren().addAll(txt, errorMsg);
            parent.getChildren().add(index, vbox);
        } catch (Exception ex) {
            return;
        }
    }
    
    public static void ErrorFieldHboxOff(TextField txt) {
        try {
            // Đặt lại viền mặc định cho TextField
            txt.setStyle("-fx-border-color: black; -fx-border-width: 1px");

            // Lấy VBox chứa TextField (sau khi ErrorFieldHbox được gọi)
            VBox vbox = (VBox) txt.getParent();
            if (vbox == null) {
                return;
            }

            // Tìm Label lỗi trong VBox
            Label errLb = (Label) vbox.lookup("#" + txt.getId() + "error");
            if (errLb != null) {
                // Xóa Label lỗi khỏi VBox
                vbox.getChildren().remove(errLb);
            }

            // Lấy HBox chứa VBox
            HBox hbox = (HBox) vbox.getParent();
            if (hbox == null) {
                return;
            }

            // Lấy vị trí của VBox trong HBox
            int index = hbox.getChildren().indexOf(vbox);

            // Thay thế VBox bằng TextField (khôi phục cấu trúc ban đầu)
            hbox.getChildren().set(index, txt);
        } catch (Exception ex) {
            return;
        }
    }
    
    public static void ErrorFieldHboxOff(TextArea txt) {
        try {
            // Đặt lại viền mặc định cho TextField
            txt.setStyle("-fx-border-color: black; -fx-border-width: 1px");

            // Lấy VBox chứa TextField (sau khi ErrorFieldHbox được gọi)
            VBox vbox = (VBox) txt.getParent();
            if (vbox == null) {
                return;
            }

            // Tìm Label lỗi trong VBox
            Label errLb = (Label) vbox.lookup("#" + txt.getId() + "error");
            if (errLb != null) {
                // Xóa Label lỗi khỏi VBox
                vbox.getChildren().remove(errLb);
            }

            // Lấy HBox chứa VBox
            HBox hbox = (HBox) vbox.getParent();
            if (hbox == null) {
                return;
            }

            // Lấy vị trí của VBox trong HBox
            int index = hbox.getChildren().indexOf(vbox);

            // Thay thế VBox bằng TextField (khôi phục cấu trúc ban đầu)
            hbox.getChildren().set(index, txt);
        } catch (Exception ex) {
            return;
        }
    }
}
