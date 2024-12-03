package application.Controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import application.Model.Budgeting;
import application.Manager.BudgetManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;

public class BudgetController {

    @FXML
    private TableView<Budgeting> budgetTable;
    @FXML
    private TableColumn<Budgeting, String> categoryColumn;
    @FXML
    private TableColumn<Budgeting, Double> limitColumn;
    @FXML
    private TableColumn<Budgeting, Double> spendingColumn;
    @FXML
    private TableColumn<Budgeting, Double> remainingColumn;

    @FXML
    private ComboBox<String> categoryDropdown;
    @FXML
    private TextField limitField;
    @FXML
    private PieChart spendingChart;

    private BudgetManager budgetManager = new BudgetManager();

    @FXML
    public void initialize() {
        // Bind table columns
        // New column for date and time

        categoryColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCategory()));
        limitColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getLimit()));
        spendingColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getCurrentSpending()));
        remainingColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getRemainingBudget()));

        budgetTable.setItems(budgetManager.getBudgets());

        // Initialize category dropdown
        categoryDropdown.setItems(FXCollections.observableArrayList("Groceries", "Food", "Travel", "Food", "Entertainment", "Utilities", "Other"));

        // Load saved budgets
        budgetManager.loadBudgets();
        updateSpendingChart();
    }

    @FXML
    private void handleAddBudget() {
        // Validate inputs
        String category = categoryDropdown.getValue();
        if (category == null || category.isEmpty()) {
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

        // Add budget and clear inputs
        budgetManager.addBudget(category, limit);
        limitField.clear();
        categoryDropdown.getSelectionModel().clearSelection();

        // Update spending chart
        updateSpendingChart();
    }

    @FXML
    private void handleDeleteBudget() {
        Budgeting selectedBudget = budgetTable.getSelectionModel().getSelectedItem();
        if (selectedBudget == null) {
            showError("Please select a budget to delete.");
            return;
        }

        // Delete the selected budget
        budgetManager.deleteBudget(selectedBudget);
        updateSpendingChart();
    }

    private void updateSpendingChart() {
        spendingChart.getData().clear();
        for (Budgeting budget : budgetManager.getBudgets()) {
            spendingChart.getData().add(new PieChart.Data(budget.getCategory(), budget.getCurrentSpending()));
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}