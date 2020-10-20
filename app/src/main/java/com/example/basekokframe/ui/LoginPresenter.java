package com.example.basekokframe.ui;

import com.example.basekokframe.api.ApiInterface;
import com.example.basekokframe.api.CallBack;
import com.example.basekokframe.base.KokBasePresenterIml;
import com.example.basekokframe.bean.BaseBean;
import com.example.basekokframe.bean.UserInfo;
import com.orhanobut.logger.Logger;

import io.reactivex.Observable;

public class LoginPresenter extends KokBasePresenterIml<LoginView> {
    public LoginPresenter(LoginView view) {
        super(view);
    }

    /**
     * 登录
     */
    public void login() {
        Observable<BaseBean<UserInfo>> login = mModel.request(ApiInterface.class).login("1", "2", "3");
        mModel.execute(login, new CallBack<BaseBean<UserInfo>>() {
            @Override
            public void onSuccess(BaseBean<UserInfo> userInfoBaseBean) {
                mView.toast("登录成功");
            }

            @Override
            public void onFaile(String errMsg, int errCode, BaseBean bean) {
                mView.toast("登录失败");
            }
        });
    }
}
