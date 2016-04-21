package com.cml.imitate.netease.modules.main;

import com.cml.imitate.netease.R;
import com.cml.imitate.netease.modules.BaseFragment;

/**
 * Created by cmlBeliever on 2016/4/21.
 */
public class FriendFragment extends BaseFragment {
    @Override
    protected int getContainerRes() {
        return R.layout.fragment_friend;
    }

    public static FriendFragment getInstance(){
        return new FriendFragment();
    }
}
