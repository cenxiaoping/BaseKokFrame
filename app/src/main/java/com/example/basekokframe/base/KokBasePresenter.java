package com.example.basekokframe.base;

import com.example.basekokframe.BaseEvent;
import com.example.basekokframe.SpUtils;
import com.example.basekokframe.UserInfoBean;
import com.kok.base.BasePresenter;

public interface KokBasePresenter extends BasePresenter {

    BaseModel mModel = BaseModel.getInstance();

    /**
     * 是否已登录
     *
     * @return false 没有登录，true 已经登录
     */
    boolean isLoging();

    /**
     * 更新登录状态
     *
     * @param isLogin
     */
    void setLoinState(boolean isLogin);

    /**
     * 获取SpUtils工具类实例
     *
     * @return
     */
    SpUtils getSP();

    /**
     * 获取用户信息
     *
     * @return 返回登录用户信息
     */
    UserInfoBean getUserInfo();

    /**
     * 获取UserId
     *
     * @return
     */
    String getUserID();

    /**
     * 发送总线
     *
     * @param event
     */
    void post(BaseEvent event);

    /**
     * 判断字符串是否未空，如果为空则返回空串
     * 不为空，返回实际值
     *
     * @param text
     * @return
     */
    String isEmpty(String text);

    /**
     * @return
     */
    boolean isLogingWithJump();
}
