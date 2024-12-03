package application;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import application.Controller.ExpenseController;
import application.Model.Expense;
import application.Service.ExpenseService;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ExpenseList {
	
	public HBox initializeUI(ObservableList<Expense> expenses) {
		try {
			
			
			TableView<Expense> table = new TableView<>();
			    
		    HBox root = new HBox();
	        root.setPrefWidth(600);
	       
	        TableColumn<Expense, String> typeCol = new TableColumn<>("Type");
	        typeCol.setCellValueFactory(new PropertyValueFactory<>("category"));

	        TableColumn<Expense, Double> amountCol = new TableColumn<>("Amount");
	        amountCol.setCellValueFactory(new PropertyValueFactory<>("expenseAmount"));

	        TableColumn<Expense, String> descriptionCol = new TableColumn<>("Description");
	        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
	        
	        TableColumn<Expense, Date> dateCol = new TableColumn<>("Expense Date");
	        dateCol.setCellValueFactory(new PropertyValueFactory<>("expenseDate"));
	        

	        dateCol.setCellFactory(column -> {
	            return new TableCell<Expense, Date>() {
	                @Override
	                protected void updateItem(Date item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (item == null || empty) {
	                        setText(null);
	                    } else {
	                    	Calendar calendar = Calendar.getInstance();
	                    	calendar.setTime(item);

	                    	int year = calendar.get(Calendar.YEAR);
	                    	int month = calendar.get(Calendar.MONTH) + 1;
	                    	int day = calendar.get(Calendar.DAY_OF_MONTH);
	                    	
	                    	setText(month+"/"+day+"/"+year);
	                    }
	                }
	            };
	        });

	        TableColumn<Expense, Void> editCol = new TableColumn<>("Edit");
	        editCol.setCellFactory(param -> new TableCell<>() {
	            private final Button editButton = new Button("Edit");

	            {
	                editButton.setOnAction(event -> {
	                    Expense expense = getTableView().getItems().get(getIndex());
	                    openEditStage(expense, getIndex());
	                });
	            }

	            @Override
	            protected void updateItem(Void item, boolean empty) {
	                super.updateItem(item, empty);
	                if (empty) {
	                    setGraphic(null);
	                } else {
	                    setGraphic(editButton);
	                }
	            }
	        });

	        TableColumn<Expense, Void> deleteCol = new TableColumn<>("Delete");
	        deleteCol.setCellFactory(param -> new TableCell<>() {
	            private final Button deleteButton = new Button("Delete");

	            {
	                deleteButton.setOnAction(event -> {
	                    Expense expense = getTableView().getItems().get(getIndex());
	                    expenses.remove(expense);
	                    ExpenseService expenseService =  new ExpenseService();
	                    expenseService.removeExpense(expense.getId());
	                });
	            }

	            @Override
	            protected void updateItem(Void item, boolean empty) {
	                super.updateItem(item, empty);
	                if (empty) {
	                    setGraphic(null);
	                } else {
	                    setGraphic(deleteButton);
	                }
	            }
	        });

	        table.getColumns().addAll(typeCol, amountCol, descriptionCol, dateCol,editCol, deleteCol);
	        table.setItems(expenses);

	        root.getChildren().add(table);
	        
	        return root;
		} catch(Exception e) {
			e.printStackTrace();
		}
        return null;
    }
	
	private void openEditStage(Expense expense, int index) {
	        
        Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/View/CreateEditExpense.fxml"));
			root = loader.load();
			ExpenseController controller = loader.getController();
			controller.setData(expense, index);
			
			Scene scene = new Scene(root,700,500);
			Stage newStage = new Stage();
			newStage.setTitle("Edit Expense");
			newStage.setScene(scene);
			newStage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
	
	

}
