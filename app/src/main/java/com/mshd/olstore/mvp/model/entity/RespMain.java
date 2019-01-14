package com.mshd.olstore.mvp.model.entity;

import com.mshd.olstore.base.BaseResp;

import java.util.List;

/**
 * @author xushengwei
 * @date 2019/1/9
 */
public class RespMain extends BaseResp{

    private MainData data ;

    public MainData getData() {
        return data;
    }

    public void setData(MainData data) {
        this.data = data;
    }

    public class MainData {
        private List<BillBean> list ;
        private StoreVO storeVO;
        private StoreCustomerVO storeCustomerVO;
        private StoreIncomeVO storeIncomeVO;
        private StoreUserVO storeUserVO;

        public List<BillBean> getList() {
            return list;
        }

        public void setList(List<BillBean> list) {
            this.list = list;
        }

        public StoreVO getStoreVO() {
            return storeVO;
        }

        public void setStoreVO(StoreVO storeVO) {
            this.storeVO = storeVO;
        }

        public StoreCustomerVO getStoreCustomerVO() {
            return storeCustomerVO;
        }

        public void setStoreCustomerVO(StoreCustomerVO storeCustomerVO) {
            this.storeCustomerVO = storeCustomerVO;
        }

        public StoreIncomeVO getStoreIncomeVO() {
            return storeIncomeVO;
        }

        public void setStoreIncomeVO(StoreIncomeVO storeIncomeVO) {
            this.storeIncomeVO = storeIncomeVO;
        }

        public StoreUserVO getStoreUserVO() {
            return storeUserVO;
        }

        public void setStoreUserVO(StoreUserVO storeUserVO) {
            this.storeUserVO = storeUserVO;
        }
    }

    public class StoreVO{
        private String id;
        private String totalIncome;
        private String totalCustomer;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTotalIncome() {
            return totalIncome;
        }

        public void setTotalIncome(String totalIncome) {
            this.totalIncome = totalIncome;
        }

        public String getTotalCustomer() {
            return totalCustomer;
        }

        public void setTotalCustomer(String totalCustomer) {
            this.totalCustomer = totalCustomer;
        }
    }
    public class StoreCustomerVO{
        private String todayCustomer ;

        public String getTodayCustomer() {
            return todayCustomer;
        }

        public void setTodayCustomer(String todayCustomer) {
            this.todayCustomer = todayCustomer;
        }
    }
    public class StoreIncomeVO{
        private String id;
        private String todayIncome;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTodayIncome() {
            return todayIncome;
        }

        public void setTodayIncome(String todayIncome) {
            this.todayIncome = todayIncome;
        }
    }
    public class StoreUserVO{
        private String headUrl;
        private String name;

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
