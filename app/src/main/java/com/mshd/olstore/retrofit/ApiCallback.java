package com.mshd.olstore.retrofit;


import android.text.TextUtils;

import com.mshd.olstore.base.BaseResp;
import com.wuxiaolong.androidutils.library.LogUtil;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

public abstract class ApiCallback<M extends BaseResp> extends DisposableObserver<M> {


    public abstract void onSuccess(M model);

    public abstract void onError(String msg);

    public abstract void onFailure(String code, String msg);

    public abstract void onLogin();

    public abstract void onFinish();



    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //httpException.response().errorBody().string()
            int code = httpException.code();
            String msg = httpException.getMessage();
            LogUtil.d("code=" + code);
            if (code == 504) {
                msg = "网络不给力";
            }
            if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试";
            }
            onError(msg);
        } else {
            onError(e.getMessage());
        }
        onFinish();
    }

    @Override
    public void onNext(M model) {
        String code = model.getCode();
        if (TextUtils.equals("1", code)) {
            onSuccess(model);
        } else if (TextUtils.equals(Contact.CODE_LOGIN, code)) {
            onLogin();
        } else {
            onFailure(model.getCode(), model.getMessage());
        }


    }

    @Override
    public void onComplete() {
        onFinish();
    }
}
