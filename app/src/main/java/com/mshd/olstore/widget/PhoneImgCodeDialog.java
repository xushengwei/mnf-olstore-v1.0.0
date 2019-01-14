package com.mshd.olstore.widget;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.flyco.dialog.widget.base.BaseDialog;
import com.mshd.olstore.R;

/**
 * @author xushengwei
 * @date 2019/1/9
 */
public class PhoneImgCodeDialog extends BaseDialog implements View.OnClickListener {


    private byte[] bys;
    private onCommitClickListener onCommitClickListener;
    private TextView tv_cancel;
    private TextView tv_comt;

    private EditText et_inputContent;
    private ImageView iv_img_code;

    public PhoneImgCodeDialog(Context context, byte[] bys) {
        super(context);
        this.bys = bys;
    }


    @Override
    public View onCreateView() {
        widthScale(0.85f);
        //showAnim(new Swing());

        View inflate = View.inflate(mContext, R.layout.layout_dialog_phone_img_code, null);

        iv_img_code = inflate.findViewById(R.id.iv_img_code);
        et_inputContent = inflate.findViewById(R.id.et_inputContent);

        tv_cancel = inflate.findViewById(R.id.tv_cancel);
        tv_comt = inflate.findViewById(R.id.tv_comt);

        Glide.with(mContext).load(bys).into(iv_img_code);

        return inflate;
    }

    @Override
    public void setUiBeforShow() {

        tv_cancel.setOnClickListener(this);
        tv_comt.setOnClickListener(this);
        iv_img_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_img_code:
                if (onCommitClickListener!=null){
                    onCommitClickListener.onCodeImageClickListener();
                }
                break;

            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_comt:
                String inputString = et_inputContent.getText().toString().trim();
                if (StringUtils.isEmpty(inputString)) {
                    ToastUtils.showShort("输入内容不能为空");
                    return;
                }
                if (onCommitClickListener!=null){
                    onCommitClickListener.onCommitClickListener(inputString);
                }

                break;
            default:
                break;
        }
    }

    public void setOnBalanceTypeInputListener(onCommitClickListener onCommitClickListener) {
        this.onCommitClickListener = onCommitClickListener;
    }

    public interface onCommitClickListener {
        void onCommitClickListener(String inputCodeString);

        void onCodeImageClickListener();
    }
}
