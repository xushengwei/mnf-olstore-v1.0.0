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

/**
 * @author xushengwei
 * @date 2019/1/9
 */
public class MessageClickDialog extends BaseDialog implements View.OnClickListener {


    private String msg;
    private OnComtListener comtListener;
    private TextView tv_cancel;
    private TextView tv_comt;


    public MessageClickDialog(Context context, String msg) {
        super(context);
        this.msg = msg;
    }


    @Override
    public View onCreateView() {
        widthScale(0.85f);
        //showAnim(new Swing());

        View inflate = View.inflate(mContext, R.layout.layout_dialog_message_click, null);

        TextView tv_messageContent = inflate.findViewById(R.id.tv_messageContent);
        tv_messageContent.setText(msg);

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
                dismiss();
                if (comtListener != null) {
                    comtListener.onComtListener();
                }
                break;
            default:
                break;
        }
    }

    public void setOnComtListener(OnComtListener comtListener) {
        this.comtListener = comtListener;
    }

    public interface OnComtListener {
        void onComtListener();
    }
}
