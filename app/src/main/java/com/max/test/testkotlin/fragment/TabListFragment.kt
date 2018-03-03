package com.max.test.testkotlin.fragment;

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.max.test.testkotlin.R
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_tab_list.view.*
import java.util.ArrayList

class TabListFragment: Fragment(), OnRefreshListener, OnLoadmoreListener {

    var mPull2RefreshLayout: SmartRefreshLayout? = null
    var mListView: RecyclerView? = null
    private val mItems = ArrayList<HomeItem>()
    private var mAdapter: HomeAdapter? = null

    companion object {
        fun getInstance(): TabListFragment {
            return TabListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_tab_list, null)
        mListView = v.rv_list
        mPull2RefreshLayout = v.pull_2_refresh_layout
        initView()
        getData(true, 0)
        return v
    }

    private fun initView(){
        mPull2RefreshLayout!!.refreshHeader = MaterialHeader(context)
        mPull2RefreshLayout!!.refreshFooter = ClassicsFooter(context)
        mPull2RefreshLayout!!.isEnableOverScrollBounce = false
        mPull2RefreshLayout!!.setEnableHeaderTranslationContent(false)
        mPull2RefreshLayout!!.setOnRefreshListener(this)
        mPull2RefreshLayout!!.setOnLoadmoreListener(this)

        mListView!!.layoutManager = LinearLayoutManager(context)
        mListView!!.addItemDecoration(MySimpleDivider(context!!))
        mAdapter = HomeAdapter(R.layout.tab_list_item, mItems)
        //设置动画
        mAdapter!!.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        //每次都显示动画
        mAdapter!!.isFirstOnly(false)
        mListView!!.adapter = mAdapter
    }

    private fun getData(isRefreshing: Boolean, delayedTime: Long = 2000) {

        Handler().postDelayed(object: Runnable {
            override fun run() {
                if (isRefreshing) {
                    mItems.clear()
                }
                mItems.add(HomeItem(R.mipmap.ic_launcher, "你的名字0", "名字很长很长很长很长很长很长很长很长"))
                mItems.add(HomeItem(R.mipmap.ic_launcher, "你的名字1", "名字很长很长很长很长很长很长很长很长"))
                mItems.add(HomeItem(R.mipmap.ic_launcher, "你的名字2", "名字很长很长很长很长很长很长很长很长"))
                mItems.add(HomeItem(R.mipmap.ic_launcher, "你的名字3", "名字很长很长很长很长很长很长很长很长"))
                mItems.add(HomeItem(R.mipmap.ic_launcher, "你的名字4", "名字很长很长很长很长很长很长很长很长"))
                mItems.add(HomeItem(R.mipmap.ic_launcher, "你的名字5", "名字很长很长很长很长很长很长很长很长"))
                mItems.add(HomeItem(R.mipmap.ic_launcher, "你的名字6", "名字很长很长很长很长很长很长很长很长"))
                mItems.add(HomeItem(R.mipmap.ic_launcher, "你的名字7", "名字很长很长很长很长很长很长很长很长"))
                mItems.add(HomeItem(R.mipmap.ic_launcher, "你的名字8", "名字很长很长很长很长很长很长很长很长"))
                mItems.add(HomeItem(R.mipmap.ic_launcher, "你的名字9", "名字很长很长很长很长很长很长很长很长"))
                mItems.add(HomeItem(R.mipmap.ic_launcher, "你的名字10", "名字很长很长很长很长很长很长很长很长"))
                mAdapter!!.notifyDataSetChanged()
                mPull2RefreshLayout!!.finishRefresh(true)
                mPull2RefreshLayout!!.finishLoadmore(true)
            }

        }, delayedTime)

    }

    override fun onLoadmore(refreshlayout: RefreshLayout?) {
        getData(false)
    }

    override fun onRefresh(refreshlayout: RefreshLayout?) {
        getData(true)
    }

    inner class HomeAdapter(layoutResId: Int, data: List<HomeItem>) : BaseQuickAdapter<HomeItem, BaseViewHolder>(layoutResId, data) {

        override fun convert(helper: BaseViewHolder, item: HomeItem) {
            helper.setImageResource(R.id.img_tab, item.imageResource)
            helper.setText(R.id.tv_title, item.title)
            helper.setText(R.id.tv_content, item.content)
        }
    }

    inner class HomeItem {
        var imageResource: Int = 0
        var title: String? = null
        var content: String? = null

        constructor() {}
        constructor(imgId: Int, title: String, content: String) {
            imageResource = imgId
            this.title = title
            this.content = content
        }
    }

    internal inner class MySimpleDivider : RecyclerView.ItemDecoration {

        private var dividerHeight: Int = 0
        private var dividerPaint: Paint? = null

        constructor(context: Context) {
            this.dividerPaint = Paint()
            this.dividerPaint!!.color = context.resources.getColor(R.color.colorPrimary)
            this.dividerHeight = dip2px(context, 1f)
        }

        constructor(context: Context, colorId: Int, dividerHeight: Int) {
            this.dividerPaint = Paint()
            this.dividerPaint!!.color = context.resources.getColor(colorId)
            this.dividerHeight = dip2px(context, dividerHeight.toFloat())
        }

        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
            val childCount = parent.childCount
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight

            for (i in 0 until childCount - 1) {
                val view = parent.getChildAt(i)
                val top = view.bottom.toFloat()
                val bottom = (view.bottom + dividerHeight).toFloat()
                c.drawRect(left.toFloat(), top, right.toFloat(), bottom, dividerPaint!!)
            }
        }

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
            super.getItemOffsets(outRect, view, parent, state)
            // 在底部空出1dp的空间，画divider线
            outRect.bottom = dividerHeight
        }

        fun dip2px(context: Context, dipValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dipValue * scale + 0.5f).toInt()
        }
    }
}