package com.mshd.olstore.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.mshd.olstore.R;
import com.mshd.olstore.base.BaseResp;
import com.mshd.olstore.base.MvpActivity;
import com.mshd.olstore.mvp.contract.AcquireCodelContract;
import com.mshd.olstore.mvp.contract.LoginContract;
import com.mshd.olstore.mvp.contract.NormalContract;
import com.mshd.olstore.mvp.presenter.AcquireCodePresenter;
import com.mshd.olstore.mvp.presenter.LoginPresenter;
import com.mshd.olstore.mvp.presenter.MultipointLoginPresenter;
import com.mshd.olstore.retrofit.Contact;
import com.mshd.olstore.utils.CountDownTimerUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xushengwei
 * @date 2019/1/9
 */
public class LoginByCodeActivity extends MvpActivity<LoginPresenter> implements View.OnClickListener, LoginContract.View {


    private EditText et_identifyingCode;
    private CountDownTimerUtils countDownTimerUtils;
    private TextView tv_getCode;
    private String phoneNo;
    private String inputImageCode;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_code);
        mImmersionBar.statusBarDarkFont(true, 0.2f).statusBarView(R.id.top_view).init();

        phoneNo = getIntent().getStringExtra("phoneNo");
        inputImageCode = getIntent().getStringExtra("inputImageCode");


        Button btn_login = findViewById(R.id.btn_login);
        TextView tv_phoneNo =findViewById(R.id.tv_phoneNo);
        TextView tv_changePhoneNo =findViewById(R.id.tv_changePhoneNo);

        et_identifyingCode = findViewById(R.id.et_identifyingCode);
        tv_getCode = findViewById(R.id.tv_getCode);

        tv_phoneNo.setText(phoneNo);

        tv_getCode.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_changePhoneNo.setOnClickListener(this);

        countDownTimerUtils = new CountDownTimerUtils(tv_getCode, 60000, 1000);
        countDownTimerUtils.start();
    }

    private void getPhoneCode(String phone, String imgCode) {
        Map<String, String> map = new HashMap<>();
        map.put("code", imgCode);
        map.put("mobile", phone);
        map.put("type", "1");
        mvpPresenter.getPhoneCode(map);
    }


    private void requestLogin() {
        String code = et_identifyingCode.getText().toString().trim();
        if (!RegexUtils.isMobileSimple(phoneNo)) {
            ToastUtils.showShort("请输入正确的手机号码");
            return;
        }

        if (TextUtils.isEmpty(code)) {
            ToastUtils.showShort("请输入验证码");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("identifyingCode", code);
        map.put("phone", phoneNo);
        mvpPresenter.multipointLogin(map);

    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
    }

    @Override
    public void onFailure(String code, String msg) {
        ToastUtils.showShort(msg+"-"+code);
        if (StringUtils.equals("", code)) {

        }
    }

    @Override
    public void onGetImgSuccess(byte[] imgBys) {

    }

    @Override
    public void onGetPhoneCodeSuccess(BaseResp resp) {
        ToastUtils.showShort("已发送");
        countDownTimerUtils = new CountDownTimerUtils(tv_getCode, 60000, 1000);
        countDownTimerUtils.start();
    }

    @Override
    public void onLoginSuccess(BaseResp resp) {
        startActivity(MainActivity.class);
        ActivityUtils.finishOtherActivities(MainActivity.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:

                requestLogin();

                break;

            case R.id.tv_getCode:
                getPhoneCode(phoneNo, inputImageCode);
                break;
            case R.id.tv_changePhoneNo:
                finish();
                break;
            default:
                break;
        }
    }


}
