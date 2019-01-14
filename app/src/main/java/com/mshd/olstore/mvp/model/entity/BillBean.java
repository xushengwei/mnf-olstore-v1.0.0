package com.mshd.olstore.mvp.model.entity;

/**
 * @author xushengwei
 * @date 2019/1/9
 */
public class BillBean {
    private String id;
    private String orderNo;//订单编号
    private String createTime;//创建时间
    private String goodsDownPayment;//首付价格
    private String goodsDownRepayment;//余款
    private String goodsBuyPice;//总价
    private String goodsName;//
    private String goodsId;//
    private String goodsSource;//商品渠道

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getGoodsBuyPice() {
        return goodsBuyPice;
    }

    public void setGoodsBuyPice(String goodsBuyPice) {
        this.goodsBuyPice = goodsBuyPice;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsSource() {
        return goodsSource;
    }

    public void setGoodsSource(String goodsSource) {
        this.goodsSource = goodsSource;
    }
}
