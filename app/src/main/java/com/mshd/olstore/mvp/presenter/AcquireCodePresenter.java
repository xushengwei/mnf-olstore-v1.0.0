package com.mshd.olstore.mvp.presenter;

import android.app.Activity;

import com.blankj.utilcode.util.ToastUtils;
import com.mshd.olstore.base.BasePresenter;
import com.mshd.olstore.base.BaseResp;
import com.mshd.olstore.mvp.contract.AcquireCodelContract;
import com.mshd.olstore.retrofit.ApiCallback;


public class AcquireCodePresenter extends BasePresenter<AcquireCodelContract.Model, AcquireCodelContract.View> {


    public AcquireCodePresenter(AcquireCodelContract.View view, Activity activity) {
        attachView(view);
        this.mActivity = activity;
        attachView(view);

    }

    public void acquirecode(String mobile) {
        mvpView.showLoading();
        addSubscription(apiStores.acquirecode(mobile),
                new ApiCallback<BaseResp>() {
                    @Override
                    public void onSuccess(BaseResp model) {
                        mvpView.onGetCodeSuccess();
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
