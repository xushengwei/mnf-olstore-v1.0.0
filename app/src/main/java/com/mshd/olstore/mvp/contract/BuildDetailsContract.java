package com.mshd.olstore.mvp.contract;


import com.mshd.olstore.base.BaseModel;
import com.mshd.olstore.base.BaseView;
import com.mshd.olstore.mvp.model.entity.RespBuildDetails;
import com.mshd.olstore.mvp.model.entity.RespMain;

/**
 * @author xushengwei
 * @date 2018/10/30
 */
public interface BuildDetailsContract {
    interface  View extends BaseView {

        void onBuildDetailsSuccess(RespBuildDetails respBuildDetails);

    }

    interface Model extends BaseModel {

    }
}
