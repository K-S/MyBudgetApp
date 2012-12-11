package etsu.edu.fishersc.budgetapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class SlideMenu {
    public static class SlideMenuAdapter extends ArrayAdapter<SlideMenu.SlideMenuAdapter.MenuDesc> {
        Activity act;
        SlideMenu.SlideMenuAdapter.MenuDesc[] items;
        class MenuItem {
            public TextView label;
            public ImageView icon;
        }
        static class MenuDesc {
            public int icon;
            public String label;
        }
        public SlideMenuAdapter(Activity act, SlideMenu.SlideMenuAdapter.MenuDesc[] items) {
            super(act, R.id.menu_label, items);
            this.act = act;
            this.items = items;
            }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = act.getLayoutInflater();
                rowView = inflater.inflate(R.layout.menu_listitem, null);
                MenuItem viewHolder = new MenuItem();
                viewHolder.label = (TextView) rowView.findViewById(R.id.menu_label);
                //viewHolder.icon = (ImageView) rowView.findViewById(R.id.menu_icon);
                rowView.setTag(viewHolder);
            }

            MenuItem holder = (MenuItem) rowView.getTag();
            String s = items[position].label;
            holder.label.setText(s);
            holder.icon.setImageResource(items[position].icon);

            return rowView;
        }
    }
}
