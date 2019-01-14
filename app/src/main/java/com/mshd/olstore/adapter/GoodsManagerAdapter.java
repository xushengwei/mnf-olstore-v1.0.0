package com.mshd.olstore.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mshd.olstore.R;
import com.mshd.olstore.mvp.model.entity.RespStoreGoods;

import java.util.List;

/**
 * @author xushengwei
 * @date 2019/1/5
 */
public class GoodsManagerAdapter extends BaseQuickAdapter<RespStoreGoods.StoreGoodsData, BaseViewHolder> {


    //商品状态(1出售中/2待审核/3已下架)
    private String status;
    private OnButtonClickListener onButtonClickListener;

    public GoodsManagerAdapter(int layoutResId, @Nullable List<RespStoreGoods.StoreGoodsData> data, String status) {
        super(layoutResId, data);
        this.status = status;
    }

    @Override
    protected void convert(BaseViewHolder helper, final RespStoreGoods.StoreGoodsData item) {

        TextView tv_delete = helper.getView(R.id.tv_delete);
        TextView tv_editStatus = helper.getView(R.id.tv_editStatus);
        RelativeLayout rl_showCode = helper.getView(R.id.rl_showCode);

        ImageView iv_goodsImg = helper.getView(R.id.iv_goodsImg);

        TextView tv_goodsName = helper.getView(R.id.tv_goodsName);
        TextView tv_goodsBuyPice = helper.getView(R.id.tv_goodsBuyPice);
        TextView tv_goodsDownPayment = helper.getView(R.id.tv_goodsDownPayment);
        TextView tv_goodsDownRepayment = helper.getView(R.id.tv_goodsDownRepayment);
        TextView tv_salesVolume = helper.getView(R.id.tv_salesVolume);
        TextView tv_turnover = helper.getView(R.id.tv_turnover);

        Glide.with(mContext).load(item.getGoodsUrl()).into(iv_goodsImg);
        tv_goodsName.setText(item.getGoodsName());
        tv_goodsBuyPice.setText("￥" + item.getGoodsBuyPice());
        tv_goodsDownPayment.setText("￥" + item.getGoodsDownPayment());
        tv_goodsDownRepayment.setText("￥" + item.getGoodsDownRepayment());
        String salesVolume = item.getSalesVolume();
        String turnover = item.getTurnover();
        tv_salesVolume.setText(StringUtils.isEmpty(salesVolume) ? "0" : salesVolume);
        tv_turnover.setText("￥" + (StringUtils.isEmpty(turnover) ? "0" : turnover));

        final String goodsId = item.getId();

        //商品状态(1出售中/2待审核/3已下架)
        switch (status) {
            case "1":
                rl_showCode.setVisibility(View.VISIBLE);
                tv_editStatus.setText("下架");
                break;
            case "2":
                rl_showCode.setVisibility(View.GONE);
                tv_editStatus.setText("下架");
                break;
            case "3":
                rl_showCode.setVisibility(View.GONE);
                tv_editStatus.setText("上架");
                break;
            default:
                break;
        }
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClickListener != null) {
                    onButtonClickListener.onDeleteButtonClick(goodsId);
                }
            }
        });

        tv_editStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClickListener != null) {
                    onButtonClickListener.onEditButtonClick(goodsId);
                }
            }
        });

        rl_showCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClickListener != null) {
                    onButtonClickListener.onShowButtonClick(item);
                }
            }
        });
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;

    }

    public interface OnButtonClickListener {

        void onDeleteButtonClick(String goodsId);

        void onEditButtonClick(String goodsId);

        void onShowButtonClick(RespStoreGoods.StoreGoodsData itemData);
    }
}
