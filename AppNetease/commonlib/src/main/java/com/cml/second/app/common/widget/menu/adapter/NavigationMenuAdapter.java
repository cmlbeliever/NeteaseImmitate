package com.cml.second.app.common.widget.menu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cml.second.app.common.R;
import com.cml.second.app.common.widget.menu.NavigationMenuView;

import java.util.List;

/**
 * Created by cmlBeliever on 2016/3/7.
 */
public class NavigationMenuAdapter extends BaseAdapter {

    public List<NavigationMenuView.MenuItem> items;
    private Context context;

    public NavigationMenuAdapter(List<NavigationMenuView.MenuItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.adapter_navigation_menu, parent, false);
            convertView = view;
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NavigationMenuView.MenuItem item = items.get(position);

        holder.titleView.setText(item.menuText);
        holder.iconView.setImageResource(item.iconRes);
        holder.budgetView.setText("3");

        return convertView;
    }

    private static class ViewHolder {
        public ImageView iconView;
        public TextView titleView;
        public TextView budgetView;

        public ViewHolder(View view) {
            this.iconView = (ImageView) view.findViewById(R.id.navi_icon);
            this.titleView = (TextView) view.findViewById(R.id.navi_txt);
            this.budgetView = (TextView) view.findViewById(R.id.navi_budget);
        }
    }
}
