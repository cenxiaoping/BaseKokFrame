package com.example.basekokframe.base;

import com.example.basekokframe.BaseEvent;
import com.example.basekokframe.SpUtils;
import com.example.basekokframe.UserInfoBean;
import com.kok.base.BaseView;

public class KokBasePresenterIml<V extends BaseView> implements KokBasePresenter {
    protected BaseView mView;

    public KokBasePresenterIml(V view) {
        mView = view;
    }

    @Override
    public boolean isLoging() {
        return false;
    }

    @Override
    public void setLoinState(boolean isLogin) {

    }

    @Override
    public SpUtils getSP() {
        return null;
    }

    @Override
    public UserInfoBean getUserInfo() {
        return null;
    }

    @Override
    public String getUserID() {
        return null;
    }

    @Override
    public void post(BaseEvent event) {

    }

    @Override
    public String isEmpty(String text) {
        return null;
    }

    @Override
    public boolean isLogingWithJump() {
        return false;
    }
}
