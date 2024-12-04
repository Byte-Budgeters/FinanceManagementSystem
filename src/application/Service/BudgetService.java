package application.Service;

import java.util.List;

import application.Model.Budget;
import application.Repository.BudgetRepository;

public class BudgetService {
	private BudgetRepository budgetRepository = new BudgetRepository();
	
    public Budget getBudgetById(int id) {
        return budgetRepository.getBudgetById(id);
    }

    public List<Budget> getAllBudget() {
        return budgetRepository.getAllBudget();
    }

    public List<Budget> getBudgetByUserId(int userId) {
        return budgetRepository.getBudgetByUserId(userId);
    }

    public boolean addBudget(Budget budget) {
        // Validate the limit field instead of budgetAmount
        if (budget.getLimit() <= 0) {
            System.err.println("Budget limit must be greater than zero.");
            return false;
        }
        if (budget.getBudgetCategory() == null || budget.getBudgetCategory().isEmpty()) {
            System.err.println("Budget Category cannot be empty.");
            return false;
        }
        return budgetRepository.saveBudget(budget);
    }

    public boolean removeBudget(int budgetId) {
        return budgetRepository.deleteBudget(budgetId);
    }

    public boolean modifyBudget(Budget budget) {
    	if (budget.getLimit() <= 0) {
            System.err.println("Budget limit must be greater than zero.");
            return false;
        }
        return budgetRepository.updateBudget(budget);
    }
}