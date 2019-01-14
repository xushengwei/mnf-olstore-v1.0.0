package com.mshd.olstore.mvp.presenter;

import android.app.Activity;

import com.blankj.utilcode.util.ToastUtils;
import com.mshd.olstore.base.BasePresenter;
import com.mshd.olstore.mvp.contract.OrderListContract;
import com.mshd.olstore.mvp.contract.StoreListContract;
import com.mshd.olstore.mvp.model.entity.RespMain;
import com.mshd.olstore.mvp.model.entity.RespOrder;
import com.mshd.olstore.retrofit.ApiCallback;

import java.util.Map;

import okhttp3.RequestBody;

public class OrderListPresenter extends BasePresenter<OrderListContract.Model, OrderListContract.View> {

    public OrderListPresenter(OrderListContract.View view, Activity activity) {
        attachView(view);
        this.mActivity =activity ;

    }

    public void getOneDayTurnover(Map<String, String> map) {
        mvpView.showLoading();
        //RequestBody requestBody = getRequestBody(map);
        addSubscription(apiStores.getOneDayTurnover(map),
                new ApiCallback<RespOrder>() {
                    @Override
                    public void onSuccess(RespOrder model) {
                        mvpView.onGetOrderListSuccess(model);
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
    public void getOneMouthTurnover(Map<String, String> map) {
        mvpView.showLoading();
        //RequestBody requestBody = getRequestBody(map);
        addSubscription(apiStores.getOneMouthTurnover(map),
                new ApiCallback<RespOrder>() {
                    @Override
                    public void onSuccess(RespOrder model) {
                        mvpView.onGetOrderListSuccess(model);
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
