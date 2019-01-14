package com.mshd.olstore.mvp.model.entity;

import com.mshd.olstore.base.BaseResp;

import java.util.List;

/**
 * @author xushengwei
 * @date 2019/1/9
 */
public class RespOrder extends BaseResp {
    private List<BillBean> data ;

    public List<BillBean> getData() {
        return data;
    }

    public void setData(List<BillBean> data) {
        this.data = data;
    }
}
