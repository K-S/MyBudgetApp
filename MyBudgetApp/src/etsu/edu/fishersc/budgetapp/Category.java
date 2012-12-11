package etsu.edu.fishersc.budgetapp;


public class Category
{
	private String name; //holds name of category
	private double amount;  //holds amount of all transactions in category
	private float percentOfTotalBudget; //holds percent of total budget
	
	//default constructor
	Category()
	{
		name = "";
		amount = 0.0;
		percentOfTotalBudget = 0;
	}//end Category()
	
	//Parameterized Constructor
	Category(String categoryName, double categoryAmount, float percent)
	{
		name = categoryName;
		amount = categoryAmount;
		percentOfTotalBudget = percent;
	}//end Category(String,double,float)
	
	public String getName()
	{
		return name;
	}//end getName
	
	public void setName(String newName)
	{
		name = newName;
	}//end setName
	
	public double getAmount()
	{
		return amount;
	}//end getAmount
	
	public void setAmount(double newAmount)
	{
		amount = newAmount;
	}//end setAmount
	
	public float getPercentOfTotalBudget()
	{
		return percentOfTotalBudget;
	}//end getPercentOfTotalBudget
	
	public void setpercentOfTotalBudget(float newPercent)
	{
		percentOfTotalBudget = newPercent;
	}//end setPercentOfTotalBudget
	


}//end class Category
