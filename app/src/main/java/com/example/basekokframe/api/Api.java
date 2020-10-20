package com.example.basekokframe.api;


import com.example.basekokframe.api.converter.GsonConverterFactory;
import com.example.basekokframe.api.interceptor.HeaderInterceptor;
import com.example.basekokframe.api.interceptor.LoggingInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


public class Api {


    private final Retrofit.Builder mRetrofitBuilder;

    private static class ApiHolder {
        public static Api api = new Api();
    }

    private Api() {

        //初始化Okhttp
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new LoggingInterceptor())
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                })
//                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();

        mRetrofitBuilder = new Retrofit.Builder().baseUrl("http://rap2api.taobao.org/app/mock/118371/").client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    public static Api getInstance() {
        return ApiHolder.api;
    }

    /**
     * 设置请求Host
     *
     * @param url
     * @return
     */
    public Api setBaseUrl(String url) {
        mRetrofitBuilder.baseUrl(url);
        return ApiHolder.api;
    }

    public <T> T create(Class<T> cls) {
        return mRetrofitBuilder.build().create(cls);
    }

}
