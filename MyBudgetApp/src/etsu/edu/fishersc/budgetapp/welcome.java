package etsu.edu.fishersc.budgetapp;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class welcome extends Activity
{
	private EditText AmountEditText;
	private Activity act;
	Budget myBudget = new Budget();
	private Button doneButton;
	LinearLayout myLinearLayout;
	LinearLayout myScrollView;
	ScrollView newScrollView;
	private static final String BUDGET_AMOUNT = "BUDGET_AMOUNT";
	private float BudgetAmount;
	private SharedPreferences preferences; // stores the high score
	ArrayList<String> categorys = new ArrayList<String>();
	EditText DateEditText;
	EditText PeriodEditText;
	//Intent welcomeIntent = new Intent(this, welcome.class);
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 this.setTheme(android.R.style.Theme_Holo_NoActionBar_Fullscreen);
        setContentView(R.layout.welcome);
        act = this;
    	newScrollView = (ScrollView) findViewById(R.id.categoryScrollView);
    	
        
       // preferences = getSharedPreferences("BUDGET", 0);
		//SharedPreferences.Editor editor = preferences.edit();
		//editor.putFloat(BUDGET_AMOUNT, Float.parseFloat(AmountEditText.getText().toString()));
		//editor.putFloat(BUDGET_AMOUNT,  BudgetAmount);
		 //editor.commit();
        if (savedInstanceState != null){
    	    categorys = savedInstanceState.getStringArrayList("categorys");
        }
        else
        {
        	categorys = myBudget.getCategorys();
        }
        inflateAddCategory();	
	      inflateCategorys();
	      
	      	DateEditText = (EditText) findViewById(R.id.dateEditText);
	        AmountEditText = (EditText) findViewById(R.id.AmountEditText);
	        AmountEditText.requestFocus();
	}
	
	@TargetApi(16)
	public void inflateCategorys()
    {
    	
    	
    	myLinearLayout = (LinearLayout) findViewById(R.id.LinearLayout1);
    	myScrollView = (LinearLayout) findViewById(R.id.ScrollViewLL);

    	
    	

    		for(int i = 1; i < categorys.size(); i++)
    		{
    		
    		
    			Button DeleteButton;
    			TextView myCategory;
    			View categoryList;
    			LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    			categoryList = inflater.inflate(R.layout.categorylistitem, null);
    	
    			myScrollView.addView(categoryList);
    			//myLayout.addView(historyList);
    			Object tag = "category";
    			myCategory = (TextView) categoryList.findViewWithTag(tag);
    			myCategory.setText(categorys.get(i));
    			DeleteButton = (Button) categoryList.findViewWithTag("delCat");
    	
    			DeleteButton.setTranslationX(55);
    		
    		
    			DeleteButton.setOnClickListener(deleteButtonListener);
    			DeleteButton.setTag(i);
    			categoryList.setTag(i);
    		
    			//Object tag2 = "transaction";
    			//TransactionTextView = (TextView) historyList.findViewWithTag(tag2);
    			//((TextView) TransactionTextView).setText("Transaction" + i);
    		}

    }
	
	
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		  super.onSaveInstanceState(savedInstanceState);
		  savedInstanceState.putStringArrayList("categorys", categorys);
		}
	public void inflateAddCategory()
	{
		View addCategory;
		LinearLayout myLinearLayout;
    	myLinearLayout = (LinearLayout) findViewById(R.id.LinearLayout1);
    	 LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 		addCategory = inflater.inflate(R.layout.addcategory, null);
 		myLinearLayout.addView(addCategory);
 		//myScrollView.invalidate();
 		Button addButton;
 		addButton = (Button) addCategory.findViewWithTag("AddCat");
		addButton.setOnClickListener(addListener);
 		doneButton = new Button(this);
    	doneButton.setText("Done");
    	doneButton.setOnClickListener(doneListener);
    	myLinearLayout.addView(doneButton);
    	
		
	}
	
	
	AsyncTask<Object, Object, Object> saveBudgetTask = 
            new AsyncTask<Object, Object, Object>() 
            {
               @Override
               protected Object doInBackground(Object... params) 
               {
                  saveBudget(); // save contact to the database
                  return null;
               } // end method doInBackground
   
               @Override
               protected void onPostExecute(Object result) 
               {
                  finish(); // return to the previous Activity
               } // end method onPostExecute
            }; // end AsyncTask
            
         // save the contact to the database using a separate thread
	public OnClickListener doneListener = new OnClickListener() 
    {
		
		public void onClick(View v) {

			BudgetAmount = Float.parseFloat(AmountEditText.getText().toString());
			Bundle bundle = new Bundle();
		    bundle.putFloat("BUDGET_AMOUNT", BudgetAmount);
		    String Date = DateEditText.getText().toString();
		    bundle.putString("DATE", Date);
		    String Period = PeriodEditText.getText().toString();
		    bundle.putString("PERIOD", Period);
			final Intent myIntent = new Intent();
			myIntent.putExtras(bundle);
	        // store the new high score
	    	saveBudgetTask.execute((Object[]) null); 
	    	//saveBudget();
	    	setResult(RESULT_OK, myIntent);
		    finish();
		}
    	
    };
    
    public OnClickListener deleteButtonListener = new OnClickListener()
    {

		public void onClick(View v) {
			
			View myView;
			int Count = myScrollView.getChildCount();
			myBudget.removeCategory(Integer.parseInt(v.getTag().toString()));
			myScrollView.removeView(myScrollView.findViewWithTag(Integer.parseInt(v.getTag().toString())));
			for(int i = Integer.parseInt(v.getTag().toString()) + 1; i < Count; i++)
			{
				myView = myScrollView.findViewWithTag(i);
				myView.setTag(i -1);
				myView = myScrollView.findViewWithTag(i);
				myView.setTag(i-1);
			}
			
 		}
    	
	
    };
    
    public OnClickListener addListener = new OnClickListener()
    {

		public void onClick(View v) {
			
			String Category;
			EditText AddEditText;
			AddEditText = (EditText) myLinearLayout.findViewWithTag("addEditText");
			Category = AddEditText.getText().toString();
			myBudget.addCategory(Category);
			AddEditText.setText(null);
			ScrollView theScrollView;
			
			theScrollView = (ScrollView) findViewById(R.id.categoryScrollView);
			
			Button DeleteButton;
    		TextView myCategory;
    		View categoryList;
    		 LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    		categoryList = inflater.inflate(R.layout.categorylistitem, null);
    	
    		
    		myScrollView.addView(categoryList);
    		//myLayout.addView(historyList);
    		Object tag = "category";
    		myCategory = (TextView) categoryList.findViewWithTag(tag);
    		myCategory.setText(Category);
    		DeleteButton = (Button) categoryList.findViewWithTag("delCat");
    		DeleteButton.setOnClickListener(deleteButtonListener);
    		DeleteButton.setTranslationX(55);
    		DeleteButton.setTag(myScrollView.getChildCount() -1);
    		categoryList.setTag(myScrollView.getChildCount() -1);
    		
    		theScrollView.scrollTo(0, (int)theScrollView.getY() + 48);
    		
			
		}
    	
    };
    
    public void saveBudget()
    {
    	DatabaseConnector databaseConnector = new DatabaseConnector(this);
    	for(int i =0; i < myBudget.getCategoryCount(); i++)
    		databaseConnector.insertCategory(myBudget.getCategory(i));
    	
    }
	

}
