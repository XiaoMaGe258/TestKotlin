package com.max.test.testkotlin.fragment;

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.max.test.testkotlin.R
import kotlinx.android.synthetic.main.fragment_home_tabs.view.*
import java.util.*

class HomeTabsFragment : Fragment() {
    val mFragments = ArrayList<Fragment>()
    val mTitles = arrayOf(
            "热门", "Kotlin", "iOS", "Android", "前端", "后端", "设计", "工具资源")

    companion object {
        fun getInstance(): HomeTabsFragment {
            return HomeTabsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home_tabs, null)

        initData(v)

        return v
    }

    private fun initData(v: View){

        for (title in mTitles) {
            mFragments.add(SimpleCardFragment.getInstance(title))
        }

        v.vp_tab_item_pager.adapter = MyPagerAdapter(childFragmentManager)
        v.tl_sliding_tab.setViewPager(v.vp_tab_item_pager)
    }

    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mTitles[position]
        }
    }
}