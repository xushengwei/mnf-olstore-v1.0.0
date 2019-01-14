package com.mshd.olstore.widget;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.dialog.widget.base.BaseDialog;
import com.mshd.olstore.R;
import com.mshd.olstore.mvp.model.entity.RespStoreClerk;

/**
 * @author xushengwei
 * @date 2019/1/9
 */
public class ClerkInputDialog extends BaseDialog implements View.OnClickListener {


    private RespStoreClerk.StoreClerkData storeClerkInfo;
    private OnInputListener onInputListener;
    private TextView tv_cancel;
    private TextView tv_comt;

    private EditText et_name;
    private EditText et_phone;

    public ClerkInputDialog(Context context, RespStoreClerk.StoreClerkData storeClerkInfo) {
        super(context);
        this.storeClerkInfo=storeClerkInfo;
    }

    public ClerkInputDialog(Context context) {
        super(context);
    }


    @Override
    public View onCreateView() {
        widthScale(0.85f);
        //showAnim(new Swing());

        View inflate = View.inflate(mContext, R.layout.layout_dialog_clerk_input, null);

        et_name = inflate.findViewById(R.id.et_inputName);
        et_phone = inflate.findViewById(R.id.et_inputhone);
        if (storeClerkInfo!=null){
            et_name.setText(storeClerkInfo.getStoreClerkName());
            et_phone.setText(storeClerkInfo.getPhone());
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
                String name = et_name.getText().toString().trim();
                String phone = et_phone.getText().toString().trim();
                if (StringUtils.isEmpty(name)){
                    ToastUtils.showShort("姓名");
                    return;
                }

                if (StringUtils.isEmpty(phone)||! RegexUtils.isMobileSimple(phone)){
                    ToastUtils.showShort("请输入正确的手机号码");
                    return;
                }
                dismiss();
                if (onInputListener != null) {
                    onInputListener.onInputListener(name,phone);
                }
                break;
            default:
                break;
        }
    }

    public void setInputListener(OnInputListener onInputListener) {
        this.onInputListener = onInputListener;
    }

    public interface OnInputListener {
        void onInputListener(String name, String phone);
    }
}
