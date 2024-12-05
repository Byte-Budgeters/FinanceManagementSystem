package application.Repository;

import application.Model.Expense;
import application.Model.ExpenseSummary;
import application.Resources.UserSession;
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
    public List<Expense> getExpensesByUserIdAndMonthAndYear(int userId, int month, int year) {
        String query = "SELECT * FROM \"Byte-Budgeters\".\"Expense\" " +
                       "WHERE user_id = ? AND EXTRACT(MONTH FROM expense_date) = ? " +
                       "AND EXTRACT(YEAR FROM expense_date) = ? " +
                       "ORDER BY expense_date DESC";
        List<Expense> expenses = new ArrayList<>();

        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, month);
            stmt.setInt(3, year);

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
                    System.out.print(expense.toString());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching expenses by month and year: " + e.getMessage());
        }

        return expenses;
    }

    
    public List<Expense> getExpensesByCategory(String category) {
        String query = "SELECT * FROM \"Byte-Budgeters\".\"Expense\" WHERE category = ?";
        List<Expense> expenses = new ArrayList<>();

        try (Connection connection = DatabaseService.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, category);
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
    public ExpenseSummary getMonthlyExpenseSummaryAndAverageForLast3Months(ExpenseSummary summary, int year, int month) {
        String monthlyExpenseSummaryQuery = """
            WITH MonthlyExpenses AS (
                SELECT 
                    category,
                    SUM(expense_amount) AS total_expense
                FROM "Byte-Budgeters"."Expense"
                WHERE user_id = ?
                  AND EXTRACT(YEAR FROM expense_date) = ?
                  AND EXTRACT(MONTH FROM expense_date) = ?
                GROUP BY category
            ),
            MaxCategory AS (
                SELECT category AS max_category
                FROM MonthlyExpenses
                WHERE total_expense = (SELECT MAX(total_expense) FROM MonthlyExpenses)
                LIMIT 1
            ),
            MinCategory AS (
                SELECT category AS min_category
                FROM MonthlyExpenses
                WHERE total_expense = (SELECT MIN(total_expense) FROM MonthlyExpenses)
                LIMIT 1
            )
            SELECT 
                (SELECT MAX(total_expense) FROM MonthlyExpenses) AS max_expense,
                (SELECT MIN(total_expense) FROM MonthlyExpenses) AS min_expense,
                (SELECT SUM(total_expense) FROM MonthlyExpenses) AS total_monthly_expense,
                (SELECT max_category FROM MaxCategory) AS max_category,
                (SELECT min_category FROM MinCategory) AS min_category;
        """;

        String averageMonthlyExpenseQuery = """
            WITH Last3MonthsExpenses AS (
                SELECT 
                    EXTRACT(MONTH FROM expense_date) AS month, 
                    EXTRACT(YEAR FROM expense_date) AS year, 
                    SUM(expense_amount) AS total_expense
                FROM "Byte-Budgeters"."Expense"
                WHERE user_id = ?
                  AND expense_date >= CURRENT_DATE - INTERVAL '3 MONTH'
                GROUP BY EXTRACT(MONTH FROM expense_date), EXTRACT(YEAR FROM expense_date)
            )
            SELECT 
                AVG(total_expense) AS avg_monthly_expense
            FROM Last3MonthsExpenses;
        """;

        try (Connection connection = DatabaseService.getConnection()) {
            // First query: Get monthly expense summary
            try (PreparedStatement stmt = connection.prepareStatement(monthlyExpenseSummaryQuery)) {
                stmt.setInt(1, UserSession.getUserID());
                stmt.setInt(2, year);
                stmt.setInt(3, month);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        summary.setMaxExpenseAmount(rs.getDouble("max_expense"));
                        summary.setMinExpenseAmount(rs.getDouble("min_expense"));
                        summary.setTotalExpenseAmount(rs.getDouble("total_monthly_expense"));
                        summary.setMaxExpenseCategory(rs.getString("max_category"));
                        summary.setMinExpenseCategory(rs.getString("min_category"));
                        
                    }
                }
            }

            // Second query: Get average monthly expense for the last 3 months
            try (PreparedStatement stmt = connection.prepareStatement(averageMonthlyExpenseQuery)) {
                stmt.setInt(1, UserSession.getUserID());

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        summary.setAvgThreeMonthExpense(rs.getFloat("avg_monthly_expense"));
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching monthly expense summary and average for last 3 months: " + e.getMessage());
        }

        return summary;
    }


}
