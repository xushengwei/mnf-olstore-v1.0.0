package com.mshd.olstore.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.mshd.olstore.R;
import com.mshd.olstore.adapter.MainBillAdapter;
import com.mshd.olstore.base.MvpActivity;
import com.mshd.olstore.mvp.contract.OrderListContract;
import com.mshd.olstore.mvp.contract.StoreListContract;
import com.mshd.olstore.mvp.model.entity.BillBean;
import com.mshd.olstore.mvp.model.entity.RespMain;
import com.mshd.olstore.mvp.model.entity.RespOrder;
import com.mshd.olstore.mvp.presenter.OrderListPresenter;
import com.mshd.olstore.mvp.presenter.StoreListPresenter;
import com.mshd.olstore.utils.DateFormatUtils;
import com.mshd.olstore.utils.TimePicker;
import com.mshd.olstore.widget.RecycleViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单管理
 * @author xushengwei
 * @date 2019/1/7
 */
public class OrderManagerActivity extends MvpActivity<StoreListPresenter> implements View.OnClickListener, StoreListContract.View, OrderListContract.View {

    private TimePickerView timePickerView;
    private boolean isDayDate = false;
    private TextView tv_seltDate;
    private TextView tv_todayCustomer;
    private TextView tv_todayIncome;
    private TextView tv_totalCustomer;
    private TextView tv_totalIncome;
    private Date pagerDate;
    private OrderListPresenter orderListPresenter;
    private MainBillAdapter mainBillAdapter;
    private List<BillBean> adapterList;
    private SmartRefreshLayout smartResreshLayout;

    @Override
    protected StoreListPresenter createPresenter() {
        return new StoreListPresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manager);
        mImmersionBar.statusBarDarkFont(true, 0.2f).statusBarView(R.id.top_view).init();
        orderListPresenter = new OrderListPresenter(this, this);
        initToolBar("订单");

        pagerDate = TimeUtils.getNowDate();

        tv_todayCustomer = findViewById(R.id.tv_todayCustomer);
        tv_todayIncome = findViewById(R.id.tv_todayIncome);
        tv_totalCustomer = findViewById(R.id.tv_totalCustomer);
        tv_totalIncome = findViewById(R.id.tv_totalIncome);

        RadioGroup rg_date = findViewById(R.id.rg_date);
        tv_seltDate = findViewById(R.id.tv_seltDate);

        ImageView iv_back = findViewById(R.id.iv_back);
        smartResreshLayout = findViewById(R.id.smartResreshLayout);
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterList = new ArrayList<>();

        mainBillAdapter = new MainBillAdapter(R.layout.item_main_bill, adapterList, false);
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.gray_back)));
//        recyclerView.setNestedScrollingEnabled(false);
//        recyclerView.setFocusableInTouchMode(false);
        recyclerView.setAdapter(mainBillAdapter);
        iv_back.setImageResource(R.mipmap.arrow_left);

        tv_seltDate.setOnClickListener(this);
        rg_date.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_day:
                        isDayDate = true;
                        break;
                    case R.id.rb_month:
                        isDayDate = false;
                        break;

                    default:
                        break;
                }
                setDateText(isDayDate, pagerDate);
            }
        });
        smartResreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                setDateText(isDayDate, pagerDate);
            }
        });
        getStoreList();
        setDateText(isDayDate, pagerDate);
    }

    private void setDateText(boolean isDayDate, Date date) {
        Map<String, String> map = new HashMap<>();
        map.put("date", TimeUtils.date2String(date));
        String time;
        if (isDayDate) {
            time = TimeUtils.date2String(date, DateFormatUtils.getMonthDayFormat());
            orderListPresenter.getOneDayTurnover(map);
        } else {
            time = TimeUtils.date2String(date, DateFormatUtils.getMonthFormat());
            orderListPresenter.getOneMouthTurnover(map);
        }
        tv_seltDate.setText(time);
    }


    private void getStoreList() {
        Map<String, String> map = new HashMap<>();
        mvpPresenter.getStoreList(map);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_seltDate:
                timePickerView = TimePicker.getTimePickerView(OrderManagerActivity.this, isDayDate, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        pagerDate = date;
                        setDateText(isDayDate, pagerDate);
                    }
                });
                timePickerView.show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timePickerView != null && timePickerView.isShowing()) {
            timePickerView.dismiss();
            timePickerView = null;
        }

    }

    @Override
    public void onGetStoreListSuccess(RespMain respMain) {
        tv_totalIncome.setText("￥" + respMain.getData().getStoreVO().getTotalIncome());
        tv_totalCustomer.setText(respMain.getData().getStoreVO().getTotalCustomer());
        tv_todayIncome.setText("￥" + respMain.getData().getStoreIncomeVO().getTodayIncome());
        tv_todayCustomer.setText(respMain.getData().getStoreCustomerVO().getTodayCustomer());

    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
        smartResreshLayout.finishRefresh();
    }

    @Override
    public void onFailure(String code, String msg) {
        ToastUtils.showShort(msg+"-"+code);
    }

    @Override
    public void onGetOrderListSuccess(RespOrder respOrder) {
        List<BillBean> data = respOrder.getData();
        adapterList.clear();
        if (data != null) {
            adapterList.addAll(data);
        }
        mainBillAdapter.notifyDataSetChanged();
    }
}
