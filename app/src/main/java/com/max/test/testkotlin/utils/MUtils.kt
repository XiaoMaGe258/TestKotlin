package com.max.test.testkotlin.utils

import android.content.Context
import android.content.Intent
import android.net.Uri


/**
 * Created by Administrator on 2018-3-3.
 */
class MUtils {

    companion object {

        fun actionCall(context: Context, number: String) {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number))
            context.startActivity(intent)
        }
    }
}