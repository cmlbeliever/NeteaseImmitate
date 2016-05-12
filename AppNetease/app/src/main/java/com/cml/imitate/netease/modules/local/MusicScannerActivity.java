package com.cml.imitate.netease.modules.local;

import android.animation.Animator;
import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.cml.imitate.netease.R;
import com.cml.imitate.netease.modules.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by cmlBeliever on 2016/4/26.
 */
public class MusicScannerActivity extends BaseActivity implements MusicScannerContract.View {

    private MusicScannerContract.Present present;

    @Bind(R.id.scan_icon)
    ImageView scanImageView;

    @Bind(R.id.scan_effect_icon)
    ImageView scanEffectImageView;//扫描效果图片

    @Bind(R.id.scan_result_txt)
    TextView scanResultTextView;

    private ValueAnimator animator;//放大镜动画

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_scanner);
        ButterKnife.bind(this);
        new MusicScannerPresent(this);
        //扫描开始
        present.scan(this);
    }


    @Override
    public void updateScannerText(String path) {
        scanResultTextView.setText(path);
    }

    @Override
    public void updateScannerResult(String text) {
        scanResultTextView.setText(text);
    }


    @Override
    public void startScanAnim() {
        animator = ValueAnimator.ofFloat(360, 0);
        animator.setDuration(50000);
        animator.setEvaluator(new FloatEvaluator());
        animator.setRepeatCount(-1);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatMode(ValueAnimator.REVERSE);
        final int radius = 30;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float angle = (float) animation.getAnimatedValue();
                float translateX = (float) (radius * Math.cos(angle));
                float translateY = (float) (radius * Math.sin(angle));
                scanImageView.setTranslationX(translateX);
                scanImageView.setTranslationY(translateY);
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                scanImageView.setSelected(true);
                scanImageView.setTranslationX(0);
                scanImageView.setTranslationY(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();

        //扫描效果添加
//        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 10, 500);
        TranslateAnimation translateAnimation = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_PARENT, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.7f);
        translateAnimation.setInterpolator(new DecelerateInterpolator());
        translateAnimation.setDuration(2000);
        translateAnimation.setRepeatCount(-1);
        translateAnimation.setRepeatMode(TranslateAnimation.RESTART);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                scanEffectImageView.setVisibility(View.VISIBLE);
//                scanEffectImageView.bringToFront();
//                scanEffectImageView.requestLayout();
//                scanEffectImageView.invalidate();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                scanEffectImageView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        scanEffectImageView.clearAnimation();
        scanEffectImageView.startAnimation(translateAnimation);
    }

    @Override
    public void stopScanAnim() {
        scanEffectImageView.clearAnimation();
        scanEffectImageView.setVisibility(View.GONE);
        if (null != animator && animator.isRunning()) {
            animator.end();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setPresenter(MusicScannerContract.Present presenter) {
        this.present = presenter;
    }
}
