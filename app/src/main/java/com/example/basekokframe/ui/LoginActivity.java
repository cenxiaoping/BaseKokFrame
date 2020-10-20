package com.example.basekokframe.ui;

import android.view.View;

import com.example.basekokframe.R;
import com.example.basekokframe.base.KokBaseActivity;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class LoginActivity extends KokBaseActivity<LoginPresenter> implements LoginView{
    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public void initView() {

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login();
            }
        });
    }
}
