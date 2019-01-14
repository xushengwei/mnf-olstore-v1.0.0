package com.mshd.olstore.mvp.contract;


import com.mshd.olstore.base.BaseModel;
import com.mshd.olstore.base.BaseResp;
import com.mshd.olstore.base.BaseView;
import com.mshd.olstore.mvp.model.entity.RespMain;
import com.mshd.olstore.mvp.model.entity.RespStoreSetting;

/**
 * @author xushengwei
 * @date 2018/10/30
 */
public interface StoreSettingContract {
    interface  View extends BaseView {

        void onGetStoreDetailsSuccess(RespStoreSetting respStoreSetting);

        void onEditStoreDetailsSuccess(BaseResp baseResp);
    }

    interface Model extends BaseModel {

    }
}
