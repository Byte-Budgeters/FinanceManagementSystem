package application.Controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FinanceLoginSignup {

    private static final String DB_URL = "jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:6543/postgres";
    private static final String DB_USER = "postgres.gjgvodaqvjfdujvkgzye";
    private static final String DB_PASSWORD = "FinanceSystemOODProject"; // Replace with your actual password

    @FXML
    private TextField userName;

    @FXML
    private TextField password;

    @FXML
    private Label myMessage;

    @FXML
    private void handleButtonClick() throws NoSuchAlgorithmException, IOException {
        String email = userName.getText();
        String pwd = password.getText();

        if (registerUser(email, pwd)) {
            myMessage.setText("User registered successfully!");
        } else {
            myMessage.setText("Email already exists! Please choose another.");
        }
    }

    @FXML
    private void handleLoginClick() throws NoSuchAlgorithmException, IOException {
        String email = userName.getText();
        String pwd = password.getText();

        if (loginUser(email, pwd)) {
            myMessage.setText("Login successful!");
            openDashboard();
        } else {
            myMessage.setText("Invalid email or password!");
        }
    }

    private void openDashboard() throws IOException {
    	System.out.println("Dashboard path 1: "+getClass().getResource("/application/Dashboard.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Dashboard.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) myMessage.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private static boolean registerUser(String email, String password) throws IOException, NoSuchAlgorithmException {
        if (isUserExists(email)) {
            return false;
        }

        String hashedPassword = hashPassword(password);

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO \"Byte-Budgeters\".\"Users\" (email, password, created_at) VALUES (?, ?, NOW())")) {

            statement.setString(1, email);
            statement.setString(2, hashedPassword);
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean loginUser(String email, String userpassword) throws IOException, NoSuchAlgorithmException {
        String hashedPassword = hashPassword(userpassword);

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"Byte-Budgeters\".\"Users\" WHERE email = ? AND password = ?")) {

            statement.setString(1, email);
            statement.setString(2, hashedPassword);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isUserExists(String email) throws IOException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"Byte-Budgeters\".\"Users\" WHERE email = ?")) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(password.getBytes());
        StringBuilder hashString = new StringBuilder();
        for (byte b : hashBytes) {
            hashString.append(String.format("%02x", b));
        }
        return hashString.toString();
    }
}