package com.max.test.testkotlin.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.max.test.testkotlin.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Date;

/**
 * 签到详情日历，点选效果
 */
public class SignSelectorDecorator implements DayViewDecorator {

    private CalendarDay date;
    private final Drawable drawable;

    public SignSelectorDecorator(Activity context) {
        drawable = context.getResources().getDrawable(R.drawable.ic_signed12);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
//        view.addSpan(new StyleSpan(Typeface.BOLD));
//        view.addSpan(new RelativeSizeSpan(1.2f));
        view.addSpan(new ForegroundColorSpan(Color.TRANSPARENT));
//        view.addSpan(new ForegroundColorSpan(Color.parseColor("#404040")));
        view.setSelectionDrawable(drawable);
    }

    //从外面传进来目标日期
    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}
