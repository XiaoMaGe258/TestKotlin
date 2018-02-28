package com.max.test.testkotlin.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.max.test.testkotlin.R
import kotlinx.android.synthetic.main.activity_web_view.*
import android.net.http.SslError
import android.webkit.*


/**
 * Created by Max on 2018-2-28.
 */
class WebViewActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        initView(intent.getStringExtra("url"))
    }

    private fun initView(url: String){
        val webSettings = wv_web.settings
        webSettings.javaScriptEnabled = true
        webSettings.setSupportZoom(false)
        webSettings.builtInZoomControls = false

        wv_web.webViewClient = object : WebViewClient() {

            override fun onReceivedSslError(view: WebView,
                                            handler: SslErrorHandler, error: SslError) {
                handler.proceed()
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }

        wv_web.loadUrl(url)
    }
}