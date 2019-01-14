package com.mshd.olstore.mvp.presenter;

import android.app.Activity;

import com.blankj.utilcode.util.ToastUtils;
import com.mshd.olstore.base.BasePresenter;
import com.mshd.olstore.mvp.contract.OssTokenContract;
import com.mshd.olstore.mvp.model.entity.RespGetToken;
import com.mshd.olstore.retrofit.ApiCallback;


public class OssTokenPresenter extends BasePresenter<OssTokenContract.Model, OssTokenContract.View> {

    public OssTokenPresenter(OssTokenContract.View view , Activity activity) {
        attachView(view);
        this.mActivity =activity ;
    }

    public void getOssToken(String type) {
        mvpView.showLoading();
        addSubscription(apiStores.getToken(type),
                new ApiCallback<RespGetToken>() {
                    @Override
                    public void onSuccess(RespGetToken model) {
                        mvpView.onGetTokenSuccess(model);
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
