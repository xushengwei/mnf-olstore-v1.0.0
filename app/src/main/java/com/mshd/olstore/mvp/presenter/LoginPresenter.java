package com.mshd.olstore.mvp.presenter;

import android.app.Activity;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.mshd.olstore.base.BasePresenter;
import com.mshd.olstore.base.BaseResp;
import com.mshd.olstore.base.SpKey;
import com.mshd.olstore.mvp.contract.LoginContract;
import com.mshd.olstore.mvp.model.entity.RespLogin;
import com.mshd.olstore.mvp.model.entity.RespMain;
import com.mshd.olstore.retrofit.ApiCallback;

import java.io.IOException;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {

    public LoginPresenter(LoginContract.View view, Activity activity) {
        attachView(view);
        this.mActivity = activity;

    }



    public void getImageCode(String url, String phone) {
        mvpView.showLoading();
        addSubscription(apiStores.getImg(url, phone),
                new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        byte[] bys = new byte[0];
                        try {
                            bys = responseBody.bytes(); //注意：把byte[]转换为bitmap时，也是耗时操作，也必须在子线程
                            mvpView.onGetImgSuccess(bys);
                        } catch (IOException e) {
                            e.printStackTrace();
                            onError(e);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        mvpView.hideLoading();

                    }

                    @Override
                    public void onComplete() {
                        mvpView.hideLoading();
                    }
                });
    }


    public void getPhoneCode(Map<String, String> map) {
        mvpView.showLoading();
        RequestBody requestBody = getRequestBody(map);
        addSubscription(apiStores.getPhoneCode(requestBody),
                new ApiCallback<BaseResp>() {
                    @Override
                    public void onSuccess(BaseResp model) {
                        mvpView.onGetPhoneCodeSuccess(model);
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


    public void multipointLogin(Map<String, String> map) {
        mvpView.showLoading();
        RequestBody requestBody = getRequestBody(map);
        addSubscription(apiStores.multipointLogin(requestBody),
                new ApiCallback<RespLogin>() {
                    @Override
                    public void onSuccess(RespLogin model) {
                        String sessionId = model.getData().getSessionId();
                        String userId = model.getData().getUserId();
                        String type =model.getData().getIdentity();
                        SPUtils.getInstance().put(SpKey.USER_TYPE,type);
                        SPUtils.getInstance().put(SpKey.SPRING_SESSION, sessionId);
                        SPUtils.getInstance().put(SpKey.USER_ID, userId);
                        SPUtils.getInstance().put(SpKey.LOGSTATUS,true);
                        mvpView.onLoginSuccess(model);
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
