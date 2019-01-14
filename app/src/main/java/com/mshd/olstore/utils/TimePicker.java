package com.mshd.olstore.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mshd.olstore.R;

import java.util.Calendar;
import java.util.Date;

/**
 * @author xushengwei
 * @date 2019/1/8
 */
public class TimePicker {
    public static TimePickerView getTimePickerView(Context context, boolean hasDay, OnTimeSelectListener onTimeSelectListener) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);

        //正确设置方式 原因：注意事项有说明
        startDate.set(2013, 0, 1);
        endDate.set(2020, 11, 31);

        TimePickerView pvTime;

        TimePickerBuilder timePickerBuilder = new TimePickerBuilder(context, onTimeSelectListener);
        if (hasDay) {
            timePickerBuilder.setType(new boolean[]{true, true, true, false, false, false});
            timePickerBuilder .setLabel("年", "月", "日", null, null, null);//默认设置为年月日时分秒
        } else {
            timePickerBuilder.setType(new boolean[]{true, true, false, false, false, false});
            timePickerBuilder .setLabel("年", "月", null, null, null, null);//默认设置为年月日时分秒
        }
        pvTime = timePickerBuilder
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setContentTextSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                .setTitleText("Title")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                //.setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(context.getResources().getColor(R.color.textcolor_black))//确定按钮文字颜色
                .setCancelColor(context.getResources().getColor(R.color.textcolor_black))//取消按钮文字颜色
                .setTitleBgColor(context.getResources().getColor(R.color.color_white))//标题背景颜色 Night mode
                .setBgColor(context.getResources().getColor(R.color.color_white))//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();
        return pvTime;
    }
}
