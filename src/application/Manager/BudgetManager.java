package application.Manager;

import application.Model.Budgeting;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetManager {
    private static final String FILE_PATH = "budgets.dat";
    private ObservableList<Budgeting> budgets = FXCollections.observableArrayList();

    public ObservableList<Budgeting> getBudgets() {
        return budgets;
    }

    public void addBudget(String category, double limit) {
        budgets.add(new Budgeting(category, limit));
        saveBudgets();
    }

    public void deleteBudget(Budgeting budget) {
        budgets.remove(budget);
        saveBudgets();
    }

    public void loadBudgets() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            // Load data as a standard list
            List<Budgeting> loadedBudgets = (List<Budgeting>) ois.readObject();
            // Convert the list to an ObservableList
            budgets.setAll(loadedBudgets);
        } catch (FileNotFoundException e) {
            // File not found: no saved data exists yet
            System.out.println("No existing budget data found.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveBudgets() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            // Convert ObservableList to a standard ArrayList for serialization
            List<Budgeting> serializableList = new ArrayList<>(budgets);
            oos.writeObject(serializableList);
            System.out.println("Budgets saved successfully to " + FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving budgets to file.");
        }
    }
}