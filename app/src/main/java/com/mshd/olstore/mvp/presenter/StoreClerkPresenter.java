package com.mshd.olstore.mvp.presenter;

import android.app.Activity;

import com.blankj.utilcode.util.ToastUtils;
import com.mshd.olstore.base.BasePresenter;
import com.mshd.olstore.base.BaseResp;
import com.mshd.olstore.mvp.contract.BuildDetailsBindContract;
import com.mshd.olstore.mvp.contract.StoreClerkContract;
import com.mshd.olstore.mvp.contract.StoreListContract;
import com.mshd.olstore.mvp.model.entity.RespStoreClerk;
import com.mshd.olstore.retrofit.ApiCallback;

import java.util.Map;

import okhttp3.RequestBody;

public class StoreClerkPresenter extends BasePresenter<StoreClerkContract.Model, StoreClerkContract.View> {

    public StoreClerkPresenter(StoreClerkContract.View view, Activity activity) {
        attachView(view);
        this.mActivity =activity ;

    }

    public void selectStoreClerk(Map<String, String> map) {
        mvpView.showLoading();
        RequestBody requestBody = getRequestBody(map);
        addSubscription(apiStores.selectStoreClerk(requestBody),
                new ApiCallback<RespStoreClerk>() {
                    @Override
                    public void onSuccess(RespStoreClerk model) {
                        mvpView.onSelectStoreClerkSuccess(model);
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
                        login(mActivity,"");
                    }

                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }

                });
    }
    public void deleteclerk(Map<String, String> map) {
        mvpView.showLoading();
        //RequestBody requestBody = getRequestBody(map);
        addSubscription(apiStores.deleteclerk(map),
                new ApiCallback<BaseResp>() {
                    @Override
                    public void onSuccess(BaseResp model) {
                        mvpView.onDeleteClerkSuccess(model);
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
                        login(mActivity,"");
                    }

                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }

                });
    }
    public void updateClerk(Map<String, String> map) {
        mvpView.showLoading();
        RequestBody requestBody = getRequestBody(map);
        addSubscription(apiStores.updateClerk(requestBody),
                new ApiCallback<BaseResp>() {
                    @Override
                    public void onSuccess(BaseResp model) {
                        mvpView.onUpdateClerkSuccess(model);
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
                        login(mActivity,"");
                    }

                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }

                });
    }


    public void addClerk(Map<String, String> map) {
        mvpView.showLoading();
        RequestBody requestBody = getRequestBody(map);
        addSubscription(apiStores.addClerk(requestBody),
                new ApiCallback<BaseResp>() {
                    @Override
                    public void onSuccess(BaseResp model) {
                        mvpView.onAddClerkSuccess(model);
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
                        login(mActivity,"");
                    }

                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }

                });
    }
}
