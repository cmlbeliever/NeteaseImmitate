package com.cml.imitate.netease.modules.main;

import com.cml.imitate.netease.R;
import com.cml.imitate.netease.modules.BaseFragment;

/**
 * Created by cmlBeliever on 2016/4/21.
 * 歌单
 */
public class LocalSetlistFragment extends BaseFragment{
    @Override
    protected int getContainerRes() {
        return  R.layout.fragment_setlist;
    }

    public static LocalSetlistFragment getInstance(){
        return new LocalSetlistFragment();
    }
}
