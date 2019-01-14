package com.mshd.olstore.mvp.presenter;

import android.app.Activity;

import com.blankj.utilcode.util.ToastUtils;
import com.mshd.olstore.base.BasePresenter;
import com.mshd.olstore.mvp.contract.NormalContract;
import com.mshd.olstore.mvp.contract.OrderRefundContract;
import com.mshd.olstore.mvp.contract.OssTokenContract;
import com.mshd.olstore.mvp.model.entity.RespGetToken;
import com.mshd.olstore.retrofit.ApiCallback;


public class OrderRefundPresenter extends BasePresenter<OrderRefundContract.Model, OrderRefundContract.View> {

    public OrderRefundPresenter(OrderRefundContract.View view , Activity activity) {
        attachView(view);
        this.mActivity =activity ;
    }

    public void refund(String orderNo) {
        mvpView.showLoading();
        addSubscription(apiStores.refund(orderNo),
                new ApiCallback<RespGetToken>() {
                    @Override
                    public void onSuccess(RespGetToken model) {
                        mvpView.onRefundSuccess();
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
