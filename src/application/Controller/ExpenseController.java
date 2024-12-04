package application.Controller;

import application.Model.ExpenseControl;

import java.util.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import application.Model.Expense;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import application.Service.ExpenseService;
import application.Resources.UserSession;
import application.ExpenseView;

public class ExpenseController implements ExpenseControl {
	@FXML
	TextField amountInput;
	@FXML
	TextField descriptionInput;
	@FXML
	Button saveBtn;
	@FXML
	Button cancelBtn;
	@FXML
	Label msgLabel;
	@FXML
	MenuItem groceries;
	@FXML
	MenuItem rent;
	@FXML
	MenuItem utilities;
	@FXML
	MenuItem commute;
	@FXML
	MenuItem food;
	@FXML
	MenuItem entertainment;
	@FXML
	MenuButton expenseTypeMenu;
	@FXML
	DatePicker expenseDateInput;
	
	private static int editIndex = -1;
	private static Expense editExpense;
	
	public void setData(Expense expense, int index) {
		editIndex = index;
		expenseTypeMenu.setText(expense.getCategory() );
		amountInput.setText(expense.getExpenseAmount()+"");
		descriptionInput.setText(expense.getDescription());
		
		LocalDate dateLocal = Instant.ofEpochMilli(expense.getExpenseDate().getTime())
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
		expenseDateInput.setValue(dateLocal);
		editExpense = expense;
		msgLabel.setText("");
	}
	
	public void clickCancel() {
		Stage stage = (Stage) cancelBtn.getScene().getWindow();
		System.out.println("cancel");
		stage.setTitle("Expense Tracker");
		Stage currentStage = (Stage) cancelBtn.getScene().getWindow();
		currentStage.close();
	}
	
	public void clickSave() {
		Stage currentStage = (Stage) saveBtn.getScene().getWindow();
//		System.out.println("save");
		
		String expenseType = expenseTypeMenu.getText();
		String description = descriptionInput.getText();
		String amountText = amountInput.getText();
		
		if (description.isEmpty() || expenseType.toString() == "Choose Type" || expenseType.isEmpty() || amountText.isEmpty()) {
			msgLabel.setText("Please fill out all the fields.");
			return;
		}
		
		float amount;
		 try {
			  amount =  Float.parseFloat(amountText);
		        
		    } catch (NumberFormatException e) {
		    	msgLabel.setText("Amount must be a number!");
		       return;
		    }
		
		
//		System.out.println(expenseType+"0");
		
		LocalDate selectedDate = expenseDateInput.getValue();
		if (selectedDate == null) {
			msgLabel.setText("Please enter a date.");
			return;
		}
		
		Date date = java.sql.Date.valueOf(selectedDate);
		
		 		
		Expense expense;
		
		if (editIndex == -1) {
			expense = new Expense();
			expense.setUserIdFK(UserSession.getUserID());
			expense.setCreatedAt(new Date());
		} else {
			expense = editExpense;
		}
		
		expense.setCategory(expenseType);
		expense.setDescription(description);
		expense.setExpenseAmount(amount);
		expense.setExpenseDate(date);
		
		ExpenseService expenseService = new ExpenseService();
		
		if (editIndex != -1) {
			System.out.println(expense.getId());
			boolean res = expenseService.modifyExpense(expense);
			if (!res) {
				msgLabel.setText("Something went wrong.");
				return;
			}
			ExpenseView.setExpenses(editIndex, expense);
			editIndex = -1;
		} else {
			
			boolean res = expenseService.addExpense(expense);
			if (!res) {
				msgLabel.setText("Something went wrong.");
				return;
			}
			ExpenseView.addExpenses(expense);
		}
		
		currentStage.close();
	}
	
	public void clickGroceries() {
//		System.out.println("groceries");
		expenseTypeMenu.setText(groceries.getText());
	}
	
	public void clickRent() {
//		System.out.println("rent");
		expenseTypeMenu.setText(rent.getText());
	}
	
	public void clickUtilities() {
//		System.out.println("utilities");
		expenseTypeMenu.setText(utilities.getText());
	}
	
	public void clickCommute() {
//		System.out.println("commute");
		expenseTypeMenu.setText(commute.getText());
	}
	
	public void clickFood() {
//		System.out.println("food");
		expenseTypeMenu.setText(food.getText());
	}
	
	public void clickEntertainment() {
//		System.out.println("entertainment");
		expenseTypeMenu.setText(entertainment.getText());
	}
}
