package com.mshd.olstore.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mshd.olstore.R;
import com.mshd.olstore.base.BasePresenter;
import com.mshd.olstore.base.MvpActivity;

/**
 * @author xushengwei
 * @date 2019/1/7
 */
public class GoodsActivity extends MvpActivity implements View.OnClickListener {
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        mImmersionBar.statusBarDarkFont(true, 0.2f).statusBarView(R.id.top_view).init();

        ImageView iv_back = findViewById(R.id.iv_back);
        Button btn_addGoods =findViewById(R.id.btn_addGoods);

        iv_back.setImageResource(R.mipmap.arrow_left);

        btn_addGoods.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_addGoods:
                startActivity(GoodsManagerActivity.class);
                break;

            default:
                break;
        }
    }
}
