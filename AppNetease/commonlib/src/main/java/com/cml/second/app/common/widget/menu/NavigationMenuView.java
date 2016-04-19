package com.cml.second.app.common.widget.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
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
    private int mMaxWidth;
    private Drawable mInsetForeground;
    private Rect mInsets;

    public NavigationMenuView(Context context) {
        this(context, null);
    }

    public NavigationMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a = context.obtainStyledAttributes(attrs,
                android.support.design.R.styleable.ScrimInsetsFrameLayout, defStyleAttr,
                android.support.design.R.style.Widget_Design_ScrimInsetsFrameLayout);
        mInsetForeground = a.getDrawable(android.support.design.R.styleable.ScrimInsetsFrameLayout_insetForeground);
        a.recycle();
        setWillNotDraw(true); // No need to draw until the insets are adjusted

        ViewCompat.setOnApplyWindowInsetsListener(this,
                new android.support.v4.view.OnApplyWindowInsetsListener() {
                    @Override
                    public WindowInsetsCompat onApplyWindowInsets(View v,
                                                                  WindowInsetsCompat insets) {
                        if (null == mInsets) {
                            mInsets = new Rect();
                        }
                        mInsets.set(insets.getSystemWindowInsetLeft(),
                                insets.getSystemWindowInsetTop(),
                                insets.getSystemWindowInsetRight(),
                                insets.getSystemWindowInsetBottom());
                        onInsetsChanged(mInsets);
                        setWillNotDraw(mInsets.isEmpty() || mInsetForeground == null);
                        ViewCompat.postInvalidateOnAnimation(NavigationMenuView.this);
                        return insets.consumeSystemWindowInsets();
                    }
                });

        this.init();
    }

    protected void onInsetsChanged(Rect insets) {

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
