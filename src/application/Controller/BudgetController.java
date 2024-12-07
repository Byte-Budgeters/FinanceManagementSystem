package application.Controller;

import application.Model.Budget;
import application.Manager.BudgetManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;

import java.util.HashMap;
import java.util.Map;

public class BudgetController {

    @FXML
    private TableView<Budget> budgetTable;
    @FXML
    private TableColumn<Budget, String> budgetCategoryColumn;
    @FXML
    private TableColumn<Budget, Double> limitColumn;
    @FXML
    private TableColumn<Budget, Double> spendingColumn;
    @FXML
    private TableColumn<Budget, Double> remainingColumn;

    @FXML
    private ComboBox<String> categoryDropdown;
    @FXML
    private TextField limitField;
    @FXML
    private PieChart spendingChart;

    private BudgetManager budgetManager = new BudgetManager();

    @FXML
    public void initialize() {
        // Initialize table columns
        budgetCategoryColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getBudgetCategory()));
        limitColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getLimit()));
        spendingColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getCurrentSpending()));
        remainingColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getRemainingBudget()));

        // Load data into the TableView
        budgetTable.setItems(budgetManager.getBudgets());
        
        budgetTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updatePieChartForCategory(newSelection);
            }
        });


        // Populate the category dropdown
        categoryDropdown.setItems(FXCollections.observableArrayList("Groceries", "Utilities", "Rent", "Commute", "Food", "Entertainment"));

        // Update the spending chart
        updateSpendingChart();

        // Restrict limit field to numeric input
        limitField.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*(\\.\\d*)?")) {
                limitField.setText(oldText);
            }
        });
    }

    private void updateSpendingChart() {
        spendingChart.getData().clear();
        Map<String, Double> spendingByCategory = new HashMap<>();
        for (Budget budget : budgetManager.getBudgets()) {
            spendingByCategory.merge(budget.getBudgetCategory(), budget.getCurrentSpending(), Double::sum);
        }
        spendingByCategory.forEach((category, spending) ->
            spendingChart.getData().add(new PieChart.Data(category, spending)));
    }
    
    private void updatePieChartForCategory(Budget budget) {
        spendingChart.getData().clear();

        // Add "Spent" segment
        PieChart.Data spentSegment = new PieChart.Data("Spent", budget.getCurrentSpending());

        // Add "Remaining" segment
        PieChart.Data remainingSegment = new PieChart.Data("Remaining", budget.getRemainingBudget());

        spendingChart.getData().addAll(spentSegment, remainingSegment);
    }

    @FXML
    private void handleAddBudget() {
        String budgetCategory = categoryDropdown.getValue();
        if (budgetCategory == null || budgetCategory.isEmpty()) {
            showError("Please select a category.");
            return;
        }

        String limitText = limitField.getText();
        double limit;
        try {
            limit = Double.parseDouble(limitText);
        } catch (NumberFormatException e) {
            showError("Please enter a valid number for the budget limit.");
            return;
        }

        budgetManager.addBudget(budgetCategory, limit);

        // Clear fields and update UI
        limitField.clear();
        categoryDropdown.getSelectionModel().clearSelection();
        budgetTable.setItems(budgetManager.getBudgets());
        updateSpendingChart();
    }

    @FXML
    private void handleModifyBudget() {
        Budget selectedBudget = budgetTable.getSelectionModel().getSelectedItem();
        if (selectedBudget == null) {
            showError("Please select a budget to modify.");
            return;
        }

        String newLimitText = limitField.getText();
        double newLimit;
        try {
            newLimit = Double.parseDouble(newLimitText);
        } catch (NumberFormatException e) {
            showError("Please enter a valid number for the budget limit.");
            return;
        }

        selectedBudget.setLimit(newLimit);
        budgetManager.modifyBudget(selectedBudget);

        // Refresh table and chart
        budgetTable.refresh();
        updateSpendingChart();
    }

    @FXML
    private void handleDeleteBudget() {
        Budget selectedBudget = budgetTable.getSelectionModel().getSelectedItem();
        if (selectedBudget == null) {
            showError("Please select a budget to delete.");
            return;
        }

        budgetManager.deleteBudget(selectedBudget);

        // Refresh table and chart
        budgetTable.setItems(budgetManager.getBudgets());
        updateSpendingChart();
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}