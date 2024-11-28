package application.Repository;

import application.Model.Expense;
import application.Service.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {

    // Save an expense
    public boolean saveExpense(Expense expense) {
        String query = "INSERT INTO \"Byte-Budgeters\".\"Expense\" (user_id, category, expense_amount, description, expense_date) " +
                       "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, expense.getUserIdFK());
            statement.setString(2, expense.getCategory());
            statement.setFloat(3, expense.getExpenseAmount());
            statement.setString(4, expense.getDescription());
            statement.setTimestamp(5, new java.sql.Timestamp(expense.getExpenseDate().getTime()));

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error saving expense: " + e.getMessage());
            return false;
        }
    }

    // Delete an expense
    public boolean deleteExpense(int expenseId) {
        String query = "DELETE FROM \"Byte-Budgeters\".\"Expense\" WHERE id = ?";
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, expenseId);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting expense: " + e.getMessage());
            return false;
        }
    }

    // Update an expense
    public boolean updateExpense(Expense expense) {
        String query = "UPDATE \"Byte-Budgeters\".\"Expense\" SET user_id = ?, category = ?, expense_amount = ?, description = ?, expense_date = ? " +
                       "WHERE id = ?";
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, expense.getUserIdFK());
            statement.setString(2, expense.getCategory());
            statement.setFloat(3, expense.getExpenseAmount());
            statement.setString(4, expense.getDescription());
            statement.setTimestamp(5, new java.sql.Timestamp(expense.getExpenseDate().getTime()));
            statement.setInt(6, expense.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating expense: " + e.getMessage());
            return false;
        }
    }

    // Fetch expense by ID
    public Expense getExpenseById(int id) {
        String query = "SELECT * FROM \"Byte-Budgeters\".\"Expense\" WHERE id = ?";
        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Expense expense = new Expense();
                    expense.setId(rs.getInt("id"));
                    expense.setUserIdFK(rs.getInt("user_id"));
                    expense.setCategory(rs.getString("category"));
                    expense.setExpenseAmount(rs.getFloat("expense_amount"));
                    expense.setDescription(rs.getString("description"));
                    expense.setExpenseDate(rs.getTimestamp("expense_date"));
                    expense.setCreatedAt(rs.getTimestamp("created_at"));
                    return expense;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching expense by ID: " + e.getMessage());
        }
        return null;
    }

    // Fetch all expenses
    public List<Expense> getAllExpenses() {
        String query = "SELECT * FROM \"Byte-Budgeters\".\"Expense\"";
        List<Expense> expenses = new ArrayList<>();

        try (Connection connection = DatabaseService.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Expense expense = new Expense();
                expense.setId(rs.getInt("id"));
                expense.setUserIdFK(rs.getInt("user_id"));
                expense.setCategory(rs.getString("category"));
                expense.setExpenseAmount(rs.getFloat("expense_amount"));
                expense.setDescription(rs.getString("description"));
                expense.setExpenseDate(rs.getTimestamp("expense_date"));
                expense.setCreatedAt(rs.getTimestamp("created_at"));
                expenses.add(expense);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all expenses: " + e.getMessage());
        }

        return expenses;
    }

    // Fetch expenses by user ID
    public List<Expense> getExpensesByUserId(int userId) {
        String query = "SELECT * FROM \"Byte-Budgeters\".\"Expense\" WHERE user_id = ?";
        List<Expense> expenses = new ArrayList<>();

        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Expense expense = new Expense();
                    expense.setId(rs.getInt("id"));
                    expense.setUserIdFK(rs.getInt("user_id"));
                    expense.setCategory(rs.getString("category"));
                    expense.setExpenseAmount(rs.getFloat("expense_amount"));
                    expense.setDescription(rs.getString("description"));
                    expense.setExpenseDate(rs.getTimestamp("expense_date"));
                    expense.setCreatedAt(rs.getTimestamp("created_at"));
                    expenses.add(expense);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching expenses by user ID: " + e.getMessage());
        }

        return expenses;
    }
}
