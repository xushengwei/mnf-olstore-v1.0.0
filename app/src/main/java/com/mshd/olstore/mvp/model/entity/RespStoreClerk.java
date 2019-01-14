package com.mshd.olstore.mvp.model.entity;

import com.mshd.olstore.base.BaseResp;

import java.util.List;

/**
 * @author xushengwei
 * @date 2019/1/10
 */
public class RespStoreClerk extends BaseResp {

    private List<StoreClerkData> data;

    public List<StoreClerkData> getData() {
        return data;
    }

    public void setData(List<StoreClerkData> data) {
        this.data = data;
    }

    public class StoreClerkData {
        private String id ;
        private String phone;
        private String identifier;
        private String storeClerkName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getStoreClerkName() {
            return storeClerkName;
        }

        public void setStoreClerkName(String storeClerkName) {
            this.storeClerkName = storeClerkName;
        }
    }
}
