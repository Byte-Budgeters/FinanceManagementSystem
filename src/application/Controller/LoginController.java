package application.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    private void handleLoginButton(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        // Basic validation
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter username and password");
            return;
        }
        
        // Here you would typically add your authentication logic
        System.out.println("Login attempt: " + username);
    }
    
    @FXML
    private void handleSignUpButton(ActionEvent event) {
        try {
            // Load the signup FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/View/signup.fxml"));
            Parent signupRoot = loader.load();

            // Get current stage
            Stage stage = (Stage) usernameField.getScene().getWindow();

            // Apply new scene and stylesheet
            Scene signupScene = new Scene(signupRoot);
            signupScene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
            stage.setScene(signupScene);
            stage.setTitle("Sign Up");
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Error loading signup page");
        }
    }
    
    @FXML
    private void handleForgotPasswordButton(ActionEvent event) {
        // Implement forgot password logic
        System.out.println("Forgot Password clicked");
    }
}
