package com.cml.second.app.common.widget.menu;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by cmlBeliever on 2016/3/7.
 */
public class BudgetView extends ActionProvider {

    /**
     * Creates a new instance.
     *
     * @param context Context for accessing resources.
     */
    public BudgetView(Context context) {
        super(context);
    }


    @Override
    public View onCreateActionView(MenuItem forItem) {
        TextView textView = new TextView(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        textView.setText("水电费111"+   forItem.getTitle());
        return textView;
    }

    @Override
    public View onCreateActionView() {
        TextView textView = new TextView(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        textView.setText("水电费");
        return textView;
    }

    private void init() {
    }

}
