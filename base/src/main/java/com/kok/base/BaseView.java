package com.kok.base;

import android.app.Activity;
import android.content.Context;

public interface BaseView {

    Context getContext();

    Activity getActivity();

    void showLoading();

    void dismissLoading();

    void toast(String text);

    void toLogin();

    boolean isShowing();
}
