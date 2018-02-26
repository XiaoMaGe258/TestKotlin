package com.max.test.testkotlin.fragment;

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.max.test.testkotlin.R

/**
 * 更多
 * */
class HomeMoreFragment : Fragment() {

    companion object {
        fun getInstance(): HomeMoreFragment {
            return HomeMoreFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_home_more, null)
        initData()
        return v
    }

    private fun initData(){

    }
}