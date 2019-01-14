package com.mshd.olstore.mvp.presenter;

import android.app.Activity;

import com.blankj.utilcode.util.ToastUtils;
import com.mshd.olstore.base.BasePresenter;
import com.mshd.olstore.base.BaseResp;
import com.mshd.olstore.mvp.contract.BuildDetailsBindContract;
import com.mshd.olstore.mvp.contract.OrderListContract;
import com.mshd.olstore.mvp.model.entity.RespOrder;
import com.mshd.olstore.retrofit.ApiCallback;

import java.util.Map;

public class BuildDetailsBindPresenter extends BasePresenter<BuildDetailsBindContract.Model, BuildDetailsBindContract.View> {

    public BuildDetailsBindPresenter(BuildDetailsBindContract.View view, Activity activity) {
        attachView(view);
        this.mActivity =activity ;

    }

    public void bindingBankCard(Map<String, String> map) {
        mvpView.showLoading();
        //RequestBody requestBody = getRequestBody(map);
        addSubscription(apiStores.bindingBankCard(map),
                new ApiCallback<BaseResp>() {
                    @Override
                    public void onSuccess(BaseResp model) {
                        mvpView.onBindSuccess(model);
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
    public void bindingWX(Map<String, String> map) {
        mvpView.showLoading();
        //RequestBody requestBody = getRequestBody(map);
        addSubscription(apiStores.bindingWX(map),
                new ApiCallback<BaseResp>() {
                    @Override
                    public void onSuccess(BaseResp model) {
                        mvpView.onBindSuccess(model);
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
    public void changeBinding(Map<String, String> map) {
        mvpView.showLoading();
        //RequestBody requestBody = getRequestBody(map);
        addSubscription(apiStores.changeBinding(map),
                new ApiCallback<BaseResp>() {
                    @Override
                    public void onSuccess(BaseResp model) {
                        mvpView.onBindSuccess(model);
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
