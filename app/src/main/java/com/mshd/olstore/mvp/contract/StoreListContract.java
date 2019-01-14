package com.mshd.olstore.mvp.contract;


import com.mshd.olstore.base.BaseModel;
import com.mshd.olstore.base.BaseResp;
import com.mshd.olstore.base.BaseView;
import com.mshd.olstore.mvp.model.entity.RespMain;
import com.mshd.olstore.mvp.model.entity.RespStoreClerk;

/**
 * @author xushengwei
 * @date 2018/10/30
 */
public interface StoreListContract {
    interface  View extends BaseView {

        void onGetStoreListSuccess(RespMain respMain);

    }

    interface Model extends BaseModel {

    }
}
