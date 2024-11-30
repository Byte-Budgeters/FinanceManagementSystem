package application.Model;

import java.util.Date;

public class Budget {
	private int id;
    private int userId;
    private String budgetCategory;
    private float budgetAmount;
    private String budgetDescription;
    private Date budgetDate;
    private Date createdAt;

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
    
    @Override
    public String toString() {
        return "Expense{" +
               "id=" + id +
               ", userId=" + userId +
               ", category='" + budgetCategory + '\'' +
               ", expenseAmount=" + budgetAmount +
               ", description='" + budgetDescription + '\'' +
               ", expenseDate=" + budgetDate +
               ", createdAt=" + createdAt +
               '}';
    }
}
