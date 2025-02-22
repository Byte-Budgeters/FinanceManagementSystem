package application.Manager;

import application.Model.Budget;
import application.Service.ExpenseService;
import application.Resources.UserSession;
import application.Service.BudgetService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class BudgetManager {
    private ObservableList<Budget> budgets = FXCollections.observableArrayList();
    private BudgetService budgetService = new BudgetService();
    private ExpenseService expenseService = new ExpenseService(); // Integrated ExpenseService

    public BudgetManager() {
        loadBudgets(); // Load budgets from the database
    }

    public ObservableList<Budget> getBudgets() {
        return FXCollections.observableArrayList(budgets);
    }

    public void addBudget(String budgetCategory, double limit) {
        Budget newBudget = new Budget(budgetCategory, limit); // Create a new budget
        if (budgetService.addBudget(newBudget)) { // Save to database
        	double totalSpending = expenseService
                    .getExpensesByCategory(newBudget.getBudgetCategory(), getCurrentMonth(), getCurrentYear())
                    .stream()
                    .mapToDouble(expense -> expense.getExpenseAmount())
                    .sum();

        	newBudget.setCurrentSpending(totalSpending);

            budgets.add(newBudget); // Add to in-memory list
        }
    }

    public void deleteBudget(Budget budget) {
        if (budgetService.removeBudget(budget.getId())) { // Remove from database
            budgets.remove(budget); // Remove from in-memory list
        }
    }

    public void loadBudgets() {
        List<Budget> budgetList = budgetService.getBudgetByUserId(UserSession.getUserID()); // Fetch from database
        for (Budget budget : budgetList) {
            double totalSpending = expenseService
                .getExpensesByCategory(budget.getBudgetCategory(), getCurrentMonth(), getCurrentYear())
                .stream()
                .mapToDouble(expense -> expense.getExpenseAmount())
                .sum();

            budget.setCurrentSpending(totalSpending);
            
        }
        budgets.setAll(budgetList); // Update in-memory list
    }

    public void modifyBudget(Budget budget) {
        if (budgetService.modifyBudget(budget)) { // Update in database
            budgets.set(budgets.indexOf(budget), budget); // Update in-memory list
        }
    }

    private int getCurrentMonth() {
        return java.time.LocalDate.now().getMonthValue();
    }

    private int getCurrentYear() {
        return java.time.LocalDate.now().getYear();
    }
}