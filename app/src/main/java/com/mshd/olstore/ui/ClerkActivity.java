package com.mshd.olstore.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.mshd.olstore.R;
import com.mshd.olstore.adapter.ClerkManagerAdapter;
import com.mshd.olstore.adapter.MainBillAdapter;
import com.mshd.olstore.base.BasePresenter;
import com.mshd.olstore.base.BaseResp;
import com.mshd.olstore.base.MvpActivity;
import com.mshd.olstore.mvp.contract.StoreClerkContract;
import com.mshd.olstore.mvp.contract.StoreListContract;
import com.mshd.olstore.mvp.model.entity.RespStoreClerk;
import com.mshd.olstore.mvp.presenter.StoreClerkPresenter;
import com.mshd.olstore.widget.ClerkInputDialog;
import com.mshd.olstore.widget.MessageClickDialog;
import com.mshd.olstore.widget.RecycleViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店员管理
 * @author xushengwei
 * @date 2019/1/7
 */
public class ClerkActivity extends MvpActivity<StoreClerkPresenter> implements StoreClerkContract.View, View.OnClickListener {

    private List<RespStoreClerk.StoreClerkData> adapterList;
    private ClerkManagerAdapter clerkManagerAdapter;

    private MessageClickDialog messageClickDialog;
    private ClerkInputDialog clerkInputDialog;
    private SmartRefreshLayout smartResreshLayout;

    @Override
    protected StoreClerkPresenter createPresenter() {
        return new StoreClerkPresenter(this, this);

    }


    /**
     * 菜单创建器。
     */
    private SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {

            int width = getResources().getDimensionPixelSize(R.dimen.qb_px_50);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;


            SwipeMenuItem addItem = new SwipeMenuItem(mActivity)
                    .setText("删除")
                    .setTextSize(16)
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height).setBackgroundColorResource(R.color.item_delt_bg);

            SwipeMenuItem addItem2 = new SwipeMenuItem(mActivity)
                    .setText("编辑")
                    .setTextSize(16)
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height).setBackgroundColorResource(R.color.item_edit_bg);

            swipeRightMenu.addMenuItem(addItem2);
            swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。

        }

    };

    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {


        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            final int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
            final String id = adapterList.get(adapterPosition).getId();
            LogUtils.d("clerkId:"+id+"-"+adapterPosition);
            if (0 == menuPosition) {
                //编辑
                clerkInputDialog = new ClerkInputDialog(ClerkActivity.this,adapterList.get(adapterPosition));
                clerkInputDialog.setInputListener(new ClerkInputDialog.OnInputListener() {
                    @Override
                    public void onInputListener(String name, String phone) {

                        updateClerk(id,name, phone);
                    }
                });
                clerkInputDialog.show();

            } else if (1 == menuPosition) {
                //删除
                messageClickDialog = new MessageClickDialog(ClerkActivity.this, "是否删除？");
                messageClickDialog.setOnComtListener(new MessageClickDialog.OnComtListener() {
                    @Override
                    public void onComtListener() {
                        deleteclerk(id);

                    }
                });
                messageClickDialog.show();
            }

        }
    };


    private void deleteclerk(String storeClerkId) {
        Map<String, String> map = new HashMap<>();
        map.put("storeClerkId", storeClerkId);
        mvpPresenter.deleteclerk(map);
    }


    private void updateClerk(String id, String name, String phone) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("phone", phone);
        map.put("storeClerkName", name);
        mvpPresenter.updateClerk(map);
    }

    private void addClerk(String name, String phone) {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("phone", phone);
        mvpPresenter.addClerk(map);
    }


    private void getStoreClerkList() {
        Map<String, String> map = new HashMap<>();
        mvpPresenter.selectStoreClerk(map);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk);
        mImmersionBar.statusBarDarkFont(true, 0.2f).statusBarView(R.id.top_view).init();
        initToolBar("店员管理");

        Button btn_addGoods =findViewById(R.id.btn_addGoods);
        ImageView iv_back = findViewById(R.id.iv_back);
        SwipeMenuRecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterList = new ArrayList<>();

        recyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        recyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
        clerkManagerAdapter = new ClerkManagerAdapter(R.layout.item_clerk, adapterList);
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.gray_back)));
//        recyclerView.setNestedScrollingEnabled(false);
//        recyclerView.setFocusableInTouchMode(false);
        recyclerView.setAdapter(clerkManagerAdapter);

        iv_back.setImageResource(R.mipmap.arrow_left);
        smartResreshLayout = findViewById(R.id.smartResreshLayout);
        smartResreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getStoreClerkList();
            }
        });

        btn_addGoods.setOnClickListener(this);

        getStoreClerkList();
    }

    @Override
    public void onSelectStoreClerkSuccess(RespStoreClerk respStoreClerk) {
        List<RespStoreClerk.StoreClerkData> data = respStoreClerk.getData();
        adapterList.clear();
        if (data != null) {
            adapterList.addAll(data);
        }
        clerkManagerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onUpdateClerkSuccess(BaseResp resp) {
        ToastUtils.showShort("修改成功");
        getStoreClerkList();
    }

    @Override
    public void onDeleteClerkSuccess(BaseResp resp) {
        ToastUtils.showShort("删除成功");
        getStoreClerkList();
    }

    @Override
    public void onAddClerkSuccess(BaseResp resp) {
        ToastUtils.showShort("增加成功");
        getStoreClerkList();
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
    protected void onDestroy() {
        super.onDestroy();
        if (messageClickDialog != null && messageClickDialog.isShowing()) {
            messageClickDialog.dismiss();
            messageClickDialog = null;
        }

        if (clerkInputDialog != null && clerkInputDialog.isShowing()) {
            messageClickDialog.dismiss();
            messageClickDialog = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_addGoods:
                clerkInputDialog = new ClerkInputDialog(ClerkActivity.this);
                clerkInputDialog.setInputListener(new ClerkInputDialog.OnInputListener() {
                    @Override
                    public void onInputListener(String name, String phone) {
                        addClerk(name, phone);
                    }
                });
                clerkInputDialog.show();
                break;
            default:
                break;
        }
    }
}
