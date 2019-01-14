package com.mshd.olstore.base;


public interface BaseView {
    void showLoading();

    void hideLoading();

    void onFailure(String code, String msg);
}
