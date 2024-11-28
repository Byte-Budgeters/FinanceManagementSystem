package application;

import java.util.Date;
import java.util.List;

import application.Model.Expense;
import application.Service.ExpenseService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
    	System.out.println(getClass().getResource("/application/View/Test.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Finance Tracker System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        
       // ExpenseService service = new ExpenseService();

        
        // Create an expense
//        Expense expense = new Expense();
//        expense.setUserIdFK(1);
//        expense.setCategory("Food");
//        expense.setExpenseAmount(15.50f);
//        expense.setDescription("Lunch at a cafe");
//        expense.setExpenseDate(new Date());
//
//        // Add the expense
//        boolean isAdded = service.addExpense(expense);
//        System.out.println("Expense added: " + isAdded);

        // Modify the expense (example)
 //       expense.setId(1);
//        expense.setDescription("Dinner at a restaurant");
//        boolean isModified = service.modifyExpense(expense);
//        System.out.println("Expense modified: " + isModified);

        // Remove the expense
//        boolean isRemoved = service.removeExpense(expense.getId());
//        System.out.println("Expense removed: " + isRemoved);
        
//        Expense expense = service.getExpenseById(2);
//        System.out.println(expense.toString());
//        
//        List<Expense> expenses = service.getExpensesByUserId(1);
//        for(Expense e: expenses) {
//        	System.out.println(e.toString());
//        }
//        List<Expense> expensesAll = service.getAllExpenses();
//        for(Expense e: expensesAll) {
//        	System.out.println(e.toString());
//        }
    }
}