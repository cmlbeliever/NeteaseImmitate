package com.cml.imitate.netease.modules.main;

import com.cml.imitate.netease.R;
import com.cml.imitate.netease.modules.BaseFragment;

/**
 * Created by cmlBeliever on 2016/4/21.
 * 个性推荐
 */
public class SuggestionFragment extends BaseFragment{
    @Override
    protected int getContainerRes() {
        return R.layout.fragment_suggestion;
    }

    public static SuggestionFragment getInstance(){
        return new SuggestionFragment();
    }
}
