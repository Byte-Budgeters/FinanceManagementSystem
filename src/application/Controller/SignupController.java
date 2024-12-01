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
import java.security.NoSuchAlgorithmException;

import application.Service.UserService;

public class SignupController {
    @FXML
    private TextField firstNameField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private TextField lastNameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField confirmPasswordField;
    
    @FXML
    private Label errorLabel;
    
    private UserService userService = new UserService();
    
    @FXML
    private void handleSignUpButton(ActionEvent event) throws NoSuchAlgorithmException {
        // Validate input fields
        if (!validateInputs()) {
            return;
        }
        
        // Perform signup logic
        if(userService.registerUser(emailField.getText(), passwordField.getText(),firstNameField.getText(),lastNameField.getText())) {
        	errorLabel.setText("SignUp Successfully completed");
        }
        
        System.out.println("Signup attempt for: " + firstNameField.getText());
        
        // You would typically save user to database here
    }
    
    private boolean validateInputs() {
        // Clear previous errors
        errorLabel.setText("");
        
        // Check if any field is empty
        if (firstNameField.getText().isEmpty() || 
            emailField.getText().isEmpty() || 
            lastNameField.getText().isEmpty() || 
            passwordField.getText().isEmpty() || 
            confirmPasswordField.getText().isEmpty()) {
            errorLabel.setText("All fields are required");
            return false;
        }
        
        // Check if passwords match
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            errorLabel.setText("Passwords do not match");
            return false;
        }
        
        return true;
    }
    
    @FXML
    private void handleLoginButton(ActionEvent event) {
        try {
            // Load the login FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/View/login.fxml"));
            Parent loginRoot = loader.load();

            // Get current stage
            Stage stage = (Stage) firstNameField.getScene().getWindow();

            // Apply new scene and stylesheet
            Scene loginScene = new Scene(loginRoot);
            loginScene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
            stage.setScene(loginScene);
            stage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Error loading login page");
        }
    }
}
