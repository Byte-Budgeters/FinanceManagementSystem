package application.Repository;

import application.Model.User;
import application.Resources.UserSession;

import java.sql.*;

public class UserRepository {
    private static final String DB_URL = "jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:6543/postgres";
    private static final String DB_USER = "postgres.gjgvodaqvjfdujvkgzye";
    private static final String DB_PASSWORD = "FinanceSystemOODProject";

    public boolean save(User user) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO \"Byte-Budgeters\".\"Users\" (email, password,first_name,last_name,created_at) VALUES (?, ?,?,?,now())")) {

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean existsByEmail(String email) {
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

    public boolean validateUser(String email, String hashedPassword) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"Byte-Budgeters\".\"Users\" WHERE email = ? AND password = ?")) {

            statement.setString(1, email);
            statement.setString(2, hashedPassword);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) { 
                UserSession.setUserID(resultSet.getInt("id")); 
                return true; // Return true because there's data
            } else {
                return false; 
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}