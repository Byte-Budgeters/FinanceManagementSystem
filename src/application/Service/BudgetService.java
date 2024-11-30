package application.Service;

import java.util.List;

import application.Model.Budget;
import application.Repository.BudgetRepository;

public class BudgetService {
	public Budget getBudgetById(int id) {
		return new BudgetRepository().getBudgetById(id);
	}

	public List<Budget> getAllBudget() {
		return new BudgetRepository().getAllBudget();
	}

	public List<Budget> getBudgetByUserId(int userId) {
		return new BudgetRepository().getBudgetByUserId(userId);
	}

	public boolean addBudget(Budget budget) {
		if (budget.getBudgetAmount() <= 0) {
			System.err.println("Budget amount must be greater than zero.");
			return false;
		}

		if (budget.getBudgetCategory() == null || budget.getBudgetCategory().isEmpty()) {
			System.err.println("Budget Category cannot be empty.");
			return false;
		}

		return new BudgetRepository().saveBudget(budget);
	}

	public boolean removeBudget(int budgetId) {
		return new BudgetRepository().deleteBudget(budgetId);
	}

	public boolean modifyBudget(Budget budget) {
		return new BudgetRepository().updateBudget(budget);
	}
}
