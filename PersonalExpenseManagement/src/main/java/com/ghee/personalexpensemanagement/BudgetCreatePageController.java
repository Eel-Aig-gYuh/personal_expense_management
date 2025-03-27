/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ghee.personalexpensemanagement;

import com.ghee.pojo.Budget;
import com.ghee.pojo.Category;
import com.ghee.pojo.Users;
import com.ghee.services.BudgetServices;
import com.ghee.services.CategoryServices;
import com.ghee.utils.DatePickerUtils;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author giahu
 */
public class BudgetCreatePageController implements Initializable {

    @FXML private Button btnCancel;
    
    @FXML private ComboBox<Category> cbCategories;
    
    @FXML private TextField txtTarget;
    
    @FXML private DatePicker dpStartDate;
    @FXML private DatePicker dpEndDate;
    
    @FXML private Button btnSave;

    private static CategoryServices categoryServices = new CategoryServices();
    private static BudgetServices budgetServices = new BudgetServices();

    /**
     * Initializes the controller class.
     *
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

                    DatePickerUtils.disablePastDates(this.dpStartDate);
                    DatePickerUtils.disablePastDates(this.dpEndDate);

                    DatePickerUtils.restrictEndDate(this.dpStartDate, this.dpEndDate);

                    DatePickerUtils.setVietnameseDateFormat(this.dpStartDate);
                    DatePickerUtils.setVietnameseDateFormat(this.dpEndDate);

                    this.txtTarget.setText("00.0");
                } catch (SQLException ex) {
                    System.err.println("ERROR: " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            Utils.getAlert("Lỗi database. ", Alert.AlertType.ERROR).showAndWait();
            System.err.println("Chi tiết lỗi: " + ex.getMessage());
        }
    }

    public void addBudget(ActionEvent e) {
        try {
            Category categoryId = this.cbCategories.getSelectionModel().getSelectedItem();
            Users currentUser = Utils.getCurrentUser();

            Double target = Double.valueOf(this.txtTarget.getText());
            Double amount = 0.00;

            Date startDate = java.sql.Date.valueOf(this.dpStartDate.getValue());
            Date endDate = java.sql.Date.valueOf(this.dpEndDate.getValue());
            Date createdAt = new Date();

            DatePickerUtils.setVietnameseDateFormat(createdAt);

            Budget budget = new Budget(categoryId, currentUser, amount, target, startDate, endDate, createdAt);

            var success = budgetServices.createBudget(budget);

            if (success) {
                Utils.getAlert("Tạo ngân sách thành công!", Alert.AlertType.CONFIRMATION).showAndWait();
            }
            else {
                Utils.getAlert("Tạo ngân sách không thành công!", Alert.AlertType.WARNING).showAndWait();
            }

        } catch (NumberFormatException numberFormatException) {
            Utils.getAlert("Vui lòng điền thông tin ngân sách là số!", Alert.AlertType.ERROR).showAndWait();
        }
    }

}
