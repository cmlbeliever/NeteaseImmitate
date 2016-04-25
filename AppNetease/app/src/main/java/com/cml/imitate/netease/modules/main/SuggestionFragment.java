package com.cml.imitate.netease.modules.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cml.imitate.netease.R;
import com.cml.imitate.netease.modules.BaseFragment;
import com.cml.imitate.netease.modules.main.adapter.SuggestionAdapter;
import com.cml.imitate.netease.modules.suggestion.AnchorRadioFragment;
import com.cml.imitate.netease.modules.suggestion.PersonalSuggestionFragment;
import com.cml.imitate.netease.modules.suggestion.RankFragment;
import com.cml.imitate.netease.modules.suggestion.SetListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by cmlBeliever on 2016/4/21.
 * 个性推荐
 */
public class SuggestionFragment extends BaseFragment {

    @Bind(R.id.suggestion_tab)
    TabLayout tabLayout;
    @Bind(R.id.suggestion_pager)
    ViewPager viewPager;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //设置viewpager信息
        List<Class<? extends BaseFragment>> pages = new ArrayList<>();

        pages.add(PersonalSuggestionFragment.class);
        pages.add(SetListFragment.class);
        pages.add(AnchorRadioFragment.class);
        pages.add(RankFragment.class);

        viewPager.setAdapter(new SuggestionAdapter(getFragmentManager(), getActivity(), pages));

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getContainerRes() {
        return R.layout.fragment_suggestion;
    }

    public static SuggestionFragment getInstance() {
        return new SuggestionFragment();
    }
}
