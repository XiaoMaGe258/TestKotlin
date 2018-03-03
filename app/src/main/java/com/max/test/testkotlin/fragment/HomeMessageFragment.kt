package com.max.test.testkotlin.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
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
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.max.test.testkotlin.R
import com.max.test.testkotlin.ui.WebViewActivity
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_home_message.view.*
import java.util.*
import kotlin.collections.ArrayList


class HomeMessageFragment : Fragment(), OnRefreshListener, OnLoadmoreListener, BaseQuickAdapter.OnItemClickListener {

    private val mImages = ArrayList<String>()
    private val mListItems = ArrayList<HomeItem>()
    private var mAdapter: HomeAdapter? = null
    private var mRefreshLayout: RefreshLayout? = null
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
        addBannerData()
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
        mRefreshLayout = v.srl_refresh_layout
        v.srl_refresh_layout.refreshHeader = MaterialHeader(context)
        v.srl_refresh_layout.refreshFooter = ClassicsFooter(context)
        v.srl_refresh_layout.isEnableOverScrollBounce = false
        v.srl_refresh_layout.setEnableHeaderTranslationContent(false)
        v.srl_refresh_layout.setOnRefreshListener(this)
        v.srl_refresh_layout.setOnLoadmoreListener(this)

        v.rv_list.isNestedScrollingEnabled = false//用NestedScrollView替代ScrollView，解决滑动黏连问题
        v.rv_list.layoutManager = LinearLayoutManager(context)
        v.rv_list.isFocusable = false//解决刷新后，自动跳到表头位置的问题
        v.rv_list.addItemDecoration(MySimpleDivider(context!!))
        mAdapter = HomeAdapter(R.layout.item_message, mListItems)
        //设置动画
        mAdapter!!.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        //每次都显示动画
        mAdapter!!.isFirstOnly(false)
        v.rv_list.adapter = mAdapter!!
        mAdapter!!.onItemClickListener = this
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra("url", mListItems[position].contentUrl)
        startActivity(intent)
    }

    override fun onRefresh(refreshLayout: RefreshLayout?) {
        getData(true)
    }

    override fun onLoadmore(refreshLayout: RefreshLayout?) {
        getData(false)
    }

    private fun getData(isRefreshing: Boolean) {

            Handler().postDelayed(Runnable {
                if (isRefreshing) {
                    mListItems.clear()
                }
                for(i in 0 until 10){
                    mListItems.add(getRandomHomeItem())
                }

                mAdapter!!.notifyDataSetChanged()
                mRefreshLayout!!.finishRefresh(true)
                mRefreshLayout!!.finishLoadmore(true)
            }, 1000)
    }

    inner class HomeItem(imgId: Int, title: String, number: Int, flag: String, contentUrl: String) {
        var title: String? = title
        var imageResource: Int = imgId
        var num: Int = number
        var flag: String = flag
        var contentUrl: String = contentUrl
    }

    inner class HomeAdapter(layoutResId: Int, data: List<HomeItem>) : BaseQuickAdapter<HomeItem, BaseViewHolder>(layoutResId, data) {

        override fun convert(helper: BaseViewHolder, item: HomeItem) {
            helper.setText(R.id.tv_from_flag, item.flag)
            helper.setText(R.id.tv_title, item.title)
            helper.setText(R.id.tv_num, ""+item.num+"条评论")
            helper.setImageResource(R.id.iv_img, item.imageResource)
        }
    }

    private inner class MySimpleDivider : RecyclerView.ItemDecoration {

        //分割线的高度。默认没有
        private var dividerHeight: Int = 0
        private var dividerPaint: Paint? = null

        constructor(context: Context) {
            //默认分割线高度为1dp
            this.dividerPaint = Paint()
            this.dividerPaint!!.color = Color.parseColor("#CCCCCC")
            this.dividerHeight = dip2px(context, 1f)
        }

        constructor(context: Context, colorId: Int, dividerHeight: Int) {
            //自定义分割线高度，颜色
            this.dividerPaint = Paint()
            this.dividerPaint!!.color = colorId
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

    private val newsTitleArray = arrayOf(
            "愤怒的小鸟竟然真的存在！模样实在是萌化了",
            "男子花300元买的哈士奇，结果越长越奇怪......这是大师兄吧！",
            "小羊认为自己是一只狗 每日要遛还能吃狗粮",
            "上交寒假作业把老师气得直冒火？同学，鸡毛掸子了解下？",
            "陆客潮退了台游客露“本性” 大陆终于不用背锅了"
    )

    private val newsContentArray = arrayOf(
            "https://m.huanqiu.com/r/MV8wXzExNjI3MDQ0XzUzXzE1MTk3MTUxMDA=?tt_group_id=6527127919154692612",
            "https://www.toutiao.com/a6527071495796032007/",
            "http://t.m.youth.cn/transfer/toutiao/url/d.youth.cn/shrgch/201802/t20180227_11446697.htm?tt_group_id=6527024557486768654",
            "https://www.toutiao.com/a6527112758545089037/",
            "http://m.haiwainet.cn/ttc/345658/2018/0223/content_31263923_1.html?s=toutiao&tt_group_id=6525691103755108878"
    )

    private val newsIconArray = arrayOf(
            R.drawable.ic_news1,
            R.drawable.ic_news2,
            R.drawable.ic_news3,
            R.drawable.ic_news4,
            R.drawable.ic_news5
    )

    private val newsNumArray = arrayOf(
            1343,
            1985,
            2287,
            1358,
            991
    )

    private val newsFlagArray = arrayOf(
            "摄影",
            "宠物",
            "宠物",
            "社会",
            "旅游"
    )

    private fun getRandomHomeItem(): HomeItem{
        val random = Random()
        val index = random.nextInt(5)
        return HomeItem(newsIconArray[index], newsTitleArray[index], newsNumArray[index],
                newsFlagArray[index], newsContentArray[index])
    }

    private fun addBannerData(){
        mImages.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1266198176,1144251370&fm=200&gp=0.jpg")
        mImages.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1597558927,1189009039&fm=27&gp=0.jpg")
        mImages.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=766806198,3110259661&fm=27&gp=0.jpg")
    }
}