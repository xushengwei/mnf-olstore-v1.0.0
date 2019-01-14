package com.mshd.olstore.base;

import android.app.Activity;
import android.content.Intent;

import com.blankj.utilcode.util.ToastUtils;

import com.google.gson.Gson;
import com.mshd.olstore.retrofit.ApiClient;
import com.mshd.olstore.retrofit.ApiStores;
import com.mshd.olstore.retrofit.RetryWithDelay;
import com.mshd.olstore.ui.LoginActivity;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class BasePresenter<M extends BaseModel, V extends BaseView> {
    public V mvpView;
    public M mvpModel;
    protected ApiStores apiStores;
    protected CompositeDisposable mCompositeDisposable;
    protected final int REQUEST_CODE_LOGIN = 77;
    public Activity mActivity;


    public void attachView(V mvpView) {
        this.mvpView = mvpView;
        apiStores = ApiClient.retrofit().create(ApiStores.class);
    }


    public void detachView() {
        this.mvpView = null;
        this.mvpModel = null;
        onUnSubscribe();
    }


    //RxJava取消注册，以避免内存泄露
    public void onUnSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable.dispose();
        }
    }


    public void addSubscription(Observable observable, DisposableObserver observer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }

        mCompositeDisposable.add(observer);

        observable.subscribeOn(Schedulers.io())
                //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .retryWhen(new RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);

    }


    public RequestBody getRequestBody(Map<String, String> map) {
        String json = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return requestBody;
    }

    public void login(Activity activity, String loginType) {
        ToastUtils.showShort("请登录");
        activity.startActivity(new Intent(activity, LoginActivity.class));

    }


}
