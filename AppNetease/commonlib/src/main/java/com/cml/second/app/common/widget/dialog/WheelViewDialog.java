package com.cml.second.app.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.cml.second.app.common.R;
import com.cml.second.app.common.widget.WheelView;

import java.util.Arrays;

/**
 * Created by cmlBeliever on 2016/3/7.
 */
public class WheelViewDialog extends Dialog implements View.OnClickListener {

    private WheelView wheelView;
    private String[] items;
    private View doneView;
    private WheelView.OnWheelViewListener onWheelSelectListener;

    public WheelViewDialog(Context context, String[] items) {
        super(context, R.style.WheelDialog);
        this.items = items;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_wheel_dialog);

        doneView = findViewById(R.id.done_view);
        doneView.setOnClickListener(this);

        wheelView = (WheelView) findViewById(R.id.wheel_view);
        wheelView.setOffset(1);
        wheelView.setItems(Arrays.asList(items));

        //设置全屏
        int width = getContext().getResources().getDisplayMetrics().widthPixels;
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = width;
        getWindow().setAttributes(params);
    }

    public void setOnWheelSelectListener(WheelView.OnWheelViewListener onWheelSelectListener) {
        this.onWheelSelectListener = onWheelSelectListener;
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if (null != onWheelSelectListener) {
            onWheelSelectListener.onSelected(wheelView.getSeletedIndex(), wheelView.getSeletedItem());
        }
    }
}
