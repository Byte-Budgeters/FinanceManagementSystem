package application.Model;

public class ExpenseSummary {
	
	private double maxExpenseAmount;
	
	private double minExpenseAmount;
	
	private double totalExpenseAmount;
	
	private double avgThreeMonthExpense;
	
	private String maxExpenseCategory;
	
	private String minExpenseCategory;
	
	
	
	public double getMaxExpenseAmount() {
		return maxExpenseAmount;
	}

	public void setMaxExpenseAmount(double maxExpenseAmount) {
		this.maxExpenseAmount = maxExpenseAmount;
	}

	public double getMinExpenseAmount() {
		return minExpenseAmount;
	}

	public void setMinExpenseAmount(double minExpenseAmount) {
		this.minExpenseAmount = minExpenseAmount;
	}

	public double getTotalExpenseAmount() {
		return totalExpenseAmount;
	}

	public void setTotalExpenseAmount(double totalExpenseAmount) {
		this.totalExpenseAmount = totalExpenseAmount;
	}

	public double getAvgThreeMonthExpense() {
		return avgThreeMonthExpense;
	}

	public void setAvgThreeMonthExpense(double avgThreeMonthExpense) {
		this.avgThreeMonthExpense = avgThreeMonthExpense;
	}

	public String getMaxExpenseCategory() {
		return maxExpenseCategory;
	}

	public void setMaxExpenseCategory(String maxExpenseCategory) {
		this.maxExpenseCategory = maxExpenseCategory;
	}

	public String getMinExpenseCategory() {
		return minExpenseCategory;
	}

	public void setMinExpenseCategory(String minExpenseCategory) {
		this.minExpenseCategory = minExpenseCategory;
	}

	
	@Override
	public String toString() {
	    return "ExpenseSummary{" +
	            "maxExpenseAmount=" + maxExpenseAmount +
	            ", minExpenseAmount=" + minExpenseAmount +
	            ", totalExpenseAmount=" + totalExpenseAmount +
	            ", avgThreeMonthExpense=" + avgThreeMonthExpense +
	            ", maxExpenseCategory='" + maxExpenseCategory + '\'' +
	            ", minExpenseCategory='" + minExpenseCategory + '\'' +
	            '}';
	}
	
	
	

}
