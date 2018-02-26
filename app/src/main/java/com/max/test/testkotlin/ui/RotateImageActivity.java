package com.max.test.testkotlin.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.max.test.testkotlin.R;

/**
 * 旋转照片
 * Created by Max on 2017-7-31.
 */

public class RotateImageActivity extends AppCompatActivity {

    Bitmap mTarBitmap;
    String mTarImgUrl;
    ImageView mIvBigImage;
    Button mBtnRotate;
    public static void actionActivity(Activity fromActivity, String imageUrl){
        Intent intent = new Intent(fromActivity, RotateImageActivity.class);
        intent.putExtra("imageUrl", imageUrl);
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate_image);
        mTarImgUrl = getIntent().getStringExtra("imageUrl");
        mTarBitmap = getIntent().getParcelableExtra("bitmap");
        initView();
    }

    private void initView() {
        mBtnRotate = findViewById(R.id.btn_rotate);
        mIvBigImage = findViewById(R.id.iv_big_image);

        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.ic_launcher);
        Glide.with(this).load(mTarImgUrl).apply(options).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                mTarBitmap = ((BitmapDrawable)resource).getBitmap();
                mIvBigImage.setImageBitmap(mTarBitmap);
            }
        });

        if(mTarBitmap != null){
            mIvBigImage.setImageBitmap(mTarBitmap);
        }
        mBtnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateImage();
            }
        });
    }

    private void rotateImage(){
        if(mTarBitmap==null) return;
        Matrix matrix = new Matrix();
//        var bitmap = (resources.getDrawable(R.drawable.pic) as BitmapDrawable).bitmap
        // 设置旋转角度
        matrix.setRotate(90f);
        // 重新绘制Bitmap
        mTarBitmap = Bitmap.createBitmap(mTarBitmap, 0, 0, mTarBitmap.getWidth(), mTarBitmap.getHeight(), matrix, true);
        mIvBigImage.setImageBitmap(mTarBitmap);
    }

}
