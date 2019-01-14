package com.mshd.olstore.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.mshd.olstore.R;
import com.mshd.olstore.base.BasePresenter;
import com.mshd.olstore.base.MvpActivity;
import com.mshd.olstore.base.SpKey;

/**
 * @author xushengwei
 * @date 2019/1/12
 */
public class SettingActivity extends MvpActivity implements View.OnClickListener {
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mImmersionBar.statusBarDarkFont(true, 0.2f).statusBarView(R.id.top_view).init();
        initToolBar("个人信息");
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setImageResource(R.mipmap.arrow_left);

        RelativeLayout rl_storeInfo = findViewById(R.id.rl_storeInfo);
        RelativeLayout rl_logout =findViewById(R.id.rl_logout);

        findViewById(R.id.iv_setting).setVisibility(View.GONE);

        //3员工 2店长
        String userType = SPUtils.getInstance().getString(SpKey.USER_TYPE);
        if (StringUtils.equals("2",userType)){
            rl_storeInfo.setVisibility(View.VISIBLE);
        }else {
            rl_storeInfo.setVisibility(View.GONE);
        }

        rl_storeInfo.setOnClickListener(this);
        rl_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_storeInfo:
                startActivity(SettingStoreInfoActivity.class);
                break;
            case R.id.rl_logout:
                SPUtils.getInstance().clear();
                startActivity(LoginActivity.class);
                ActivityUtils.finishOtherActivities(LoginActivity.class);
                break;
            default:
                break;
        }
    }
}
