package com.max.test.testkotlin.fragment;

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.max.test.testkotlin.R
import com.max.test.testkotlin.R.id.cv_calendar
import com.max.test.testkotlin.entity.MContent
import com.max.test.testkotlin.libs.captcha.RxCaptchaActivity
import com.max.test.testkotlin.ui.RotateImageActivity
import com.max.test.testkotlin.utils.MUtils
import com.max.test.testkotlin.utils.MyToast
import com.max.test.testkotlin.utils.SignSelectorDecorator
import com.max.test.testkotlin.utils.SignedSelectorDecorator
import com.max.test.testkotlin.view.ItemBar
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.android.synthetic.main.fragment_home_more.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * 更多
 * */
class HomeMoreFragment : Fragment(), View.OnClickListener, SweetAlertDialog.OnSweetClickListener {

    var mImgView: ImageView? = null
    var mPicPath: String? = null
    var mIBSignItem: ItemBar? = null

    companion object {
        fun getInstance(): HomeMoreFragment {
            return HomeMoreFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home_more, null)
        initView(v)
        initCalendarDate()
        return v
    }

    private fun initView(v: View){
        mImgView = v.iv_avatar
        mImgView!!.setOnClickListener(this)
        v.btn_view_image.setOnClickListener(this)
        v.ib_more_item1.setOnClickListener(this)
        v.ib_more_item2.setOnClickListener(this)
        v.ib_more_item3.setOnClickListener(this)
        v.ib_more_item4.setOnClickListener(this)
        v.ib_more_item5.setOnClickListener(this)
        mIBSignItem = v.ib_more_item1
        v.ib_more_item5.hint = MContent.telephone
    }

    override fun onClick(v: View) {
        when (v) {
            v.iv_avatar ->
                selectPicture(false, false)
            v.btn_view_image -> {
                if (mPicPath == null) {
                    Toast.makeText(context, "先选择图片", Toast.LENGTH_SHORT).show()
                    return
                } else {
                    rotateImage()
                }
            }
            v.ib_more_item1 -> {
                showWorkDateDatePicker()
            }
            v.ib_more_item2 -> {
                MyToast.show(context, "别想了，不可能的")
            }
            v.ib_more_item3 -> {
                MyToast.show(context, "净想美事呢")
            }
            v.ib_more_item4 -> {
                actionCaptcha()
            }
            v.ib_more_item5 -> {
                MUtils.actionCall(context!!, MContent.telephone)
            }
        }
    }

    private fun rotateImage() {
        val intent = Intent(activity, RotateImageActivity::class.java)
        intent.putExtra("imageUrl", mPicPath)
        startActivityForResult(intent, RotateImageActivity.RequestCode)
    }

    private fun actionCaptcha() {
        val intent = Intent(activity, RxCaptchaActivity::class.java)
        startActivity(intent)
    }

    private fun selectPicture(enableCrop: Boolean, enableMultiple: Boolean) {

        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
//                .theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(8)// 最大图片选择数量 int
//                .minSelectNum()// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(getSelectModel(enableMultiple))// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
//                .previewVideo()// 是否可预览视频 true or false
                .enablePreviewAudio(false) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/jianzhibao")// 自定义拍照保存路径,可不填
                .enableCrop(enableCrop)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
//                .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
//                .compressSavePath("/jianzhibao")//压缩图片保存地址
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                .selectionMedia(null)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
//                .videoQuality()// 视频录制质量 0 or 1 int
                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
//                .recordVideoSecond()//视频秒数录制 默认60s int
                .forResult(PictureConfig.CHOOSE_REQUEST)//结果回调onActivityResult code
    }

    private fun getSelectModel(enableMultiple: Boolean): Int {
        if (enableMultiple) {
            return PictureConfig.MULTIPLE
        } else {
            return PictureConfig.SINGLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    // 图片选择结果回调
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

                    val picObj = selectList[0]
                    var picPath = ""
                    when {
                        picObj.isCut -> picPath = picObj.cutPath
                        picObj.isCompressed -> picPath = picObj.compressPath
                        else -> picPath = picObj.path
                    }

                    mPicPath = picPath
                    setImage()
                }
                RotateImageActivity.RequestCode -> {
                    mPicPath = data!!.getStringExtra("imgUrl")
                    setImage()
                }
            }
        }
    }

    private fun setImage() {
        val options = RequestOptions()
        options.skipMemoryCache(true)
        options.diskCacheStrategy(DiskCacheStrategy.NONE)
        Glide.with(this).load(mPicPath).apply(options).into(mImgView!!)
    }

    private var mCalendarDialog: AlertDialog? = null
    private var mDialogView: View? = null
    private var mCalendarView: MaterialCalendarView? = null
    private var mDecorator: SignSelectorDecorator? = null
    private val mSignedDecoratorArray = ArrayList<CalendarDay>()
    private fun initCalendarDate() {
        mDecorator = SignSelectorDecorator(activity)
        mDialogView = LayoutInflater.from(context).inflate(R.layout.calendar_dialog_layout, null)
        mCalendarView = mDialogView!!.findViewById(cv_calendar)
        mCalendarView!!.setTitleFormatter { day ->
            val dateFormat = SimpleDateFormat("yyyy年MM月", Locale.getDefault())
            dateFormat.format(day.date)
        }
        mCalendarView!!.setWeekDayLabels(arrayOf("日", "一", "二", "三", "四", "五", "六"))
        mCalendarDialog = AlertDialog.Builder(context!!).setView(mDialogView).create()
        val beforeDay = Calendar.getInstance()
        for (i in 0 until 3) {
            beforeDay.set(Calendar.DAY_OF_MONTH, beforeDay.get(Calendar.DAY_OF_MONTH) - 1)
            mSignedDecoratorArray.add(CalendarDay.from(beforeDay))
        }
        val signedDecorator = SignedSelectorDecorator(activity, mSignedDecoratorArray)
        mCalendarView!!.addDecorator(signedDecorator)
    }

    private fun showWorkDateDatePicker() {
        val maxDays = 0 //最多显示可点日期 0当日 1次日 ...
        mCalendarView!!.setOnDateChangedListener { widget, date, selected ->
            if(mDecorator!!.date != null){
                val alert = SweetAlertDialog(activity!!, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("哎？")
                        .setContentText("今天你不是已经签过到了吗？")
                        .setConfirmButton("这样啊", this)
                alert.setCanceledOnTouchOutside(false)
                alert.setCancelable(false)
                alert.show()
                return@setOnDateChangedListener
            }
            mDecorator!!.setDate(Date())
            widget.invalidateDecorators()

            val dialog = SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                    .setContentText("签到成功，长此以往，必将走上人生巅峰")
                    .setConfirmButton("没问题", this)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            dialog.show()
        }
        mCalendarView!!.showOtherDates = MaterialCalendarView.SHOW_OTHER_MONTHS

        mCalendarView!!.clearSelection()
        mCalendarView!!.addDecorator(mDecorator)


        val minTime = Calendar.getInstance()
        val maxTime = Calendar.getInstance()
        maxTime.time = Date()
        maxTime.set(Calendar.DAY_OF_MONTH, maxTime.get(Calendar.DAY_OF_MONTH) + maxDays)
        mCalendarView!!.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(minTime.time))
                .setMaximumDate(CalendarDay.from(maxTime.time))
                .commit()
        mCalendarDialog!!.show()
    }

    override fun onClick(sweetAlertDialog: SweetAlertDialog?) {
        when(sweetAlertDialog!!.alerType){
            SweetAlertDialog.SUCCESS_TYPE -> {
                mCalendarDialog!!.dismiss()
                sweetAlertDialog.dismissWithAnimation()
                mIBSignItem!!.setHint("今天已经签到", Color.parseColor("#999999"))
            }
            SweetAlertDialog.WARNING_TYPE -> {
                sweetAlertDialog.dismiss()
            }
        }

    }

}