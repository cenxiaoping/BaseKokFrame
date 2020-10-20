package com.example.basekokframe.base;

import android.widget.Toast;

import com.kok.base.BaseFragment;

public abstract class KokBaseFragment<P extends KokBasePresenter> extends BaseFragment<P> {
    public void showLoading() {

    }

    public void dismissLoading() {

    }

    public void toast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void toLogin() {

    }

    public boolean isShowing() {
        return false;
    }
}
