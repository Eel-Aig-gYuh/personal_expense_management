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
import com.ghee.formatter.MoneyFormat;
import com.ghee.utils.ManageUser;
import com.ghee.utils.MessageBox;
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
import javafx.scene.control.ButtonType;
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
    
    @FXML private Button btnCreateBudget;
    
    @FXML private Button btnHomePage;
    @FXML private Button btnBudgetPage;
    @FXML private Button btnTransactionPage;
    @FXML private Button btnAddTransaction;
    @FXML private Button btnUserPage;
    
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
        this.btnBudgetPage.setDisable(true);
        
        this.lblDayLeft.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        // xử lý sự kiện thay đổi tab
        this.tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            loadBudgetsData();
        });
        
        loadBudgetsData();
    } 
    
    public void loadBudgetsData() {
        try {
            Users currentUser = ManageUser.getCurrentUser();
            
            if (currentUser == null) {
                MessageBox.getAlert("Không tìm thấy user hiện tại !", Alert.AlertType.ERROR).showAndWait();
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
            this.lblTotalSpent.setText(MoneyFormat.formatAmount(totalSpent));
            this.lblTotalBudget.setText(MoneyFormat.formatAmount(totalBudget));
            
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
            
            // Tùy chỉnh giao diện hiển thị cho 1 cell trong listView.
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
                            
                            Label lblTarget = new Label(String.format("         : %s đ", budget.getTarget()));
                            lblTarget.setStyle("-fx-font-weight: bold;");
                            
                            HBox hboxTitle = new HBox();
                            hboxTitle.getChildren().addAll(lblCategoryName, lblTarget);
                            
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
                            
                            Button btnDelete = new Button("Xóa");
                            
                            btnDelete.setOnAction(event -> {
                                MessageBox.getYesNoAlert("Bạn có chắc chắn muốn xóa ngân sách này không?", Alert.AlertType.CONFIRMATION)
                                        .showAndWait().ifPresent(res -> {
                                            if (res == ButtonType.OK) {
                                                Button b = (Button) event.getSource();
                                                ListCell cell = (ListCell) b.getParent().getParent();
                                                Budget budgetInCell = (Budget) cell.getItem();

                                                try {
                                                    if (budgetServices.deleteBudget(budgetInCell.getId()) == true) {
                                                        MessageBox.getAlert("Xóa thành công !", Alert.AlertType.CONFIRMATION).showAndWait();
                                                        loadBudgetsData();
                                                    } else {
                                                        MessageBox.getAlert("Xóa không thành công !", Alert.AlertType.ERROR).showAndWait();
                                                    }
                                                } catch (SQLException ex) {
                                                    System.err.println(ex.getMessage());
                                                }
                                            }
                                        });
                            });
                            
                            HBox hBox = new HBox(10);
                            
                            VBox vBox = new VBox(5, hboxTitle, lblRemainingAmount, progressBarItem, lblToday);
                            hBox.getChildren().addAll(vBox, btnDelete);
                            setGraphic(hBox);
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(BudgetHomePageController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
            });
            
            // Sự kiện khi nhấn vào 1 cell trong listView.
            listViewBudgets.setOnMouseClicked(event -> {
                Budget selectedBudget = listViewBudgets.getSelectionModel().getSelectedItem();
                
                if (selectedBudget != null) {
                    // chuyển hướng tới updateBudget.
                    goToDetailBudgetPage(selectedBudget);
                }
            
            });
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void goToDetailBudgetPage(Budget selectedBudget) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("budgetDetailPage.fxml"));
            Parent root = loader.load();
            
            BudgetDetailPageController bcpc = loader.getController();
            bcpc.setSelectedBudget(selectedBudget);
            // System.out.println(selectedBudget);
            
            Stage stage = (Stage) btnCreateBudget.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang chi tiết ngân sách !";
            
            System.err.println("Chi tiết lỗi: " + ex.getMessage());
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }
    
    public void goToCreateBudgetPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("budgetCreatePage.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) btnCreateBudget.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang tạo ngân sách !";
            
            System.err.println("Chi tiết lỗi: " + ex.getMessage());
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }
    
    // ============ Điều hướng
    public void goToHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage.fxml"));
            Parent root = loader.load();

            // chuyển trang qua account 
            Stage stage = (Stage) btnHomePage.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang tạo ngân sách !";
            
            System.err.println("Chi tiết lỗi: " + ex.getMessage());
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }
    
    public void goToTransactionHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transactionHomePage.fxml"));
            Parent root = loader.load();

            // chuyển trang qua account 
            Stage stage = (Stage) btnTransactionPage.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang tạo ngân sách !";
            
            System.err.println("Chi tiết lỗi: " + ex.getMessage());
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }
    
    public void goToCreateTransactionPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transactionCreatePage.fxml"));
            Parent root = loader.load();

            // chuyển trang qua account 
            Stage stage = (Stage) btnAddTransaction.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang tạo ngân sách !";
            
            System.err.println("Chi tiết lỗi: " + ex.getMessage());
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }
}
