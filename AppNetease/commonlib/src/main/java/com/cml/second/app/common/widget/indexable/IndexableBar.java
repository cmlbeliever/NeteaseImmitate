package com.cml.second.app.common.widget.indexable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by cmlBeliever on 2016/3/8.
 */
public class IndexableBar extends LinearLayout {

    private List<String> section;
    private int sectionHeight;
    private int textColor = Color.WHITE;
    private int textSize = 16;
    private int selectedTextColor = Color.GREEN;

    public IndexableBar(Context context) {
        super(context);
        this.init();
    }

    public IndexableBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public IndexableBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        setPadding(10, 20, 10, 20);
    }

    public void setSection(List<String> section) {
        this.section = section;
        removeAllViews();
        setItems();//添加数据
        setBackground();
    }

    private void setBackground() {
        Drawable bg = new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                bgPaint.setColor(Color.argb(64, 0, 0, 0));
                RectF bgRectf = new RectF(0, 10, getWidth(), getHeight() - 10);
                canvas.drawRoundRect(bgRectf, 10, 10, bgPaint);
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return 0;
            }
        };
        setBackgroundDrawable(bg);
    }

    private void setItems() {
        int height = getMeasuredHeight();

        //每个元素的高度
        sectionHeight = height / section.size();

        for (int i = 0; i < section.size(); i++) {
            View itemView = createItemView(sectionHeight, section.get(i));
            addView(itemView);
        }
    }

    private View createItemView(int sectionHeight, String text) {
        TextView textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        params.weight = 1;
        textView.setLayoutParams(params);
        textView.setTextColor(textColor);
        textView.setTextSize(textSize);
        textView.setText(text);
        return textView;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                View child = getView(event.getY());
                for (int i = 0; i < getChildCount(); i++) {
                    TextView c = (TextView) getChildAt(i);
                    if (c == child) {
                        c.setTextColor(selectedTextColor);
                    } else {
                        c.setTextColor(textColor);
                    }
                }
                return true;
        }

        return false;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        sectionHeight = (getMeasuredHeight() - getPaddingBottom() - getPaddingTop()) / section.size();
    }

    private View getView(float y) {
        return getChildAt((int) ((y - getPaddingBottom()) / sectionHeight));
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getSelectedTextColor() {
        return selectedTextColor;
    }

    public void setSelectedTextColor(int selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
    }
}
