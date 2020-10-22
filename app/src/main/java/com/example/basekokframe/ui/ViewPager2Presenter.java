package com.example.basekokframe.ui;

import com.example.basekokframe.api.ApiInterface;
import com.example.basekokframe.api.CallBack;
import com.example.basekokframe.base.KokBasePresenterIml;
import com.example.basekokframe.bean.BaseBean;
import com.example.basekokframe.bean.UserInfo;

import io.reactivex.Observable;

public class ViewPager2Presenter extends KokBasePresenterIml<ViewPager2View> {
    public ViewPager2Presenter(ViewPager2View view) {
        super(view);
    }

}
