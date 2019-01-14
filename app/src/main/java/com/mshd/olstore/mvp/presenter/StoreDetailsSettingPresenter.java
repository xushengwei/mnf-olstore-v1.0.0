package com.mshd.olstore.mvp.presenter;

import android.app.Activity;

import com.blankj.utilcode.util.ToastUtils;
import com.mshd.olstore.base.BasePresenter;
import com.mshd.olstore.base.BaseResp;
import com.mshd.olstore.mvp.contract.StoreListContract;
import com.mshd.olstore.mvp.contract.StoreSettingContract;
import com.mshd.olstore.mvp.model.entity.RespMain;
import com.mshd.olstore.mvp.model.entity.RespStoreSetting;
import com.mshd.olstore.retrofit.ApiCallback;

import java.util.Map;

import okhttp3.RequestBody;

public class StoreDetailsSettingPresenter extends BasePresenter<StoreSettingContract.Model, StoreSettingContract.View> {

    public StoreDetailsSettingPresenter(StoreSettingContract.View view, Activity activity) {
        attachView(view);
        this.mActivity = activity;

    }

    public void getStoreDetail(Map<String, String> map) {
        mvpView.showLoading();
        RequestBody requestBody = getRequestBody(map);
        addSubscription(apiStores.getStoreDetail(requestBody),
                new ApiCallback<RespStoreSetting>() {
                    @Override
                    public void onSuccess(RespStoreSetting model) {
                        mvpView.onGetStoreDetailsSuccess(model);
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.showShort(msg);
                    }

                    @Override
                    public void onFailure(String code, String msg) {
                        mvpView.onFailure(code, msg);
                    }

                    @Override
                    public void onLogin() {
                        login(mActivity, "");
                    }

                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }

                });
    }

    public void updateStoreName(Map<String, String> map) {
        mvpView.showLoading();
        RequestBody requestBody = getRequestBody(map);
        addSubscription(apiStores.updateStoreName(requestBody),
                new ApiCallback<BaseResp>() {
                    @Override
                    public void onSuccess(BaseResp model) {
                        mvpView.onEditStoreDetailsSuccess(model);
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.showShort(msg);
                    }

                    @Override
                    public void onFailure(String code, String msg) {
                        mvpView.onFailure(code, msg);
                    }

                    @Override
                    public void onLogin() {
                        login(mActivity, "");
                    }

                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }

                });
    }
}
