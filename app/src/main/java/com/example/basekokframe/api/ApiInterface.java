package com.example.basekokframe.api;

import com.example.basekokframe.bean.BaseBean;
import com.example.basekokframe.bean.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    Observable<BaseBean<UserInfo>> login(@Field("phone") String phone, @Field("smsCode") String smsCode, @Field("imageCode") String imageCode);
}
