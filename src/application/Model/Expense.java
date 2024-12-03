package application.Model;

import java.util.Date;

public class Expense {
    private int id;
    private int userIdFK;
    private String category;
    private float expenseAmount;
    private String description;
    private Date expenseDate;
    private Date createdAt;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserIdFK() {
        return userIdFK;
    }

    public void setUserIdFK(int userIdFK) {
        this.userIdFK = userIdFK;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(float amount) {
        this.expenseAmount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
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
               ", userIdFK=" + userIdFK +
               ", category='" + category + '\'' +
               ", expenseAmount=" + expenseAmount +
               ", description='" + description + '\'' +
               ", expenseDate=" + expenseDate +
               ", createdAt=" + createdAt +
               '}';
    }
}
