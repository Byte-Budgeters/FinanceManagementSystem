package application.Model;

import java.io.Serializable;

public class Budgeting implements Serializable {
    private static final long serialVersionUID = 1L;

    private String category;
    private double limit;
    private double currentSpending;

    public Budgeting(String category, double limit) {
        this.category = category;
        this.limit = limit;
        this.currentSpending = 0.0; // Initialize spending to 0
    }

    // Getters
    public String getCategory() {
        return category;
    }

    public double getLimit() {
        return limit;
    }

    public double getCurrentSpending() {
        return currentSpending;
    }

    public double getRemainingBudget() {
        return limit - currentSpending;
    }

    // Add spending
    public void addSpending(double amount) {
        this.currentSpending += amount;
    }
}