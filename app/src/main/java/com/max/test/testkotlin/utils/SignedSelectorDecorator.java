package com.max.test.testkotlin.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;

import com.max.test.testkotlin.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.ArrayList;
import java.util.Date;

/**
 * 签到详情日历，已签效果
 */
public class SignedSelectorDecorator implements DayViewDecorator {

    private ArrayList<CalendarDay> dateArray;
    private final Drawable drawable;

    public SignedSelectorDecorator(Activity context, ArrayList<CalendarDay> date) {
        drawable = context.getResources().getDrawable(R.drawable.ic_signed12);
        this.dateArray = date;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dateArray != null && hasDate(day);
    }

    private boolean hasDate(CalendarDay day){
        for(CalendarDay date : dateArray){
            String dateStr = ""+date.getYear()+date.getMonth()+date.getDay();
            String dayStr = ""+day.getYear()+day.getMonth()+day.getDay();
            if(dateStr.equals(dayStr)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
//        view.addSpan(new StyleSpan(Typeface.BOLD));
//        view.addSpan(new RelativeSizeSpan(1.2f));
        view.addSpan(new ForegroundColorSpan(Color.TRANSPARENT));
//        view.addSpan(new ForegroundColorSpan(Color.parseColor("#404040")));
        view.setSelectionDrawable(drawable);
    }

}
