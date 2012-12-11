package etsu.edu.fishersc.budgetapp;

public class BudgetItem
{
	private String date;
	private String name;
	private double amount;
	private float percentOfTotalBudget;
	//private String transactionRecipent;
	private int cat_id;
	
	
	public BudgetItem()
	{
		date ="";
		name = "";
		amount = 0.0;
		percentOfTotalBudget = 0;
	}
	
	public BudgetItem(float percent, String itemName, double amount, int id)
	{
		
		date = "";
		name = itemName;
		amount = percent*amount;
		percentOfTotalBudget = percent;
		cat_id = id;
	}

	public BudgetItem(String itemDate, String itemName, double itemAmount, int recipent, double budgetAmount)
	{
		date = itemDate;
		name = itemName;
		amount = itemAmount;
		percentOfTotalBudget = (float)(itemAmount/budgetAmount);
		cat_id = recipent;
	}
	
	public void setAmount(double itemAmount)
	{
		amount = itemAmount;
	}
	
	public double getAmount()
	{
		return amount;
	}
	
	public void setPercentOfTotalBudget(float percent)
	{
		percentOfTotalBudget = percent;
	}
	
	public float getPercentOfTotalBudget()
	{
		return percentOfTotalBudget;
	}
	
	public void setItemName(String itemName)
	{
		name = itemName;
	}
	
	public String getItemName()
	{
		return name;
	}
	
	public void setCategoryId(int id)
	{
		cat_id = id;
	}
	
	public int getCategoryId()
	{
		return cat_id;
	}
	public String toString()
	{
		String info = "";
		info += "\nDate: " + date + "\nName: " + name + "\nAmount: " + amount;
		return info;
	}
	
	public String getDate()
	{
		return date;
	}
	public void setDate(String date2)
	{
		String[] date = date2.split("/");
	}
	
}