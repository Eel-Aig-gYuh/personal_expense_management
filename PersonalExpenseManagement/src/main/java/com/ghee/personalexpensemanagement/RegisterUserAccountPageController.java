/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ghee.personalexpensemanagement;

import com.ghee.config.AppConfigs;
import com.ghee.personalexpensemanagement.Utils;
import com.ghee.pojo.Users;
import com.ghee.services.UserServices;
import com.ghee.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author giahu
 */
public class RegisterUserAccountPageController implements Initializable {

    private String firstname;
    private String lastname;
    private String email;
    private String avatarUrl;
    private String role;
    private Date createdAt;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField confirmPasswordField;

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

    public void setUserData(String firstname, String lastname, String email, String avatarUrl, String role, Date createAt) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.role = role;
        this.createdAt = createAt;
    }

    /**
     * Thêm user vào db.
     *
     * @param e
     * @throws java.lang.Exception
     */
    public void addUserHandler(ActionEvent e) throws Exception {
        String username = this.usernameField.getText().trim();
        String password = this.passwordField.getText().trim();
        String confirmPassword = this.confirmPasswordField.getText().trim();
        
        if (username.equals("") || password.equals("") || confirmPassword.equals("")) {
            MessageBox.getAlert(AppConfigs.ERROR_NOT_ENOUGH_INFORMATION, Alert.AlertType.ERROR).showAndWait();
            return;
        }

        // check lenght
        if (username.length() <= AppConfigs.LENGHT_OF_ACCOUNT || password.length() <= AppConfigs.LENGHT_OF_ACCOUNT) {
            MessageBox.getAlert(AppConfigs.ERROR_LENGHT_OF_ACCOUNT, Alert.AlertType.ERROR).showAndWait();
            return;
        }

        // check password && confirm
        if (!password.equals(confirmPassword)) {
            MessageBox.getAlert(AppConfigs.ERROR_PASS_AND_CONFIRM, Alert.AlertType.ERROR).showAndWait();
            return;
        }
        
        if (!password.matches(AppConfigs.PASSWORD_PATTERN)) {
            MessageBox.getAlert(AppConfigs.ERROR_PASS_PATTERN, Alert.AlertType.ERROR).showAndWait();
            return;
        }

        try {
            Users user = new Users(username, password, firstname, lastname, email, role, createdAt);

            var results = s.registerUser(user);
            var success = (boolean) results.get("success");
            var msg = (String) results.get("message");
            
            if (!success) {
                System.err.println(msg);
                MessageBox.getAlert("Đăng ký không thành công ! " + msg, Alert.AlertType.WARNING).showAndWait();
            } else {
                MessageBox.getAlert("Đăng ký thành công !", Alert.AlertType.CONFIRMATION).showAndWait();
                goToLoginPage();
            }

        } catch (SQLException ex) {
            Logger.getLogger(RegisterUserAccountPageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            System.err.println("ERROR: " + ex.getMessage());
        }
    }

    public void goToLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginUser.fxml"));
            Parent root = loader.load();

            // chuyển trang qua account 
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang đăng nhập !";
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }

    public void goBack() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("registerUserInfoPage.fxml"));
            Parent root = loader.load();
            
            RegisterUserInfoPageController infoPageController = loader.getController();
            infoPageController.setUserData(firstname, lastname, email, avatarUrl, role, createdAt);

            // chuyển trang qua account 
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang đăng ký !";
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }
}
