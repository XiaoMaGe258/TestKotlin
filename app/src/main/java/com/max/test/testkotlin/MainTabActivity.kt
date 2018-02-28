package com.max.test.testkotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.flyco.tablayout.utils.UnreadMsgUtils
import com.max.test.testkotlin.entity.TabEntity
import com.max.test.testkotlin.fragment.HomeContactsFragment
import com.max.test.testkotlin.fragment.HomeMessageFragment
import com.max.test.testkotlin.fragment.HomeMoreFragment
import com.max.test.testkotlin.fragment.HomeTabsFragment
import kotlinx.android.synthetic.main.activity_main_tab.*
import java.util.*

class MainTabActivity : AppCompatActivity() {

    private val mTitles = arrayOf("首页", "消息", "联系人", "更多")//
    private val mIconUnSelectIds = intArrayOf(
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect)//
    private val mIconSelectIds = intArrayOf(
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select, R.mipmap.tab_more_select)//

    private val mFragments = ArrayList<Fragment>()
    private val mTabEntities = ArrayList<CustomTabEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_tab)
        for (i in mTitles.indices) {
            mTabEntities.add(TabEntity(mTitles[i], mIconSelectIds[i], mIconUnSelectIds[i]))
        }

        mFragments.add(HomeTabsFragment.getInstance())
        mFragments.add(HomeMessageFragment.getInstance())
        mFragments.add(HomeContactsFragment.getInstance())
        mFragments.add(HomeMoreFragment.getInstance())

        vp_view_pager.offscreenPageLimit = 3
        vp_view_pager.adapter = MyPagerAdapter(supportFragmentManager)

        initTab()

    }

    private fun initTab() {
        tl_tab_view.setTabData(mTabEntities)
        tl_tab_view.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                //第一次点击
                vp_view_pager.currentItem = position
            }

            override fun onTabReselect(position: Int) {
                //重复点击tab按钮
            }
        })

        vp_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                tl_tab_view.currentTab = position
                if(position == 1){
                    tl_tab_view.hideMsg(1)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })


//        tl_tab_view.showMsg(0, 3)
        tl_tab_view.showMsg(1, 10)
//        tl_tab_view.showMsg(2, 999)
        tl_tab_view.setMsgMargin(2, -10f, 0f)
//        val msgView1: MsgView = tl_tab_view.getMsgView(1)
//        msgView1.backgroundColor = Color.parseColor("#3355aa")
        tl_tab_view.showDot(3)
        val msgView3 = tl_tab_view.getMsgView(3)
        if (msgView3 != null) {
            UnreadMsgUtils.setSize(msgView3, dp2px(7f))
        }
    }

    private fun dp2px(dp: Float): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mTitles[position]
        }

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }
    }
}