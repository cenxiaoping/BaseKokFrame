package com.example.basekokframe.ui;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.basekokframe.R;
import com.example.basekokframe.base.KokBaseActivity;
import com.example.basekokframe.utils.FullLifecycleObserverIml;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class LoginActivity extends KokBaseActivity<LoginPresenter> implements LoginView {

    private MutableLiveData<String> currentName = new MutableLiveData<>();
    private EditText et_pwd;

    public MutableLiveData<String> getCurrentName() {
        if (currentName == null) {
            currentName = new MutableLiveData<String>();
        }
        return currentName;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        Logger.addLogAdapter(new AndroidLogAdapter());
        currentName.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                et_pwd.setText(s);
            }
        });

    }

    @Override
    public void initView() {

        et_pwd = findViewById(R.id.et_pwd);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPresenter.login();
//                currentName.postValue("123456");
                startActivity(RecyclerViewActivity.class);
            }
        });
    }
}
