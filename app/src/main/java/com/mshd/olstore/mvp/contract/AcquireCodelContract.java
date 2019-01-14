package com.mshd.olstore.mvp.contract;


import com.mshd.olstore.base.BaseModel;
import com.mshd.olstore.base.BaseView;

/**
 * @author xushengwei
 * @date 2018/10/30
 */
public interface AcquireCodelContract {
    interface  View extends BaseView {

        void onGetCodeSuccess();

    }

    interface Model extends BaseModel {

    }
}
