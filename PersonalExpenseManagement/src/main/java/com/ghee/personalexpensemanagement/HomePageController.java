/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ghee.personalexpensemanagement;

import com.ghee.pojo.Users;
import com.ghee.pojo.Wallet;
import com.ghee.services.WalletServices;
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

    @FXML private Button loginButton;
    @FXML private Button btnAddBudget;
    @FXML private Button btnAddTransaction;

    private final WalletServices walletServices = new WalletServices();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
                    Utils.getAlert("Lỗi kết nối database", Alert.AlertType.ERROR).show();
                }
            }
        } catch (Exception ex) {
            System.out.println("Chi tiết lỗi " + ex.getMessage());
            Utils.getAlert("Lỗi init trong trang chủ !", Alert.AlertType.ERROR).showAndWait();
        }
    }

    public void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginUser.fxml"));
            Parent root = loader.load();

            // chuyển trang qua account 
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang đăng ký !";
            Utils.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }
    
    public void goToCreateBudgetPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("budgetCreatePage.fxml"));
            Parent root = loader.load();

            // chuyển trang qua account 
            Stage stage = (Stage) btnAddBudget.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            String message = "Không thể chuyển qua trang tạo ngân sách !";
            
            System.err.println("Chi tiết lỗi: " + ex.getMessage());
            Utils.getAlert(message, Alert.AlertType.ERROR).show();
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
            Utils.getAlert(message, Alert.AlertType.ERROR).show();
        }
    }

    public void logout() {
        Utils.setCurrentUser(null);
    }

}
