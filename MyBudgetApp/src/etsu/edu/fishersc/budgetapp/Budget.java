package etsu.edu.fishersc.budgetapp;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Budget
{
	private double budgetAmount;
	private double remainingAmount;
	private  Calendar budgetStartDate;
	private  int budgetPeriod;
	private  Calendar budgetEndDate;
	private ArrayList<String> categorys;
	
	
	Budget()
	{
		budgetAmount = 0;
		remainingAmount = 0;
		categorys = new ArrayList<String>();
		budgetStartDate = null;
		budgetPeriod = 0;
		categorys.add("Income");
		categorys.add("Coffee");
		categorys.add("Entertainment");
		categorys.add("Groceries");
		categorys.add("Gas");
		categorys.add("Bills");
	}
	
	Budget(double amount, String date, int period)
	{
		budgetAmount = amount;
		remainingAmount = amount;
		categorys = new ArrayList<String>();
		budgetPeriod = period;
		String[] parts = date.split("/");
		budgetStartDate= Calendar.getInstance();
		budgetStartDate.set(Integer.parseInt(parts[2]), Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
		budgetEndDate = Calendar.getInstance();
	}
	
	public void setBudgetStartDate(String date)
	{
		String[] parts = date.split("/");
		budgetStartDate.set(Integer.parseInt(parts[2]), Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
	}
	
	public void setBudgetPeriod(String Period)
	{
		String[] parts = Period.split(" ");
		int temp = Integer.parseInt(parts[0]);
		budgetPeriod = temp;
	}
	
	public void setBudgetEndDate(String date)
	{
		String[] parts = date.split("/");
		budgetEndDate.add(Calendar.MONTH, budgetPeriod);
	}
	public void setBudgetAmount(double amount)
	{
		budgetAmount = amount;
		remainingAmount = amount;
	
	}
	
	public double getBudgetAmount()
	{
		return budgetAmount;
	}
	
	public double getRemainingtAmount()
	{
		return remainingAmount;
	}
	
	public String getBudgetStartDate()
	{
		String format = "";
		format += budgetStartDate.get(Calendar.MONTH) + "/";
		format += budgetStartDate.get(Calendar.DAY_OF_MONTH) + "/";
		format += budgetStartDate.get(Calendar.YEAR);
		return format;
	}
	
	public String getBudgetEndDate()
	{
		String format = "";
		format += budgetEndDate.get(Calendar.MONTH) + "/";
		format += budgetEndDate.get(Calendar.DAY_OF_MONTH) + "/";
		format += budgetEndDate.get(Calendar.YEAR);
		return format;
	}
	
	public void addItem(BudgetItem item)
	{
		remainingAmount -= item.getAmount();
	}
	
	public ArrayList<String> getCategorys()
	{
		return categorys;
	}
	
	public void addCategory(String category)
	{
		categorys.add(category);
	}
	
	public void setCategorys(ArrayList<String> cat)
	{
		categorys = cat;
	}
	
	public int getCategoryCount()
	{
		return categorys.size();
	}
	
	public String getCategory(int index)
	{
		return categorys.get(index);
	}
	
	public void removeCategory(int index)
	{
		categorys.remove(index);
	}
	
	public void Clear()
	{
		budgetAmount = 0;
		remainingAmount = 0;
		categorys = new ArrayList<String>();
		budgetStartDate = null;
		budgetPeriod = 0;
		categorys.add("Coffee");
		categorys.add("Entertainment");
		categorys.add("Groceries");
		categorys.add("Gas");
		categorys.add("Bills");
	}
	
	public String getAmountLeftInPeriod()
	{
		Calendar tempCal = Calendar.getInstance();
		int temp = tempCal.get(Calendar.MONTH);
		int tempEndMonth = budgetEndDate.get(Calendar.MONTH);
		return  tempEndMonth - temp + "Months";
		
	}

	


}//end class Budget

