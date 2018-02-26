package com.max.test.testkotlin.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.max.test.testkotlin.R;

/**
 * 更多
 * */
@SuppressLint("ValidFragment")
public class HomeMoreFragment extends Fragment {

    public static HomeMoreFragment getInstance() {
        HomeMoreFragment sf = new HomeMoreFragment();
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_more, null);

        return v;
    }
}