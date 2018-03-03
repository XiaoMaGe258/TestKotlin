package com.max.test.testkotlin.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.max.test.testkotlin.R;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 旋转照片
 * Created by Max on 2017-7-31.
 */

public class RotateImageActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBigImage;
    private Button mBtnRotate;
    private Button mBtnSave;
    private Bitmap mTarBitmap;
    private String mTarImgUrl;

    public static final int RequestCode = 101;

    public static void actionActivity(Activity fromActivity, String imageUrl){
        Intent intent = new Intent(fromActivity, RotateImageActivity.class);
        intent.putExtra("imageUrl", imageUrl);
        fromActivity.startActivityForResult(intent, RequestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate_image);
        mTarImgUrl = getIntent().getStringExtra("imageUrl");

        initView();
    }

    private void initView() {
        mBtnRotate = findViewById(R.id.btn_rotate);
        mBtnSave = findViewById(R.id.btn_save);
        mIvBigImage = findViewById(R.id.iv_big_image);

        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.ic_launcher);
        options.skipMemoryCache(true);
        options.diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(this).load(mTarImgUrl).apply(options).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource,
                                        @Nullable Transition<? super Drawable> transition) {
                mTarBitmap = ((BitmapDrawable)resource).getBitmap();
                mIvBigImage.setImageBitmap(mTarBitmap);
            }
        });

        if(mTarBitmap != null){
            mIvBigImage.setImageBitmap(mTarBitmap);
        }
        mBtnRotate.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
    }

    private void rotateImage(){
        if(mTarBitmap==null) return;
        Matrix matrix = new Matrix();
        // 设置旋转角度
        matrix.setRotate(90f);
        // 重新绘制Bitmap
        mTarBitmap = Bitmap.createBitmap(mTarBitmap, 0, 0, mTarBitmap.getWidth(),
                mTarBitmap.getHeight(), matrix, true);
        mIvBigImage.setImageBitmap(mTarBitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_rotate:
                rotateImage();
                break;
            case R.id.btn_save:
                if(mTarBitmap == null){
                    return;
                }
                String url = saveImageToSdCard(mTarBitmap);
                Intent data = new Intent();
                data.putExtra("imgUrl", url);
                setResult(RESULT_OK, data);
                finish();
                break;

        }
    }

    //将Bitmap图片存储到sd卡上
    public static String saveImageToSdCard(Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "jianzhibao");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
//        String fileName = System.currentTimeMillis() + ".jpg";
        String fileName = "tempPicture.jpg";
        File file = new File(appDir, fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
}
