package com.mshd.olstore.mvp.presenter;

import android.app.Activity;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.mshd.olstore.base.BasePresenter;
import com.mshd.olstore.base.BaseResp;
import com.mshd.olstore.base.SpKey;
import com.mshd.olstore.mvp.contract.NormalContract;
import com.mshd.olstore.mvp.contract.StoreListContract;
import com.mshd.olstore.mvp.model.entity.RespLogin;
import com.mshd.olstore.mvp.model.entity.RespMain;
import com.mshd.olstore.retrofit.ApiCallback;

import java.util.Map;

import okhttp3.RequestBody;

public class StoreListPresenter extends BasePresenter<StoreListContract.Model, StoreListContract.View> {

    public StoreListPresenter(StoreListContract.View view, Activity activity) {
        attachView(view);
        this.mActivity =activity ;

    }

    public void getStoreList(Map<String, String> map) {
        mvpView.showLoading();
        RequestBody requestBody = getRequestBody(map);
        addSubscription(apiStores.getStoreList(requestBody),
                new ApiCallback<RespMain>() {
                    @Override
                    public void onSuccess(RespMain model) {
                        mvpView.onGetStoreListSuccess(model);
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
