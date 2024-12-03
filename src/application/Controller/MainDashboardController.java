package application.Controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

import application.Resources.UserSession;

public class MainDashboardController {
    @FXML private StackPane contentPane;

    @FXML
    public void initialize() {
        // Load dashboard by default
        switchToDashboard();
    }

    @FXML
    public void switchToDashboard() {
    	System.out.println(UserSession.getUserID());
    	 loadView("/application/View/dashboard.fxml");
    }

    @FXML
    public void switchToExpense() {
        loadView("../View/Expense.fxml");
    }

    @FXML
    public void switchToBudget() {
        loadView("../View/Budget.fxml");
    }
    
    @FXML
    public void logout() throws IOException {
    	try {
            // Load the signup FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/View/login.fxml"));
            Parent signupRoot = loader.load();

            // Get current stage
            Stage stage = (Stage) contentPane.getScene().getWindow();
            
            UserSession.clearUserID();

            // Apply new scene and stylesheet
            Scene signupScene = new Scene(signupRoot);
            signupScene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
            stage.setFullScreen(true);
            stage.setScene(signupScene);
            stage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
           
        }
    }

    private void loadView(String fxmlPath) {
        try {
            // Clear previous content
            contentPane.getChildren().clear();
            
            // Load new view
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
           
            contentPane.getChildren().add(view);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML: " + fxmlPath);
        }
    }
}