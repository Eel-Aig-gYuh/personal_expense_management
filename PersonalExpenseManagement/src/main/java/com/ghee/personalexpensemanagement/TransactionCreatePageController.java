/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ghee.personalexpensemanagement;

import com.ghee.config.AppConfigs;
import com.ghee.pojo.Category;
import com.ghee.pojo.Transaction;
import com.ghee.pojo.Users;
import com.ghee.pojo.Wallet;
import com.ghee.services.CategoryServices;
import com.ghee.services.TransactionServices;
import com.ghee.services.WalletServices;
import com.ghee.formatter.DatePickerUtils;
import com.ghee.utils.ManageUser;
import com.ghee.utils.MessageBox;
import com.ghee.utils.MessageErrorField;
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
import javafx.scene.control.Label;
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
    @FXML private ComboBox<Object> cbCategories;
    
    @FXML private TextField txtTarget;
    @FXML private TextArea txtDescription;
    
    @FXML private DatePicker dpTransactionDate;
    
    @FXML private Button btnSave;
    
    @FXML private Label lblTitle;
    
    private static CategoryServices categoryServices = new CategoryServices();
    private static WalletServices walletServices = new WalletServices();
    private static TransactionServices transactionServices = new TransactionServices();
    
    private ObservableList<Object> categoryItems;
    
    private Transaction selectedTransaction;
    
    public void setSelectedTransaction(Transaction t) {
        this.selectedTransaction = t;
        
        loadSelectedTransaction();
    }
    
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
        
        DatePickerUtils.disableFutureDates(this.dpTransactionDate);
        DatePickerUtils.setVietnameseDateFormat(this.dpTransactionDate);
    }
    
    public void loadSelectedTransaction () {
        if (this.selectedTransaction != null) {
            btnSave.setText(String.valueOf("Cập nhật"));
            
            if (btnSave.getText().equals("Cập nhật"))
                lblTitle.setText("CẬP NHẬT GIAO DỊCH");
            else 
                lblTitle.setText("THÊM GIAO DỊCH");
            
            txtTarget.setText(String.valueOf(this.selectedTransaction.getAmount()));
            txtDescription.setText(this.selectedTransaction.getDescription());
            
            dpTransactionDate.setValue(LocalDate.parse(this.selectedTransaction.getTransactionDate().toString()));
            cbCategories.setValue(this.selectedTransaction.getCategoryId());
        }
    }
    
    /**
     * Hiển thị category ở combobox
     */
    public void loadCategories() {
        try {
            Users currentUser = ManageUser.getCurrentUser();

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
    
    /**
     * Xử lý sự kiện thêm giao dịch.
     * @param e
     * @throws SQLException 
     */
    public void addTransaction(ActionEvent e) throws SQLException {
        if (btnSave.getText().equals("Cập nhật")) {
            updateTransaction();
            return ;
        }
        try {
            Users currentUser = ManageUser.getCurrentUser();
            Category categoryId = (Category) this.cbCategories.getSelectionModel().getSelectedItem();
            Wallet walletId = walletServices.getWalletById(currentUser.getId());
            
            Double amount = Double.valueOf(this.txtTarget.getText());
            
            Date transactionDate = java.sql.Date.valueOf(this.dpTransactionDate.getValue());
            Date createdAt = new Date();
            DatePickerUtils.setVietnameseDateFormat(createdAt);
            
            String description = this.txtDescription.getText();
            if (description.length() >= 255 ) {
                MessageBox.getAlert(AppConfigs.ERROR_LENGHT_DESCRIPTION, Alert.AlertType.ERROR).showAndWait();
                return ;
            }
            
            Transaction transaction = new Transaction(currentUser, categoryId, walletId, amount, transactionDate, description, createdAt);
            
            try {
                var results = transactionServices.addTransaction(transaction);
                
                boolean success = (boolean) results.get("success");
                String msg = (String) results.get("message");
                
                if (success) {
                    MessageBox.getAlert(msg, Alert.AlertType.CONFIRMATION).showAndWait();
                    goBack();
                }
                else {
                    MessageBox.getAlert(msg, Alert.AlertType.WARNING).showAndWait();
                }
                
            } catch (SQLException ex) {
                MessageBox.getAlert("Thêm không thành công !", Alert.AlertType.ERROR).showAndWait();
                System.err.println("ERROR: " + ex.getMessage());
            }
            
        } catch (NumberFormatException numberFormatException) {
            MessageBox.getAlert("Vui lòng điền thông tin ngân sách là số!", Alert.AlertType.ERROR).showAndWait();
        }
    }
    
    /** 
     * Xử lý sự kiện cập nhật giao dịch.
     * @throws java.sql.SQLException
     */
    public void updateTransaction() throws SQLException {
        try {
            Users currentUser = ManageUser.getCurrentUser();
            Category categoryId = (Category) this.cbCategories.getSelectionModel().getSelectedItem();
            Double amount = Double.valueOf(this.txtTarget.getText());
            
            if (this.txtTarget.getText().trim().equals("")) {
                MessageErrorField.ErrorFieldHbox(txtTarget, AppConfigs.ERROR_AMOUNT);
                return;
            } else {
                MessageErrorField.ErrorFieldHboxOff(txtTarget);
            }
            
            Date transactionDate = java.sql.Date.valueOf(this.dpTransactionDate.getValue());
            
            String description = this.txtDescription.getText();
            if (description.length() >= 255 ) {
                // MessageBox.getAlert(AppConfigs.ERROR_LENGHT_DESCRIPTION, Alert.AlertType.ERROR).showAndWait();
                MessageErrorField.ErrorFieldHbox(txtDescription, AppConfigs.ERROR_LENGHT_DESCRIPTION);
                return ;
            } else {
                MessageErrorField.ErrorFieldHboxOff(txtDescription);
            }
            
            // chuẩn bị dữ liệu.
            Transaction transaction = new Transaction();
            transaction.setId(this.selectedTransaction.getId());
            transaction.setUserId(currentUser);
            transaction.setCategoryId(categoryId);
            transaction.setWalletId(walletServices.getWalletById(currentUser.getId()));
            transaction.setAmount(amount);
            transaction.setTransactionDate(transactionDate);
            transaction.setDescription(description);
            
            try {
                var results = transactionServices.updateTransaction(transaction);
                
                boolean success = (boolean) results.get("success");
                String msg = (String) results.get("message");
                
                MessageBox.getAlert(msg, Alert.AlertType.CONFIRMATION).showAndWait();
                if (success) {
                    goBack();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        } catch(NumberFormatException ex) {
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

    
    public void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transactionHomePage.fxml"));
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
