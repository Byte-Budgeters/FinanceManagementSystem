package application.Service;

import java.util.List;

import application.Model.Expense;
import application.Repository.ExpenseRepository;

public class ExpenseService {
	
	
	 public Expense getExpenseById(int id) {
	        return new ExpenseRepository().getExpenseById(id);
	    }
	    
	    public List<Expense> getAllExpenses() {
	        return new ExpenseRepository().getAllExpenses(); 
	    }
	    
	    public List<Expense> getExpensesByUserId(int userId) {
	        return new ExpenseRepository().getExpensesByUserId(userId); 
	    }
	    public List<Expense> getExpensesByCategory(String category) {
	        return new ExpenseRepository().getExpensesByCategory(category); 
	    }

    public boolean addExpense(Expense expense) {
        if (expense.getExpenseAmount() <= 0) {
            System.err.println("Expense amount must be greater than zero.");
            return false;
        }

        if (expense.getCategory() == null || expense.getCategory().isEmpty()) {
            System.err.println("Category cannot be empty.");
            return false;
        }

        return new ExpenseRepository().saveExpense(expense); 
    }

    public boolean removeExpense(int expenseId) {
        return new ExpenseRepository().deleteExpense(expenseId); 
    }

    
    public boolean modifyExpense(Expense expense) {
        return new ExpenseRepository().updateExpense(expense); 
    }
    public List<Expense> getExpensesByUserIdAndMonthAndYear(int userId, int month, int year){
    	return new ExpenseRepository().getExpensesByUserIdAndMonthAndYear(userId, month, year);
    }
}
