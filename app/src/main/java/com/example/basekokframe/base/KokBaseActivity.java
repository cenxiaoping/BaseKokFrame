package com.example.basekokframe.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.basekokframe.utils.FullLifecycleObserverIml;
import com.kok.base.BaseActivity;
import com.kok.base.BaseView;

public abstract class KokBaseActivity<P extends KokBasePresenter> extends BaseActivity<P> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLifecycle().addObserver(new FullLifecycleObserverIml());

        //设置setContentView
        if (setLayoutId() != 0) {
            setContentView(setLayoutId());
        }
    }

    public void showLoading() {

    }

    public void dismissLoading() {

    }

    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void toLogin() {

    }

    public boolean isShowing() {
        return false;
    }
}
