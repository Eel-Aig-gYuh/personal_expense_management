/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ghee.personalexpensemanagement;

import com.ghee.config.AppConfigs;
import com.ghee.pojo.Users;
import com.ghee.services.UserServices;
import com.ghee.utils.ManageUser;
import com.ghee.utils.MessageBox;
import com.ghee.utils.MessageErrorField;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author giahu
 */
public class LoginUserController implements Initializable {

    @FXML private TextField usernameField;
    @FXML private TextField passwordFieldVisible;
    
    @FXML private PasswordField passwordField;

    @FXML private Button loginButton;
    
    private boolean isPasswordVisible = true;

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
        passwordFieldVisible.textProperty().bindBidirectional(passwordField.textProperty());
        
        togglePasswordVisibility();
    }
    
    public void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible; // Đảo ngược trạng thái

        if (isPasswordVisible) {
            // Hiện mật khẩu: Ẩn PasswordField, hiện TextField
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            passwordFieldVisible.setVisible(true);
            passwordFieldVisible.setManaged(true);
            
        } else {
            // Ẩn mật khẩu: Hiện PasswordField, ẩn TextField
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            passwordFieldVisible.setVisible(false);
            passwordFieldVisible.setManaged(false);
            
        }
    }

    public void login() throws IOException {
        boolean hasError = false;

        if (this.usernameField.getText().trim().equals("")) {
            MessageErrorField.ErrorFieldHbox(usernameField, AppConfigs.NULL_USERNAME);
            hasError = true;
        } else {
            MessageErrorField.ErrorFieldHboxOff(usernameField);
        }
        
        if (this.passwordField.getText().trim().equals("")) {
            MessageErrorField.ErrorFieldHbox(passwordField, AppConfigs.NULL_PASSWORD);
            hasError = true;
        } else {
            MessageErrorField.ErrorFieldHboxOff(passwordField);
        }

        if (!hasError) {
            try {
                String username = this.usernameField.getText().trim();
                String password = this.passwordField.getText().trim();
        
                Users user = s.loginUser(username, password);

                if (user != null) {
                    ManageUser.setCurrentUser(user);

                    MessageBox.getAlert("Đăng nhập thành công !", Alert.AlertType.CONFIRMATION).showAndWait();

                    goToHomePage();
                } else {
                    MessageBox.getAlert("Thông tin tài khoản hoặc mật khẩu không chính xác !", Alert.AlertType.CONFIRMATION).showAndWait();
                }
            } catch (SQLException ex) {
                MessageBox.getAlert("Lỗi SQL", Alert.AlertType.ERROR).show();
            }
        }
    }
    
    public void showPassword() {
        this.passwordField.setStyle("text");
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
            MessageBox.getAlert(message, Alert.AlertType.ERROR).showAndWait();
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
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }
}
