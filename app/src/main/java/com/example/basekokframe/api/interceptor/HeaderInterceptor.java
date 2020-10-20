package com.example.basekokframe.api.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    private String TAG = "HeaderInterceptor_http";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

//        //鉴权
//        String timestamp = String.valueOf(System.currentTimeMillis());
//        StringBuffer sb = new StringBuffer();
//        sb.append(StringUtils.getDevicesId()).append(Constant.KEYWORD).append(timestamp);
//        String md5Str = MD5.getMD5Str(sb.toString());
//
//
        builder.addHeader("X-LC-Id", "lr5t4wHvmkgTpAijsJFtAtpi-gzGzoHsz");
        builder.addHeader("X-LC-Key", "HuaftFAacAxwtbnHKeVCsJTm");

//        builder.addHeader(Constant.SIGNATURE, md5Str);
//        builder.addHeader(Constant.MACHINEID, StringUtils.getDevicesId());
//
//        Request newRequest = builder.build();
//
//        if(BuildConfig.DEBUG){
//            Log.d(TAG, newRequest.url() + "");
//            Log.d(TAG, Constant.PALTFORM + ":" + Constant.ANDROID_TAG);
//            Log.d(TAG, Constant.TIMESTAMP + ":" + timestamp);
//            Log.d(TAG, Constant.MACHINEID + ":" + StringUtils.getDevicesId());
//            Log.d(TAG, Constant.SIGNATURE + ":" + md5Str);
//        }

        return chain.proceed(builder.build());
    }
}