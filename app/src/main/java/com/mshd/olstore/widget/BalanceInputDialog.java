package com.mshd.olstore.widget;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.dialog.widget.base.BaseDialog;
import com.mshd.olstore.R;

/**
 * @author xushengwei
 * @date 2019/1/9
 */
public class BalanceInputDialog extends BaseDialog implements View.OnClickListener {


    private OnBalanceTypeInputListener onBalanceTypeInputListener;
    private TextView tv_cancel;
    private TextView tv_comt;
    private boolean isWechatType = false;
    private EditText et_inputContent;

    public BalanceInputDialog(Context context,boolean isWechatType ) {
        super(context);
        this.isWechatType=isWechatType ;
    }



    @Override
    public View onCreateView() {
        widthScale(0.85f);
        //showAnim(new Swing());

        View inflate = View.inflate(mContext, R.layout.layout_dialog_balance_type_input, null);

        TextView tv_inputType = inflate.findViewById(R.id.tv_inputType);
        et_inputContent = inflate.findViewById(R.id.et_inputContent);
        if (isWechatType){
            tv_inputType.setText("微信号");
            et_inputContent.setHint("请输入您的银行卡号");
        }else {
            tv_inputType.setText("银行卡");
            et_inputContent.setHint("请输入您的微信号");
        }

        tv_cancel = inflate.findViewById(R.id.tv_cancel);
        tv_comt = inflate.findViewById(R.id.tv_comt);

        return inflate;
    }

    @Override
    public void setUiBeforShow() {

        tv_cancel.setOnClickListener(this);
        tv_comt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_comt:
                String inputString = et_inputContent.getText().toString().trim();
                if (StringUtils.isEmpty(inputString)){
                    ToastUtils.showShort("输入内容不能为空");
                    return;
                }
                dismiss();
                if (onBalanceTypeInputListener != null) {
                    onBalanceTypeInputListener.onBalanceTypeInput(isWechatType,inputString);
                }
                break;
            default:
                break;
        }
    }

    public void setOnBalanceTypeInputListener(OnBalanceTypeInputListener onBalanceTypeSeltListener) {
        this.onBalanceTypeInputListener = onBalanceTypeSeltListener;
    }

    public interface OnBalanceTypeInputListener {
        void onBalanceTypeInput(boolean isWechatType,String inputString);
    }
}
