package application.Controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LandingPageController {
    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    public void initialize() {
        if (loginButton != null) {
            loginButton.setOnAction(event -> handleLoginButtonClick());
        } else {
            System.err.println("loginButton is null");
        }

        if (signUpButton != null) {
            signUpButton.setOnAction(event -> handleSignUpButtonClick());
        } else {
            System.err.println("signUpButton is null");
        }
    }

    private void handleLoginButtonClick() {
        // Handle the login button click
    	 try {
             // Load the signup FXML
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/View/login.fxml"));
             Parent signupRoot = loader.load();

             // Get current stage
             Stage stage = (Stage) loginButton.getScene().getWindow();

             // Apply new scene and stylesheet
             Scene signupScene = new Scene(signupRoot);
             signupScene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
//             stage.setFullScreen(true);
             stage.setScene(signupScene);
             stage.setTitle("Login");
         } catch (IOException e) {
             e.printStackTrace();
            
         }
    }

    private void handleSignUpButtonClick() {
        // Handle the sign up button click
    	 try {
             // Load the signup FXML
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/View/signup.fxml"));
             Parent signupRoot = loader.load();

             // Get current stage
             Stage stage = (Stage) loginButton.getScene().getWindow();

             // Apply new scene and stylesheet
             Scene signupScene = new Scene(signupRoot);
             signupScene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
//             stage.setFullScreen(true);
             stage.setScene(signupScene);
             stage.setTitle("Sign Up");
         } catch (IOException e) {
             e.printStackTrace();
            
         }
    }
}