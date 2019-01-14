package com.mshd.olstore.mvp.model.entity;

import com.mshd.olstore.base.BaseResp;

/**
 * @author xushengwei
 * @date 2019/1/9
 */
public class RespBuildDetails extends BaseResp {

    private BuildDetailsData data ;

    public BuildDetailsData getData() {
        return data;
    }

    public void setData(BuildDetailsData data) {
        this.data = data;
    }

    public class  BuildDetailsData {
        private String storeId ;
        private String status ;//(1银行卡/2微信号)
        private String bankCard ;//(1已绑定/2未绑定)
        private String wxCard ;//(1已绑定/2未绑定)

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBankCard() {
            return bankCard;
        }

        public void setBankCard(String bankCard) {
            this.bankCard = bankCard;
        }

        public String getWxCard() {
            return wxCard;
        }

        public void setWxCard(String wxCard) {
            this.wxCard = wxCard;
        }
    }
}
