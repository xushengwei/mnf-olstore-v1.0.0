package com.mshd.olstore.mvp.presenter;

import android.app.Activity;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.mshd.olstore.base.BasePresenter;
import com.mshd.olstore.base.SpKey;
import com.mshd.olstore.mvp.contract.NormalContract;
import com.mshd.olstore.mvp.model.entity.RespLogin;
import com.mshd.olstore.retrofit.ApiCallback;


import java.util.Map;

import okhttp3.RequestBody;

public class MultipointLoginPresenter extends BasePresenter<NormalContract.Model, NormalContract.View> {

    public MultipointLoginPresenter(NormalContract.View view, Activity activity) {
        attachView(view);
        this.mActivity =activity ;

    }

    public void multipointLogin(Map<String, String> map) {
        mvpView.showLoading();
        RequestBody requestBody = getRequestBody(map);
        addSubscription(apiStores.multipointLogin(requestBody),
                new ApiCallback<RespLogin>() {
                    @Override
                    public void onSuccess(RespLogin model) {
                        String sessionId = model.getData().getSessionId();
                        String userId = model.getData().getUserId();
                        SPUtils.getInstance().put(SpKey.SPRING_SESSION, sessionId);
                        SPUtils.getInstance().put(SpKey.USER_ID, userId);
                        LogUtils.d("SPRING_SESSION:"+ SPUtils.getInstance().getString(SpKey.SPRING_SESSION));
                        mvpView.onSuccess();
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
