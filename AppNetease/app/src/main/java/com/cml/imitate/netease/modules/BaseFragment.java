package com.cml.imitate.netease.modules;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.socks.library.KLog;

import butterknife.ButterKnife;

/**
 * Created by cmlBeliever on 2016/4/20.
 */
public abstract class BaseFragment extends Fragment {

    public static final String TAG = BaseFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KLog.d(TAG, "oncreate=====>" + getClass().getSimpleName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContainerRes(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    protected abstract int getContainerRes();

}
