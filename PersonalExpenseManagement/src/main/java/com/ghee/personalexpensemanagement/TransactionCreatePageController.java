/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ghee.personalexpensemanagement;

import com.ghee.pojo.Budget;
import com.ghee.pojo.Category;
import com.ghee.pojo.Transaction;
import com.ghee.pojo.Users;
import com.ghee.pojo.Wallet;
import com.ghee.services.BudgetServices;
import com.ghee.services.CategoryServices;
import com.ghee.services.TransactionServices;
import com.ghee.utils.DatePickerUtils;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author giahu
 */
public class TransactionCreatePageController implements Initializable {

    @FXML private ComboBox<Wallet> cbWallets;
    @FXML private TextField txtTarget;
    @FXML private ComboBox<Category> cbCategories;
    @FXML private TextArea txtDescription;
    @FXML private DatePicker dpTransactionDate;
    @FXML private Button btnSave;
    
    private static CategoryServices categoryServices = new CategoryServices();
    private static TransactionServices transactionServices = new TransactionServices();
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Users currentUser = Utils.getCurrentUser();
            if (currentUser != null) {
                // hiển thị thông tin categories ở combobox
                try {
                    List<Category> cates = categoryServices.getCategoriesByUserId(currentUser.getId());
                    this.cbCategories.setItems(FXCollections.observableArrayList(cates));
                    
                    this.dpTransactionDate.setValue(LocalDate.now());
                    DatePickerUtils.setVietnameseDateFormat(this.dpTransactionDate);
                    
                    
                } catch (SQLException ex) {
                    System.err.println("ERROR: " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            Utils.getAlert("Lỗi database. ", Alert.AlertType.ERROR).showAndWait();
            System.err.println("Chi tiết lỗi: " + ex.getMessage());
        }
    }
    
    public void addTransaction(ActionEvent e) {
        try {
            Category categoryId = this.cbCategories.getSelectionModel().getSelectedItem();
            Users currentUser = Utils.getCurrentUser();
            
            Double target = Double.valueOf(this.txtTarget.getText());
            
            Date transactionDate = java.sql.Date.valueOf(this.dpTransactionDate.getValue());
            Date createdAt = new Date();
            DatePickerUtils.setVietnameseDateFormat(createdAt);
            
            String description = this.txtDescription.getText();
            
            // Transaction transaction = new Transaction(target, transactionDate, description, createdAt);
            
        } catch (NumberFormatException numberFormatException) {
            Utils.getAlert("Vui lòng điền thông tin ngân sách là số!", Alert.AlertType.ERROR).showAndWait();
        }
    }
    
}
