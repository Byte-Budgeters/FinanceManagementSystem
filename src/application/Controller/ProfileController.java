package application.Controller;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import application.Model.User;
import application.Resources.UserSession;
import application.Service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProfileController {

	 @FXML private StackPane contentPane;
	 
    @FXML
    private VBox passwordChangeBox;

    @FXML
    private PasswordField currentPasswordField;
    
    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;
    
    @FXML
    private Label errorLabel;
    
    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label createdAtLabel;
    
    UserService userService = new UserService();
    
    @FXML
    public void initialize() {
        // This would typically come from a database or user session
       User user = userService.getCurrentUser();
       nameLabel.setText(user.getFirstName() + " " + user.getLastName());
       emailLabel.setText(user.getEmail());
       SimpleDateFormat outputFormatter = new SimpleDateFormat("MMMM dd, yyyy");
       createdAtLabel.setText(outputFormatter.format(user.getCreatedAt()));
    }

    // Show the password change form
    @FXML
    public void handleChangePassword() {
        passwordChangeBox.setVisible(true);  // Show the password change form
    }

    // Handle the Cancel button action (close the password form)
    @FXML
    public void handleCancelChangePassword() {
        passwordChangeBox.setVisible(false);  // Hide the password change form
    }

    // Handle the Change Password button action (process the form)
    @FXML
    public void handleConfirmChangePassword() throws NoSuchAlgorithmException {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        System.out.println("Current:"+currentPassword+" New: "+newPassword+" confirm: "+confirmPassword);
        // Validate passwords and process
        if (newPassword.equals(confirmPassword)) {
        	
        	User user = userService.getCurrentUser();
        	System.out.println(user);
        	
        	
        	if(! userService.checkPassword(currentPassword, user.getPassword())) {
        		errorLabel.setText("Wrong Password");
        	}
        	else {
        		userService.changePassword(newPassword);
                // Logic to change password (for example, save to the database)
                System.out.println("Password changed successfully!");
                passwordChangeBox.setVisible(false);  // Hide the form after successful change	
        	}
        	
        } else {
            // Show error message if passwords do not match
            System.out.println("Passwords do not match!");
            errorLabel.setText("Passwords do not match!");
        }
    }
    @FXML
    public void handleDeleteAccount() {
        userService.deleteUser();
        try {
            // Load the signup FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/View/login.fxml"));
            Parent signupRoot = loader.load();

            // Get current stage
            Stage stage = (Stage) passwordChangeBox.getScene().getWindow();;
            
            UserSession.clearUserID();

            // Apply new scene and stylesheet
            Scene signupScene = new Scene(signupRoot);
            signupScene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
           // stage.setFullScreen(true);
            stage.setScene(signupScene);
            stage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("contentPane is null. Ensure it is properly initialized.");
            e.printStackTrace();
        }
    } 

}
