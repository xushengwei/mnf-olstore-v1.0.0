package com.mshd.olstore.mvp.contract;


import com.mshd.olstore.base.BaseModel;
import com.mshd.olstore.base.BaseView;
import com.mshd.olstore.mvp.model.entity.RespMain;
import com.mshd.olstore.mvp.model.entity.RespStoreGoods;

/**
 * @author xushengwei
 * @date 2018/10/30
 */
public interface StoreGoodsContract {
    interface View extends BaseView {

        void onGetStoreGoodsByStatusSuccess(RespStoreGoods respStoreGoods);
        void onStoreGoodsEditSuccess();
        void onStoreGoodsDeleteSuccess();
    }

    interface Model extends BaseModel {

    }
}
