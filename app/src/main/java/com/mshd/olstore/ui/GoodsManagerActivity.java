package com.mshd.olstore.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.mshd.olstore.R;
import com.mshd.olstore.adapter.fragment.GoodsPagerAdapter;
import com.mshd.olstore.base.BasePresenter;
import com.mshd.olstore.base.MvpActivity;
import com.mshd.olstore.event.RefreshTabCountEvent;
import com.mshd.olstore.ui.fragment.GoodsPagerFragment;
import com.mshd.olstore.widget.FilterTitle;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * 商品管理
 * @author xushengwei
 * @date 2019/1/7
 */
public class GoodsManagerActivity extends MvpActivity implements View.OnClickListener {

    private List<String> titles;
    private GoodsPagerAdapter goodsPagerAdapter;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_manager);
        mImmersionBar.statusBarDarkFont(true, 0.2f).statusBarView(R.id.top_view).init();
        EventBusActivityScope.getDefault(this).register(this);
        initToolBar("商品");

        ImageView iv_back = findViewById(R.id.iv_back);
        Button btn_addGoods = findViewById(R.id.btn_addGoods);
        slidingTabLayout = findViewById(R.id.slidingTabLayout);


        titles = new ArrayList<>();
        titles.add("出售中");
        titles.add("待审核");
        titles.add("仓库中");
        List<GoodsPagerFragment> fragments = new ArrayList<>();
        //商品状态(1出售中/2待审核/3已下架)
        fragments.add(GoodsPagerFragment.newInstance("1"));
        fragments.add(GoodsPagerFragment.newInstance("2"));
        fragments.add(GoodsPagerFragment.newInstance("3"));

        goodsPagerAdapter = new GoodsPagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(goodsPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        //给TabLayout设置适配器
        slidingTabLayout.setViewPager(viewPager);

        iv_back.setImageResource(R.mipmap.arrow_left);

        btn_addGoods.setOnClickListener(this);

    }
    private int  tabCountA ;
    private int  tabCountB ;
    private int  tabCountC ;

    @Subscribe
    public void RefreshTabCountEvent(RefreshTabCountEvent refreshTabCountEvent) {
      String status =  refreshTabCountEvent.status;
      //  商品状态(1出售中/2待审核/3已下架)
      switch (status){
          case "1":
              tabCountA=refreshTabCountEvent.tabCount;
              LogUtils.d("tabCountA:"+tabCountA);
              break;
          case "2":
              tabCountB=refreshTabCountEvent.tabCount;
              LogUtils.d("tabCountB:"+tabCountB);
              break;
          case "3":
              tabCountC=refreshTabCountEvent.tabCount;
              LogUtils.d("tabCountC:"+tabCountC);
              break;
          default:
              break;
      }

        titles.clear();
        titles.add("出售中("+tabCountA+")");
        titles.add("待审核("+tabCountB+")");
        titles.add("仓库中("+tabCountC+")");
        goodsPagerAdapter.notifyDataSetChanged();
        slidingTabLayout.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusActivityScope.getDefault(this).unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addGoods:
                startActivity(GoodsAddActivity.class);
                break;

            default:
                break;
        }
    }
}
