/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ghee.personalexpensemanagement;

import com.ghee.personalexpensemanagement.CategoryCreatePageController;
import com.ghee.personalexpensemanagement.Utils;
import com.ghee.pojo.Category;
import com.ghee.pojo.Transaction;
import com.ghee.pojo.Users;
import com.ghee.pojo.Wallet;
import com.ghee.services.CategoryServices;
import com.ghee.services.TransactionServices;
import com.ghee.services.WalletServices;
import com.ghee.formatter.DatePickerUtils;
import com.ghee.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author giahu
 */
public class TransactionCreatePageController implements Initializable {

    @FXML private ComboBox<Wallet> cbWallets;
    @FXML private TextField txtTarget;
    @FXML private ComboBox<Object> cbCategories;
    @FXML private TextArea txtDescription;
    @FXML private DatePicker dpTransactionDate;
    @FXML private Button btnSave;
    
    private static CategoryServices categoryServices = new CategoryServices();
    private static WalletServices walletServices = new WalletServices();
    private static TransactionServices transactionServices = new TransactionServices();
    
     private ObservableList<Object> categoryItems;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.categoryItems = FXCollections.observableArrayList();
        
        loadCategories();
        
        cbCategories.setOnAction(event -> {
            Object selectedItem = cbCategories.getSelectionModel().getSelectedItem();
            if ("Thêm danh mục".equals(selectedItem)) {
                // Mở cửa sổ createCategory.fxml
                goToCreateCategoryPage();
            }
        });
        
        this.dpTransactionDate.setValue(LocalDate.now());
        DatePickerUtils.setVietnameseDateFormat(this.dpTransactionDate);
    }
    
    /**
     * Hiển thị category ở combobox
     */
    public void loadCategories() {
        try {
            Users currentUser = Utils.getCurrentUser();

            if (currentUser != null) {
                List<Category> cates = categoryServices.getCategoriesByUserId(currentUser.getId());

                this.categoryItems.clear();
                this.categoryItems.add("Thêm danh mục");
                if (!cates.isEmpty()) {
                    cates.forEach(c -> this.categoryItems.add(c));
                }
                this.cbCategories.setItems(categoryItems);
                
                this.cbCategories.setCellFactory(params -> new ListCell<>() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else if (item instanceof Category) {
                            setText(((Category) item).getName());
                        } else {
                            setText(item.toString());
                        }
                    }
                });
                
                this.cbCategories.setButtonCell(new ListCell<>() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else if (item instanceof Category) {
                            setText(((Category) item).getName());
                        } else {
                            setText(item.toString());
                        }
                    }
                });
            }

        } catch (SQLException ex) {
            System.err.println("Chi tiết lỗi: " + ex.getMessage());
        }
    } 
    
    public void addTransaction(ActionEvent e) throws SQLException {
        try {
            Users currentUser = Utils.getCurrentUser();
            Category categoryId = (Category) this.cbCategories.getSelectionModel().getSelectedItem();
            Wallet walletId = walletServices.getWalletById(currentUser.getId());
            
            Double amount = Double.valueOf(this.txtTarget.getText());
            
            Date transactionDate = java.sql.Date.valueOf(this.dpTransactionDate.getValue());
            Date createdAt = new Date();
            DatePickerUtils.setVietnameseDateFormat(createdAt);
            
            String description = this.txtDescription.getText();
            
            Transaction transaction = new Transaction(currentUser, categoryId, walletId, amount, transactionDate, description, categoryId.getType(), createdAt);
            
            try {
                var results = transactionServices.addTransaction(transaction);
                
                boolean success = (boolean) results.get("success");
                String msg = (String) results.get("message");
                
                if (success) {
                    MessageBox.getAlert("Thêm giao dịch thành công !", Alert.AlertType.CONFIRMATION).showAndWait();
                    goToHomePage();
                }
                else {
                    MessageBox.getAlert("Thêm giao dịch không thành công ! " + msg, Alert.AlertType.WARNING).showAndWait();
                }
                
            } catch (SQLException ex) {
                MessageBox.getAlert("Thêm không thành công !", Alert.AlertType.ERROR).showAndWait();
                System.err.println("ERROR: " + ex.getMessage());
            }
            
        } catch (NumberFormatException numberFormatException) {
            MessageBox.getAlert("Vui lòng điền thông tin ngân sách là số!", Alert.AlertType.ERROR).showAndWait();
        }
    }
    
    public void goToCreateCategoryPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("categoryCreatePage.fxml"));
            Parent root = loader.load();
            
            CategoryCreatePageController categoryCreatePageController = loader.getController();
            categoryCreatePageController.setParentController("transactionCreatePage");

            // chuyển trang qua account 
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.setScene(new Scene(root));
            
            loadCategories();
        } catch (IOException ex) {
            String message = "Không thể chuyển qua giao diện thêm danh mục !";
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }

    
    public void goToHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage.fxml"));
            Parent root = loader.load();

            // chuyển trang qua account 
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang chủ !";
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }
    
}
