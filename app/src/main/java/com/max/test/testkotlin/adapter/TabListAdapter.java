package com.max.test.testkotlin.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by Administrator on 2018-3-3.
 */
public class TabListAdapter extends AnimationAdapter{


    /**
     * Creates a new AnimationAdapter, wrapping given BaseAdapter.
     *
     * @param baseAdapter the BaseAdapter to wrap.
     */
    protected TabListAdapter(@NonNull BaseAdapter baseAdapter) {
        super(baseAdapter);
    }

    @NonNull
    @Override
    public Animator[] getAnimators(@NonNull ViewGroup parent, @NonNull View view) {
        Animator bottomInAnimator = ObjectAnimator.ofFloat(view, "translationY", 500, 0);
        Animator rightInAnimator = ObjectAnimator.ofFloat(view, "translationX", parent.getWidth(), 0);
        return new Animator[] { bottomInAnimator, rightInAnimator };
    }


    /*@Override
    public long getAnimationDelayMillis() {
        return 500;
    }

    @Override
    public long getAnimationDurationMillis() {
        return 500;
    }*/
}