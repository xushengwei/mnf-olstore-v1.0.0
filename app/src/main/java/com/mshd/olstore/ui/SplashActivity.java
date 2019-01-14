package com.mshd.olstore.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.mshd.olstore.R;
import com.mshd.olstore.base.BasePresenter;
import com.mshd.olstore.base.MvpActivity;
import com.mshd.olstore.base.SpKey;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @author xushengwei
 * @date 2018/11/13
 */
public class SplashActivity extends MvpActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mImmersionBar
                .transparentBar()
                .init();
        //MobclickAgent.openActivityDurationTrack(false);
        String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.WRITE_APN_SETTINGS, Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_NUMBERS};

        Acp.getInstance(getApplicationContext()).request(new AcpOptions.Builder()
                        .setPermissions(mPermissionList)
                        .setDeniedMessage("未获取到权限，此模块无法运行，请设置")
                        .setDeniedCloseBtn("关闭")
                        .setDeniedSettingBtn("去设置")
                        .setRationalMessage("应用需要此权限才能正常运行")
                        .setRationalBtn("确定")
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {

                        Observable.timer(1500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Long aLong) {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                Intent intent = new Intent();
                                String spingSession = SPUtils.getInstance().getString(SpKey.SPRING_SESSION);
                                boolean isLogin = SPUtils.getInstance().getBoolean(SpKey.LOGSTATUS, false);
                                if (StringUtils.isEmpty(spingSession) || !isLogin) {
                                    intent.setClass(SplashActivity.this, LoginActivity.class);
                                } else {
                                    intent.setClass(SplashActivity.this, MainActivity.class);
                                }
                                startActivity(intent);
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onDenied(List<String> permissions) {

                    }
                });

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
