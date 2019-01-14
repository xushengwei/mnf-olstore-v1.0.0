package com.mshd.olstore.adapter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mshd.olstore.ui.fragment.GoodsPagerFragment;

import java.util.List;

/**
 * @author xushengwei
 * @date 2018/12/14
 */
public class GoodsPagerAdapter extends FragmentPagerAdapter {

    private  List<String> mTitles;
    private List<GoodsPagerFragment> fragments;

    public GoodsPagerAdapter(FragmentManager fm, List<String> mTitles, List<GoodsPagerFragment> fragments) {
        super(fm);
        this.fragments = fragments;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments!=null?fragments.get(i):null;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

}
