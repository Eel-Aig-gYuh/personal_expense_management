/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ghee.personalexpensemanagement;

import com.ghee.pojo.Users;
import com.ghee.services.UserServices;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author giahu
 */
public class LoginUserController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    private UserServices s = null;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.s = new UserServices();
    }

    public void login() throws IOException {
        String username = this.usernameField.getText().trim();
        String password = this.passwordField.getText().trim();

        if ("".equals(username.trim()) || "".equals(password.trim())) {
            String message = username.equals("") ? "Vui lòng điền tài khoản !" : "Vui lòng điền mật khẩu !";
            Utils.getAlert(message, Alert.AlertType.CONFIRMATION).showAndWait();
            return;
        }

        try {
            Users user = s.loginUser(username, password);

            if (user != null) {
                Utils.setCurrentUser(user);

                Utils.getAlert("Đăng nhập thành công !", Alert.AlertType.CONFIRMATION).showAndWait();

                goToHomePage();
            } else {
                Utils.getAlert("Thông tin tài khoản hoặc mật khẩu không chính xác !", Alert.AlertType.CONFIRMATION).showAndWait();
            }
        } catch (SQLException ex) {
            Utils.getAlert("Lỗi SQL", Alert.AlertType.ERROR).show();
        }

    }

    public void goToHomePage() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage.fxml"));
            Parent root = loader.load();

            // chuyển trang qua account 
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang chủ !";
            System.out.println(ex.getMessage());
            Utils.getAlert(message, Alert.AlertType.ERROR).showAndWait();
        }
    }

    public void goToRegisterPage() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("registerUserInfoPage.fxml"));
            Parent root = loader.load();

            // chuyển trang qua account 
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang đăng ký !";
            Utils.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }
}
