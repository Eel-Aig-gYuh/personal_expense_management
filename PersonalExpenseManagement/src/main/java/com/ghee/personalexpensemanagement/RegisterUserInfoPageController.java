/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ghee.personalexpensemanagement;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ghee.config.AppConfigs;
import com.ghee.config.CloudinaryConfig;
import com.ghee.utils.MessageBox;
import com.ghee.utils.MessageErrorField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author giahu
 */
public class RegisterUserInfoPageController implements Initializable {

    @FXML private TextField lastnameField;
    @FXML private TextField firstnameField;
    @FXML private TextField emailField;
    
    @FXML private Button uploadAvatarButton;
    @FXML private Button nextPageButton;
    
    @FXML private ImageView avatar;
    
    private String avatarPath;
    private Cloudinary cloudinary;
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // check firstname 
        this.firstnameField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.isBlank()) {
                MessageErrorField.ErrorFieldHbox(firstnameField, AppConfigs.NULL_FIRSTNAME);
                
            } else if (newValue.matches(AppConfigs.PATTERN_SPECIAL_CHAR)) {
                MessageErrorField.ErrorFieldHbox(firstnameField, AppConfigs.ERROR_FIRSTNAME_PATTERN_CHAR);
               
            } else if (newValue.matches(AppConfigs.PATTERN_NUMBER)) {
                MessageErrorField.ErrorFieldHbox(firstnameField, AppConfigs.ERROR_FIRSTNAME_PATTERN_NUMBER);
                
            } else if (newValue.length() > AppConfigs.LENGHT_OF_FIRSTNAME) {
                MessageErrorField.ErrorFieldHbox(firstnameField, AppConfigs.ERROR_FIRSTNAME_OUT_OF_LENGHT);
                
            } else {
                MessageErrorField.ErrorFieldHboxOff(firstnameField);
            }
        });
        
        // check lastname 
        this.lastnameField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.isBlank()) {
                MessageErrorField.ErrorFieldHbox(lastnameField, AppConfigs.NULL_LASTNAME);
            } 
            else if (newValue.matches(AppConfigs.PATTERN_SPECIAL_CHAR)) {
                MessageErrorField.ErrorFieldHbox(lastnameField, AppConfigs.ERROR_LASTNAME_PATTERN_CHAR);
            } 
            else if (newValue.matches(AppConfigs.PATTERN_NUMBER)) {
                MessageErrorField.ErrorFieldHbox(lastnameField, AppConfigs.ERROR_LASTNAME_PATTERN_NUMBER);
            } 
            else if (newValue.length() > AppConfigs.LENGHT_OF_LASTNAME) {
                MessageErrorField.ErrorFieldHbox(lastnameField, AppConfigs.ERROR_LASTNAME_OUT_OF_LENGHT);
                
            } 
            else {
                MessageErrorField.ErrorFieldHboxOff(lastnameField);
            }
        });
        
        // check email
        this.emailField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.isBlank() || !newValue.matches(AppConfigs.PATTERN_EMAIL) || newValue.matches(AppConfigs.PATTERN_SPACE)) {
                MessageErrorField.ErrorFieldHbox(emailField, AppConfigs.ERROR_EMAIL_PATTERN);
            } 
            else {
                MessageErrorField.ErrorFieldHboxOff(emailField);
            }
        });
    }
    
    public void setUserData(String firstname, String lastname, String email, String avatarUrl, String role, Date createAt) {
        this.firstnameField.setText(firstname);
        this.lastnameField.setText(lastname);
        this.emailField.setText(email);
        this.avatarPath = avatarUrl;
        if (avatarPath != null) {
            Image img = new Image(avatarPath);
            this.avatar.setImage(img);
        }
            
    }
    
    /**
     * Upload avatar
     */
    public void handleUploadAvatar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn file ảnh");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(uploadAvatarButton.getScene().getWindow());
        if (selectedFile != null) {
            try {
                // Lấy Cloudinary từ CloudinaryConfig
                Cloudinary cloudinary = CloudinaryConfig.getCloudinary();

                // Upload ảnh lên Cloudinary
                Map uploadResult = cloudinary.uploader().upload(selectedFile, ObjectUtils.emptyMap());
                avatarPath = (String) uploadResult.get("secure_url");

                // Hiển thị ảnh lên ImageView
                Image image = new Image(avatarPath);
                avatar.setImage(image);

            } catch (IllegalStateException e) {
                // Xử lý lỗi nếu thiếu thông tin Cloudinary trong file .env
                MessageBox.getAlert("Không thể kết nối đến Cloudinary. Vui lòng kiểm tra file .env!", Alert.AlertType.ERROR).show();

            } catch (IOException e) {
                // Xử lý lỗi khi upload ảnh
                MessageBox.getAlert("Không thể upload ảnh lên Cloudinary: ", Alert.AlertType.ERROR).show();

            } catch (Exception e) {
                // Xử lý các ngoại lệ không mong muốn khác
                MessageBox.getAlert("Đã xảy ra lỗi: ", Alert.AlertType.ERROR).show();
                
            }
        }
    }
  
    public void goToAccountPage(ActionEvent e) throws IOException {
        String firstname = this.firstnameField.getText().trim();
        String lastname = this.lastnameField.getText().trim();
        String email = this.emailField.getText().trim();
        
        System.out.println(firstname);
        
        String role = "User";
        Date createAt = new Date();
        
        boolean hasError = false;
        
        // check firstname
        if (firstname.isBlank()) {
            MessageErrorField.ErrorFieldHbox(firstnameField, AppConfigs.NULL_FIRSTNAME);
            hasError = true;
        } else if (firstname.matches(AppConfigs.PATTERN_SPECIAL_CHAR)) {
            MessageErrorField.ErrorFieldHbox(firstnameField, AppConfigs.ERROR_FIRSTNAME_PATTERN_CHAR);
            hasError = true;
        } else if (firstname.matches(AppConfigs.PATTERN_NUMBER)) {
            MessageErrorField.ErrorFieldHbox(firstnameField, AppConfigs.ERROR_FIRSTNAME_PATTERN_NUMBER);
            hasError = true;
        } else if (firstname.length() > AppConfigs.LENGHT_OF_FIRSTNAME) {
            MessageErrorField.ErrorFieldHbox(firstnameField, AppConfigs.ERROR_FIRSTNAME_OUT_OF_LENGHT);
            hasError = true;
        } else {
            MessageErrorField.ErrorFieldHboxOff(firstnameField);
        }

        // check lastname
        if (lastname.isBlank()) {
            MessageErrorField.ErrorFieldHbox(lastnameField, AppConfigs.NULL_LASTNAME);
            hasError = true;
        } else if (lastname.matches(AppConfigs.PATTERN_SPECIAL_CHAR)) {
            MessageErrorField.ErrorFieldHbox(lastnameField, AppConfigs.ERROR_LASTNAME_PATTERN_CHAR);
            hasError = true;
        } else if (lastname.matches(AppConfigs.PATTERN_NUMBER)) {
            MessageErrorField.ErrorFieldHbox(lastnameField, AppConfigs.ERROR_LASTNAME_PATTERN_NUMBER);
            hasError = true;
        } else if (lastname.length() > AppConfigs.LENGHT_OF_LASTNAME) {
            MessageErrorField.ErrorFieldHbox(lastnameField, AppConfigs.ERROR_LASTNAME_OUT_OF_LENGHT);
            hasError = true;
        } 
        else {
            MessageErrorField.ErrorFieldHboxOff(lastnameField);
        }
        
        // check email
        if (email.isBlank() || !email.matches(AppConfigs.PATTERN_EMAIL) || email.matches(AppConfigs.PATTERN_SPACE)) {
            MessageErrorField.ErrorFieldHbox(emailField, AppConfigs.ERROR_EMAIL_PATTERN);
            hasError = true;
        } 
        else {
            MessageErrorField.ErrorFieldHboxOff(emailField);
        }
        
        if (!hasError) {
            try {
                // chuyển dữ liệu qua trang account 
                FXMLLoader loader = new FXMLLoader(getClass().getResource("registerUserAccountPage.fxml"));
                Parent root = loader.load();

                // chuyển props qua page khác.
                RegisterUserAccountPageController accountPageController = loader.getController();
                accountPageController.setUserData(firstname, lastname, email, avatarPath, role, createAt);

                // chuyển trang qua account 
                Stage stage = (Stage) nextPageButton.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException ex) {
                String message = "Lỗi không thể chuyển sang trang kế tiếp !";
                MessageBox.getAlert(message, Alert.AlertType.WARNING).show();
            }
        }
    }
    
    public void goBack() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginUser.fxml"));
            Parent root = loader.load();

            // chuyển trang qua account 
            Stage stage = (Stage) firstnameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang đăng ký !";
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }
}
