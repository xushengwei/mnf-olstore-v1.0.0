package com.mshd.olstore.mvp.contract;


import com.mshd.olstore.base.BaseModel;
import com.mshd.olstore.base.BaseView;
import com.mshd.olstore.mvp.model.entity.RespPriceReduction;

/**
 * @author xushengwei
 * @date 2018/10/30
 */
public interface StoreGoodsAddContract {
    interface  View extends BaseView {

        void onSuccess();

        void onGetPriceReduction(RespPriceReduction mode);

    }

    interface Model extends BaseModel {

    }
}
