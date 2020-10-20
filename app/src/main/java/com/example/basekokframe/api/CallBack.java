package com.example.basekokframe.api;

import com.example.basekokframe.bean.BaseBean;

public interface CallBack<T> {
    /**
     * 网络异常
     */
    int NET_ERROR = 10001;
    /**
     * 服务器异常
     */
    int SERVICE_ERROR = 10002;
    /**
     * 解析异常
     */
    int PARSE_ERROR = 10003;
    /**
     * 超时异常
     */
    int TIMEOUT_ERROR = 10004;

    void onSuccess(T t);

    /**
     * 错误回调
     *
     * @param errMsg
     * @param errCode
     */
    void onFaile(String errMsg, int errCode, T bean);
}
