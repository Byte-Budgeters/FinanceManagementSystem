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
    
    public User getCurrentUser() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"Byte-Budgeters\".\"Users\" WHERE id = ?")) {
        	

            statement.setInt(1, UserSession.getUserID());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
            User user = new User();
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setEmail(resultSet.getString("email"));
            user.setCreatedAt(resultSet.getDate("created_at"));
            user.setPassword(resultSet.getString("password"));
            return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
           
        }
        return null;
    }
    public boolean updatePassword(String newPassword) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE \"Byte-Budgeters\".\"Users\" SET password = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, newPassword);
                statement.setInt(2, UserSession.getUserID());
                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteUser() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "DELETE FROM \"Byte-Budgeters\".\"Users\" WHERE id = ?";
            String DeleteExpensesql = "DELETE FROM \"Byte-Budgeters\".\"Expense\" WHERE user_id = ?";
            String DeleteBudgetsql = "DELETE FROM \"Byte-Budgeters\".\"Budget\" WHERE user_id = ?";
            
            try (PreparedStatement statement = connection.prepareStatement(sql);
            		PreparedStatement Budgetstatement = connection.prepareStatement(DeleteBudgetsql);
            		PreparedStatement Expensestatement = connection.prepareStatement(DeleteExpensesql);
            		) {
                statement.setInt(1, UserSession.getUserID());
                Budgetstatement.setInt(1, UserSession.getUserID());
                Expensestatement.setInt(1, UserSession.getUserID());
               
                 Budgetstatement.executeUpdate();
                 Expensestatement.executeUpdate();
                 int rowsDeleted = statement.executeUpdate();
                return rowsDeleted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}