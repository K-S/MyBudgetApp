package etsu.edu.fishersc.budgetapp;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import etsu.edu.fishersc.budgetapp.R;


public class MenuItemsAdapter extends BaseAdapter {
	
	private ArrayList<String> _data;
    Context _c;
    
    MenuItemsAdapter (ArrayList<String> data, Context c){
        _data = data;
        _c = c;
    }
   

	public int getCount() {
        // TODO Auto-generated method stub
        return _data.size();
    }
    
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return _data.get(position);
    }
 
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
   
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
         View v = convertView;
         if (v == null)
         {
            LayoutInflater vi = (LayoutInflater)_c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.menu_listitem, null);
         }
 
           TextView rowView = (TextView)v.findViewById(R.id.text1);
 
           rowView.setText("Home");                          
                        
        return v;
    }
}
