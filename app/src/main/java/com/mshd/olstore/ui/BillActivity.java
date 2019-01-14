package com.mshd.olstore.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.mshd.olstore.R;
import com.mshd.olstore.base.BaseResp;
import com.mshd.olstore.base.MvpActivity;
import com.mshd.olstore.mvp.contract.BuildDetailsBindContract;
import com.mshd.olstore.mvp.contract.BuildDetailsContract;
import com.mshd.olstore.mvp.contract.StoreListContract;
import com.mshd.olstore.mvp.model.entity.RespBuildDetails;
import com.mshd.olstore.mvp.model.entity.RespMain;
import com.mshd.olstore.mvp.presenter.BuildDetailsBindPresenter;
import com.mshd.olstore.mvp.presenter.BuildDetailsPresenter;
import com.mshd.olstore.mvp.presenter.StoreListPresenter;
import com.mshd.olstore.utils.BigDecimalUtil;
import com.mshd.olstore.widget.BalanceInputDialog;
import com.mshd.olstore.widget.BalanceTypeDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 收入流水
 *
 * @author xushengwei
 * @date 2019/1/7
 */
public class BillActivity extends MvpActivity<StoreListPresenter> implements StoreListContract.View, View.OnClickListener, BuildDetailsContract.View, BuildDetailsBindContract.View {

    private TextView tv_todayCustomer;
    private TextView tv_todayIncome;
    private TextView tv_totalCustomer;
    private TextView tv_totalIncome;
    private TextView tv_settlementWait;
    private TextView tv_settlemented;
    private BalanceTypeDialog balanceTypeDialog;
    private BalanceInputDialog balanceInputDialog;
    private BuildDetailsPresenter buildDetailsPresenter;
    private TextView tv_status;
    private TextView tv_bankCard;
    private TextView tv_wxCard;
    private BuildDetailsBindPresenter buildDetailsBindPresenter;
    private boolean isBindWx;
    private SmartRefreshLayout smartResreshLayout;

    @Override
    protected StoreListPresenter createPresenter() {
        return new StoreListPresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        mImmersionBar.statusBarDarkFont(true, 0.2f).statusBarView(R.id.top_view).init();
        buildDetailsPresenter = new BuildDetailsPresenter(this, this);
        buildDetailsBindPresenter = new BuildDetailsBindPresenter(this, this);
        initToolBar("收入");

        tv_todayCustomer = findViewById(R.id.tv_todayCustomer);
        tv_todayIncome = findViewById(R.id.tv_todayIncome);
        tv_totalCustomer = findViewById(R.id.tv_totalCustomer);
        tv_totalIncome = findViewById(R.id.tv_totalIncome);
        tv_settlementWait = findViewById(R.id.tv_settlementWait);
        tv_settlemented = findViewById(R.id.tv_settlemented);
        ImageView iv_back = findViewById(R.id.iv_back);
        RelativeLayout rl_settelmentType = findViewById(R.id.rl_settelmentType);
        RelativeLayout rl_settelmentBankCard = findViewById(R.id.rl_settelmentBankCard);
        RelativeLayout rl_settelmentWechat = findViewById(R.id.rl_settelmentWechat);
        tv_status = findViewById(R.id.tv_status);
        tv_bankCard = findViewById(R.id.tv_bankCard);
        tv_wxCard = findViewById(R.id.tv_wxCard);

        iv_back.setImageResource(R.mipmap.arrow_left);

        smartResreshLayout = findViewById(R.id.smartResreshLayout);
        smartResreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getStoreList();
            }
        });

        rl_settelmentType.setOnClickListener(this);
        rl_settelmentBankCard.setOnClickListener(this);
        rl_settelmentWechat.setOnClickListener(this);

        getStoreList();
    }

    private void getStoreList() {
        Map<String, String> map = new HashMap<>();
        mvpPresenter.getStoreList(map);
        buildDetailsPresenter.buildDetail(map);
    }

    private void bindingBankCard(String bankCard) {
        Map<String, String> map = new HashMap<>();
        map.put("bankCard", bankCard);
        buildDetailsBindPresenter.bindingBankCard(map);
    }

    private void bindingWX(String wxCard) {
        Map<String, String> map = new HashMap<>();
        map.put("wxCard", wxCard);
        buildDetailsBindPresenter.bindingWX(map);
    }

    private void changeBinding(String buildType) {
        //1银行卡/2微信号
        Map<String, String> map = new HashMap<>();
        map.put("build", buildType);
        buildDetailsBindPresenter.changeBinding(map);
    }

    @Override
    public void onGetStoreListSuccess(RespMain respMain) {
        tv_totalIncome.setText("￥" + respMain.getData().getStoreVO().getTotalIncome());
        tv_totalCustomer.setText(respMain.getData().getStoreVO().getTotalCustomer());
        tv_todayIncome.setText("￥" + respMain.getData().getStoreIncomeVO().getTodayIncome());
        tv_todayCustomer.setText(respMain.getData().getStoreCustomerVO().getTodayCustomer());

        tv_settlementWait.setText("￥" + respMain.getData().getStoreIncomeVO().getTodayIncome());
        tv_settlemented.setText("￥" + BigDecimalUtil.substract(respMain.getData().getStoreVO().getTotalIncome(), respMain.getData().getStoreIncomeVO().getTodayIncome()));
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
        ToastUtils.showShort(msg + "-" + code);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_settelmentType:
                balanceTypeDialog = new BalanceTypeDialog(BillActivity.this, isBindWx);
                balanceTypeDialog.setOnBalanceTypeSeltListener(new BalanceTypeDialog.OnBalanceTypeSeltListener() {
                    @Override
                    public void onBalanceTypeSelt(boolean isWechatType) {
                        String buildType;
                        if (isWechatType) {
                            buildType = "2";
                        } else {
                            buildType = "1";
                        }

                        changeBinding(buildType);
                    }
                });

                balanceTypeDialog.show();
                break;
            case R.id.rl_settelmentBankCard:
                balanceInputDialog = new BalanceInputDialog(BillActivity.this, false);
                balanceInputDialog.setOnBalanceTypeInputListener(new BalanceInputDialog.OnBalanceTypeInputListener() {
                    @Override
                    public void onBalanceTypeInput(boolean isWechatType, String inputString) {
                        bindingBankCard(inputString);
                    }
                });
                balanceInputDialog.show();
                break;

            case R.id.rl_settelmentWechat:
                balanceInputDialog = new BalanceInputDialog(BillActivity.this, true);
                balanceInputDialog.setOnBalanceTypeInputListener(new BalanceInputDialog.OnBalanceTypeInputListener() {
                    @Override
                    public void onBalanceTypeInput(boolean isWechatType, String inputString) {
                        bindingWX(inputString);
                    }
                });
                balanceInputDialog.show();
                break;
            default:

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (balanceTypeDialog != null && balanceTypeDialog.isShowing()) {
            balanceTypeDialog.dismiss();
            balanceTypeDialog = null;
        }
        if (balanceInputDialog != null && balanceInputDialog.isShowing()) {
            balanceInputDialog.dismiss();
            balanceInputDialog = null;
        }
    }

    @Override
    public void onBuildDetailsSuccess(RespBuildDetails respBuildDetails) {
        //状态(1银行卡/2微信号)
        String status = respBuildDetails.getData().getStatus();
        //(1已绑定/2未绑定)
        String wxCard = respBuildDetails.getData().getWxCard();
        String bankCard = respBuildDetails.getData().getBankCard();

        if (StringUtils.equals(status, "1")) {
            tv_status.setText("银行卡");
            isBindWx = false;
        } else if (StringUtils.equals(status, "2")) {
            tv_status.setText("微信");
            isBindWx = true;
        } else {
            tv_status.setText("请选择");
            isBindWx = false;
        }

        if (StringUtils.equals(wxCard, "1")) {
            tv_wxCard.setText("已绑定");
        } else {
            tv_wxCard.setText("未绑定");
        }

        if (StringUtils.equals(bankCard, "1")) {
            tv_bankCard.setText("已绑定");
        } else {
            tv_bankCard.setText("未绑定");
        }
    }

    @Override
    public void onBindSuccess(BaseResp resp) {
        ToastUtils.showShort("修改成功");
        getStoreList();
    }
}
