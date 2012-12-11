package etsu.edu.fishersc.budgetapp;


import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

@SuppressLint("SetJavaScriptEnabled")
@TargetApi(12)
public class MainActivity extends Activity {

	private Animation animate;
	private ViewFlipper vf;
	private ActionBar bar;
	private Window window;
	private Activity act;
	private static int statusHeight = 0;
	private LinearLayout content;
	private static View menu;
	private FrameLayout fl;
	 private  int menuSize;
	private boolean isOpen = false;
	private View editBudget;
	private View history;
	private View transaction;
	private View income;
	private View home;
	private View myTableRow;
	private View myTableRow2;
	private View myTableRow3;
	private View myTableRow4;
	private View myTableRow5;
	private Button addTransButton;
	private Button addDepositButton;
	private TableLayout main;
	private ExpandableListView myListView;
	private Budget MyBudget;
	private TextView BudgetTotal;
	private LinearLayout linLayout;
	private boolean firstRun = true;
	private static final String FIRST_RUN = "FIRST_RUN";
	private static final String BUDGET_AMOUNT = "BUDGET_AMOUNT";
	private float BudgetAmount;
	private SharedPreferences preferences; 
	ArrayList<BudgetItem> transactions = new ArrayList<BudgetItem>();
	private TextView ExpenseInt;
	private TextView BudgetLeftInt;
	private TextView PeriodLeft;
	View deleteTransaction;
	Intent welcome;
	int myCategoryID;
	String categoryName;
	LinearLayout theLinearLayout;
	LinearLayout myScrollView;
	View deleteCategory;
	Spinner spinner;
	WebView myWebView;
	List<String> list = new ArrayList<String>();
	String stuff = "<html> <h1>Stuff</h1> </html>";
	char[] pie;
	

	@Override
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //BudgetAmount = preferences.getFloat("BUDGET_AMOUNT", 0);
        setContentView(R.layout.activity_main);
       
      /* BudgetItem transaction0 = new BudgetItem();
		transaction0.setItemName("stuff");
		transaction0.setCategoryId(0);
		transactions.add(transaction0);
		
		BudgetItem transaction1 = new BudgetItem();
		transaction1.setItemName("morestuff");
		transaction1.setCategoryId(1);
		transactions.add(transaction1);
		
		BudgetItem transaction2 = new BudgetItem();
		transaction2.setItemName("otherstuff");
		transaction2.setCategoryId(1);
		transactions.add(transaction2);
		
		BudgetItem transaction3 = new BudgetItem();
		transaction3.setItemName("laststuff");
		transaction3.setCategoryId(2);
		transactions.add(transaction3);*/
		
      
        
		 preferences = getPreferences(0);
	     firstRun = preferences.getBoolean(FIRST_RUN, true);
	     BudgetAmount = preferences.getFloat("BUDGET_AMOUNT", 50);
        if(firstRun)
        {
        	SharedPreferences.Editor editor = preferences.edit();
        	firstRun = false;
        	editor.putBoolean(FIRST_RUN, firstRun);
            editor.commit(); 
             welcome = new Intent(this, welcome.class);
             startActivityForResult(welcome, 1);
        }
        
        new GetTransactionsTask().execute((Object[]) null);
        
        ExpenseInt = (TextView) findViewById(R.id.expenseInt);
        BudgetLeftInt = (TextView) findViewById(R.id.budgetLeftInt);
        //PeriodLeft = (TextView) findViewById(R.id.PeriodLeftString);
        
        
        
        main = (TableLayout) findViewById(R.id.main);
        
        addTransButton = (Button) findViewById(R.id.expenseButton);
        addDepositButton = (Button) findViewById(R.id.depositButton);
        linLayout = (LinearLayout)findViewById(R.id.linLayout);
        
        addTransButton.setOnClickListener(transactionListener);
        addDepositButton.setOnClickListener(depositListener);
        
        animate = AnimationUtils.loadAnimation(this, R.anim.out_view_navagation);
        vf = (ViewFlipper) findViewById(R.id.viewFlipper1);
        Rect Rectangle = new Rect();
        act = this;
        window = act.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(Rectangle);
        fl =  (FrameLayout) window.getDecorView();
        statusHeight = Rectangle.top;
        
        
        LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		menu = inflater.inflate(R.layout.menu, null);
		fl.addView(menu, 0);
		 myTableRow = (View) findViewById(R.id.tableRow1);
	      myTableRow2 = (View) findViewById(R.id.tableRow2);
	     myTableRow3 = (View) findViewById(R.id.tableRow3);
	     myTableRow4 = (View) findViewById(R.id.tableRow4);

	     myTableRow5 = (View) findViewById(R.id.tableRow5);

      
		BudgetTotal = (TextView) findViewById(R.id.BudgetTotalTextView);
		MyBudget = new Budget(BudgetAmount, "10/12/2012", 6);
		BudgetTotal.setText("$" + MyBudget.getBudgetAmount());
		//PeriodLeft.setText(MyBudget.getAmountLeftInPeriod());
		
       

		
       
		
		/*for(int i = 0; i < MyBudget.getCategoryCount(); i++)
			Log.v("Category", MyBudget.getCategory(i) + "");*/

        ActionBar bar = getActionBar();
        
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        bar.setDisplayHomeAsUpEnabled(true);
        //bar.setHomeButtonEnabled(true);
        LayoutInflater inflater1 = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       
       /*( try {
			FileReader f = new FileReader("file:///assests/pie.html");
			 f.read(pie);
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
			FileWriter f = new FileWriter("file:///assests/pie.html");		
			f.write(pie);
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
       /* myWebView = (WebView) findViewById(R.id.webView1);
        
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl("file:///assests/pie.html");*/
        editBudget = inflater1.inflate(R.layout.edit_budget, null);
        
		vf.addView(editBudget);
		
		history = inflater1.inflate(R.layout.history, null);
	       
		vf.addView(history);
		
		transaction = inflater1.inflate(R.layout.transaction, null);
	       
		vf.addView(transaction);
		
        income = inflater1.inflate(R.layout.income, null);
       
		vf.addView(income);
		
		
		deleteTransaction = inflater1.inflate(R.layout.delete_transaction, null);
	    vf.addView(deleteTransaction);
	    
		deleteCategory = inflater1.inflate(R.layout.delete_category, null);
		vf.addView(deleteCategory);
		
		if(transactions.size() == 0)
        {
        	ExpenseInt.setText("$0");
        	BudgetLeftInt.setText("$" + MyBudget.getBudgetAmount() + "");
        }
        else
        {
        	int AmountSpent = 0;
        	for(int i = 0; i < transactions.size(); i++)
        	{
        		if(transactions.get(i).getCategoryId() == 0)
        		{
        			AmountSpent += transactions.get(i).getAmount();
        		}
        		else
        			AmountSpent += transactions.get(i).getAmount() * -1;
        	}
        	ExpenseInt.setText("$" + AmountSpent + "");
        	float BudgetLeft = BudgetAmount - AmountSpent;
        	BudgetLeftInt.setText("$" +BudgetLeft);
        }
		
		
		
		
			//myListView.setAdapter(expListAdapter);
        
	}

	
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Bundle myBundle = new Bundle();
		//super.onActivityResult(requestCode, resultCode, data);
		myBundle = data.getExtras();
		BudgetAmount = myBundle.getFloat("BUDGET_AMOUNT", 50);
		String Date = myBundle.getString("DATE", "12/25/90");
		String Period = myBundle.getString("PERIOD", "12 Months");
		//BudgetAmount = extras.getFloat("BUDGET_AMOUNT", 50);
		MyBudget.setBudgetAmount(BudgetAmount);
		BudgetLeftInt.setText("$" + MyBudget.getBudgetAmount() + "");
		MyBudget.setBudgetStartDate(Date);
		MyBudget.setBudgetPeriod(Period);
		BudgetTotal.setText(MyBudget.getBudgetAmount() + "");
		SharedPreferences.Editor editor = preferences.edit();
    	editor.putFloat("BUDGET_AMOUNT", BudgetAmount);
    	//editor.putString(key, value)
        editor.commit(); 
        
	}
	
	
	
	   // performs database query outside GUI thread
	   private class GetBudgetTask extends AsyncTask<Object, Object, Cursor> 
	   {
	      DatabaseConnector databaseConnector = 
	         new DatabaseConnector(MainActivity.this);

	      // perform the database access
	      @Override
	      protected Cursor doInBackground(Object... params)
	      {
	         databaseConnector.open();

	         // get a cursor containing call contacts
	         return databaseConnector.getAllCategorys(); 
	      } // end method doInBackground

	      // use the Cursor returned from the doInBackground method
	      @Override
	      protected void onPostExecute(Cursor result)
	      {
	    	  int index = 0;
	    	  String category;
	    	 index = result.getColumnIndex("category_name");
	    	  result.moveToFirst();
	    	  if(MyBudget.getCategoryCount() == 0)
	    	  {
	    		  for(int i = 0; i < result.getCount(); i++)
	    		  {
	
	    			  category = result.getString(index);
	    			  MyBudget.addCategory(category); 
	    			  result.moveToNext();
	    		  }

				
	    	  }
	
			//}
	         databaseConnector.close();
	      } // end method onPostExecute
	   } // end class GetContactsTask
	   
	   
	   private class GetCategoryID extends AsyncTask<Object, Object, Cursor> 
	   {
	      DatabaseConnector databaseConnector = 
	         new DatabaseConnector(MainActivity.this);

	      // perform the database access
	      @Override
	      protected Cursor doInBackground(Object... params)
	      {
	         databaseConnector.open();

	         Log.v("name", categoryName +"");
	         // get a cursor containing call contacts
	         return databaseConnector.getCategoryID(categoryName);
	      } // end method doInBackground

	      // use the Cursor returned from the doInBackground method
	      @Override
	      protected void onPostExecute(Cursor result)
	      {
	    	  result.moveToFirst();
	    	  Log.v("id", result.getInt(0) + "");
	    	  Log.v("stuff", result.getColumnName(0) + "");
	    	  myCategoryID = result.getInt(0);
	    	  new saveTransactionTask().execute((Object[]) null);
	    	  EditText itemText;
	      	EditText paymentText;
	      	EditText dateText;
	      	
	          itemText =(EditText)findViewById(R.id.itemText);
	          paymentText =(EditText)findViewById(R.id.paymentText);
	          dateText =(EditText)findViewById(R.id.dateText);
	          String date = dateText.getText().toString();
	          String item = itemText.getText().toString();
	          Double amount = Math.abs(Double.parseDouble(paymentText.getText().toString())) * -1;
	          BudgetItem newItem =  new BudgetItem(date, item, amount, myCategoryID -1, MyBudget.getBudgetAmount());
	     	 	transactions.add(newItem);
	     	 
	
			//}
	         databaseConnector.close();
	      } // end method onPostExecute
	   } // end class GetContactsTask
	
	
	   
	   private class GetTransactionsTask extends AsyncTask<Object, Object, Cursor> 
	   {
	     DatabaseConnector databaseConnector = 
	         new DatabaseConnector(MainActivity.this);

	      // perform the database access
	      @Override
	      protected Cursor doInBackground(Object... params)
	      {
	         databaseConnector.open();

	         // get a cursor containing call contacts
	         return databaseConnector.getAllTransactionsOrderedByCategory();
	      } // end method doInBackground

	      // use the Cursor returned from the doInBackground method
	      @Override
	      protected void onPostExecute(Cursor result)
	      {
	    	  String category;
	    	  result.moveToFirst();
	    	  Log.v("results", result.getCount() + "");
			for(int i = 0; i < result.getCount(); i++)
			{
				Log.v("i", i + "");
				//android.os.Debug.waitForDebugger();
	    		BudgetItem atransaction = new BudgetItem();
	    		atransaction.setItemName(result.getString(0));
	    		atransaction.setCategoryId(result.getInt(1) -1);
	    		atransaction.setAmount(result.getDouble(2));
	    		atransaction.setDate(result.getString(3));
	    		Log.v("Transaction",atransaction.toString());
				transactions.add(atransaction);
	 	       result.moveToNext();
	    	  }
			//}
	         databaseConnector.close();
	        // this.cancel(true);
	      } // end method onPostExecute
	   } // end class GetContactsTask

	   private class saveTransactionTask extends AsyncTask<Object, Object, Object>
	            {
	               @Override
	               protected Object doInBackground(Object... params) 
	               {
	            	   Log.v("stuff", "stuffhappened");
	            	  // android.os.Debug.waitForDebugger();
	                  saveTransaction(); // save contact to the database
	                  return null;
	               } // end method doInBackground
	   
	               @Override
	               protected void onPostExecute(Object result) 
	               {
	                  //finish(); // return to the previous Activity
	               } // end method onPostExecute
	            }; // end AsyncTask
	            
	            
	            private class saveDepositTask extends AsyncTask<Object, Object, Object>
	            {
	               @Override
	               protected Object doInBackground(Object... params) 
	               {
	            	   Log.v("stuff", "stuffhappened");
	            	  // android.os.Debug.waitForDebugger();
	                  saveDeposit(); // save contact to the database
	                  return null;
	               } // end method doInBackground
	   
	               @Override
	               protected void onPostExecute(Object result) 
	               {
	                  //finish(); // return to the previous Activity
	               } // end method onPostExecute
	            }; // end AsyncTask

   // @Override
   /* public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_main, menu);
    	
        return true;
    }*/
	
	
	   @Override
	   public void onResume()
	   {
		   super.onResume();
		   new GetBudgetTask().execute((Object[]) null);
	   }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
            	if (!isOpen)
            	{
            		drawerOpen();
            		return true;
            	}
            	else
            	{
            		drawerClose();
            		return true;
            	}
            	default:
            		return super.onOptionsItemSelected(item);
        }
    }

    public OnClickListener homeListener = new OnClickListener()
    {

		public void onClick(View v) {
			//v.setBackgroundColor(255);
			vf.setDisplayedChild(vf.indexOfChild(main));
			BudgetTotal.setText(MyBudget.getBudgetAmount() + "");
			
			 if(transactions.size() == 0)
		        {
		        	ExpenseInt.setText("0");
		        	BudgetLeftInt.setText(BudgetAmount + "");
		        }
		        else
		        {
		        	int AmountSpent = 0;
		        	for(int i = 0; i < transactions.size(); i++)
		        	{
		        		AmountSpent += transactions.get(i).getAmount() * -1;
		        	}
		        	ExpenseInt.setText(AmountSpent + "");
		        	BudgetLeftInt.setText(BudgetAmount - AmountSpent +"");
		        }
			

			drawerClose();
			
		}
    	
    };
    public OnClickListener	budgetListener = new OnClickListener()
    {

		public void onClick(View v) {
			
			Button delTransactionButton;
			delTransactionButton = (Button) findViewById(R.id.deleteTransactionsButton);
			delTransactionButton.setOnClickListener(deleteTransactionButtonListener);
			
			Button saveChangesButton;
			saveChangesButton = (Button) findViewById(R.id.submitChangesButton);
			saveChangesButton.setOnClickListener(saveChangesButtonListener);
			
			Button clearBudgetButton;
			clearBudgetButton = (Button) findViewById(R.id.clearBudgetButton);
			clearBudgetButton.setOnClickListener(clearBudgetButtonListener);
			
			Button delCategoryButton;
			delCategoryButton = (Button) findViewById(R.id.deleteCategoryButton);
			delCategoryButton.setOnClickListener(deleteCategoryButtonListener);
			
			vf.setDisplayedChild(vf.indexOfChild(editBudget));
			drawerClose();
			
		}
    	
    };
    public OnClickListener historyListener = new OnClickListener()
    {

		public void onClick(View v) {
			//v.setBackgroundColor(255);
			
			vf.setDisplayedChild(vf.indexOfChild(history));
			
			// new GetTransactionsTask().execute((Object[]) null);
		   
			LinearLayout myLayout;
			
	    	myLayout = (LinearLayout) findViewById(R.id.linlayout55);
			populateHistory(myLayout);
			drawerClose();
			
			
		}
    	
    };
    public OnClickListener transactionListener = new OnClickListener()
    {

		public void onClick(View v) {
			  
			ArrayList<String> categorys = MyBudget.getCategorys();
			list.clear();
			for(int i = 0; i < categorys.size(); i++)
				list.add(categorys.get(i).toString());
			
				list.add("Create New Category");
			
			spinner = (Spinner) findViewById(R.id.spinner1);
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MainActivity.this,
					android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			spinner.setAdapter(dataAdapter);
			
			spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
			/*ArrayAdapter adapter = new ArrayAdapter<String>this,
			ArrayAdapter adapter = new ArrayAdapter<String>(this, 
			        temp);*/
			//v.setBackgroundColor(255);
			vf.setDisplayedChild(vf.indexOfChild(transaction));
			
			Button addtransactionButton;    
			addtransactionButton = (Button)findViewById(R.id.mytransactionButton);
			addtransactionButton.setOnClickListener(transactionButtonListener);
	
			if(v != addTransButton)
				drawerClose();
			
			

			
		}
    	
    };
    
    public class CustomOnItemSelectedListener implements OnItemSelectedListener {
    	 
 
    	  public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
    		
    		if(pos == spinner.getCount() -1)
    		{
    			AlertDialog.Builder getNewCategory = new AlertDialog.Builder(MainActivity.this);

    			getNewCategory.setTitle("New Category");
    			getNewCategory.setMessage("Please type a name for you new category and press OK!");

    			// Set an EditText view to get user input 
    			final EditText input = new EditText(MainActivity.this);
    			getNewCategory.setView(input);

    			getNewCategory.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int whichButton) {
    			  String value = input.getText().toString();
    			  MyBudget.addCategory(value);
    				new saveCategoryTask().execute(new String[] { value }); 
    			
    				list.add(spinner.getCount()-1, value);
    					
    					ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MainActivity.this,
    							android.R.layout.simple_spinner_item, list);
    					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    					spinner.setAdapter(dataAdapter);
    					spinner.setSelection(spinner.getCount() -2);
    			  
    			  }
    			});

    			getNewCategory.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    			  public void onClick(DialogInterface dialog, int whichButton) {
    				  spinner.setSelection(1);
    			  }
    			});
    			
    			getNewCategory.show();
    		}
    	  }

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
    }
    
    
    
    
    public OnClickListener depositListener = new OnClickListener()
    {

		public void onClick(View v) {
			//v.setBackgroundColor(255);
			vf.setDisplayedChild(vf.indexOfChild(income));
			Button addDepositButton;
			addDepositButton = (Button) findViewById(R.id.AddDepositButton);
			addDepositButton.setOnClickListener(depositButtonListener);
			if(v != addDepositButton)
				drawerClose();
			
			
		}
    	
    };
 
    
    public void drawerOpen()
    {
    	
    	
    	 
    	double statusBarHeight = Math.ceil(24 * this.getResources().getDisplayMetrics().density);
    	 myTableRow.setOnClickListener(homeListener);
    	 myTableRow2.setOnClickListener(budgetListener);
    	  myTableRow3.setOnClickListener(historyListener);
    	  myTableRow4.setOnClickListener(transactionListener);
    	  myTableRow5.setOnClickListener(depositListener);
    	 linLayout= (LinearLayout) menu.findViewWithTag("stuff");
    	 menuSize = linLayout.getWidth();
    	 // 375;
		//statusHeight = 100;
	
		content = ((LinearLayout) act.findViewById(android.R.id.content).getParent());
    
   
	
		TranslateAnimation ta = new TranslateAnimation(-menuSize, 0, 0, 0);
		ta.setDuration(500);
   
		FrameLayout.LayoutParams lays = new FrameLayout.LayoutParams(-1, -1, 3);
		lays.setMargins(0,(int) statusBarHeight, 0, 0);
		menu.setLayoutParams(lays);
		content.startAnimation(ta);
		FrameLayout.LayoutParams parm = (FrameLayout.LayoutParams) content.getLayoutParams();
		parm.setMargins(menuSize, 0, -menuSize, 0);
		content.setLayoutParams(parm);
		isOpen = true;
    }
    
    public void drawerClose()
    {
    	 myTableRow.setOnClickListener(null);
    	 myTableRow2.setOnClickListener(null);  
    	 myTableRow3.setOnClickListener(null);
    	 myTableRow4.setOnClickListener(null);
    	 myTableRow5.setOnClickListener(null);
    	
    	content = ((LinearLayout) act.findViewById(android.R.id.content).getParent());  
		   
		
		TranslateAnimation tra = new TranslateAnimation(menuSize, 0, 0, 0);
		tra.setDuration(500);
     
		
		
		content.startAnimation(tra);
		content.invalidate();
		FrameLayout.LayoutParams parm = (FrameLayout.LayoutParams) content.getLayoutParams();
		parm.setMargins(0, 0, 0, 0);
		content.setLayoutParams(parm);
		isOpen = false;
		
    }
    
    public void populateHistory(LinearLayout myLayout)
    {
    	
    	int Spent = 0;
   	 	/*LinearLayout myLayout;
    	myLayout = (LinearLayout) findViewById(R.id);*/
    	myLayout.removeAllViewsInLayout();
    	ArrayList<String> categorys;
    	categorys = MyBudget.getCategorys();
    	int j = 0;
    	for(int i =0; i < categorys.size(); i++)
    	{
    	 	LinearLayout myLinearLayout;
        	View historyList;
        	TextView myTextView;
        	TextView TransactionTextView;
        	TextView amountTextView;
        	TextView dateTextView;
        	View Category;
        	LayoutInflater inflater1 = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	historyList = inflater1.inflate(R.layout.historylist, null);

        	myLayout.addView(historyList);
        	Object tag = "theTag";
    		myTextView = (TextView) historyList.findViewWithTag(tag);
    		myTextView.setText(categorys.get(i));
    		
    		
    		if(transactions.isEmpty() || transactions.get(j).getCategoryId() != i)
    		{
    			View transactiontext;
				transactiontext = inflater1.inflate(R.layout.transactiontext, null);
				Object tag2 = "transaction";
				TransactionTextView = (TextView) transactiontext.findViewWithTag(tag2);
				myLayout.addView(transactiontext);
				((TextView) TransactionTextView).setText("No transactions");
    			
    		}
    		else
    		{
    			if(j < transactions.size())
    			{
    				while(transactions.get(j).getCategoryId() == i && j < transactions.size())
    				{
    					if(transactions.get(j).getCategoryId() == 0)
    	        		{
    	        			Spent += transactions.get(j).getAmount();
    	        		}
    					else
    					{
    						Spent += transactions.get(j).getAmount() * -1;
    					}
    					
    					View transactiontext;
    					transactiontext = inflater1.inflate(R.layout.transactiontext, null);
    					Object tag2 = "transaction";
    					TransactionTextView = (TextView) transactiontext.findViewWithTag(tag2);
    					myLayout.addView(transactiontext);
    					((TextView) TransactionTextView).setText(transactions.get(j).getItemName());
    					Object tag3 = "amount";
    					TransactionTextView = (TextView) transactiontext.findViewWithTag(tag3);
    					((TextView) TransactionTextView).setText(transactions.get(j).getAmount() * -1 + "");
    					
    					Object tag4 = "date";
    					TransactionTextView = (TextView) transactiontext.findViewWithTag(tag4);
    					((TextView) TransactionTextView).setText(transactions.get(j).getDate().toString());
    					
    					
    					/*View amounttext;
    					amounttext = inflater1.inflate(R.layout.transactiontext, null);
    					Object tag3 = "amount";
    					amountTextView = (TextView) amounttext.findViewWithTag(tag3);
    					myLayout.addView(amounttext);
    					View datetext;
    					datetext = inflater1.inflate(R.layout.transactiontext, null);
    					Object tag4 = "date";
    					dateTextView = (TextView) transactiontext.findViewWithTag(tag4);
    					myLayout.addView(datetext);*/
    					if(myLayout.getId() == R.id.deleteTransactionLinearLayout)
    					{
    						TransactionTextView.setOnClickListener(deleteTransactionListener);
    						TransactionTextView.setTag(j);
    					}
    					
    					if(transactions.size() > 0 )
    					{
    						TextView BudgetSpentText = (TextView) findViewById(R.id.budgetSpentTextView);
    						BudgetSpentText.setText("$" + Spent + "");
    						Float PercentSpent = 100 - (Spent/ BudgetAmount) * 100;
    						TextView PercentLeft = (TextView) findViewById(R.id.percentLeftTextView);
    						PercentLeft.setText(PercentSpent + "%");
    					}
    					else
    					{
    						TextView BudgetSpentText = (TextView) findViewById(R.id.budgetSpentTextView);
    						BudgetSpentText.setText("0");
    						TextView PercentLeft = (TextView) findViewById(R.id.percentLeftTextView);
    						PercentLeft.setText("100%");
    					}
    					/*((TextView) amountTextView).setText(transactions.get(j).getAmount()+"");
    					((TextView) dateTextView).setText(transactions.get(j).getDate().toString());*/
    					if(j == transactions.size() -1)
    						break;
    					else
    						j++;
    				}
    			}
    		}
    	}
    	
    	
    	
    }
    
    
    
    
    public OnClickListener depositButtonListener = new OnClickListener()
    {

		public void onClick(View v) {
			
			 
				new saveDepositTask().execute((Object[]) null);
			 EditText sourceText;
	            EditText AmountText;
	            EditText dateText;
	            
	            sourceText =(EditText)findViewById(R.id.sourceEditText);
	            AmountText =(EditText)findViewById(R.id.AmountEditText);
	            dateText =(EditText)findViewById(R.id.dateEditText);
	            
	            String recipient =  sourceText.getText().toString();
	            Double Amount = Double.parseDouble(AmountText.getText().toString());
	            String date = dateText.getText().toString();
	           BudgetItem newstuff = new BudgetItem(date, recipient, Amount, 0, MyBudget.getBudgetAmount());
	            transactions.add(newstuff);
	           // sourceText.setText("");
	        //  AmountText.setText();
	            //dateText.setText("");
			
		}
    	
    };
    public OnClickListener transactionButtonListener = new OnClickListener()
    {

        public void onClick(View v) {
        	
        	 
        	//new saveTransactionTask().execute();
           
            categoryName = spinner.getSelectedItem().toString();
            new GetCategoryID().execute((Object[]) null);
           
            //int catNumber = MyBudget.getCategoryWithName(recipient);
            
            
                // saveTransactionTask.execute((Objsect[]) null); 
                // saveTransaction();
                 
            EditText itemText;
  	      	EditText paymentText;
  	      	EditText dateText;
  	      itemText =(EditText)findViewById(R.id.itemText);
  	    paymentText =(EditText)findViewById(R.id.paymentText);
  	    dateText =(EditText)findViewById(R.id.dateText);
  	  //dateText.setText("");
      //itemText.setText("");
   // paymentText.setText(0);
        }
    };
    
    
    public void saveDeposit()
    {

       DatabaseConnector databaseConnector = new DatabaseConnector(this);
        
       EditText recipientText;
       //EditText itemText;
       EditText paymentText;
       EditText dateText;
       
        recipientText =(EditText)findViewById(R.id.sourceEditText);
        //itemText =(EditText)findViewById(R.id.itemText);
        paymentText =(EditText)findViewById(R.id.AmountEditText);
        dateText =(EditText)findViewById(R.id.dateEditText);
       String date = dateText.getText().toString();
       String recipient = recipientText.getText().toString();
       Double Amount = Double.parseDouble(paymentText.getText().toString());
       
    	databaseConnector.insertTransaction(
    			date,
    			recipient,
               1,
               Amount);
    	         
    }
    
    public void saveTransaction()
    {
    	DatabaseConnector databaseConnector = new DatabaseConnector(this);
        
    	EditText itemText;
    	EditText paymentText;
    	EditText dateText;
    	
        itemText =(EditText)findViewById(R.id.itemText);
        paymentText =(EditText)findViewById(R.id.paymentText);
        dateText =(EditText)findViewById(R.id.dateText);
        String date = dateText.getText().toString();
        String item = itemText.getText().toString();
        Double amount = Math.abs(Double.parseDouble(paymentText.getText().toString())) * -1;
    	databaseConnector.insertTransaction(
    			date,
    			item,
               myCategoryID,
               amount);
    	
    	
    }

    public OnClickListener deleteCategoryListener = new OnClickListener()
    {

		public void onClick(View v) {

			makeDeleteCategoryDialog(Integer.parseInt(v.getTag().toString()));
		}
    	
    };
    public OnClickListener deleteTransactionListener = new OnClickListener()
    {

		public void onClick(View v) {

			makeDeleteItemDialog(Integer.parseInt(v.getTag().toString()));
		}
    	
    };
    
    //Start Bryan's CODE
    //
    //
    //
    //
    public OnClickListener deleteTransactionButtonListener = new OnClickListener()
    {

		public void onClick(View v) 
		{
			
			vf.setDisplayedChild(vf.indexOfChild(deleteTransaction));
			Button DoneButton;
			DoneButton = (Button) findViewById(R.id.TransactionDoneButton);
			DoneButton.setOnClickListener(DoneListener);
			LinearLayout myLayout;
	    	myLayout = (LinearLayout) findViewById(R.id.deleteTransactionLinearLayout);
	    	populateHistory(myLayout);
			
			
		}
    	
    };
    
    
    public OnClickListener DoneListener = new OnClickListener()
    {
    	public void onClick(View v) 
		{
    		vf.setDisplayedChild(vf.indexOfChild(editBudget));
		}
    };
    public OnClickListener saveChangesButtonListener = new OnClickListener()
    {

		public void onClick(View v) 
		{
			
			makeSaveChangesDialog();
		}
    	
    };
    
    public OnClickListener clearBudgetButtonListener = new OnClickListener()
    {

		public void onClick(View v) 
		{
			makeClearBudgetDialog();
		}
    	
    };
    
    public OnClickListener deleteCategoryButtonListener = new OnClickListener()
    {

		public void onClick(View v) 
		{

			vf.setDisplayedChild(vf.indexOfChild(deleteCategory));
			inflateAddCategory();	
		      inflateCategorys();
			/*LinearLayout myLayout;
	    	myLayout = (LinearLayout) findViewById(R.id.deleteCategoryLinearLayout);
	    	populateHistory(myLayout);*/
			
		}
    	
    };
    
    private void makeDeleteItemDialog(final int transactionID)
    {
    	AlertDialog.Builder deleteItemDialogBuilder = new AlertDialog.Builder(this);
		deleteItemDialogBuilder.setTitle("Delete Item");
		
		deleteItemDialogBuilder.setMessage("Item will be deleted and amount added to Budget.\n" +
											"Are you sure you want to permanently delete this item?");
		deleteItemDialogBuilder.setPositiveButton(
				"Cancel",
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which) 
					{
						//doesn't need to do anything but still needs to be cancelable
					}
				}
			);
		deleteItemDialogBuilder.setCancelable(true);
		deleteItemDialogBuilder.setNegativeButton("Delete", 
					new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							
							final DatabaseConnector databaseConnector = 
					                  new DatabaseConnector(MainActivity.this);

					               // create an AsyncTask that deletes the contact in another 
					               // thread, then calls finish after the deletion
					               AsyncTask<Long, Object, Object> deleteTask =
					                  new AsyncTask<Long, Object, Object>()
					                  {
					                     @Override
					                     protected Object doInBackground(Long... params)
					                     {
					                        databaseConnector.deleteTransaction(params[0]); 
					                        return null;
					                     } // end method doInBackground

					                     @Override
					                     protected void onPostExecute(Object result)
					                     {
					                        //finish(); // return to the AddressBook Activity
					                     } // end method onPostExecute
					                  }; // end new AsyncTask

					               // execute the AsyncTask to delete contact at rowID
					               deleteTask.execute(new Long[] { Long.parseLong(transactionID + "" + 1) });      
							transactions.remove(transactionID);
							
							
							
							//in here we will need to delete it from the database 
							//budget_display.setText(newTotal);
						}
					});
		AlertDialog deleteItemDialog = deleteItemDialogBuilder.create();
		deleteItemDialog.show();
    }
    
    private void makeSaveChangesDialog()
    {
    	AlertDialog.Builder saveChangesDialogBuilder = new AlertDialog.Builder(this);
		saveChangesDialogBuilder.setTitle("Save Changes");
		
		saveChangesDialogBuilder.setMessage("All changes will overwrite existing data.\n" +
											"Are you sure you want to save changes?");
		saveChangesDialogBuilder.setPositiveButton(
				"Cancel",
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which) 
					{
						//doesn't need to do anything but still needs to be cancelable
					}
				}
			);
		saveChangesDialogBuilder.setCancelable(true);
		saveChangesDialogBuilder.setNegativeButton("Ok", 
					new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							EditText BudgetEditTextAmount;
							BudgetEditTextAmount = (EditText) findViewById(R.id.budget_amount_display);
							MyBudget.setBudgetAmount(Float.parseFloat(BudgetEditTextAmount.getText().toString()));
							SharedPreferences.Editor editor = preferences.edit();
							BudgetAmount = (float) MyBudget.getBudgetAmount();
					    	editor.putFloat("BUDGET_AMOUNT", BudgetAmount);
					    	editor.commit();
					    	BudgetEditTextAmount.setText("");
						}
					});
		AlertDialog saveChangesDialog = saveChangesDialogBuilder.create();
		saveChangesDialog.show();
    }
    
    private void makeClearBudgetDialog()
    {
    	AlertDialog.Builder clearBudgetDialogBuilder = new AlertDialog.Builder(this);
		clearBudgetDialogBuilder.setTitle("Clear Budget Data");
		
		clearBudgetDialogBuilder.setMessage("All data will be permanently erased.\n" +
											"Are you sure you want to continue?");
		clearBudgetDialogBuilder.setPositiveButton(
				"Cancel",
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which) 
					{
						//doesn't need to do anything but still needs to be cancelable
					}
				}
			);
		clearBudgetDialogBuilder.setCancelable(true);
		clearBudgetDialogBuilder.setNegativeButton("Ok", 
					new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							MyBudget.Clear();
							transactions.removeAll(transactions);
							MainActivity.this.deleteDatabase("BudgetTransactions");
							
						}
					});
		AlertDialog clearBudgetDialog = clearBudgetDialogBuilder.create();
		clearBudgetDialog.show();
    }
    
    private void makeDeleteCategoryDialog(final int categoryID)
    {
    	AlertDialog.Builder deleteCategoryDialogBuilder = new AlertDialog.Builder(this);
		deleteCategoryDialogBuilder.setTitle("Delete Category");
		
		deleteCategoryDialogBuilder.setMessage("The Category will be gone forever and all items with this category will be set to 'General'.\n" +
											"Are you sure you want to continue?");
		deleteCategoryDialogBuilder.setPositiveButton(
				"Cancel",
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which) 
					{
						//doesn't need to do anything but still needs to be cancelable
					}
				}
			);
		deleteCategoryDialogBuilder.setCancelable(true);
		deleteCategoryDialogBuilder.setNegativeButton("Ok", 
					new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							final DatabaseConnector databaseConnector = 
					                  new DatabaseConnector(MainActivity.this);
							AsyncTask<Long, Object, Object> deleteCategoryTask =
						            new AsyncTask<Long, Object, Object>()
						            {
						               @Override
						               protected Object doInBackground(Long... params)
						               {
						                  databaseConnector.deleteCategory(params[0]); 
						                  return null;
						               } // end method doInBackground

						               @Override
						               protected void onPostExecute(Object result)
						               {
						                  //finish(); // return to the AddressBook Activity
						               } // end method onPostExecute
						            }; // end new AsyncTask     
				deleteCategoryTask.execute(new Long[] { (long) categoryID + 1 });
				View myView;
				int Count = myScrollView.getChildCount();
				MyBudget.removeCategory(categoryID);
				myScrollView.removeView(myScrollView.findViewWithTag(categoryID));
				for(int i = categoryID + 1; i < Count; i++)
				{
					myView = myScrollView.findViewWithTag(i);
					myView.setTag(i -1);
					myView = myScrollView.findViewWithTag(i);
					myView.setTag(i-1);
				}
				
					BudgetItem transaction1 = new BudgetItem();
					
					//vf.setDisplayedChild(vf.indexOfChild(editBudget));
						}
					});
		AlertDialog deleteCategoryDialog = deleteCategoryDialogBuilder.create();
		deleteCategoryDialog.show();
    }
    
    //END BRYAN's CODE
    //
    //
    
    
    
    public void inflateCategorys()
    {
    	
    	
    	ScrollView newScrollView;
    	theLinearLayout = (LinearLayout) findViewById(R.id.deleteStuffCategory);
    	myScrollView = (LinearLayout) findViewById(R.id.deleteCategoryLinearLayout);
    	myScrollView.removeAllViews();
    	newScrollView = (ScrollView) findViewById(R.id.deleteCategoryScrollView);
    	
    	
    	ArrayList<String> categorys;
    	categorys = MyBudget.getCategorys();
    		for(int i = 0; i < categorys.size(); i++)
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
    
    
    public void inflateAddCategory()
	{
		View addCategory;
		LinearLayout aLinearLayout;
    	aLinearLayout = (LinearLayout) findViewById(R.id.deleteStuffCategory);
    	if(aLinearLayout.getChildCount() > 3)
    		aLinearLayout.removeViews(2, 2);
    	 LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 		addCategory = inflater.inflate(R.layout.addcategory, null);
 		aLinearLayout.addView(addCategory);
 		//myScrollView.invalidate();
 		Button addButton;
 		addButton = (Button) addCategory.findViewWithTag("AddCat");
		addButton.setOnClickListener(addListener);
		Button doneButton = new Button(this);
    	doneButton.setText("Done");
    	doneButton.setOnClickListener(DoneListener);
    	aLinearLayout.addView(doneButton);
    	
		
	}

    public OnClickListener deleteButtonListener = new OnClickListener()
    {

		public void onClick(View v) {
			
			

		         // execute the AsyncTask to delete contact at rowID
			
			makeDeleteCategoryDialog(Integer.parseInt(v.getTag().toString()));
			
			
		
			   
			
 		}
    	
	
    };
    
    
    
    public OnClickListener addListener = new OnClickListener()
    {

		public void onClick(View v) {
			
			String Category;
			EditText AddEditText;
			AddEditText = (EditText) theLinearLayout.findViewWithTag("addEditText");
			Category = AddEditText.getText().toString();
			MyBudget.addCategory(Category);
			new saveCategoryTask().execute(new String[] { Category }); 
			AddEditText.setText(null);
			ScrollView theScrollView;
			
			theScrollView = (ScrollView) findViewById(R.id.deleteCategoryScrollView);
			
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
    
    private class saveCategoryTask extends AsyncTask<String, Object, Object>
            {
               @Override
               protected Object doInBackground(String...params)
               {
            	   saveCategory(params[0]);
                  //databaseConnector.deleteTransaction(params[0]); 
                  return null;
               } // end method doInBackground

               @Override
               protected void onPostExecute(Object result)
               {
                  //finish(); // return to the AddressBook Activity
               } // end method onPostExecute
            }; // end new AsyncTask
    
    public void saveCategory(String category)
    {
    	DatabaseConnector databaseConnector = new DatabaseConnector(this);
    		databaseConnector.insertCategory(category);
    	
    }
    
 
         
    
 public void onAnimationStart(Animation animate) {
    	
    }


}


