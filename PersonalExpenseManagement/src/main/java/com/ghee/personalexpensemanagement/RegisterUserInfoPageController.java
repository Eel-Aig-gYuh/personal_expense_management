/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ghee.personalexpensemanagement;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ghee.config.AppConfigs;
import com.ghee.config.CloudinaryConfig;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
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

    @FXML
    private TextField lastnameField;
    @FXML
    private TextField firstnameField;
    @FXML 
    private TextField emailField;
    
    @FXML
    private Button uploadAvatarButton;
    @FXML
    private Button nextPageButton;
    
    @FXML 
    private ImageView avatar;
    
    private String avatarPath;
    private Cloudinary cloudinary;
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
                Utils.getAlert("Không thể kết nối đến Cloudinary. Vui lòng kiểm tra file .env!", Alert.AlertType.ERROR).show();

            } catch (IOException e) {
                // Xử lý lỗi khi upload ảnh
                Utils.getAlert("Không thể upload ảnh lên Cloudinary: ", Alert.AlertType.ERROR).show();

            } catch (Exception e) {
                // Xử lý các ngoại lệ không mong muốn khác
                Utils.getAlert("Đã xảy ra lỗi: ", Alert.AlertType.ERROR).show();
                
            }
        }
    }
    
  
    public void goToAccountPage() throws IOException {
        String firstname = this.firstnameField.getText();
        String lastname = this.lastnameField.getText();
        String email = this.emailField.getText();
        
        String role = "User";
        Date createAt = new Date();
        
        if (firstname.trim().equals("") || lastname.trim().equals("") || email.trim().equals("")) {
            Utils.getAlert(AppConfigs.ERROR_NOT_ENOUGH_INFORMATION, Alert.AlertType.WARNING).showAndWait();
            return ;
        }
        
        if (!email.contains("@")) {
            Utils.getAlert(AppConfigs.ERROR_EMAIL_PATTERN, Alert.AlertType.WARNING).showAndWait();
            return ;
        }
        
        try {
            // chuyển dữ liệu qua trang account 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("registerUserAccountPage.fxml"));
            Parent root = loader.load();

            RegisterUserAccountPageController accountPageController = loader.getController();

            accountPageController.setUserData(firstname, lastname, email, avatarPath, role, createAt);

            // chuyển trang qua account 
            Stage stage = (Stage) nextPageButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex){
            String message = "Lỗi không thể chuyển sang trang kế tiếp !";
            Utils.getAlert(message, Alert.AlertType.WARNING).show();
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
            Utils.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }
}
