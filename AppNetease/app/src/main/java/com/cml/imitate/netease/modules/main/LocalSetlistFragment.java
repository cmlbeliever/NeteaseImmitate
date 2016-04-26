package com.cml.imitate.netease.modules.main;

import android.content.Intent;
import android.view.View;

import com.cml.imitate.netease.R;
import com.cml.imitate.netease.modules.BaseFragment;
import com.cml.imitate.netease.modules.local.MusicScannerActivity;

import butterknife.OnClick;

/**
 * Created by cmlBeliever on 2016/4/21.
 * 歌单
 */
public class LocalSetlistFragment extends BaseFragment {
    @Override
    protected int getContainerRes() {
        return R.layout.fragment_setlist;
    }

    @OnClick(R.id.dddd)
    public void startScanActivity(View view) {
        Intent intent = new Intent(getActivity(), MusicScannerActivity.class);
        startActivity(intent);
    }

    public static LocalSetlistFragment getInstance() {
        return new LocalSetlistFragment();
    }
}
