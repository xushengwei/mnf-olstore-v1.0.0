package com.mshd.olstore.mvp.presenter;

import android.app.Activity;

import com.blankj.utilcode.util.ToastUtils;
import com.mshd.olstore.base.BasePresenter;
import com.mshd.olstore.mvp.contract.BuildDetailsContract;
import com.mshd.olstore.mvp.contract.StoreListContract;
import com.mshd.olstore.mvp.model.entity.RespBuildDetails;
import com.mshd.olstore.mvp.model.entity.RespMain;
import com.mshd.olstore.retrofit.ApiCallback;

import java.util.Map;

import okhttp3.RequestBody;

public class BuildDetailsPresenter extends BasePresenter<BuildDetailsContract.Model, BuildDetailsContract.View> {

    public BuildDetailsPresenter(BuildDetailsContract.View view, Activity activity) {
        attachView(view);
        this.mActivity =activity ;

    }

    public void buildDetail(Map<String, String> map) {
        mvpView.showLoading();
        RequestBody requestBody = getRequestBody(map);
        addSubscription(apiStores.buildDetail(requestBody),
                new ApiCallback<RespBuildDetails>() {
                    @Override
                    public void onSuccess(RespBuildDetails model) {
                        mvpView.onBuildDetailsSuccess(model);
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
