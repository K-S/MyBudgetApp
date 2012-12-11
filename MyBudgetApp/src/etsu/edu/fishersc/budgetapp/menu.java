package etsu.edu.fishersc.budgetapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;



public class menu extends ListActivity {
	
	private ListView navListView;
	 
	
			public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.menu);
	        
	        navListView = getListView();
	        // navListView.setOnClickListener(viewNavListener);
	         
	         String[] from = new String[] {"Home", "Add Deposit"};
	         int[] to = new int[] {R.id.text1};
	 		CursorAdapter myAdapter = new SimpleCursorAdapter(
	         	menu.this, R.layout.menu_listitem, null, from, to, 0);
	         	setListAdapter(myAdapter);
			}
}
