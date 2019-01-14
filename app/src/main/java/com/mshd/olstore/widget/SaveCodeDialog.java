package com.mshd.olstore.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.flyco.dialog.widget.base.BaseDialog;
import com.mshd.olstore.R;
import com.mshd.olstore.mvp.model.entity.RespStoreGoods;
import com.yzq.zxinglibrary.encode.CodeCreator;

import org.w3c.dom.Text;

/**
 * @author xushengwei
 * @date 2019/1/9
 */
public class SaveCodeDialog extends BaseDialog implements View.OnClickListener {


    private RespStoreGoods.StoreGoodsData bean;
    private OnSaveButtonClick onSaveButtonClick;
    private Button btn_saveCode;
    private LinearLayout ll_layout;
    private ImageView iv_code;
    private TextView tv_goodsDownPayment;
    private TextView tv_goodsDownRepayment;
    private TextView tv_goodsBuyPice;


    public SaveCodeDialog(Context context, RespStoreGoods.StoreGoodsData bean) {
        super(context);
        this.bean = bean;
    }


    @Override
    public View onCreateView() {
        widthScale(0.85f);
        //showAnim(new Swing());

        View inflate = View.inflate(mContext, R.layout.layout_dialog_goods_code, null);
        ll_layout = inflate.findViewById(R.id.ll_layout);
        btn_saveCode = inflate.findViewById(R.id.btn_saveCode);
        iv_code = inflate.findViewById(R.id.iv_code);
        tv_goodsDownPayment = inflate.findViewById(R.id.tv_goodsDownPayment);
        tv_goodsDownRepayment = inflate.findViewById(R.id.tv_goodsDownRepayment);
        tv_goodsBuyPice = inflate.findViewById(R.id.tv_goodsBuyPice);

        tv_goodsBuyPice.setText("￥"+bean.getGoodsBuyPice());
        tv_goodsDownPayment.setText("￥"+bean.getGoodsDownPayment());
        tv_goodsDownRepayment.setText("￥"+bean.getGoodsDownRepayment());

        String goodsUrl = bean.getGoodsUrl();

        Bitmap bitmap = CodeCreator.createQRCode(goodsUrl, 400, 400, null);
        Glide.with(mContext).load(bitmap).into(iv_code);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        btn_saveCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_saveCode:
                dismiss();
                if (onSaveButtonClick != null) {
                    onSaveButtonClick.OnSaveButtonClick(ll_layout);
                }
                break;

            default:
                break;
        }
    }

    public void setOnSaveButtonClick(OnSaveButtonClick onSaveButtonClick) {
        this.onSaveButtonClick = onSaveButtonClick;
    }

    public interface OnSaveButtonClick {
        void OnSaveButtonClick(View view);
    }
}
