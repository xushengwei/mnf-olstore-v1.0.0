package com.mshd.olstore.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.dialog.widget.base.BaseDialog;
import com.mshd.olstore.R;
import com.mshd.olstore.mvp.model.entity.RespStoreGoods;
import com.yzq.zxinglibrary.encode.CodeCreator;

/**
 * @author xushengwei
 * @date 2019/1/9
 */
public class SaveImageDialog extends BaseDialog implements View.OnClickListener {


    private Button btn_saveCode;
    private String imgUrl;
    private OnSaveButtonClick onSaveButtonClick;


    public SaveImageDialog(Context context, String imgUrl) {
        super(context);
        this.imgUrl = imgUrl;
    }


    @Override
    public View onCreateView() {
        widthScale(0.85f);
        //showAnim(new Swing());

        View inflate = View.inflate(mContext, R.layout.layout_dialog_image_save, null);
        btn_saveCode = inflate.findViewById(R.id.btn_saveCode);
        ImageView imageView = inflate.findViewById(R.id.imageView);

        Glide.with(mContext).load(imgUrl).into(imageView);

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
                    onSaveButtonClick.OnSaveButtonClick();
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
        void OnSaveButtonClick();
    }
}
