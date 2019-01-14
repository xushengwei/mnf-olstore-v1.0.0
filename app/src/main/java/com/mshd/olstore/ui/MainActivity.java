package com.mshd.olstore.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.barlibrary.ImmersionBar;
import com.mshd.olstore.R;
import com.mshd.olstore.adapter.MainBillAdapter;
import com.mshd.olstore.base.MvpActivity;
import com.mshd.olstore.base.SpKey;
import com.mshd.olstore.mvp.contract.OrderRefundContract;
import com.mshd.olstore.mvp.contract.StoreListContract;
import com.mshd.olstore.mvp.model.entity.BillBean;
import com.mshd.olstore.mvp.model.entity.RespMain;
import com.mshd.olstore.mvp.presenter.OrderRefundPresenter;
import com.mshd.olstore.mvp.presenter.StoreListPresenter;
import com.mshd.olstore.widget.MessageClickDialog;
import com.mshd.olstore.widget.ObservableScrollView;
import com.mshd.olstore.widget.RecycleViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.ielse.view.SwitchView;

/**
 * @author xushengwei
 * @date 2019/1/4
 */
public class MainActivity extends MvpActivity<StoreListPresenter> implements View.OnClickListener, StoreListContract.View, OrderRefundContract.View {

    protected ImmersionBar mImmersionBar;
    private Toolbar toolbar;
    private RelativeLayout lvHeader;
    private ImageView ivHeader;
    private View spiteLine;
    private LinearLayout rl_cardView;
    private ImageView iv_userIcon;
    private TextView tv_userName;
    private TextView tv_todayCustomer;
    private TextView tv_todayIncome;
    private TextView tv_totalCustomer;
    private TextView tv_totalIncome;
    private List<BillBean> adapterList;
    private MainBillAdapter mainBillAdapter;
    private SmartRefreshLayout smartResreshLayout;
    private OrderRefundPresenter orderRefundPresenter;
    private MessageClickDialog messageClickDialog;
    private boolean ttsStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        orderRefundPresenter = new OrderRefundPresenter(this,this);
        adapterList = new ArrayList<>();
        mImmersionBar = ImmersionBar.with(MainActivity.this);
        mImmersionBar.statusBarDarkFont(true, 0.2f).statusBarView(R.id.top_view)
                .navigationBarColor(R.color.colorPrimary)
                .fullScreen(true)
                .addTag("PicAndColor")  //给上面参数打标记，以后可以通过标记恢复
                .init();
        ttsStatus = SPUtils.getInstance().getBoolean(SpKey.TTS_STATUS,true);

        LinearLayout ll_dz_item =findViewById(R.id.ll_dz_item);

        //3员工 2店长
       String userType = SPUtils.getInstance().getString(SpKey.USER_TYPE);
       if (StringUtils.equals("3",userType)){
           ToastUtils.showShort("您的身份是：员工");
           ll_dz_item.setVisibility(View.GONE);
       }else if (StringUtils.equals("2",userType)){
           ToastUtils.showShort("您的身份是：店长");
           ll_dz_item.setVisibility(View.VISIBLE);
       }else {
           ToastUtils.showShort("未知用户类型");
           ll_dz_item.setVisibility(View.GONE);
       }


        //获取dimen属性中 标题和头部图片的高度
        final float title_height = getResources().getDimension(R.dimen.qb_px_44);
        final float head_height = getResources().getDimension(R.dimen.qb_px_216);
        initToolBar("");

        ImageView iv_message = findViewById(R.id.iv_message);
        TextView tv_orderMore = findViewById(R.id.tv_orderMore);
        iv_userIcon = findViewById(R.id.iv_userIcon);
        tv_userName = findViewById(R.id.tv_userName);
        tv_todayCustomer = findViewById(R.id.tv_todayCustomer);
        tv_todayIncome = findViewById(R.id.tv_todayIncome);
        tv_totalCustomer = findViewById(R.id.tv_totalCustomer);
        tv_totalIncome = findViewById(R.id.tv_totalIncome);
        SwitchView switchView =findViewById(R.id.switchView);

        smartResreshLayout = findViewById(R.id.smartResreshLayout);
        ObservableScrollView scrollView = findViewById(R.id.scrollView);
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        rl_cardView = findViewById(R.id.rl_cardView);
        lvHeader = findViewById(R.id.lv_header);
        ivHeader = findViewById(R.id.iv_header);
        toolbar = findViewById(R.id.toolbar);
        spiteLine = findViewById(R.id.spite_line);
        CardView cardview_order_manager = findViewById(R.id.cardview_order_manager);
        CardView cardview_bill = findViewById(R.id.cardview_bill);
        CardView cardview_goods_manager = findViewById(R.id.cardview_goods_manager);
        CardView cardview_clerk_manager = findViewById(R.id.cardview_clerk_manager);

        switchView.setOpened(ttsStatus);
        switchView.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                view.setOpened(true);
                SPUtils.getInstance().put(SpKey.TTS_STATUS,true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.setOpened(false);
                SPUtils.getInstance().put(SpKey.TTS_STATUS,false);
            }
        });


        //滑动事件回调监听（一次滑动的过程一般会连续触发多次）
        scrollView.setOnScrollListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScroll(int oldy, int dy, boolean isUp) {

                float move_distance = head_height - title_height;
                if (!isUp && dy <= move_distance) {//手指往上滑,距离未超过200dp
                    //标题栏逐渐从透明变成不透明
                    toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_white));
                    TitleAlphaChange(dy, move_distance);//标题栏渐变
                    HeaderTranslate(dy);//图片视差平移

                } else if (!isUp && dy > move_distance) {//手指往上滑,距离超过200dp
                    TitleAlphaChange(1, 1);//设置不透明百分比为100%，防止因滑动速度过快，导致距离超过200dp,而标题栏透明度却还没变成完全不透的情况。

                    HeaderTranslate(head_height);//这里也设置平移，是因为不设置的话，如果滑动速度过快，会导致图片没有完全隐藏。

                    toolbar.setBackgroundColor(getResources().getColor(R.color.color_white));
                    spiteLine.setVisibility(View.VISIBLE);

                } else if (isUp && dy > move_distance) {//返回顶部，但距离头部位置大于200dp
                    //不做处理

                } else if (isUp && dy <= move_distance) {//返回顶部，但距离头部位置小于200dp
                    //标题栏逐渐从不透明变成透明
                    TitleAlphaChange(dy, move_distance);//标题栏渐变
                    HeaderTranslate(dy);//图片视差平移

                    spiteLine.setVisibility(View.GONE);
                }
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mainBillAdapter = new MainBillAdapter(R.layout.item_main_bill, adapterList, true);
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.color_white)));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusableInTouchMode(false);
        recyclerView.setAdapter(mainBillAdapter);
        mainBillAdapter.setOnButtonClickListener(new MainBillAdapter.OnButtonClickListener() {


            @Override
            public void onRefundClick(final String orderNo) {
                messageClickDialog = new MessageClickDialog(MainActivity.this,"缺货退款？");
                messageClickDialog.setOnComtListener(new MessageClickDialog.OnComtListener() {
                    @Override
                    public void onComtListener() {
                        orderRefundPresenter.refund(orderNo);
                    }
                });
                messageClickDialog.show();

            }
        });

        smartResreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getStoreList();
            }
        });

        //取消back的返回事件
        findViewById(R.id.iv_back).setOnClickListener(null);

        iv_message.setOnClickListener(this);
        tv_orderMore.setOnClickListener(this);
        cardview_order_manager.setOnClickListener(this);
        cardview_bill.setOnClickListener(this);
        cardview_goods_manager.setOnClickListener(this);
        cardview_clerk_manager.setOnClickListener(this);

        getStoreList();
    }

    @Override
    protected StoreListPresenter createPresenter() {
        return new StoreListPresenter(this, this);
    }

    private void getStoreList() {
        Map<String, String> map = new HashMap<>();
        mvpPresenter.getStoreList(map);
    }

    private void HeaderTranslate(float distance) {
        lvHeader.setTranslationY(-distance);
//        rl_cardView.setTranslationY(distance/2);
//        ivHeader.setTranslationY(distance/2);
    }

    private void TitleAlphaChange(int dy, float mHeaderHeight_px) {//设置标题栏透明度变化
        float percent = (float) Math.abs(dy) / Math.abs(mHeaderHeight_px);
        //如果是设置背景透明度，则传入的参数是int类型，取值范围0-255
        //如果是设置控件透明度，传入的参数是float类型，取值范围0.0-1.0
        //这里我们是设置背景透明度就好，因为设置控件透明度的话，返回ICON等也会变成透明的。
        //alpha 值越小越透明
        Log.d("percent:", percent + "");
        int alpha = (int) (percent * 180);

        Log.d("alpha:", alpha + "");
        toolbar.getBackground().setAlpha(alpha);//设置控件背景的透明度，传入int类型的参数（范围0~255）

//        ivBack.getBackground().setAlpha(255 - alpha);
//        ivMore.getBackground().setAlpha(255 - alpha);
//        ivShoppingCart.getBackground().setAlpha(255 - alpha);

        //mImmersionBar.statusBarColor(R.color.color_transparent,percent).init();

        mImmersionBar.statusBarColorTransform(R.color.color_white)
                .navigationBarColorTransform(R.color.color_transparent)
                .addViewSupportTransformColor(toolbar)
                .barAlpha(percent)
                .init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mImmersionBar != null) {
            mImmersionBar.destroy();  //在BaseActivity里销毁
        }

        if (messageClickDialog!=null&&messageClickDialog.isShowing()){
            messageClickDialog.dismiss();
            messageClickDialog=null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardview_order_manager:
            case R.id.tv_orderMore:
                startActivity(OrderManagerActivity.class);
                break;
            case R.id.cardview_bill:
                startActivity(BillActivity.class);
                break;
            case R.id.cardview_goods_manager:
                startActivity(GoodsManagerActivity.class);
                break;
            case R.id.cardview_clerk_manager:
                startActivity(ClerkActivity.class);
                break;
            case R.id.iv_setting:
                startActivity(SettingStoreInfoActivity.class);
                break;

            default:
                break;
        }
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
    public void onRefundSuccess() {
        ToastUtils.showShort("已发起退款");
        getStoreList();
    }

    @Override
    public void onGetStoreListSuccess(RespMain respMain) {
        Glide.with(this).load(respMain.getData().getStoreUserVO().getHeadUrl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(iv_userIcon);
        tv_userName.setText(respMain.getData().getStoreUserVO().getName());
        tv_totalIncome.setText("￥" + respMain.getData().getStoreVO().getTotalIncome());
        tv_totalCustomer.setText(respMain.getData().getStoreVO().getTotalCustomer());
        tv_todayIncome.setText("￥" + respMain.getData().getStoreIncomeVO().getTodayIncome());
        tv_todayCustomer.setText(respMain.getData().getStoreCustomerVO().getTodayCustomer());

        List<BillBean> list = respMain.getData().getList();
        adapterList.clear();
        if (list != null) {
            adapterList.addAll(list);
        }
        mainBillAdapter.notifyDataSetChanged();
    }


}
