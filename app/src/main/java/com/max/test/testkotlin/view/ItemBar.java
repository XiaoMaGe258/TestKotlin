package com.max.test.testkotlin.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.max.test.testkotlin.R;


/**
 * 常规选项条
 * Created by Max on 2017/5/10.
 * <p>
 * xml调用方法：<p>
 * （顶部加）xmlns:app="http://schemas.android.com/apk/res-auto"
 *
 * <com.jianzhibao.ka.personal.view.ItemBar
 *      android:id="@+id/ib_setting_certification"
 *      android:layout_width="match_parent"
 *      android:layout_height="45dp"
 *      app:itemTitle="实名认证"
 *      app:itemHint="未认证"
 *      app:itemIcon="@null"
 *      />
 *
 * attrs.xml：
 * <declare-styleable name="ItemBar">
 *      <attr name="itemTitle" format="string"/>
 *      <attr name="itemTitleColor" format="color|reference"/>
 *      <attr name="itemContent" format="string"/>
 *      <attr name="itemContentColor" format="color|reference"/>
 *      <attr name="itemHint" format="string"/>
 *      <attr name="itemHintColor" format="color|reference"/>
 *      <attr name="itemIcon" format="reference"/>
 *      <attr name="showRightFlag" format="boolean"/>
 * </declare-styleable>
 */

public class ItemBar extends FrameLayout {

    private ImageView mIvIcon;
    private TextView mTvTitle;
    private TextView mTvContent;
    private ImageView mIvRightFlag;

    public ItemBar(Context context) {
        super(context);
        initView(context);

    }

    public ItemBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        resolveAttrs(attrs);
    }

    public ItemBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        resolveAttrs(attrs);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.item_bar, this);
        mIvIcon = (ImageView) findViewById(R.id.iv_icon);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mIvRightFlag = (ImageView) findViewById(R.id.iv_arrow);
    }

    private void resolveAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ItemBar);
        String title = ta.getString(R.styleable.ItemBar_itemTitle);
        int titleColor = ta.getColor(R.styleable.ItemBar_itemTitleColor,
                Color.parseColor("#404040"));
        String content = ta.getString(R.styleable.ItemBar_itemContent);
        int contentColor = ta.getColor(R.styleable.ItemBar_itemContentColor,
                Color.parseColor("#999999"));
        String hint = ta.getString(R.styleable.ItemBar_itemHint);
        int hintColor = ta.getColor(R.styleable.ItemBar_itemHintColor,
                Color.parseColor("#999999"));
        int resourceId = ta.getResourceId(R.styleable.ItemBar_itemIcon, 0);
        boolean showRightFlag = ta.getBoolean(R.styleable.ItemBar_showRightFlag, true);
        ta.recycle();

        setTitle(title, titleColor);
        setHint(hint, hintColor);
        setContent(content, contentColor);
        setIcon(resourceId);
        setShowRightFlag(showRightFlag);
    }

    public void setTitle(String title) {
        if (title != null) {
            mTvTitle.setText(title);
        }
    }

    public void setTitle(String title, int textColor) {
        setTitle(title);
        setTitleColor(textColor);
    }

    public void setTitleColor(int textColor) {
        if (textColor == 0) {
            mTvTitle.setTextColor(Color.parseColor("#404040"));
        } else {
            mTvTitle.setTextColor(textColor);
        }
    }

    public void setContent(String text) {
        if (text != null) {
            mTvContent.setText(text);
        }
    }

    public void setContentColor(int textColor) {
        if (textColor == 0) {
            mTvContent.setTextColor(Color.parseColor("#999999"));
        } else {
            mTvContent.setTextColor(textColor);
        }
    }

    public void setContent(String text, int textColor) {
        setContent(text);
        setContentColor(textColor);
    }

    public String getContent() {
        return mTvContent.getText().toString().trim();
    }

    public void setHint(String text) {
        if (text != null) {
            mTvContent.setHint(text);
        }
    }

    public void setHint(String text, int textColor) {
        setHint(text);
        setHintColor(textColor);
    }

    public void setHintColor(int textColor) {
        if (textColor == 0) {
            mTvContent.setHintTextColor(Color.parseColor("#999999"));
        } else {
            mTvContent.setHintTextColor(textColor);
        }
    }

    public String getHint() {
        return mTvContent.getHint().toString().trim();
    }

    public void setIcon(int iconResource) {
        if (iconResource > 0) {
            mIvIcon.setVisibility(View.VISIBLE);
            mIvIcon.setImageResource(iconResource);
        } else {
            mIvIcon.setVisibility(View.GONE);
        }
    }

    public void setShowRightFlag(boolean isShow) {
        mIvRightFlag.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }
}
