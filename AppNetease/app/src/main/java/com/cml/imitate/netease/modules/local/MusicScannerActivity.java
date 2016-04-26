package com.cml.imitate.netease.modules.local;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cml.imitate.netease.R;
import com.cml.imitate.netease.modules.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by cmlBeliever on 2016/4/26.
 */
public class MusicScannerActivity extends BaseActivity implements MusicScannerContract.View {

    private MusicScannerContract.Present present;

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

    }

    @Override
    public void updateScannerResult(String text) {

    }

    @Override
    public void setPresenter(MusicScannerContract.Present presenter) {
        this.present = presenter;
    }
}
