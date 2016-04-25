package com.cml.imitate.netease.modules.main.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cml.imitate.netease.R;
import com.cml.imitate.netease.modules.BaseFragment;

import java.util.List;

/**
 * Created by cmlBeliever on 2016/4/25.
 */
public class SuggestionAdapter extends FragmentPagerAdapter {

    private List<Class<? extends BaseFragment>> datas;
    private Context context;
    private String[] titles;

    public SuggestionAdapter(FragmentManager fm, Context context, List<Class<? extends BaseFragment>> datas) {
        super(fm);
        this.context = context;
        this.datas = datas;
        titles = context.getResources().getStringArray(R.array.title_suggestion);
    }

    @Override
    public Fragment getItem(int position) {
        return Fragment.instantiate(context, datas.get(position).getName());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return datas.size();
    }
}
