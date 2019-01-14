package com.mshd.olstore.mvp.contract;


import com.mshd.olstore.base.BaseModel;
import com.mshd.olstore.base.BaseView;
import com.mshd.olstore.mvp.model.entity.RespMain;
import com.mshd.olstore.mvp.model.entity.RespOrder;

/**
 * @author xushengwei
 * @date 2018/10/30
 */
public interface OrderListContract {
    interface  View extends BaseView {

        void onGetOrderListSuccess(RespOrder respOrder);

    }

    interface Model extends BaseModel {

    }
}
