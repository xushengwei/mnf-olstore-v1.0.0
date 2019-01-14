package com.mshd.olstore.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.mshd.olstore.R;
import com.mshd.olstore.adapter.GoodsManagerAdapter;
import com.mshd.olstore.base.BasePresenter;
import com.mshd.olstore.base.MvpFragment;
import com.mshd.olstore.event.RefreshGoodsDataEvent;
import com.mshd.olstore.event.RefreshTabCountEvent;
import com.mshd.olstore.mvp.contract.StoreGoodsContract;
import com.mshd.olstore.mvp.model.entity.RespStoreGoods;
import com.mshd.olstore.mvp.presenter.StoreGoodsPresenter;
import com.mshd.olstore.ui.GoodsAddActivity;
import com.mshd.olstore.utils.MyImgUtils;
import com.mshd.olstore.widget.FilterTitle;
import com.mshd.olstore.widget.MessageClickDialog;
import com.mshd.olstore.widget.RecycleViewDivider;
import com.mshd.olstore.widget.SaveCodeDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * @author xushengwei
 * @date 2019/1/8
 */
public class GoodsPagerFragment extends MvpFragment<StoreGoodsPresenter> implements StoreGoodsContract.View {

    private List<RespStoreGoods.StoreGoodsData> adapterList;
    private GoodsManagerAdapter goodsManagerAdapter;
    private String status;
    private SaveCodeDialog saveCodeDialog;
    private boolean isViewCreate;
    private String timeSort = "1";
    private String otherSort = "1";
    private String sortMode = "1";
    private MessageClickDialog messageClickDialog;


    public static GoodsPagerFragment newInstance(String status) {
        Bundle args = new Bundle();
        args.putString("status", status);
        GoodsPagerFragment fragment = new GoodsPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isViewCreate) {
            getStoreGoodsByStatus();
        }
    }

    @Override
    protected StoreGoodsPresenter createPresenter() {
        return new StoreGoodsPresenter(this, mActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_pager, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBusActivityScope.getDefault(mActivity).register(this);
        status = getArguments().getString("status");
        adapterList = new ArrayList<>();

        FilterTitle filterTitle = view.findViewById(R.id.filterTitle);
        RecyclerView recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        goodsManagerAdapter = new GoodsManagerAdapter(R.layout.item_goods_manager, adapterList, status);
        recyclerView.addItemDecoration(new RecycleViewDivider(
                mActivity, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.gray_back)));
        recyclerView.setAdapter(goodsManagerAdapter);

        goodsManagerAdapter.setOnButtonClickListener(new GoodsManagerAdapter.OnButtonClickListener() {


            @Override
            public void onDeleteButtonClick(final String goodsId) {
                messageClickDialog = new MessageClickDialog(mActivity, "确认删除？");
                messageClickDialog.setOnComtListener(new MessageClickDialog.OnComtListener() {
                    @Override
                    public void onComtListener() {
                        messageClickDialog.show();
                        Map<String, String> map = new HashMap<>();
                        map.put("storeGoodsId", goodsId);
                        mvpPresenter.storeGoodsDelete(map);
                    }
                });

            }

            @Override
            public void onEditButtonClick(final String goodsId) {
                ToastUtils.showShort("编辑");
                String msg = "";
                if (TextUtils.equals("3", status)) {
                    msg = "确认上架？";
                } else {
                    msg = "确认下架？";
                }
                messageClickDialog = new MessageClickDialog(mActivity, msg);
                messageClickDialog.setOnComtListener(new MessageClickDialog.OnComtListener() {
                    @Override
                    public void onComtListener() {
                        Map<String, String> map = new HashMap<>();
                        map.put("storeGoodsId", goodsId);
                        if (TextUtils.equals("3", status)) {
                            //仓库中状态时 按钮为上架
                            mvpPresenter.upperShelf(map);
                        } else {
                            //出售中，待审核 状态时 按钮为下架
                            mvpPresenter.lowerShelf(map);
                        }

                    }
                });

            }

            @Override
            public void onShowButtonClick(RespStoreGoods.StoreGoodsData itemData) {

                saveCodeDialog = new SaveCodeDialog(mActivity, itemData);
                saveCodeDialog.setOnSaveButtonClick(new SaveCodeDialog.OnSaveButtonClick() {
                    @Override
                    public void OnSaveButtonClick(View view) {
                        MyImgUtils.saveLayoutByDrawing(mActivity, view);
                    }
                });
                saveCodeDialog.show();

            }
        });

        filterTitle.setOnFilterCheckedListener(new FilterTitle.OnFilterCheckedListener() {
            @Override
            public void onTypeDate(boolean isUp) {
                sortMode = "1";
                if (isUp) {
                    timeSort = "1";
                } else {
                    timeSort = "2";
                }
                getStoreGoodsByStatus();
            }

            @Override
            public void onTypeSales() {
                sortMode = "2";
                getStoreGoodsByStatus();
            }

            @Override
            public void onTypeVolume() {
                sortMode = "3";
                getStoreGoodsByStatus();
            }
        });

        isViewCreate = true;
        getStoreGoodsByStatus();
    }

    private void getStoreGoodsByStatus() {
        //sort 排序方式 (1 升序 / 2 降序)(必传)
        //sortMode 排序条件(1创建时间 / 2销量 / 3成交额 / 4库存) (必传)
        //status 商品状态(1上架 / 2审核 / 3下架) (必传)
        Map<String, String> map = new HashMap<>();
        map.put("status", status);
        map.put("sortMode", sortMode);
        //按时间排序区分正倒序，其他只有一种状态
        if (StringUtils.equals("1", sortMode)) {
            map.put("sort", timeSort);
        } else {
            map.put("sort", otherSort);
        }

        mvpPresenter.getStoreGoodsByStatus(map);
    }

    /**
     * 编辑或删除后 展示不同类型的fragment要更新数据，用于刷新tab数量
     *
     * @param refreshGoodsDataEvent
     */
    @Subscribe
    public void RefreshGoodsDataEvent(RefreshGoodsDataEvent refreshGoodsDataEvent) {
        String eventStatus = refreshGoodsDataEvent.status;
        if (!StringUtils.equals(status, eventStatus)) {
            //当前页面发送的event ，当前页面不需要（也不能刷新）刷新
            getStoreGoodsByStatus();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusActivityScope.getDefault(mActivity).unregister(this);
        if (saveCodeDialog != null && saveCodeDialog.isShowing()) {
            saveCodeDialog.dismiss();
            saveCodeDialog = null;
        }
        if (messageClickDialog != null && messageClickDialog.isShowing()) {
            messageClickDialog.dismiss();
            messageClickDialog = null;
        }
    }

    @Override
    public void onGetStoreGoodsByStatusSuccess(RespStoreGoods respStoreGoods) {
        List<RespStoreGoods.StoreGoodsData> data = respStoreGoods.getData();
        adapterList.clear();
        if (data != null) {
            adapterList.addAll(data);
        }
        //获取到列表数量 ，通知activity页面刷新tab
        EventBusActivityScope.getDefault(getActivity()).post(new RefreshTabCountEvent(status, adapterList.size()));

        goodsManagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStoreGoodsEditSuccess() {
        ToastUtils.showShort("修改成功");
        EventBusActivityScope.getDefault(getActivity()).post(new RefreshGoodsDataEvent(status));
        getStoreGoodsByStatus();
    }

    @Override
    public void onStoreGoodsDeleteSuccess() {
        ToastUtils.showShort("删除成功");
        EventBusActivityScope.getDefault(getActivity()).post(new RefreshGoodsDataEvent(status));
        getStoreGoodsByStatus();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onFailure(String code, String msg) {

    }
}
