package com.mshd.olstore.mvp.contract;


import com.mshd.olstore.base.BaseModel;
import com.mshd.olstore.base.BaseView;
import com.mshd.olstore.mvp.model.entity.RespGetToken;

/**
 * @author xushengwei
 * @date 2018/10/30
 */
public interface OssTokenContract {
    interface View extends BaseView {

        void onGetTokenSuccess(RespGetToken respGetToken);

    }

    interface Model extends BaseModel {

    }
}
