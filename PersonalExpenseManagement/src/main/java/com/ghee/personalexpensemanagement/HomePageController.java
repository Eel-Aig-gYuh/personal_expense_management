/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ghee.personalexpensemanagement;

import com.ghee.pojo.Users;
import com.ghee.pojo.Wallet;
import com.ghee.services.WalletServices;
import com.ghee.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author giahu
 */
public class HomePageController implements Initializable {

    @FXML private Label welcomeLabel;
    @FXML private Label soDuLabel;

    @FXML private Button btnLogin;
    @FXML private Button btnLogout;
    
    @FXML private Button btnHomePage;
    @FXML private Button btnBudgetPage;
    @FXML private Button btnTransactionPage;
    @FXML private Button btnAddTransaction;
    @FXML private Button btnUserPage;

    private final WalletServices walletServices = new WalletServices();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btnHomePage.setDisable(true);
        
        try {
            Users user = Utils.getCurrentUser();
            welcomeLabel.setText("Chào mừng");
            if (user != null) {
                String fullName = user.getLastName() + " " + user.getFirstName();
                welcomeLabel.setText("Chào mừng " + fullName + "!");

                try {
                    Wallet wallet = walletServices.getWalletById(user.getId());

                    if (wallet != null) {
                        soDuLabel.setText(wallet.getSoDu() + " đ");
                    } else {
                        soDuLabel.setText("Lỗi không tìm thấy ví !");
                    }

                } catch (SQLException ex) {
                    System.out.println("Chi tiết lỗi " + ex.getMessage());
                    MessageBox.getAlert("Lỗi kết nối database", Alert.AlertType.ERROR).show();
                }
            }
        } catch (Exception ex) {
            System.out.println("Chi tiết lỗi " + ex.getMessage());
            MessageBox.getAlert("Lỗi init trong trang chủ !", Alert.AlertType.ERROR).showAndWait();
        }
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
                Utils.setCurrentUser(null);

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
