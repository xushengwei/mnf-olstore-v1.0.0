package com.mshd.olstore.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.animation.Attention.Swing;
import com.flyco.dialog.widget.base.BaseDialog;
import com.mshd.olstore.R;

/**
 * @author xushengwei
 * @date 2019/1/9
 */
public class BalanceTypeDialog extends BaseDialog implements View.OnClickListener {

    private RelativeLayout rl_bankCard;
    private RelativeLayout rl_wechatUser;
    private ImageView iv_bank;
    private ImageView iv_wechat;
    private OnBalanceTypeSeltListener onBalanceTypeSeltListener;
    private TextView tv_cancel;
    private TextView tv_comt;
    private boolean isWechatType;
    public BalanceTypeDialog(Context context,boolean isBindWx) {
        super(context);
        this.isWechatType=isBindWx;
    }


    @Override
    public View onCreateView() {
        widthScale(0.85f);
        //showAnim(new Swing());

        View inflate = View.inflate(mContext, R.layout.layout_dialog_balance_type, null);
        rl_bankCard = inflate.findViewById(R.id.rl_bankCard);
        rl_wechatUser = inflate.findViewById(R.id.rl_wechatUser);
        iv_bank = inflate.findViewById(R.id.iv_bank);
        iv_wechat = inflate.findViewById(R.id.iv_wechat);
        tv_cancel = inflate.findViewById(R.id.tv_cancel);
        tv_comt = inflate.findViewById(R.id.tv_comt);
        setStatus(isWechatType);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        rl_bankCard.setOnClickListener(this);
        rl_wechatUser.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_comt.setOnClickListener(this);
    }

    private void setStatus(boolean isWechatType){
        if (isWechatType){
            iv_bank.setImageResource(R.mipmap.cb_bg_normal);
            iv_wechat.setImageResource(R.mipmap.cb_bg_checked);
        }else {
            iv_bank.setImageResource(R.mipmap.cb_bg_checked);
            iv_wechat.setImageResource(R.mipmap.cb_bg_normal);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_wechatUser:
                isWechatType=true;
                setStatus(isWechatType);
                break;
            case R.id.rl_bankCard:
                isWechatType=false;
                setStatus(isWechatType);
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_comt:
                dismiss();
                if (onBalanceTypeSeltListener!=null){
                    onBalanceTypeSeltListener.onBalanceTypeSelt(isWechatType);
                }
                break;
            default:
                break;
        }
    }

    public void setOnBalanceTypeSeltListener(OnBalanceTypeSeltListener onBalanceTypeSeltListener){
        this.onBalanceTypeSeltListener =onBalanceTypeSeltListener ;
    }

    public interface OnBalanceTypeSeltListener{
        void onBalanceTypeSelt(boolean isWechatType);
    }
}
