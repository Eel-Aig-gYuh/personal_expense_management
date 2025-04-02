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
import com.ghee.utils.MoneyFormat;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author giahu
 */
public class BudgetHomePageController implements Initializable {

    @FXML TabPane tabPane;
    
    @FXML Label lblTotalAvailable;
    @FXML Label lblTotalBudget;
    @FXML Label lblTotalSpent;
    @FXML Label lblDayLeft;
    @FXML Button btnCreateBudget;
    
    @FXML ProgressBar progressBar;
    
    @FXML ListView<Budget> listViewBudgets;
    
    private final BudgetServices budgetServices = new BudgetServices();
    private final CategoryServices categoryServices = new CategoryServices();
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.lblDayLeft.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        // xử lý sự kiện thay đổi tab
        this.tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            loadBudgetsData();
        });
        
        loadBudgetsData();
    } 
    
    public void loadBudgetsData() {
        try {
            Users currentUser = Utils.getCurrentUser();
            
            if (currentUser == null) {
                Utils.getAlert("Không tìm thấy user hiện tại !", Alert.AlertType.ERROR).showAndWait();
                return;
            }
            
            LocalDate now = LocalDate.now();
            LocalDate startDate;
            LocalDate endDate;
            long daysLeft;
            
            String selectedTab = tabPane.getSelectionModel().getSelectedItem().getText();
            switch (selectedTab) {
                case "Tháng này": 
                    startDate = now.withDayOfMonth(1);
                    endDate = now.withDayOfMonth(now.lengthOfMonth());
                    daysLeft = ChronoUnit.DAYS.between(now, endDate);
                    lblDayLeft.setText(daysLeft + " ngày");
                    break;
                    
                case "Quý này": 
                    int quarter = (now.getMonthValue() - 1) / 3 + 1;
                    startDate = LocalDate.of(now.getYear(), (quarter -1) * 3 + 1, 1);
                    endDate = startDate.plusMonths(2).withDayOfMonth(startDate.plusMonths(2).lengthOfMonth());
                    daysLeft = ChronoUnit.DAYS.between(now, endDate);
                    lblDayLeft.setText(daysLeft + " ngày");
                    break;
                    
                case "Năm này": 
                    startDate = LocalDate.of(now.getYear(), 1, 1);
                    endDate = LocalDate.of(now.getYear(), 12, 31);
                    daysLeft = ChronoUnit.DAYS.between(now, endDate);
                    lblDayLeft.setText(daysLeft + " ngày");
                    break;
                    
                default: 
                    startDate = now.withDayOfMonth(1);
                    endDate = now.withDayOfMonth(now.lengthOfMonth());
            }
            
            double totalBudget = budgetServices.getTotalBudget(currentUser.getId(), startDate, endDate);
            double totalSpent = budgetServices.getTotalSpent(currentUser.getId(), startDate, endDate);
            double totalAvailabel = totalBudget-totalSpent;
            
            this.lblTotalAvailable.setText(MoneyFormat.moneyFormat(totalAvailabel));
            this.lblTotalSpent.setText(MoneyFormat.moneyFormat(totalSpent));
            this.lblTotalBudget.setText(MoneyFormat.moneyFormat(totalBudget));
            
            // Cập nhật thanh tiến trình
            if (totalBudget > 0) {
                progressBar.setProgress(totalSpent / totalBudget);
            }
            else {
                progressBar.setProgress(0);
            }
            
            // Lấy danh sách ngân sách
            List<Budget> budgets = budgetServices.getBudgetByUserIdAndDateRange(currentUser.getId(), startDate, endDate);
            listViewBudgets.getItems().setAll(budgets);
            
            listViewBudgets.setCellFactory(params -> new ListCell<Budget>() {
                @Override
                protected void updateItem(Budget budget, boolean empty) {
                    super.updateItem(budget, empty); 
                    
                    if (empty || budget == null) {
                        setGraphic(null);
                    }
                    else {
                        try {
                            Category category = categoryServices.getCategoryById(budget.getCategoryId().getId());
                            
                            Label lblCategoryName = new Label(category.getName());
                            lblCategoryName.setStyle("-fx-font-weight: bold;");
                            
                            double remainingAmount = budget.getTarget() - budget.getAmount();
                            Label lblRemainingAmount = new Label("Còn lại " + MoneyFormat.moneyFormat(remainingAmount));
                            lblRemainingAmount.setStyle("-fx-font-size: 12px;");
                            
                            ProgressBar progressBarItem = new ProgressBar();
                            progressBarItem.setPrefWidth(150);
                            if (budget.getTarget() > 0) {
                                progressBarItem.setProgress(budget.getAmount() / budget.getTarget());
                            }
                            else {
                                progressBarItem.setProgress(0);
                            }
                            
                            Label lblToday = new Label("Hôm nay");
                            lblToday.setStyle("-fx-font-size: 10px;");
                            
                            HBox hBox = new HBox(10);
                            
                            VBox vBox = new VBox(5, lblCategoryName, lblRemainingAmount, progressBarItem, lblToday);
                            hBox.getChildren().add(vBox);
                            setGraphic(hBox);
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(BudgetHomePageController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
            });
            
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void goToCreateBudgetPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("budgetCreatePage.fxml"));
            Parent root = loader.load();

            // chuyển trang qua account 
            Stage stage = (Stage) btnCreateBudget.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang tạo ngân sách !";
            
            System.err.println("Chi tiết lỗi: " + ex.getMessage());
            Utils.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }
}
