/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ghee.personalexpensemanagement;

import com.ghee.config.AppConfigs;
import com.ghee.pojo.Category;
import com.ghee.pojo.Users;
import com.ghee.services.CategoryServices;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author giahu
 */
public class CategoryCreatePageController implements Initializable {

    @FXML private TextField txtName;
    
    @FXML private RadioButton rdoThu;
    @FXML private RadioButton rdoChi;

    @FXML private Button btnCancel;
    @FXML private Button btnSave;
    
    private final CategoryServices categoryService = new CategoryServices();
    
    private String parentController;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rdoChi.setSelected(true);
    }    
    
    public void setParentController (String parentController) {
        this.parentController = parentController;
    }
    
    public void addCategory() {
        try {
            Users currentUser = ManageUser.getCurrentUser();
            
            if (currentUser != null) {
                String name = txtName.getText().trim();
                
                if (name.equals("")) {
                    // MessageBox.getAlert(AppConfigs.ERROR_NOT_ENOUGH_INFORMATION, Alert.AlertType.WARNING).showAndWait();
                    MessageErrorField.ErrorFieldHbox(txtName, AppConfigs.ERROR_NOT_ENOUGH_INFORMATION);
                    return ;
                } else {
                    MessageErrorField.ErrorFieldHboxOff(txtName);
                }
                
                String type = rdoChi.isSelected() ? "Chi" : "Thu";
                
                Category category = new Category(currentUser, type, name);
                
                var results = categoryService.createCategory(category);
                
                boolean success = (boolean) results.get("success");
                String msg = (String) results.get("message");

                if (success) {
                    MessageBox.getAlert("Tạo danh mục thành công!", Alert.AlertType.CONFIRMATION).showAndWait();
                    goBack();
                } else {
                    MessageBox.getAlert(msg, Alert.AlertType.WARNING).showAndWait();
                }
                
            }
            
        } catch (SQLException ex) {
            return;
        }
    }
    
    public void goBack() {
        if (this.parentController.equals("budgetCreatePage")) {
            goToBudgetPage();
        }
        goToTransactionPage();
    }
    
    public void goToBudgetPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("budgetcreatepage.fxml"));
            Parent root = loader.load();

            // chuyển trang qua account 
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua tạo ngân sách !";
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }
    
    public void goToTransactionPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transactioncreatepage.fxml"));
            Parent root = loader.load();

            // chuyển trang qua account 
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua tạo giao dịch !";
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }
    
}
