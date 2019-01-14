package com.mshd.olstore.mvp.contract;


import com.mshd.olstore.base.BaseModel;
import com.mshd.olstore.base.BaseResp;
import com.mshd.olstore.base.BaseView;
import com.mshd.olstore.mvp.model.entity.RespOrder;

/**
 * @author xushengwei
 * @date 2018/10/30
 */
public interface BuildDetailsBindContract {
    interface  View extends BaseView {

        void onBindSuccess(BaseResp resp);

    }

    interface Model extends BaseModel {

    }
}
