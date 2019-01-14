package com.mshd.olstore.mvp.presenter;

import android.app.Activity;

import com.blankj.utilcode.util.ToastUtils;
import com.mshd.olstore.base.BasePresenter;
import com.mshd.olstore.base.BaseResp;
import com.mshd.olstore.mvp.contract.NormalContract;
import com.mshd.olstore.mvp.contract.StoreGoodsAddContract;
import com.mshd.olstore.mvp.contract.StoreGoodsContract;
import com.mshd.olstore.mvp.model.entity.RespPriceReduction;
import com.mshd.olstore.mvp.model.entity.RespStoreGoods;
import com.mshd.olstore.retrofit.ApiCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;

public class StoreGoodsAddPresenter extends BasePresenter<StoreGoodsAddContract.Model, StoreGoodsAddContract.View> {

    public StoreGoodsAddPresenter(StoreGoodsAddContract.View view, Activity activity) {
        attachView(view);
        this.mActivity = activity;

    }

    public void storeGoodsAdd(Map<String, String> map) {
        mvpView.showLoading();
        RequestBody requestBody = getRequestBody(map);
        addSubscription(apiStores.storeGoodsAdd(requestBody),
                new ApiCallback<BaseResp>() {
                    @Override
                    public void onSuccess(BaseResp model) {
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
                        login(mActivity, "");
                    }

                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }

                });
    }


    public void storeGoodsRuKu(Map<String, String> map) {
        mvpView.showLoading();
        RequestBody requestBody = getRequestBody(map);
        addSubscription(apiStores.storeGoodsRuKu(requestBody),
                new ApiCallback<BaseResp>() {
                    @Override
                    public void onSuccess(BaseResp model) {
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
                        login(mActivity, "");
                    }

                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }

                });
    }
    public void priceReduction() {
        mvpView.showLoading();
        RequestBody requestBody = getRequestBody(new HashMap<String, String>());
        addSubscription(apiStores.priceReduction(requestBody),
                new ApiCallback<RespPriceReduction>() {
                    @Override
                    public void onSuccess(RespPriceReduction model) {
                        mvpView.onGetPriceReduction(model);
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
