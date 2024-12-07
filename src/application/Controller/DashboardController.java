package application.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

import application.Model.ExpenseSummary;
import application.Repository.ExpenseRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class DashboardController {

	@FXML
    private Label totalExpensesLabel;

    @FXML
    private Label averageExpensesLabel;

    @FXML
    private Label highestExpenseLabel;

    @FXML
    private Label lowestExpenseLabel;
    
    @FXML
    private ComboBox<String> monthYearDropdown;
    
   

    @FXML
    public void initialize() throws ParseException {
    	 LocalDate currentDate = LocalDate.now();
    	    int currentYear = currentDate.getYear();
    	    int currentMonth = currentDate.getMonthValue();

    	    // Populate ComboBox with month-year values
    	    for (int month = 1; month <= 12; month++) {
    	        String monthName = LocalDate.of(currentYear, month, 1)
    	            .getMonth()
    	            .getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    	        monthYearDropdown.getItems().add(monthName + " - " + currentYear);
    	    }
    	    String currentMonthYear = currentDate.getMonth()
    	            .getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    	            + " - " + currentYear;
    	        monthYearDropdown.setValue(currentMonthYear);

    	        // Set listener for dropdown selection
    	        monthYearDropdown.setOnAction(e -> {
					try {
						handleDropdownSelection();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
    	        handleDropdownSelection();
    }

    public void updateMetrics(double totalExpenses, double averageExpenses, String highestCategory, double highestAmount, String lowestCategory, double lowestAmount) {
        
    	
    	totalExpensesLabel.setText("$" + String.format("%.2f", totalExpenses));
        averageExpensesLabel.setText("$" + String.format("%.2f", averageExpenses));
        highestExpenseLabel.setText(highestCategory + ": $" + String.format("%.2f", highestAmount));
        lowestExpenseLabel.setText(lowestCategory + ": $" + String.format("%.2f", lowestAmount));
    }
    private void handleDropdownSelection() throws ParseException {
        String selectedOption = monthYearDropdown.getValue();
        if (selectedOption != null) {
           
            // Add logic to update metrics based on the selected month and year
            ExpenseRepository expenseRepository = new ExpenseRepository();
            ExpenseSummary expenseSummary = new ExpenseSummary();
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM - yyyy");
            Date date = sdf.parse(selectedOption);

            // Extract the month and year from the Date object
            int month = date.getMonth() + 1; // getMonth() returns 0-based month, so adding 1
            int year = 1900 + date.getYear(); // getYear() returns year since 1900

            expenseRepository.getMonthlyExpenseSummaryAndAverageForLast3Months(expenseSummary, year, month);
            
           
            if ((Double)expenseSummary.getTotalExpenseAmount() == null) {
            	totalExpensesLabel.setText("Data not Avaliable");
            } else {
            	totalExpensesLabel.setText(String.format("%.2f", expenseSummary.getTotalExpenseAmount()));
            }
            if ((Double)expenseSummary.getAvgThreeMonthExpense() == null) {
            	averageExpensesLabel.setText("Data not Avaliable");
            } else {
            	averageExpensesLabel.setText(String.format("%.2f", expenseSummary.getAvgThreeMonthExpense()));
            }
            
            if ((Double)expenseSummary.getMaxExpenseAmount() == null) {
            	highestExpenseLabel.setText("Data not Avaliable");
            } else {
            	highestExpenseLabel.setText(String.format("%.2f", expenseSummary.getMaxExpenseAmount()));
            }
            if ((Double)expenseSummary.getMinExpenseAmount() == null) {
            	lowestExpenseLabel.setText("Data not Avaliable");
            } else {
            	lowestExpenseLabel.setText(String.format("%.2f", expenseSummary.getMinExpenseAmount()));
            }
           // highestExpenseLabel.setText(expenseSummary.getMaxExpenseCategory() + ":" + String.format("%.2f", expenseSummary.getMaxExpenseAmount()));
            //lowestExpenseLabel.setText(expenseSummary.getMinExpenseCategory() + ":" + String.format("%.2f", expenseSummary.getMinExpenseAmount()));
            
        }
    }

}
