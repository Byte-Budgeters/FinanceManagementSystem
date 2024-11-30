package application.Controller;

import application.Service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class FinanceLoginSignup {

    @FXML
    private TextField userName;

    @FXML
    private TextField password;

    @FXML
    private Label myMessage;

    private UserService userService = new UserService();

    @FXML
    private void handleButtonClick() throws NoSuchAlgorithmException, IOException {
        String email = userName.getText();
        String pwd = password.getText();

        if (userService.registerUser(email, pwd)) {
            myMessage.setText("User registered successfully!");
        } else {
            myMessage.setText("Email already exists! Please choose another.");
        }
    }

    @FXML
    private void handleLoginClick() throws NoSuchAlgorithmException, IOException {
        String email = userName.getText();
        String pwd = password.getText();

        if (userService.loginUser(email, pwd)) {
            myMessage.setText("Login successful!");
            openDashboard();
        } else {
            myMessage.setText("Invalid email or password!");
        }
    }

    private void openDashboard() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Dashboard.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) myMessage.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}