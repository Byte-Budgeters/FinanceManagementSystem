package application.Controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import application.Model.Budget;
import application.Manager.BudgetManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;

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
        budgetCategoryColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getBudgetCategory()));
        limitColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getLimit()));
        spendingColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getCurrentSpending()));
        remainingColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getRemainingBudget()));

        budgetTable.setItems(budgetManager.getBudgets());

        categoryDropdown.setItems(FXCollections.observableArrayList("Groceries", "Food", "Travel", "Entertainment", "Utilities", "Other"));

        budgetManager.loadBudgets();
        updateSpendingChart();
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
        limitField.clear();
        categoryDropdown.getSelectionModel().clearSelection();

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

        // Update the selected budget's limit
        selectedBudget.setLimit(newLimit);
        budgetManager.saveBudgets(); // Save the changes

        // Refresh the table
        budgetTable.refresh();

        // Clear input fields
        limitField.clear();
        categoryDropdown.getSelectionModel().clearSelection();

        // Update the Pie Chart
        updateSpendingChart();

        System.out.println("Budget modified successfully.");
    }

    @FXML
    private void handleDeleteBudget() {
        Budget selectedBudget = budgetTable.getSelectionModel().getSelectedItem();
        if (selectedBudget == null) {
            showError("Please select a budget to delete.");
            return;
        }

        budgetManager.deleteBudget(selectedBudget);
        updateSpendingChart();
    }

    private void updateSpendingChart() {
        spendingChart.getData().clear();
        for (Budget budget : budgetManager.getBudgets()) {
            spendingChart.getData().add(new PieChart.Data(budget.getBudgetCategory(), budget.getCurrentSpending()));
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}