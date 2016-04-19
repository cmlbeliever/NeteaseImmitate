package com.cml.imitate.netease.modules;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cml.imitate.netease.R;


/**
 * Created by cmlBeliever on 2016/2/23.
 */
public class BaseActivity extends AppCompatActivity {

    public static final String TAG = BaseActivity.class.getSimpleName();

    public Toolbar toolbar;
    private TextView titleView;

    protected int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int height = getResources().getDimensionPixelSize(resourceId);
        return height;
    }

    protected void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        titleView = (TextView) findViewById(R.id.toolbar_title);
    }

    /**
     * 将默认的menu栏迁移到自定义的custom title容器上，以解决title居中问题
     */
    protected void setupCustomNavigation() {
        int size = toolbar.getChildCount();

        //设置toolbar的菜单栏偏移问题
        ImageButton menuBtn = null;

        for (int i = 0; i < size; i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof ImageButton) {
                menuBtn = (ImageButton) view;
                break;
            }
        }

        if (null != menuBtn) {
            toolbar.removeView(menuBtn);
            RelativeLayout toolbarTitleContainer = (RelativeLayout) findViewById(R.id.toolbar_title_container);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            toolbarTitleContainer.addView(menuBtn, 0, params);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setCustomTitle(int titleRes) {
        titleView.setText(titleRes);
    }

    public void setCustomTitle(CharSequence title) {
        titleView.setText(title);
    }
}
