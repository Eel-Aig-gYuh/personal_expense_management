 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ghee.personalexpensemanagement;

import com.ghee.config.AppConfigs;
import com.ghee.formatter.MoneyFormat;
import com.ghee.pojo.Category;
import com.ghee.pojo.Transaction;
import com.ghee.pojo.Users;
import com.ghee.pojo.Wallet;
import com.ghee.services.CategoryServices;
import com.ghee.services.StaticticsServices;
import com.ghee.services.TransactionServices;
import com.ghee.services.WalletServices;
import com.ghee.utils.ManageUser;
import com.ghee.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author giahu
 */
public class HomePageController implements Initializable {

    @FXML private Label welcomeLabel;
    @FXML private Label soDuLabel;
    @FXML private Label lblNoData;
    @FXML private Label lblNoData1;
    @FXML private Label lblNoData2;

    @FXML private Button btnLogin;
    @FXML private Button btnLogout;
    
    @FXML private Button btnHomePage;
    @FXML private Button btnBudgetPage;
    @FXML private Button btnTransactionPage;
    @FXML private Button btnAddTransaction;
    @FXML private Button btnUserPage;
   
    // báo cáo chi tiêu:
    @FXML private ComboBox<String> cbTimeRange;
    
    @FXML private BarChart<String, Double> barChart;
    @FXML private BarChart<String, Double> barChartComparison;
    
    @FXML private LineChart<String, Double> lineChartSpent;
    @FXML private CategoryAxis spentXAxis;
    
    @FXML private TabPane tabPaneReport;
    @FXML private Tab tabSpendingByBudget;
    @FXML private Tab tabSpendingByDay;
    @FXML private Tab tabSpendingByComparison;
    
    @FXML private AnchorPane apSpendingByBudget;
    
    
    private final WalletServices walletServices = new WalletServices();
    private final TransactionServices transactionServices = new TransactionServices();
    private final CategoryServices categoryServices = new CategoryServices();
    private final StaticticsServices staticticsServices = new StaticticsServices();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btnHomePage.setDisable(true);
        this.btnLogin.setVisible(false);

        try {
            Users user = ManageUser.getCurrentUser();
            welcomeLabel.setText("Chào mừng");
            if (user != null) {
                String fullName = user.getLastName() + " " + user.getFirstName();
                welcomeLabel.setText("Chào mừng " + fullName + "!");

                try {
                    Wallet wallet = walletServices.getWalletById(user.getId());

                    if (wallet != null) {
                        soDuLabel.setText(MoneyFormat.moneyFormat(wallet.getSoDu()) + "đ");
                    } else {
                        soDuLabel.setText("Lỗi không tìm thấy ví !");
                    }

                } catch (SQLException ex) {
                    System.out.println("Chi tiết lỗi " + ex.getMessage());
                    MessageBox.getAlert("Lỗi kết nối database", Alert.AlertType.ERROR).show();
                }
                
                this.cbTimeRange.getItems().addAll("Tuần này", "Tháng này", "Quý này", "Năm này");
                this.cbTimeRange.setValue("Tháng này");

                // khi thay đổi trong combobox thì load lại barchart
                this.cbTimeRange.setOnAction(event -> {
                    if (tabPaneReport.getSelectionModel().getSelectedIndex() == 0) {
                        loadSpendingReport();
                    } else if (tabPaneReport.getSelectionModel().getSelectedIndex() == 1) {
                        loadDaySpentReport();
                    }
                });
                
                tabPaneReport.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
                    if (newTab == tabSpendingByBudget) {
                        cbTimeRange.setVisible(true);
                        loadSpendingReport();
                    } else if (newTab == tabSpendingByDay) {
                        cbTimeRange.setVisible(true);
                        loadDaySpentReport();
                    } else if (newTab == tabSpendingByComparison) {
                        cbTimeRange.setVisible(false);
                        loadComparisonReport();
                    }
                });
                loadSpendingReport();
            }
        } catch (Exception ex) {
            System.out.println("Chi tiết lỗi " + ex.getMessage());
            MessageBox.getAlert("Lỗi init trong trang chủ !", Alert.AlertType.ERROR).showAndWait();
        }
    }
    
    
    /** 
     * hiển thị báo cáo chi tiêu ở home page;
     */
    public void loadSpendingReport() {
        Users currentUser = ManageUser.getCurrentUser();
        
        if (currentUser == null) {
            this.barChart.getData().clear();
            return;
        }
        
        try {
            
            LocalDate now = LocalDate.now();
            LocalDate startDate;
            LocalDate endDate;
            
            String timeRange = this.cbTimeRange.getValue();
            switch (timeRange) {
                case "Tuần này":
                    startDate = now.with(DayOfWeek.MONDAY);
                    endDate = now.with(DayOfWeek.SUNDAY);
                    break;
                case "Tháng này":
                    startDate = now.withDayOfMonth(1);
                    endDate = now.withDayOfMonth(now.lengthOfMonth());
                    break;
                case "Quý này":
                    int quarter = (now.getMonthValue() - 1) / 3 + 1;
                    startDate = LocalDate.of(now.getYear(), (quarter - 1) * 3 + 1, 1);
                    endDate = startDate.plusMonths(2).withDayOfMonth(startDate.plusMonths(2).lengthOfMonth());
                    break;    
                case "Năm này":
                    startDate = LocalDate.of(now.getYear(), 1, 1);
                    endDate = LocalDate.of(now.getYear(), 12, 31);
                    break;
                default:
                    startDate = now.withDayOfMonth(1);
                    endDate = now.withDayOfMonth(now.lengthOfMonth());
            }
            
            // Kiểm tra khoảng thời gian hợp lệ (không quá tương lai)
            if (endDate.isAfter(now)){
                endDate = now;
            }
            
            // Lấy danh sách các giao dịch theo danh mục
            Map<String, Double> spendingByCategory = staticticsServices.statSpendingByCategory(currentUser, startDate, endDate);
            
            if (spendingByCategory.isEmpty()) {
                this.barChart.getData().clear();
                this.barChart.setVisible(false);
                this.lblNoData.setVisible(true);
                this.lblNoData.setText(AppConfigs.NO_DATA_TRANSACTION);
 
                return;
            }
            
            this.barChart.setVisible(true);
            this.lblNoData.setVisible(false);
            
            // Vẽ biểu đồ.
            drawBarChart(spendingByCategory);
            
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void drawBarChart(Map<String, Double> datas) {
        this.barChart.getData().clear();
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Chi tiêu");
        
        // Kích hoạt hiệu ứng động
        this.barChart.setAnimated(false);

        for (Map.Entry<String, Double> entry: datas.entrySet()) {
            XYChart.Data<String, Double> data = new XYChart.Data<>(entry.getKey(), entry.getValue());
            series.getData().add(data);
            
            
            // Thêm Tooltip
            Tooltip tooltip = new Tooltip("Danh mục: " + entry.getKey() + "\nSố tiền: " + MoneyFormat.moneyFormat(entry.getValue()) + "đ");
            Tooltip.install(data.getNode(), tooltip);
        }
        
        this.barChart.getData().add(series);
    }
    
    /**
     * Vẽ line chart chi tiêu theo ngày.
     */
    public void loadDaySpentReport() {
        Users user = ManageUser.getCurrentUser();
        if (user == null) {
            this.lineChartSpent.getData().clear();
            return;
        }
        
        try {
            LocalDate now = LocalDate.now();
            LocalDate startDate;
            LocalDate endDate;
            
            String timeRange = this.cbTimeRange.getValue();
            switch (timeRange) {
                case "Tuần này":
                    startDate = now.with(DayOfWeek.MONDAY);
                    endDate = now.with(DayOfWeek.SUNDAY);
                    break;
                case "Tháng này":
                    startDate = now.withDayOfMonth(1);
                    endDate = now.withDayOfMonth(now.lengthOfMonth());
                    break;
                case "Quý này":
                    int quarter = (now.getMonthValue() - 1) / 3 + 1;
                    startDate = LocalDate.of(now.getYear(), (quarter - 1) * 3 + 1, 1);
                    endDate = startDate.plusMonths(2).withDayOfMonth(startDate.plusMonths(2).lengthOfMonth());
                    break;    
                case "Năm này":
                    startDate = LocalDate.of(now.getYear(), 1, 1);
                    endDate = LocalDate.of(now.getYear(), 12, 31);
                    break;
                default:
                    startDate = now.withDayOfMonth(1);
                    endDate = now.withDayOfMonth(now.lengthOfMonth());
            }
            
            if (endDate.isAfter(now)) {
                endDate = now;
            }
            
            Map<LocalDate, Double> transactionByDay = staticticsServices.statTransactionByDay(user, startDate, endDate);
            
            if (transactionByDay.isEmpty() || transactionByDay.values().stream().allMatch(amount -> amount == 0.0)) {
                this.lineChartSpent.getData().clear();
                this.lineChartSpent.setVisible(false);
                this.lblNoData1.setVisible(true);
                this.lblNoData1.setText(AppConfigs.NO_DATA_TRANSACTION);
            }
            
            this.lineChartSpent.setVisible(true);
            this.lblNoData1.setVisible(false);
            
            drawLineChartSpentByDay(transactionByDay, startDate, endDate);
            
        } catch (SQLException ex) {
            return;
        }
    }
    
    public void drawLineChartSpentByDay(Map<LocalDate, Double> datas, LocalDate startDate, LocalDate endDate) {
        this.lineChartSpent.getData().clear();
        
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Thống kê chi tiêu trong tháng ...");
        
        LocalDate currentDate = startDate;
        
        this.lineChartSpent.setAnimated(false);
        
        while (!currentDate.isAfter(endDate)) {
            String dateStr = currentDate.format(DateTimeFormatter.ofPattern("dd/MM"));
            Double amount = datas.getOrDefault(currentDate, 0.0);
            // System.out.printf("%s - %f ", dateStr, amount);
            series.getData().add(new XYChart.Data<>(dateStr, amount));
            currentDate = currentDate.plusDays(1);
        }
        
        this.lineChartSpent.getData().add(series);
    }
    
    public void loadComparisonReport() {
        Users user = ManageUser.getCurrentUser();
        if (user == null) {
            this.lineChartSpent.getData().clear();
            return;
        }
        
        try {
            LocalDate now = LocalDate.now();

            // Tháng này
            LocalDate currStartDate = now.withDayOfMonth(1);
            LocalDate currEndDate = now.withDayOfMonth(now.lengthOfMonth());
            if (currEndDate.isAfter(now)) {
                currEndDate = now;
            }

            // Tháng trước
            LocalDate previousMonth = now.minusMonths(1);
            LocalDate preStartDate = previousMonth.withDayOfMonth(1);
            LocalDate preEndDate = previousMonth.withDayOfMonth(previousMonth.lengthOfMonth());
            
            
            Map<String, Double> comparisonDatas = staticticsServices.statComparison(user, currStartDate, currEndDate, preStartDate, preEndDate);
            
            if (comparisonDatas.isEmpty() || comparisonDatas.values().stream().allMatch(amount -> amount == 0.0)) {
                this.barChartComparison.getData().clear();
                this.barChartComparison.setVisible(false);
                this.lblNoData2.setVisible(true);
                this.lblNoData2.setText(AppConfigs.NO_DATA_TRANSACTION);
            }
            
            this.barChartComparison.setVisible(true);
            this.lblNoData2.setVisible(false);
            
            drawBarChartComparison(comparisonDatas);
            
        } catch (SQLException ex) {
            return;
        }
    }
    
    public void drawBarChartComparison(Map<String, Double> datas) {
        this.barChartComparison.getData().clear();
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Bảng thống kê chi trong 2 tháng !");
        
         this.barChartComparison.setAnimated(false);
        
        for (Map.Entry<String, Double> entry: datas.entrySet()) {
            XYChart.Data<String, Double> data = new XYChart.Data<>(entry.getKey(), entry.getValue());
            series.getData().add(data);
            
            
            // Thêm Tooltip
            Tooltip tooltip = new Tooltip("Danh mục: " + entry.getKey() + "\nSố tiền: " + MoneyFormat.moneyFormat(entry.getValue()) + "đ");
            Tooltip.install(data.getNode(), tooltip);
        }
        
        this.barChartComparison.getData().add(series);
    }

    public void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginUser.fxml"));
            Parent root = loader.load();

            // chuyển trang qua account 
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang đăng ký !";
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }

    public void logout() {
        var result = MessageBox.getYesNoAlert("Bạn có chắc chắn muốn đăng xuất không ?", Alert.AlertType.CONFIRMATION).showAndWait();

        System.out.println(result.get());
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Người dùng chọn "Đồng ý" -> Thực hiện đăng xuất
            try {
                // Xóa thông tin người dùng hiện tại
                ManageUser.setCurrentUser(null);

                // Chuyển về màn hình đăng nhập
                FXMLLoader loader = new FXMLLoader(getClass().getResource("loginUser.fxml"));
                Parent root = loader.load();

                // Lấy stage hiện tại (cửa sổ chính)
                Stage stage = (Stage) btnLogout.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Đăng nhập");
                stage.show();
            } catch (IOException e) {
                System.err.println("Lỗi khi mở login.fxml: " + e.getMessage());
                MessageBox.getAlert("Lỗi khi chuyển về màn hình đăng nhập!", Alert.AlertType.ERROR).showAndWait();
            }
        }
        // Nếu người dùng chọn "Hủy", không làm gì cả (hộp thoại tự đóng)
        
    }
    
    public void goToLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginUser.fxml"));
            Parent root = loader.load();

            // chuyển trang qua account 
            Stage stage = (Stage) btnBudgetPage.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang ngân sách !";
            
            System.err.println("Chi tiết lỗi: " + ex.getMessage());
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }

    // ====================== điều hướng
    public void goToBudgetHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("budgetHomePage.fxml"));
            Parent root = loader.load();

            // chuyển trang qua account 
            Stage stage = (Stage) btnBudgetPage.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang ngân sách !";
            
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
    
    
}
