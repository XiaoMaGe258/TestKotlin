package com.max.test.testkotlin.ui

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.max.test.testkotlin.R
import kotlinx.android.synthetic.main.custom_toolbar_general.*

/**
 * Created by Administrator on 2018-3-2.
 */
open class BaseActivity: AppCompatActivity() {

    var mContext: Activity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_toolbar_general)
        mContext = this
        initCustomActionBar()
    }

    private fun initCustomActionBar(){
        action_back_layout.setOnClickListener { finish() }
    }

    fun setTitle(title: String){
        ab_title.text = title
    }
    fun hintBackFlag(){
        action_back_layout.visibility = View.GONE
    }
    fun hintToolBar(){
        toolbar_root.visibility = View.GONE
    }
    override fun setContentView(layoutSourceId: Int) {

        if (R.layout.custom_toolbar_general == layoutSourceId) {
            super.setContentView(R.layout.custom_toolbar_general)
            layout_center.removeAllViews()
        } else {
            val addView = LayoutInflater.from(this).inflate(layoutSourceId, null)
            layout_center.addView(addView,
                    LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT))
        }
    }
}