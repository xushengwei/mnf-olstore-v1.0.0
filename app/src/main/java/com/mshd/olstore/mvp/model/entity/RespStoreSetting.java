package com.mshd.olstore.mvp.model.entity;

import com.mshd.olstore.base.BaseResp;

import java.util.List;

/**
 * @author xushengwei
 * @date 2019/1/11
 */
public class RespStoreSetting extends BaseResp {

    private StoreSettingData data ;

    public StoreSettingData getData() {
        return data;
    }

    public void setData(StoreSettingData data) {
        this.data = data;
    }

    public class StoreSettingData {
        private String storeIdentifier ;
        private String address ;
        private String storeName ;
        private List<QrCodeVOS> qrcodeVOS;


        public String getStoreIdentifier() {
            return storeIdentifier;
        }

        public void setStoreIdentifier(String storeIdentifier) {
            this.storeIdentifier = storeIdentifier;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public List<QrCodeVOS> getQrcodeVOS() {
            return qrcodeVOS;
        }

        public void setQrcodeVOS(List<QrCodeVOS> qrcodeVOS) {
            this.qrcodeVOS = qrcodeVOS;
        }
    }


    public class QrCodeVOS{
        private String id ;
        private String statsu ;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatsu() {
            return statsu;
        }

        public void setStatsu(String statsu) {
            this.statsu = statsu;
        }
    }
}
