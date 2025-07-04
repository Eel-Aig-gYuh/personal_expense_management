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
import com.ghee.utils.MessageErrorField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
    private boolean isFormatting; // bo dem
    
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
        this.setCategoryItems(FXCollections.observableArrayList());
        
        getCbCategories().setOnAction(event -> {
            Object selectedItem = getCbCategories().getSelectionModel().getSelectedItem();
            if ("Thêm danh mục".equals(selectedItem)) {
                // Mở cửa sổ createCategory.fxml
                goToCreateCategoryPage();
            }
        });
  
        this.getDpEndDate().setEditable(false);
        this.getDpStartDate().setEditable(false);
        
        DatePickerUtils.disablePastDates(this.getDpStartDate());
        DatePickerUtils.disablePastDates(this.getDpEndDate());

        DatePickerUtils.restrictEndDate(this.getDpStartDate(), this.getDpEndDate());

        DatePickerUtils.setVietnameseDateFormat(this.getDpStartDate());
        DatePickerUtils.setVietnameseDateFormat(this.getDpEndDate());
        
        loadCategories();
    }
    
    public void loadSelectedBudget() {
        this.getCbCategories().setValue(this.selectedBudget.getCategoryId());
        
        this.getDpStartDate().setValue(LocalDate.parse(this.selectedBudget.getStartDate().toString()));
        this.getDpEndDate().setValue(LocalDate.parse(this.selectedBudget.getEndDate().toString()));
        
        this.getTxtTarget().setText(String.valueOf(this.selectedBudget.getTarget()));
        this.getBtnSave().setText("Cập nhật");
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
                List<Category> cates = getCategoryServices().getCategoriesByUserId(currentUser.getId());

                this.getCategoryItems().clear();
                this.getCategoryItems().add("Thêm danh mục");
                if (!cates.isEmpty()) {
                    cates.forEach(c -> this.getCategoryItems().add(c));
                }
                this.getCbCategories().setItems(getCategoryItems());

                this.getCbCategories().setCellFactory(params -> new ListCell<>() {
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

                this.getCbCategories().setButtonCell(new ListCell<>() {
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
    
    public void deleteCategory(Category category) {
        // System.err.println("lasdk");
    }
    
    public void updateCategory(Category category) {
        
    }

    /**
     * thêm ngân sách mới.
     *
     * @param e
     * @throws java.sql.SQLException
     */
    public void addBudget(ActionEvent e) throws SQLException {
        try {
            Category categoryId = (Category) this.getCbCategories().getSelectionModel().getSelectedItem();
            
            if (categoryId == null) {
                MessageBox.getAlert(AppConfigs.ERROR_CATEGORY_IS_NULL, Alert.AlertType.WARNING).showAndWait();
                return; 
            }
            
            Users currentUser = ManageUser.getCurrentUser();

            Double target = Double.valueOf(this.getTxtTarget().getText());
            
            if (target.isNaN() || target <= 0) {
                // MessageBox.getAlert(AppConfigs.ERROR_TARGET_IS_NEGATIVE, Alert.AlertType.WARNING).showAndWait();
                MessageErrorField.ErrorFieldHbox(getTxtTarget(), AppConfigs.ERROR_TARGET_IS_NEGATIVE);
                return; 
            } else if (target < AppConfigs.MIN_TARGET) {
                // MessageBox.getAlert(AppConfigs.ERROR_TARGET_LESS_THAN_MIN, Alert.AlertType.WARNING).showAndWait();
                MessageErrorField.ErrorFieldHbox(getTxtTarget(), AppConfigs.ERROR_TARGET_LESS_THAN_MIN);
                return;
            } else if (target > AppConfigs.MAX_TARGET) {
                // MessageBox.getAlert(AppConfigs.ERROR_TARGET_LESS_THAN_MIN, Alert.AlertType.WARNING).showAndWait();
                MessageErrorField.ErrorFieldHbox(getTxtTarget(), AppConfigs.ERROR_TARGET_LESS_THAN_MAX);
                return; 
            }
            else {
                MessageErrorField.ErrorFieldHboxOff(getTxtTarget());
            }
            
            Double amount = 0.00;

            Date startDate = java.sql.Date.valueOf(this.getDpStartDate().getValue());
            Date endDate = java.sql.Date.valueOf(this.getDpEndDate().getValue());
            Date createdAt = new Date();
            
            if (startDate == null || endDate == null || startDate.after(endDate)) {
                MessageBox.getAlert(AppConfigs.ERROR_DATE_IS_NOT_CORRECT, Alert.AlertType.WARNING).showAndWait();
                return;
            }

            DatePickerUtils.setVietnameseDateFormat(createdAt);

            if (this.getBtnSave().getText().equals("Cập nhật")) {
                // Cập nhật ngân sách.
                Budget budget = new Budget(this.selectedBudget.getId(), categoryId, target, startDate, endDate);
            
                var results = getBudgetServices().updateBudget(budget);
            
                boolean success = (boolean) results.get("success");
                String msg = (String) results.get("message");
            
                MessageBox.getAlert(msg, Alert.AlertType.CONFIRMATION).showAndWait();
                if (success)
                    goBack();
            }
            else {
                // tạo ngân sách;
                System.out.println("tao ngan sach");
                
                Budget budget = new Budget(categoryId, currentUser, amount, target, startDate, endDate, createdAt);
                
                var results = getBudgetServices().createBudget(budget);
            
                boolean success = (boolean) results.get("success");
                String msg = (String) results.get("message");
                // System.out.println(msg);
                
                if (msg.startsWith("208")) {
                    MessageBox.getYesNoAlert("Đã tồn tại một ngân sách trong khoảng thời gian này, bạn có muốn cập nhật lại không?", Alert.AlertType.CONFIRMATION)
                            .showAndWait().ifPresent(res -> {
                                if (res == ButtonType.OK) {
                                    try {
                                        int idSameBudget = Integer.parseInt(msg.substring(msg.indexOf(":")+1));
                                        // System.out.println(idSameBudget);
                                        
                                        budget.setId(idSameBudget);
                                        
                                        var resultsUpdate = getBudgetServices().updateBudget(budget);
                                        
                                        Boolean successUpdate = (boolean) resultsUpdate.get("success");
                                        String msgUpdate = (String) resultsUpdate.get("message");
                                        
                                        MessageBox.getAlert(msgUpdate, Alert.AlertType.CONFIRMATION).showAndWait();
                                        if (successUpdate) {
                                            goBack();
                                        }
                                    } catch (SQLException ex) {
                                        Logger.getLogger(BudgetCreatePageController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });
                } else {
                    MessageBox.getAlert(msg, Alert.AlertType.CONFIRMATION).showAndWait();
                    if (success) {
                        goBack();
                    }
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
            Stage stage = (Stage) getBtnSave().getScene().getWindow();
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

            Stage stage = (Stage) getBtnSave().getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang chủ !";
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }

    /**
     * @return the btnCancel
     */
    public Button getBtnCancel() {
        return btnCancel;
    }

    /**
     * @param btnCancel the btnCancel to set
     */
    public void setBtnCancel(Button btnCancel) {
        this.btnCancel = btnCancel;
    }

    /**
     * @return the btnSave
     */
    public Button getBtnSave() {
        return btnSave;
    }

    /**
     * @param btnSave the btnSave to set
     */
    public void setBtnSave(Button btnSave) {
        this.btnSave = btnSave;
    }

    /**
     * @return the cbCategories
     */
    public ComboBox<Object> getCbCategories() {
        return cbCategories;
    }

    /**
     * @param cbCategories the cbCategories to set
     */
    public void setCbCategories(ComboBox<Object> cbCategories) {
        this.cbCategories = cbCategories;
    }

    /**
     * @return the txtTarget
     */
    public TextField getTxtTarget() {
        return txtTarget;
    }

    /**
     * @param txtTarget the txtTarget to set
     */
    public void setTxtTarget(TextField txtTarget) {
        this.txtTarget = txtTarget;
    }

    /**
     * @return the dpStartDate
     */
    public DatePicker getDpStartDate() {
        return dpStartDate;
    }

    /**
     * @param dpStartDate the dpStartDate to set
     */
    public void setDpStartDate(DatePicker dpStartDate) {
        this.dpStartDate = dpStartDate;
    }

    /**
     * @return the dpEndDate
     */
    public DatePicker getDpEndDate() {
        return dpEndDate;
    }

    /**
     * @param dpEndDate the dpEndDate to set
     */
    public void setDpEndDate(DatePicker dpEndDate) {
        this.dpEndDate = dpEndDate;
    }

    /**
     * @return the categoryServices
     */
    public static CategoryServices getCategoryServices() {
        return categoryServices;
    }

    /**
     * @param aCategoryServices the categoryServices to set
     */
    public static void setCategoryServices(CategoryServices aCategoryServices) {
        categoryServices = aCategoryServices;
    }

    /**
     * @return the budgetServices
     */
    public static BudgetServices getBudgetServices() {
        return budgetServices;
    }

    /**
     * @param aBudgetServices the budgetServices to set
     */
    public static void setBudgetServices(BudgetServices aBudgetServices) {
        budgetServices = aBudgetServices;
    }

    /**
     * @return the categoryItems
     */
    public ObservableList<Object> getCategoryItems() {
        return categoryItems;
    }

    /**
     * @param categoryItems the categoryItems to set
     */
    public void setCategoryItems(ObservableList<Object> categoryItems) {
        this.categoryItems = categoryItems;
    }
}
