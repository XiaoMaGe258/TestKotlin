package com.max.test.testkotlin.fragment

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.max.test.testkotlin.R
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_home_message.view.*
import kotlin.collections.ArrayList
import android.support.v7.widget.RecyclerView
//import sun.swing.SwingUtilities2.drawRect
//import com.sun.deploy.ui.CacheUpdateProgressDialog.dismiss
import com.max.test.testkotlin.fragment.HomeMessageFragment.HomeItem
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.BaseQuickAdapter







class HomeMessageFragment : Fragment() {

    private val mImages = ArrayList<String>()
    private val mListItems = ArrayList<HomeItem>()
    private var mAdapter: HomeAdapter? = null
    companion object {
        fun getInstance(): HomeMessageFragment {
            return HomeMessageFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home_message, null)
        initData(v)
        return v
    }

    private fun initData(v: View){
        initBanner(v)
        initList(v)
        getData(true)
    }

    private fun initBanner(v: View){
        mImages.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1266198176,1144251370&fm=200&gp=0.jpg")
        mImages.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1597558927,1189009039&fm=27&gp=0.jpg")
        mImages.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=766806198,3110259661&fm=27&gp=0.jpg")

        v.bn_banner.setImageLoader(GlideImageLoader())
        v.bn_banner.setImages(mImages)
        v.bn_banner.start()
    }

    private inner class GlideImageLoader : ImageLoader() {
        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
            Glide.with(context!!).load(path).apply(RequestOptions()).into(imageView!!)
        }
    }


    private fun initList(v: View){
        v.rv_list.isNestedScrollingEnabled = false//用NestedScrollView替代ScrollView，解决滑动黏连问题
        v.rv_list.layoutManager = LinearLayoutManager(context)
        v.rv_list.addItemDecoration(MySimpleDivider(context!!))
        mAdapter = HomeAdapter(R.layout.message_item, mListItems)
        //设置动画
        mAdapter!!.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        //每次都显示动画
        mAdapter!!.isFirstOnly(false)
        v.rv_list.adapter = mAdapter!!
    }

    private fun getData(isRefreshing: Boolean) {

            Handler().postDelayed(Runnable {
                if (isRefreshing) {
                    mListItems.clear()
                }
                for(i in 0 until 10){
                    mListItems.add(HomeItem(R.mipmap.ic_launcher, "你的名字"+i))
                }

                mAdapter!!.notifyDataSetChanged()
//                mPull2RefreshLayout.finishRefresh(true)
//                mPull2RefreshLayout.finishLoadmore(true)
//                if (mDialog != null) {
//                    mDialog.dismiss()
//                }
            }, 2000)
    }

    inner class HomeItem {
        var title: String? = null
        var imageResource: Int = 0

        constructor() {}
        constructor(imgId: Int, title: String) {
            this.imageResource = imgId
            this.title = title
        }
    }

    inner class HomeAdapter(layoutResId: Int, data: List<HomeItem>) : BaseQuickAdapter<HomeItem, BaseViewHolder>(layoutResId, data) {

        override fun convert(helper: BaseViewHolder, item: HomeItem) {
            helper.setText(R.id.tv_tab, item.title)
            helper.setImageResource(R.id.img_tab, item.imageResource)
        }
    }

    private inner class MySimpleDivider : RecyclerView.ItemDecoration {

        private var dividerHeight: Int = 0
        private var dividerPaint: Paint? = null

        constructor(context: Context) {
            this.dividerPaint = Paint()
            this.dividerPaint!!.setColor(context.resources.getColor(R.color.colorPrimary))
            this.dividerHeight = dip2px(context, 1f)
        }

        constructor(context: Context, colorId: Int, dividerHeight: Int) {
            this.dividerPaint = Paint()
            this.dividerPaint!!.setColor(context.resources.getColor(colorId))
            this.dividerHeight = dip2px(context, dividerHeight.toFloat())
        }

        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            val childCount = parent.childCount
            val left = parent.paddingLeft + 0f
            val right = parent.width - parent.paddingRight + 0f

            for (i in 0 until childCount - 1) {
                val view = parent.getChildAt(i)
                val top = view.bottom.toFloat()
                val bottom = (view.bottom + dividerHeight).toFloat()
                c.drawRect(left, top, right, bottom, dividerPaint)
            }
        }

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
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