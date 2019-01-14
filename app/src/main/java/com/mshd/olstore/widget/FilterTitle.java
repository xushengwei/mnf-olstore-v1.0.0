package com.mshd.olstore.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mshd.olstore.R;


/**
 * @author xushengwei
 * @date 2018/12/1
 */
public class FilterTitle extends LinearLayout implements View.OnClickListener {

    private int filterType = 0;
    private boolean isFilterData ;
    private boolean isFilterDataUp ;
    private OnFilterCheckedListener onFilterCheckedListener;

    private ImageView iv_date;
    private TextView tv_date;
    private TextView tv_sales;
    private TextView tv_volume;

    public FilterTitle(Context context) {
        super(context);
        init(context);
    }

    public FilterTitle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FilterTitle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public FilterTitle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //invertory  volume
        View view = LayoutInflater.from(context).inflate(R.layout.layout_filter_title, null);
        LinearLayout item_ll_date = view.findViewById(R.id.item_ll_date);
        LinearLayout item_ll_sales = view.findViewById(R.id.item_ll_sales);
        LinearLayout item_ll_volume = view.findViewById(R.id.item_ll_volume);

        iv_date = view.findViewById(R.id.iv_date);

        tv_date = view.findViewById(R.id.tv_date);
        tv_sales = view.findViewById(R.id.tv_sales);
        tv_volume = view.findViewById(R.id.tv_volume);

        item_ll_date.setOnClickListener(this);
        item_ll_sales.setOnClickListener(this);
        item_ll_volume.setOnClickListener(this);


        addView(view);
    }

    private void switchType( int newType) {
        switch (newType) {
            case 0:
                tv_date.setTextColor(getResources().getColor(R.color.red_amt));
                tv_sales.setTextColor(getResources().getColor(R.color.textcolor_black));
                tv_volume.setTextColor(getResources().getColor(R.color.textcolor_black));
                if (isFilterData){
                    //当前排序类型的切换
                    isFilterDataUp=!isFilterDataUp;
                }else {
                    //默认类型
                    isFilterDataUp=false;
                }

                if (isFilterDataUp){
                    iv_date.setBackgroundResource(R.mipmap.filter_up);
                }else {
                    iv_date.setBackgroundResource(R.mipmap.filter_down);

                }


                if (onFilterCheckedListener != null) {
                    onFilterCheckedListener.onTypeDate(isFilterDataUp);
                }

                isFilterData=true;
                break;
            case 1:
                tv_date.setTextColor(getResources().getColor(R.color.textcolor_black));
                tv_sales.setTextColor(getResources().getColor(R.color.red_amt));
                tv_volume.setTextColor(getResources().getColor(R.color.textcolor_black));
                isFilterData=false;
                iv_date.setBackgroundResource(R.mipmap.filter_normal);
                if (onFilterCheckedListener != null) {
                    onFilterCheckedListener.onTypeSales();
                }
                break;
            case 2:
                tv_date.setTextColor(getResources().getColor(R.color.textcolor_black));
                tv_sales.setTextColor(getResources().getColor(R.color.textcolor_black));
                tv_volume.setTextColor(getResources().getColor(R.color.red_amt));
                isFilterData=false;
                iv_date.setBackgroundResource(R.mipmap.filter_normal);
                if (onFilterCheckedListener != null) {
                    onFilterCheckedListener.onTypeVolume();
                }
                break;
            default:
                break;
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_ll_date:
                filterType = 0;
                break;
            case R.id.item_ll_sales:
                filterType = 1;
                break;
            case R.id.item_ll_volume:
                filterType = 2;
                break;
            default:
                break;
        }

        switchType( filterType);
    }

    public void setOnFilterCheckedListener(OnFilterCheckedListener onFilterCheckedListener) {
        this.onFilterCheckedListener = onFilterCheckedListener;
    }

    public interface OnFilterCheckedListener {
        void onTypeDate(boolean isUp);

        void onTypeSales();

        void onTypeVolume();
    }
}
