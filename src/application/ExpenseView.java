package application;

import application.Model.Expense;
import application.Service.ExpenseService;
import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ExpenseView {
	
	private static ObservableList<Expense> expenses = FXCollections.observableArrayList();
	
	public VBox initializeUI() {
		
		List<Expense> allExpenses = new ExpenseService().getAllExpenses();
		expenses.clear();
//		System.out.println("zzzzzzaaaaaaaa");
		expenses.addAll(allExpenses);

		
		
		 	VBox root = new VBox(20);
	        root.setPadding(new Insets(20, 10, 10, 10));
	        
	        Button createBtn = new Button();
	        createBtn.setText("Create Expense");
	        
	        createBtn.setOnAction(e -> {
	        	try {
	        		Stage newStage = new Stage();
	        		newStage.setTitle("Create Expense");
					Parent root2 = FXMLLoader.load(getClass().getResource("/application/View/CreateEditExpense.fxml"));
					Scene scene = new Scene(root2,700,500);
					newStage.setScene(scene);
					newStage.showAndWait();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        });
	
		ExpenseList expenseList = new ExpenseList();
		HBox expenseTable = expenseList.initializeUI(expenses);
		
		root.getChildren().addAll(createBtn, expenseTable);
		return root;
       
	}
	
	public static ObservableList<Expense> getExpenses() {
		return ExpenseView.expenses;
	}
	
	public static void setExpenses(int index, Expense updatedExpense) {
		expenses.set(index, updatedExpense);
	}
	
	public static void addExpenses(Expense newExpense) {
		expenses.add(newExpense);
	}
	
}


