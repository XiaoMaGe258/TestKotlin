package com.max.test.testkotlin.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.WindowManager
import com.max.test.testkotlin.MainTabActivity
import com.max.test.testkotlin.R
import com.max.test.testkotlin.libs.splash.ModelSVG
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * 闪屏
 * Created by Max on 2018-3-2.
 */

class SplashActivity: BaseActivity() {

    private val checkhandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            animated_svg_text.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash)
        hintToolBar()
        setSvg(ModelSVG.values()[6])
        checkUpdate()
    }

    private fun setSvg(modelSvg: ModelSVG) {
        animated_svg_view.setGlyphStrings(*modelSvg.glyphs)
        animated_svg_view.setFillColors(modelSvg.colors)
        animated_svg_view.setViewportSize(modelSvg.width, modelSvg.height)
        animated_svg_view.setTraceResidueColor(0x32000000)
        animated_svg_view.setTraceColors(modelSvg.colors)
        animated_svg_view.rebuildGlyphData()
        animated_svg_view.start()
    }

    private fun checkUpdate() {
        object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(2500)
                    val msg = checkhandler.obtainMessage()
                    checkhandler.sendMessage(msg)
                    Thread.sleep(100)
                    toMain()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }.start()
    }

    fun toMain() {
        val intent = Intent(mContext, MainTabActivity::class.java)
        startActivity(intent)
        finish()
    }

}
