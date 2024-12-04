package application.Model;

import java.util.Date;

public class Budget {
    private int id;
    private int userId;
    private String budgetCategory;
    private float budgetAmount; // Retained for compatibility
    private String budgetDescription;
    private Date budgetDate;
    private Date createdAt;
    private double limit;
    private double currentSpending;

    public Budget() {
    	this.budgetDate = new Date();
        this.createdAt = new Date();
    }

    public Budget(String budgetCategory, double limit) {
        this.budgetCategory = budgetCategory;
        this.limit = limit;
        this.currentSpending = 0.0; // Initialize spending to 0
        this.budgetDate = new Date(); // Default to current date
        this.createdAt = new Date();
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBudgetCategory() {
        return budgetCategory;
    }

    public void setBudgetCategory(String budgetCategory) {
        this.budgetCategory = budgetCategory;
    }

    public float getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(float budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public String getBudgetDescription() {
        return budgetDescription;
    }

    public void setBudgetDescription(String budgetDescription) {
        this.budgetDescription = budgetDescription;
    }

    public Date getBudgetDate() {
        return budgetDate;
    }

    public void setBudgetDate(Date budgetDate) {
        this.budgetDate = budgetDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public double getCurrentSpending() {
        return currentSpending;
    }

    public void setCurrentSpending(double currentSpending) {
        this.currentSpending = currentSpending;
    }

    public double getRemainingBudget() {
        return limit - currentSpending;
    }

    // Add spending
    public void addSpending(double amount) {
        this.currentSpending += amount;
    }

    @Override
    public String toString() {
        return "Budget{" +
               "id=" + id +
               ", userId=" + userId +
               ", budgetCategory='" + budgetCategory + '\'' +
               ", budgetAmount=" + budgetAmount +
               ", description='" + budgetDescription + '\'' +
               ", limit=" + limit +
               ", currentSpending=" + currentSpending +
               ", budgetDate=" + budgetDate +
               ", createdAt=" + createdAt +
               '}';
    }
}