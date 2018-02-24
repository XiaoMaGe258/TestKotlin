package com.max.test.testkotlin.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.max.test.testkotlin.R;
//import kotlinx.android.synthetic.main.fragment_tabs.*
import java.util.ArrayList;

public class TabsFragment extends Fragment {
    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
    private String[] mTitles ={
            "热门", "Kotlin", "iOS", "Android", "前端", "后端", "设计", "工具资源"};

    public static TabsFragment getInstance() {
        TabsFragment sf = new TabsFragment();
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tabs, null);

        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance(title));
        }

        SlidingTabLayout tabLayout = v.findViewById(R.id.tl_sliding_tab);
        ViewPager vp = v.findViewById(R.id.vp_tab_item_pager);
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        vp.setAdapter(adapter);
        tabLayout.setViewPager(vp);

        return v;
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}