package com.mshd.olstore.mvp.contract;


import com.mshd.olstore.base.BaseModel;
import com.mshd.olstore.base.BaseResp;
import com.mshd.olstore.base.BaseView;
import com.mshd.olstore.mvp.model.entity.RespStoreSetting;

import okhttp3.ResponseBody;

/**
 * @author xushengwei
 * @date 2018/10/30
 */
public interface LoginContract {
    interface  View extends BaseView {

        void onGetImgSuccess(byte[] imgBys);

        void onGetPhoneCodeSuccess(BaseResp resp);

        void onLoginSuccess(BaseResp resp);
    }

    interface Model extends BaseModel {

    }
}
