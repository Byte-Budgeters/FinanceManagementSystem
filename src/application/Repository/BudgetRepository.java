package application.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.Model.Budget;
import application.Service.DatabaseService;

public class BudgetRepository {
	 // Save an expense
    public boolean saveBudget(Budget budget) {
        String query = "INSERT INTO \"Byte-Budgeters\".\"Budget\" (user_id, budget_category, budget_amount, budget_description, budget_date) " +
                       "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, budget.getUserId());
            statement.setString(2, budget.getBudgetCategory());
            statement.setFloat(3, budget.getBudgetAmount());
            statement.setString(4, budget.getBudgetDescription());
            statement.setTimestamp(5, new java.sql.Timestamp(budget.getBudgetDate().getTime()));

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error saving Budget: " + e.getMessage());
            return false;
        }
    }

    // Delete an expense
    public boolean deleteBudget(int budgetId) {
        String query = "DELETE FROM \"Byte-Budgeters\".\"Budget\" WHERE id = ?";
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, budgetId);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting Budget: " + e.getMessage());
            return false;
        }
    }

    // Update an expense
    public boolean updateBudget(Budget budget) {
        String query = "UPDATE \"Byte-Budgeters\".\"Budget\" SET user_id = ?, budget_category = ?, budget_amount = ?, budget_description = ?, budget_date = ? " +
                       "WHERE id = ?";
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, budget.getUserId());
            statement.setString(2, budget.getBudgetCategory());
            statement.setFloat(3, budget.getBudgetAmount());
            statement.setString(4, budget.getBudgetDescription());
            statement.setTimestamp(5, new java.sql.Timestamp(budget.getBudgetDate().getTime()));
            statement.setInt(6, budget.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating budget: " + e.getMessage());
            return false;
        }
    }

    // Fetch expense by ID
    public Budget getBudgetById(int id) {
        String query = "SELECT * FROM \"Byte-Budgeters\".\"Budget\" WHERE id = ?";
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Budget budget = new Budget();
                    budget.setId(rs.getInt("id"));
                    budget.setUserId(rs.getInt("user_id"));
                    budget.setBudgetCategory(rs.getString("budget_category"));
                    budget.setBudgetAmount(rs.getFloat("budget_amount"));
                    budget.setBudgetDescription(rs.getString("budget_description"));
                    budget.setBudgetDate(rs.getTimestamp("budget_date"));
                    budget.setCreatedAt(rs.getTimestamp("created_at"));
                    return budget;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching budget by ID: " + e.getMessage());
        }
        return null;
    }

    // Fetch all expenses
    public List<Budget> getAllBudget() {
        String query = "SELECT * FROM \"Byte-Budgeters\".\"Budget\"";
        List<Budget> budgets = new ArrayList<>();

        try (Connection connection = DatabaseService.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Budget budget = new Budget();
                budget.setId(rs.getInt("id"));
                budget.setUserId(rs.getInt("user_id"));
                budget.setBudgetCategory(rs.getString("budget_category"));
                budget.setBudgetAmount(rs.getFloat("budget_amount"));
                budget.setBudgetDescription(rs.getString("budget_description"));
                budget.setBudgetDate(rs.getTimestamp("budget_date"));
                budget.setCreatedAt(rs.getTimestamp("created_at"));
                budgets.add(budget);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all budget: " + e.getMessage());
        }

        return budgets;
    }

    // Fetch expenses by user ID
    public List<Budget> getBudgetByUserId(int userId) {
        String query = "SELECT * FROM \"Byte-Budgeters\".\"Budget\" WHERE user_id = ?";
        List<Budget> budgets = new ArrayList<>();

        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Budget budget = new Budget();
                    budget.setId(rs.getInt("id"));
                    budget.setUserId(rs.getInt("user_id"));
                    budget.setBudgetCategory(rs.getString("budget_category"));
                    budget.setBudgetAmount(rs.getFloat("budget_amount"));
                    budget.setBudgetDescription(rs.getString("budget_description"));
                    budget.setBudgetDate(rs.getTimestamp("budget_date"));
                    budget.setCreatedAt(rs.getTimestamp("created_at"));
                    budgets.add(budget);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching budget by user ID: " + e.getMessage());
        }

        return budgets;
    }
}
