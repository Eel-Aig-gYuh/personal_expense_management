/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ghee.personalexpensemanagement;

import com.ghee.formatter.MoneyFormat;
import com.ghee.pojo.Transaction;
import com.ghee.pojo.Users;
import com.ghee.services.TransactionServices;
import com.ghee.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author giahu
 */
public class TransactionHomePageController implements Initializable {

    @FXML
    private Label lblSoDuDau;
    @FXML
    private Label lblSoDuCuoi;
    @FXML
    private Label lblTong;

    @FXML
    private Button btnHomePage;
    @FXML
    private Button btnBudgetPage;
    @FXML
    private Button btnTransactionPage;
    @FXML
    private Button btnAddTransaction;
    @FXML
    private Button btnUserPage;

    @FXML
    private TabPane tabPane;

    @FXML
    private ListView listViewTransactions;

    private final TransactionServices transactionServices = new TransactionServices();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btnTransactionPage.setDisable(true);

        this.tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            loadTransactionDatas();
        });

        loadTransactionDatas();
    }

    /**
     * cập nhật dữ liệu transaction.
     */
    public void loadTransactionDatas() {
        try {
            Users currentUser = Utils.getCurrentUser();

            LocalDate now = LocalDate.now();
            LocalDate startDate;
            LocalDate endDate;

            String selectedTab = tabPane.getSelectionModel().getSelectedItem().getText();
            switch (selectedTab) {
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

            // lấy số dư đầu/cuối
            double soDuDau = transactionServices.getOpeningBalance(currentUser.getId(), startDate);
            double soDuCuoi = transactionServices.getClosingBalance(currentUser.getId(), endDate);

            lblSoDuDau.setText(MoneyFormat.moneyFormat(soDuDau) + " đ");
            lblSoDuCuoi.setText(MoneyFormat.moneyFormat(soDuCuoi) + " đ");
            lblTong.setText(MoneyFormat.moneyFormat(soDuCuoi) + " đ");

            // Lấy danh sách giao dịch
            List<Transaction> transactions = transactionServices.getTransactionByUserIdAdnDateRange(currentUser.getId(), startDate, endDate);
            // System.out.println(transactions);

            // Nhóm các giao dịch theo ngày
            Map<Date, List<Transaction>> transactionByDate = transactions.stream()
                    .collect(Collectors.groupingBy(Transaction::getTransactionDate, TreeMap::new, Collectors.toList()));

            List<Object> displayItems = new ArrayList<>();
            transactionByDate.forEach((date, dailyTransaction) -> {
                displayItems.add(date); // Thêm tiêu đề ngày
                dailyTransaction.forEach(dt -> displayItems.add(dt));
            });

            // Cập nhật listView
            this.listViewTransactions.getItems().setAll(displayItems);
            // System.err.println(displayItems);

            // Tùy chỉnh hiển thị danh sách trong 1 cell của list view
            listViewTransactions.setCellFactory(params -> new ListCell<>() {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);

                    // System.out.println(item);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox(10);
                        if (item instanceof Date) {
                            // Hiển thị ngày;
                            LocalDate date = LocalDate.parse(item.toString());
                            String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("vi"));
                            String formatDate = String.format("%d %s, tháng %d %d", date.getDayOfMonth(), dayOfWeek, date.getMonthValue(), date.getYear());
                            double dailyTotal = calculateDailyTotal(date);

                            Label lblDate = new Label(formatDate);
                            lblDate.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                            Label lblDailyTotal = new Label(MoneyFormat.moneyFormat(dailyTotal));
                            String styleThu = "-fx-font-size: 14px; -fx-text-fill: green;";
                            String styleChi = "-fx-font-size: 14px; -fx-text-fill: red;";
                            lblDailyTotal.setStyle(dailyTotal > 0 ? styleThu: styleChi);

                            hbox.getChildren().addAll(lblDate, lblDailyTotal);
                            setGraphic(hbox);
                        }
                        if (item instanceof Transaction) {

                            Transaction transaction = (Transaction) item;

                            Label lblCategoryName = new Label(transaction.getCategoryId().getName());
                            lblCategoryName.setStyle("-fx-font-size: 14px;");

                            Label lblAmount = new Label(MoneyFormat.moneyFormat(transaction.getAmount()) + " đ");
                            String styleThu = "-fx-font-size: 14px; -fx-text-fill: green;";
                            String styleChi = "-fx-font-size: 14px; -fx-text-fill: red;";
                            
                            lblAmount.setStyle(((Transaction) item).getType().equals("Thu") ? styleThu: styleChi);

                            hbox.getChildren().addAll(lblCategoryName, lblAmount);
                            setGraphic(hbox);
                        }
                    }
                }

            });

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public double calculateDailyTotal(LocalDate date) {
        List<Transaction> tmp = (List<Transaction>) listViewTransactions.getItems().stream().filter(item -> item instanceof Transaction)
                .collect(Collectors.toList());
        
        return tmp.stream().filter(t -> t.getTransactionDate().equals(java.sql.Date.valueOf(date)))
                .mapToDouble(t -> {
                    return t.getType().equals("Chi")? t.getAmount() * -1.0 : t.getAmount();
                })
                .sum();
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
            String message = "Không thể chuyển qua trang chủ !";

            System.err.println("Chi tiết lỗi: " + ex.getMessage());
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }

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
            String message = "Không thể chuyển qua trang tạo giao dịch !";

            System.err.println("Chi tiết lỗi: " + ex.getMessage());
            MessageBox.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }

}
