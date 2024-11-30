package application;

import java.util.Date;
import java.util.List;

import application.Model.Budget;
import application.Model.Expense;
import application.Service.BudgetService;
import application.Service.ExpenseService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
    	//System.out.println(getClass().getResource("/application/View/Test.fxml"));
    	Parent root = FXMLLoader.load(getClass().getResource("/application/View/login.fxml"));
    	// Parent root = FXMLLoader.load(getClass().getResource("./View/login.fxml"));
        Scene scene = new Scene(root,600,600);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setTitle("Finance Tracker System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
       // launch(args);
        
         ExpenseService service = new ExpenseService();
         BudgetService budgetservice = new BudgetService();

//        
//        //  Create an expense
//         Expense expense = new Expense();
//         expense.setUserIdFK(1);
//         expense.setCategory("Food");
//         expense.setExpenseAmount(15.50f);
//         expense.setDescription("Lunch at a cafe");
//         expense.setExpenseDate(new Date());
// 
//        //   Add the expense
//         boolean isAdded = service.addExpense(expense);
//         System.out.println("Expense added: " + isAdded);
//
//        //  Modify the expense (example)
//         expense.setId(1);
//         expense.setDescription("Dinner at a restaurant");
//         boolean isModified = service.modifyExpense(expense);
//         System.out.println("Expense modified: " + isModified);
//
//        //  Remove the expense
//         boolean isRemoved = service.removeExpense(expense.getId());
//         System.out.println("Expense removed: " + isRemoved);
//        
//         Expense expense1 = service.getExpenseById(2);
//         System.out.println(expense1.toString());
//         
//         List<Expense> expenses = service.getExpensesByUserId(1);
//         for(Expense e: expenses) {
//         	System.out.println(e.toString());
//         }
//         List<Expense> expensesAll = service.getAllExpenses();
//         for(Expense e: expensesAll) {
//         	System.out.println(e.toString());
//         }
         
     //  Create an expense
         Budget budget = new Budget();
         budget.setUserId(1);
         budget.setBudgetCategory("Food");
         budget.setBudgetAmount(15.50f);
         budget.setBudgetDescription("Lunch at a cafe");
         budget.setBudgetDate(new Date());
 
        //   Add the expense
         boolean isBudgetAdded = budgetservice.addBudget(budget);
         System.out.println("Expense added: " + isBudgetAdded);

        //  Modify the expense (example)
         budget.setId(1);
         budget.setBudgetDescription("Dinner at a restaurant");
         boolean isBudgetModified = budgetservice.modifyBudget(budget);
         System.out.println("Expense modified: " + isBudgetModified);

        //  Remove the expense
         boolean isBudgetRemoved = service.removeExpense(budget.getId());
         System.out.println("Expense removed: " + isBudgetRemoved);
        
         Budget budget1 = budgetservice.getBudgetById(2);
         System.out.println(budget1.toString());
         
         List<Budget> budgets = budgetservice.getBudgetByUserId(1);
         for(Budget e: budgets) {
         	System.out.println(e.toString());
         }
         List<Budget> budgetAll = budgetservice.getAllBudget();
         for(Budget e: budgetAll) {
         	System.out.println(e.toString());
         }
    }
}