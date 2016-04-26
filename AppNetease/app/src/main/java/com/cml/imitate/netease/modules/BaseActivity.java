package com.cml.imitate.netease.modules;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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

    protected void setTranslucenteBar() {
        //隐藏导航栏
        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//全屏隐藏虚拟按键
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            WindowManager.LayoutParams params = window.getAttributes();
//            params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//            window.setAttributes(params);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

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
