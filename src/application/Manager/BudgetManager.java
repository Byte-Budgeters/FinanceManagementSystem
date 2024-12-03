package application.Manager;

import application.Model.Budget;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetManager {
    private static final String FILE_PATH = "budgets.dat";
    private ObservableList<Budget> budgets = FXCollections.observableArrayList();

    public ObservableList<Budget> getBudgets() {
        return budgets;
    }

    public void addBudget(String budgetCategory, double limit) {
        Budget newBudget = new Budget(budgetCategory, limit);
        budgets.add(newBudget);
        saveBudgets();
    }

    public void deleteBudget(Budget budget) {
        budgets.remove(budget);
        saveBudgets();
    }

    public void loadBudgets() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            List<Budget> loadedBudgets = (List<Budget>) ois.readObject();
            budgets.setAll(loadedBudgets);
        } catch (FileNotFoundException e) {
            System.out.println("No existing budget data found.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveBudgets() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            List<Budget> serializableList = new ArrayList<>(budgets);
            oos.writeObject(serializableList);
            System.out.println("Budgets saved successfully to " + FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving budgets to file.");
        }
    }
}