package com.cml.second.app.common.widget.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cml.second.app.common.R;
import com.cml.second.app.common.widget.menu.adapter.NavigationMenuAdapter;

import java.util.List;

/**
 * Created by cmlBeliever on 2016/3/7.
 */
public class NavigationMenuView extends LinearLayout implements AdapterView.OnItemClickListener {

    private ListView listView;
    private OnMenuSelectedLisener menuSelectedLisener;

    public NavigationMenuView(Context context) {
        super(context);
        this.init();
    }

    public NavigationMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public NavigationMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        setOrientation(VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.view_navigation_menu, this);
        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(this);
    }

    public void setMenus(List<MenuItem> menus) {
        listView.setAdapter(new NavigationMenuAdapter(menus, getContext()));
    }

    public void setHeader(View view) {
        listView.addHeaderView(view);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != menuSelectedLisener) {
            menuSelectedLisener.onMenuSelected(listView, (MenuItem) parent.getItemAtPosition(position), position);
        }
    }

    public OnMenuSelectedLisener getMenuSelectedLisener() {
        return menuSelectedLisener;
    }

    public void setMenuSelectedLisener(OnMenuSelectedLisener menuSelectedLisener) {
        this.menuSelectedLisener = menuSelectedLisener;
    }

    public static interface OnMenuSelectedLisener {
        void onMenuSelected(ListView menuView, MenuItem item, int index);
    }

    public static class MenuItem {
        public int menuText;
        public int iconRes;
        public boolean checked;
        public int budgetValue;

        public MenuItem(int menuText, int iconRes) {
            this.menuText = menuText;
            this.iconRes = iconRes;
        }

        public MenuItem(int menuText, int iconRes, boolean checked, int budgetValue) {
            this.menuText = menuText;
            this.iconRes = iconRes;
            this.checked = checked;
            this.budgetValue = budgetValue;
        }
    }
}
