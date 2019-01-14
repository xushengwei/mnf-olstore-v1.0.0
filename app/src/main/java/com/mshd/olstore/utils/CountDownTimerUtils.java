package com.mshd.olstore.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;


import com.mshd.olstore.R;

import java.lang.ref.WeakReference;

/**
 * @author xushengwei
 * @date 2018/11/22
 */
public class CountDownTimerUtils extends CountDownTimer {
    private WeakReference<TextView> mTextView;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownTimerUtils(TextView textView, long millisInFuture, long countDownInterval) {
        //这里的构造方法需要加入一个参数，传入一个TextView对象参数是为了对这个TextView对象进行点击事件的处理。
        // millisInFuture是传给onTick()的参数，countDownInterval是表示多长时间调用一次onTick()。即倒计时每隔多长时间
        //调用onTick()方法，即倒计时时间每次显示间隔多少秒。
        super(millisInFuture, countDownInterval);
        this.mTextView = new WeakReference(textView);
    }

    @Override
    public void onTick(long millisUntilFinished) {//该方法在倒计时时调用

        //用弱引用 先判空 避免崩溃
        if (mTextView.get() == null) {
            cancle();
            return;
        }
        mTextView.get().setClickable(false); //设置不可点击
        mTextView.get().setText( "重新发送("+millisUntilFinished / 1000+")");  //设置倒计时时间
        mTextView.get().setBackgroundResource(R.drawable.shape_getcode_gray); //设置按钮为灰色，这时是不能点击的
        mTextView.get().setTextColor(Color.parseColor("#999999"));
    }

    @Override
    public void onFinish() {//计时结束之后实现这个方法

        //用软引用 先判空 避免崩溃
        if (mTextView.get() == null){
            cancle();
            return;
        }
        mTextView.get().setText("重新获取");
        mTextView.get().setClickable(true);//重新获得点击
        mTextView.get().setBackgroundResource(R.drawable.shape_rb_normal);//还原背景色
        mTextView.get().setTextColor(Color.parseColor("#ffffff"));
    }


    public void cancle() {
        if (this != null) {
            this.cancel();
        }
    }
}