/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ghee.personalexpensemanagement;

import com.ghee.config.AppConfigs;
import com.ghee.pojo.Budget;
import com.ghee.pojo.Category;
import com.ghee.pojo.Users;
import com.ghee.services.BudgetServices;
import com.ghee.services.CategoryServices;
import com.ghee.formatter.DatePickerUtils;
import com.ghee.utils.ManageUser;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author giahu
 */
public class BudgetCreatePageController implements Initializable {

    @FXML private Button btnCancel;
    @FXML private Button btnSave;

    @FXML private ComboBox<Object> cbCategories;

    @FXML private TextField txtTarget;

    @FXML private DatePicker dpStartDate;
    @FXML private DatePicker dpEndDate;

    private static CategoryServices categoryServices = new CategoryServices();
    private static BudgetServices budgetServices = new BudgetServices();

    private ObservableList<Object> categoryItems;
    
    private String parentController;
    private Budget selectedBudget;
    
    public void setSelectedBudget(Budget b) {
        this.selectedBudget = b;
        loadSelectedBudget();
    }

    /**
     * Initializes the controller class.
     *
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
        
        this.dpEndDate.setEditable(false);
        this.dpStartDate.setEditable(false);
        
        DatePickerUtils.disablePastDates(this.dpStartDate);
        DatePickerUtils.disablePastDates(this.dpEndDate);

        DatePickerUtils.restrictEndDate(this.dpStartDate, this.dpEndDate);

        DatePickerUtils.setVietnameseDateFormat(this.dpStartDate);
        DatePickerUtils.setVietnameseDateFormat(this.dpEndDate);
    }
    
    public void loadSelectedBudget() {
        this.cbCategories.setValue(this.selectedBudget.getCategoryId());
        
        this.dpStartDate.setValue(LocalDate.parse(this.selectedBudget.getStartDate().toString()));
        this.dpEndDate.setValue(LocalDate.parse(this.selectedBudget.getEndDate().toString()));
        
        this.txtTarget.setText(String.valueOf(this.selectedBudget.getTarget()));
        this.btnSave.setText("Cập nhật");
    }
    
    public void setParentController(String parentController){
        this.parentController = parentController;
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
     * thêm ngân sách mới.
     *
     * @param e
     */
    public void addBudget(ActionEvent e) throws SQLException {
        try {
            Category categoryId = (Category) this.cbCategories.getSelectionModel().getSelectedItem();
            
            if (categoryId == null) {
                MessageBox.getAlert(AppConfigs.ERROR_CATEGORY_IS_NULL, Alert.AlertType.WARNING).showAndWait();
                return; 
            }
            
            Users currentUser = ManageUser.getCurrentUser();

            Double target = Double.valueOf(this.txtTarget.getText());
            
            if (target.isNaN() || target <= 0) {
                MessageBox.getAlert(AppConfigs.ERROR_TARGET_IS_NEGATIVE, Alert.AlertType.WARNING).showAndWait();
                return; 
            } else if (target <= 100000) {
                MessageBox.getAlert(AppConfigs.ERROR_TARGET_LESS_THAN_MIN, Alert.AlertType.WARNING).showAndWait();
                return;
            }
            
            Double amount = 0.00;

            Date startDate = java.sql.Date.valueOf(this.dpStartDate.getValue());
            Date endDate = java.sql.Date.valueOf(this.dpEndDate.getValue());
            Date createdAt = new Date();
            
            if (startDate == null || endDate == null || startDate.after(endDate)) {
                MessageBox.getAlert(AppConfigs.ERROR_DATE_IS_NOT_CORRECT, Alert.AlertType.WARNING).showAndWait();
                return;
            }

            DatePickerUtils.setVietnameseDateFormat(createdAt);

            if (this.btnSave.getText().equals("Cập nhật")) {
                // Cập nhật ngân sách.
                Budget budget = new Budget(this.selectedBudget.getId(), categoryId, target, startDate, endDate);
            
                var results = budgetServices.updateBudget(budget);
            
                boolean success = (boolean) results.get("success");
                String msg = (String) results.get("message");
            
                MessageBox.getAlert(msg, Alert.AlertType.CONFIRMATION).showAndWait();
                if (success)
                    goBack();
            }
            else {
                // tạo ngân sách;
                Budget budget = new Budget(categoryId, currentUser, amount, target, startDate, endDate, createdAt);
                
                var results = budgetServices.createBudget(budget);
            
                boolean success = (boolean) results.get("success");
                String msg = (String) results.get("message");
                
                MessageBox.getAlert(msg, Alert.AlertType.CONFIRMATION).showAndWait();
                if (success) {
                    goBack();
                }
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
            categoryCreatePageController.setParentController("budgetCreatePage");

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("budgetHomePage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang chủ !";
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }
}
