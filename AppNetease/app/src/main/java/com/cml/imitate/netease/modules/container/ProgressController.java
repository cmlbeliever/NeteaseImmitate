package com.cml.imitate.netease.modules.container;

import android.os.CountDownTimer;

/**
 * Created by cmlBeliever on 2016/5/24.
 */
public class ProgressController {
    private CountDownTimer countDownTimer;

    public ProgressController(final int duration, long delay, final ContainerContract.View view) {
        countDownTimer = new CountDownTimer(duration, delay) {
            @Override
            public void onTick(long millisUntilFinished) {
                view.setPlayProgress((int) ((duration - millisUntilFinished) / (float) duration * 100));
            }

            @Override
            public void onFinish() {
                view.setPlayProgress(0);
            }
        };

    }

    public void start() {
        countDownTimer.start();
    }

    public void cancel() {
        countDownTimer.cancel();
    }
}
