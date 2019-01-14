package com.mshd.olstore.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mshd.olstore.R;
import com.mshd.olstore.mvp.model.entity.BillBean;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author xushengwei
 * @date 2019/1/5
 */
public class MainBillAdapter extends BaseQuickAdapter<BillBean, BaseViewHolder> {


    private boolean isMainItem;
    private OnButtonClickListener onButtonClickListener;

    public MainBillAdapter(int layoutResId, @Nullable List<BillBean> data, boolean isMainItem) {
        super(layoutResId, data);
        this.isMainItem =isMainItem;
    }

    @Override
    protected void convert(BaseViewHolder helper, BillBean item) {
        TextView tv_goodsName = helper.getView(R.id.tv_goodsName);
        TextView tv_orderNo = helper.getView(R.id.tv_orderNo);
        TextView tv_orderTime = helper.getView(R.id.tv_orderTime);
        TextView tv_goodsBuyPice = helper.getView(R.id.tv_goodsBuyPice);
        TextView tv_goodsDownPayment = helper.getView(R.id.tv_goodsDownPayment);
        TextView tv_goodsDownRepayment = helper.getView(R.id.tv_goodsDownRepayment);


        LinearLayout ll_left_layout = helper.getView(R.id.ll_left_layout);
        RelativeLayout rl_refund=helper.getView(R.id.rl_refund);
        if (isMainItem){
            rl_refund.setVisibility(View.VISIBLE);
            ll_left_layout.setBackgroundColor(mContext.getResources().getColor(R.color.gray_back));
        }else {
            rl_refund.setVisibility(View.GONE);
            ll_left_layout.setBackgroundColor(mContext.getResources().getColor(R.color.color_white));
        }
        final String orderNo = item.getOrderNo();

        if (item!=null){
            tv_goodsName.setText(item.getGoodsSource());
            tv_orderNo.setText(item.getOrderNo());
            //new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            tv_orderTime.setText(TimeUtils.millis2String(Long.parseLong(item.getCreateTime()),new SimpleDateFormat("HH:mm:ss")));
            tv_goodsBuyPice.setText("￥"+item.getGoodsBuyPice());
            tv_goodsDownPayment.setText("￥"+item.getGoodsDownPayment());
            tv_goodsDownRepayment.setText("￥"+item.getGoodsDownRepayment());
        }
        rl_refund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClickListener!=null){
                        onButtonClickListener.onRefundClick(orderNo);
                }
            }
        });


    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener){
        this.onButtonClickListener=onButtonClickListener;
    }

    public interface OnButtonClickListener{
        void onRefundClick(String orderNo);
    }
}
