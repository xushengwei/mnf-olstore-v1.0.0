package com.mshd.olstore.mvp.model.entity;

import com.mshd.olstore.base.BaseResp;

import java.util.List;

/**
 * @author xushengwei
 * @date 2019/1/10
 */
public class RespStoreGoods extends BaseResp {

    private List<StoreGoodsData> data ;


    public List<StoreGoodsData> getData() {
        return data;
    }

    public void setData(List<StoreGoodsData> data) {
        this.data = data;
    }

    public class StoreGoodsData{
        private String id ;
        private String goodsBuyPice ;
        private String goodsDownPayment ;
        private String goodsDownRepayment ;
        private String goodsName ;
        private String goodsNum ;
        private String goodsUrl ;
        private String salesVolume ;
        private String turnover ;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoodsBuyPice() {
            return goodsBuyPice;
        }

        public void setGoodsBuyPice(String goodsBuyPice) {
            this.goodsBuyPice = goodsBuyPice;
        }

        public String getGoodsDownPayment() {
            return goodsDownPayment;
        }

        public void setGoodsDownPayment(String goodsDownPayment) {
            this.goodsDownPayment = goodsDownPayment;
        }

        public String getGoodsDownRepayment() {
            return goodsDownRepayment;
        }

        public void setGoodsDownRepayment(String goodsDownRepayment) {
            this.goodsDownRepayment = goodsDownRepayment;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(String goodsNum) {
            this.goodsNum = goodsNum;
        }

        public String getGoodsUrl() {
            return goodsUrl;
        }

        public void setGoodsUrl(String goodsUrl) {
            this.goodsUrl = goodsUrl;
        }

        public String getSalesVolume() {
            return salesVolume;
        }

        public void setSalesVolume(String salesVolume) {
            this.salesVolume = salesVolume;
        }

        public String getTurnover() {
            return turnover;
        }

        public void setTurnover(String turnover) {
            this.turnover = turnover;
        }
    }

}
