package application.Repository;

import application.Model.Budget;
import application.Resources.UserSession;
import application.Service.DatabaseService;
import java.util.Date;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetRepository {

    // Save a budget
    public boolean saveBudget(Budget budget) {
        String query = "INSERT INTO \"Byte-Budgeters\".\"Budget\" (user_id, budget_category, budget_limit, current_spending, budget_date) " +
                       "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, UserSession.getUserID());
            statement.setString(2, budget.getBudgetCategory());
            statement.setDouble(3, budget.getLimit());
            statement.setDouble(4, budget.getCurrentSpending());
            Date dateToUse = (budget.getBudgetDate() != null) ? budget.getBudgetDate() : new Date();
            statement.setTimestamp(5, new java.sql.Timestamp(dateToUse.getTime()));

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        budget.setId(generatedKeys.getInt(1)); // Set the auto-generated ID
                    }
                }
            }
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error saving Budget: " + e.getMessage());
            return false;
        }
    }

    // Delete a budget
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

    // Update a budget
    public boolean updateBudget(Budget budget) {
        String query = "UPDATE \"Byte-Budgeters\".\"Budget\" SET user_id = ?, budget_category = ?, budget_limit = ?, current_spending = ?, budget_date = ? " +
                       "WHERE id = ?";
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, UserSession.getUserID());
            statement.setString(2, budget.getBudgetCategory());
            statement.setDouble(3, budget.getLimit());
            statement.setDouble(4, budget.getCurrentSpending());
            statement.setTimestamp(5, new Timestamp(budget.getBudgetDate().getTime()));
            statement.setInt(6, budget.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating Budget: " + e.getMessage());
            return false;
        }
    }

    // Update spending
    public boolean updateSpending(int budgetId, double amount) {
        String query = "UPDATE \"Byte-Budgeters\".\"Budget\" SET current_spending = ? WHERE id = ?";
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDouble(1, amount);
            statement.setInt(2, budgetId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating spending: " + e.getMessage());
            return false;
        }
    }

    // Fetch budget by ID
    public Budget getBudgetById(int id) {
        String query = "SELECT * FROM \"Byte-Budgeters\".\"Budget\" WHERE id = ?";
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBudget(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Budget by ID: " + e.getMessage());
        }
        return null;
    }

    // Fetch all budgets
    public List<Budget> getAllBudget() {
        String query = "SELECT * FROM \"Byte-Budgeters\".\"Budget\"";
        List<Budget> budgets = new ArrayList<>();

        try (Connection connection = DatabaseService.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                budgets.add(mapResultSetToBudget(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all budgets: " + e.getMessage());
        }

        return budgets;
    }

    // Fetch budgets by user ID
    public List<Budget> getBudgetByUserId(int userId) {
        String query = "SELECT * FROM \"Byte-Budgeters\".\"Budget\" WHERE user_id = ?";
        List<Budget> budgets = new ArrayList<>();

        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    budgets.add(mapResultSetToBudget(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching budgets by user ID: " + e.getMessage());
        }

        return budgets;
    }

    // Helper method to map ResultSet to Budget
    private Budget mapResultSetToBudget(ResultSet rs) throws SQLException {
        Budget budget = new Budget();
        budget.setId(rs.getInt("id"));
        budget.setUserId(rs.getInt("user_id"));
        budget.setBudgetCategory(rs.getString("budget_category"));
        budget.setLimit(rs.getDouble("budget_limit"));
        budget.setCurrentSpending(rs.getDouble("current_spending"));
        budget.setBudgetDate(rs.getTimestamp("budget_date"));
        budget.setCreatedAt(rs.getTimestamp("created_at"));
        return budget;
    }
    
    public List<Budget> getBudgetByUserIdAndMonthAndYear(int userId, int month, int year) {
        String query = "SELECT * FROM \"Byte-Budgeters\".\"Budget\" " +
                       "WHERE user_id = ? AND EXTRACT(MONTH FROM budget_date) = ? " +
                       "AND EXTRACT(YEAR FROM budget_date) = ? " +
                       "ORDER BY budget_date DESC";
        List<Budget> budgets = new ArrayList<>();

        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, month);
            stmt.setInt(3, year);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    budgets.add(mapResultSetToBudget(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching budgets by month and year: " + e.getMessage());
        }

        return budgets;
    }

}