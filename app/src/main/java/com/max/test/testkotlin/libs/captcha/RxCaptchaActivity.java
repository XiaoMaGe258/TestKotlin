package com.max.test.testkotlin.libs.captcha;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.max.test.testkotlin.R;
import com.max.test.testkotlin.ui.BaseActivity;
import com.max.test.testkotlin.utils.MToast;

public class RxCaptchaActivity extends BaseActivity implements View.OnClickListener {

    RxSwipeCaptcha mRxSwipeCaptcha;
    SeekBar mSeekBar;
    Button mBtnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captcha);
        initView();
    }

    private void initView() {
        setTitle("滑块验证码");
        mRxSwipeCaptcha = findViewById(R.id.sc_swipe_captcha);
        mSeekBar = findViewById(R.id.sb_seek_bar);
        mBtnChange = findViewById(R.id.btn_change);
        mBtnChange.setOnClickListener(this);
        mRxSwipeCaptcha.setOnCaptchaMatchCallback(new RxSwipeCaptcha.OnCaptchaMatchCallback() {
            @Override
            public void matchSuccess(RxSwipeCaptcha rxSwipeCaptcha) {
                MToast.show(RxCaptchaActivity.this, "验证通过！");
                mSeekBar.setEnabled(false);
            }

            @Override
            public void matchFailed(RxSwipeCaptcha rxSwipeCaptcha) {
                MToast.show(RxCaptchaActivity.this, "验证失败:拖动滑块将悬浮头像正确拼合");
                rxSwipeCaptcha.resetCaptcha();
                mSeekBar.setProgress(0);
            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mRxSwipeCaptcha.setCurrentSwipeValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //随便放这里是因为控件
                mSeekBar.setMax(mRxSwipeCaptcha.getMaxSwipeValue());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mRxSwipeCaptcha.matchCaptcha();
            }
        });

        //测试从网络加载图片是否ok
        RequestOptions options = new RequestOptions();
        Glide.with(this).load(R.drawable.douyu).apply(options).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                mRxSwipeCaptcha.setImageBitmap(((BitmapDrawable)resource).getBitmap());
                mRxSwipeCaptcha.createCaptcha();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_change) {
            mRxSwipeCaptcha.createCaptcha();
            mSeekBar.setEnabled(true);
            mSeekBar.setProgress(0);
        }
    }
}
