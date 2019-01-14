package com.mshd.olstore.mvp.contract;


import com.mshd.olstore.base.BaseModel;
import com.mshd.olstore.base.BaseView;

/**
 * @author xushengwei
 * @date 2018/10/30
 */
public interface NormalContract {
    interface  View extends BaseView {

        void onSuccess();

    }

    interface Model extends BaseModel {

    }
}
