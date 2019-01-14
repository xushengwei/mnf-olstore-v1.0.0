package com.mshd.olstore.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.mshd.olstore.R;
import com.mshd.olstore.base.BaseResp;
import com.mshd.olstore.base.MvpActivity;
import com.mshd.olstore.mvp.contract.StoreSettingContract;
import com.mshd.olstore.mvp.model.entity.RespStoreSetting;
import com.mshd.olstore.mvp.presenter.StoreDetailsSettingPresenter;
import com.mshd.olstore.utils.MyImgUtils;
import com.mshd.olstore.widget.SaveImageDialog;

import java.util.HashMap;
import java.util.Map;

/**
 * 设置
 *
 * @author xushengwei
 * @date 2019/1/7
 */
public class SettingStoreInfoActivity extends MvpActivity<StoreDetailsSettingPresenter> implements StoreSettingContract.View, View.OnClickListener {

    private TextView tv_storeNo;
    private EditText et_storeName;
    private EditText et_storeAddress;

    @Override
    protected StoreDetailsSettingPresenter createPresenter() {
        return new StoreDetailsSettingPresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_store_info);
        mImmersionBar.statusBarDarkFont(true, 0.2f).statusBarView(R.id.top_view).init();
        initToolBar("设置");

        tv_storeNo = findViewById(R.id.tv_storeNo);
        et_storeName = findViewById(R.id.et_storeName);
        et_storeAddress = findViewById(R.id.et_storeAddress);
        Button btn_save = findViewById(R.id.btn_save);
        TextView tv_codeA = findViewById(R.id.tv_codeA);
        TextView tv_codeB = findViewById(R.id.tv_codeB);
        TextView tv_codeC = findViewById(R.id.tv_codeC);
        ImageView iv_back = findViewById(R.id.iv_back);
        findViewById(R.id.iv_setting).setVisibility(View.GONE);
        iv_back.setImageResource(R.mipmap.arrow_left);

        getStoreDetails();
        btn_save.setOnClickListener(this);
        tv_codeA.setOnClickListener(this);
        tv_codeB.setOnClickListener(this);
        tv_codeC.setOnClickListener(this);
    }

    private void getStoreDetails() {
        Map<String, String> map = new HashMap<>();
        mvpPresenter.getStoreDetail(map);

    }

    private void updateStoreName(String name, String address) {
        Map<String, String> map = new HashMap<>();
        map.put("storeName", name);
        map.put("storeAddress", address);
        mvpPresenter.updateStoreName(map);
    }

    private void saveInfo() {
        String storeName = et_storeName.getText().toString().trim();
        String storeAddress = et_storeAddress.getText().toString().trim();
        if (StringUtils.isEmpty(storeName)) {
            ToastUtils.showShort("请输入店铺名称");
            return;
        }

        if (StringUtils.isEmpty(storeAddress)) {
            ToastUtils.showShort("请输入店铺地址");
            return;
        }

        updateStoreName(storeName, storeAddress);
    }

    @Override
    public void onGetStoreDetailsSuccess(RespStoreSetting respStoreSetting) {
        tv_storeNo.setText(respStoreSetting.getData().getStoreIdentifier());
        et_storeName.setText(respStoreSetting.getData().getStoreName());
        et_storeAddress.setText(respStoreSetting.getData().getAddress());
    }

    @Override
    public void onEditStoreDetailsSuccess(BaseResp baseResp) {
        ToastUtils.showShort("修改成功");
        finish();
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
    }

    @Override
    public void onFailure(String code, String msg) {
        ToastUtils.showShort(msg + "-" + code);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                saveInfo();
                break;
            case R.id.tv_codeA:
            case R.id.tv_codeB:
            case R.id.tv_codeC:
                final String imgUrl ="https://wx.qlogo.cn/mmopen/vi_32/l0rh6aAjq1Q9TeHSQHBgVV5E16hVBT0tZ0xCRVIXt5rjvxf3dK0cWyrMV4r1loSX8Byj2xFdzqFeo8avSGUCtA/132";
                SaveImageDialog saveImageDialog = new SaveImageDialog(SettingStoreInfoActivity.this, imgUrl);
                saveImageDialog.setOnSaveButtonClick(new SaveImageDialog.OnSaveButtonClick() {
                    @Override
                    public void OnSaveButtonClick() {
                        final View view = LayoutInflater.from(SettingStoreInfoActivity.this).inflate(R.layout.layout_image_save, null);
                        final ImageView imageView =view.findViewById(R.id.imageView);
                        Glide.with(SettingStoreInfoActivity.this).load(imgUrl).into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                imageView.setImageDrawable(resource);
                                MyImgUtils.saveLayoutByDrawing(SettingStoreInfoActivity.this,view);
                            }
                        });

                    }
                });
                saveImageDialog.show();
                break;
            default:
                break;
        }
    }
}
