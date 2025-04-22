/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ghee.personalexpensemanagement;

import com.ghee.formatter.MoneyFormat;
import com.ghee.pojo.Budget;
import com.ghee.services.BudgetServices;
import com.ghee.services.StaticticsServices;
import com.ghee.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author giahu
 */
public class BudgetDetailPageController implements Initializable {

    @FXML private Button btnGoBack;
    @FXML private Button btnUpdateBudget;
    @FXML private Button btnDeleteBudget;
    @FXML private Button btnViewTransactions;
    
    @FXML private Label lblCategoryName;
    @FXML private Label lblTarget;
    @FXML private Label lblSpent;
    @FXML private Label lblRemaining;
    @FXML private Label lblDayRemaining;
    @FXML private Label lblDateRange;
    
    // thống kê
    @FXML private LineChart<String, Number> lineChart;
            
    @FXML private ProgressBar progressBar;

    private final BudgetServices budgetServices = new BudgetServices();
    private final StaticticsServices staticticsServices = new StaticticsServices();
    
    private Budget selectedBudget;

    public void setSelectedBudget(Budget budget) {
        this.selectedBudget = budget;
        if (this.selectedBudget != null) {
            loadUI();
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (this.selectedBudget != null) {
            loadUI();
        }
    }
    
    public void loadUI() {
        double target = this.selectedBudget.getTarget();
        double amount = this.selectedBudget.getAmount() ;
        double remaining = this.selectedBudget.getTarget() - this.selectedBudget.getAmount();
        
        this.lblCategoryName.setText(this.selectedBudget.getCategoryId().getName());
        this.lblTarget.setText(String.valueOf(MoneyFormat.moneyFormat(target)));
        this.lblSpent.setText(String.valueOf(MoneyFormat.moneyFormat(amount)));
        
        this.lblRemaining.setText(String.valueOf(MoneyFormat.moneyFormat(remaining)));
        
        if (selectedBudget.getTarget() > 0){
            progressBar.setProgress(amount/target);
        } else {
            progressBar.setProgress(0);
        }
        
        String startDate = LocalDate.parse(this.selectedBudget.getStartDate().toString()).format(DateTimeFormatter.ofPattern("dd/MM"));
        String endDate = LocalDate.parse(this.selectedBudget.getEndDate().toString()).format(DateTimeFormatter.ofPattern("dd/MM"));
        this.lblDateRange.setText(startDate + " - " + endDate);
        
        if (LocalDate.now().isBefore(LocalDate.parse(selectedBudget.getEndDate().toString()))) {
            long daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(selectedBudget.getEndDate().toString())) + 1;
            this.lblDayRemaining.setText("Còn " + String.valueOf(daysRemaining) + " ngày");
        } else {
            this.lblDayRemaining.setText("Đã quá thời hạn ngân sách");
        }
        
        drawChart();
    }
    
    public void drawChart() {
        try {
            Map<LocalDate, Double> datas = staticticsServices.statSpendingByBudget(this.selectedBudget);
            
            // Kiểm tra có dữ liệu không để vẽ biểu đồ.
            if (datas.isEmpty()) {
                lineChart.getData().clear();
                return;
            }
            
            this.lineChart.setAnimated(false);
            
            // Sử dụng TreeMap để tự động sắp xếp theo ngày.
            Map<LocalDate, Double> sortedDatas = new TreeMap<>(datas);
            
            // Vẽ biểu đồ.
            lineChart.getData().clear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Thống kê chi tiểu của ngân sách " + this.selectedBudget.getCategoryId().getName());
            
            for (Map.Entry<LocalDate, Double> item : sortedDatas.entrySet()) {
                String dateLabel = item.getKey().format(DateTimeFormatter.ofPattern("dd/MM"));
                
                series.getData().add(new XYChart.Data<>(dateLabel, item.getValue()));
            }
            
            lineChart.getData().add(series);
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void deleteBudget(ActionEvent event) {
        MessageBox.getYesNoAlert("Bạn có chắc chắn muốn xóa ngân sách này không?", Alert.AlertType.CONFIRMATION)
                .showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button) event.getSource();
                        ListCell cell = (ListCell) b.getParent().getParent();
                        Budget budgetInCell = (Budget) cell.getItem();

                        try {
                            if (budgetServices.deleteBudget(budgetInCell.getId()) == true) {
                                MessageBox.getAlert("Xóa thành công !", Alert.AlertType.CONFIRMATION).showAndWait();
                                goBack();
                            } else {
                                MessageBox.getAlert("Xóa không thành công !", Alert.AlertType.ERROR).showAndWait();
                            }
                        } catch (SQLException ex) {
                            System.err.println(ex.getMessage());
                        }
                    }
                });
    }

    public void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("budgetHomePage.fxml"));
            Parent root = loader.load();

            BudgetHomePageController bhpc = loader.getController();
            bhpc.loadBudgetsData();

            // chuyển trang qua account 
            Stage stage = (Stage) btnGoBack.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang chủ ngân sách !";

            System.err.println("Chi tiết lỗi: " + ex.getMessage());
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }

    public void goToUpdateBudget() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("budgetCreatePage.fxml"));
            Parent root = loader.load();

            BudgetCreatePageController bcpc = loader.getController();
            bcpc.setSelectedBudget(this.selectedBudget);

            // chuyển trang qua account 
            Stage stage = (Stage) btnUpdateBudget.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang tạo ngân sách !";

            System.err.println("Chi tiết lỗi: " + ex.getMessage());
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }
    
    public void goToViewTransaction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("budgetViewTransactionPage.fxml"));
            Parent root = loader.load();

            System.out.println(this.selectedBudget);
            BudgetViewTransactionPageController bvtpc = loader.getController();
            bvtpc.setSelectedBudget(this.selectedBudget);
            
            // chuyển trang qua account 
            Stage stage = (Stage) btnViewTransactions.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang chi tiết giao dịch của ngân sách !";

            System.err.println("Chi tiết lỗi: " + ex.getMessage());
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }

}
