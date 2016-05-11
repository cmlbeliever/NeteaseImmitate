package com.cml.imitate.netease.anim;

import android.animation.FloatEvaluator;
import android.animation.TypeEvaluator;
import android.view.View;

/**
 * Created by cmlBeliever on 2016/5/11.
 */
public class CircleInterpolator implements TypeEvaluator<Float> {

    private float radius;
    private FloatEvaluator floatEvaluator = new FloatEvaluator();
    private View view;

    public CircleInterpolator(float radius, View view) {
        this.radius = radius;
        this.view = view;
    }

    @Override
    public Float evaluate(float fraction, Float startValue, Float endValue) {
        return null;
    }
}
