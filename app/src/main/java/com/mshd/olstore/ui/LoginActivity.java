package com.mshd.olstore.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.mshd.olstore.R;
import com.mshd.olstore.base.BaseResp;
import com.mshd.olstore.base.MvpActivity;
import com.mshd.olstore.mvp.contract.LoginContract;
import com.mshd.olstore.mvp.presenter.LoginPresenter;
import com.mshd.olstore.retrofit.Contact;
import com.mshd.olstore.widget.PhoneImgCodeDialog;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xushengwei
 * @date 2019/1/9
 */
public class LoginActivity extends MvpActivity<LoginPresenter> implements View.OnClickListener, LoginContract.View {

    private EditText et_phoneNo;
    private PhoneImgCodeDialog phoneImgCodeDialog;
    private String phoneNo;
    private String inputImageCode ;


    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this, this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mImmersionBar.statusBarDarkFont(true, 0.2f).statusBarView(R.id.top_view).init();

        Button btn_next = findViewById(R.id.btn_next);
        et_phoneNo = findViewById(R.id.et_phoneNo);


        btn_next.setOnClickListener(this);

    }


    private void getImageCode() {
        phoneNo = et_phoneNo.getText().toString().trim();
        if (StringUtils.isEmpty(phoneNo)) {
            ToastUtils.showShort("请输入手机号码");
            return;
        }

        if (!RegexUtils.isMobileSimple(phoneNo)) {
            ToastUtils.showShort("请输入正确的手机号码");
            return;
        }
        mvpPresenter.getImageCode(Contact.API_SERVER_URL + Contact.getImgCodeUrl, phoneNo);
    }

    private void getPhoneCode(String phone, String imgCode) {
        Map<String, String> map = new HashMap<>();
        map.put("code", imgCode);
        map.put("mobile", phone);
        map.put("type", "1");
        mvpPresenter.getPhoneCode(map);
    }

    @Override
    public void onGetImgSuccess(byte[] imgBys) {
        phoneImgCodeDialog = new PhoneImgCodeDialog(LoginActivity.this, imgBys);
        phoneImgCodeDialog.setOnBalanceTypeInputListener(new PhoneImgCodeDialog.onCommitClickListener() {
            @Override
            public void onCommitClickListener(String inputCodeString) {
                inputImageCode=inputCodeString;
                getPhoneCode(phoneNo, inputCodeString);
            }

            @Override
            public void onCodeImageClickListener() {
                //重新获取验证码
                phoneImgCodeDialog.dismiss();
                getImageCode();

            }
        });
        phoneImgCodeDialog.show();
    }

    @Override
    public void onGetPhoneCodeSuccess(BaseResp resp) {
        if (phoneImgCodeDialog!=null&&phoneImgCodeDialog.isShowing()){
            phoneImgCodeDialog.dismiss();
            phoneImgCodeDialog=null;
        }
        Intent intent = new Intent(LoginActivity.this, LoginByCodeActivity.class);
        intent.putExtra("phoneNo", phoneNo);
        intent.putExtra("inputImageCode",inputImageCode);
        startActivity(intent);
    }

    @Override
    public void onLoginSuccess(BaseResp resp) {

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
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (phoneImgCodeDialog != null && phoneImgCodeDialog.isShowing()) {
            phoneImgCodeDialog.dismiss();
            phoneImgCodeDialog = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                getImageCode();
                break;

            default:
                break;
        }
    }


}
