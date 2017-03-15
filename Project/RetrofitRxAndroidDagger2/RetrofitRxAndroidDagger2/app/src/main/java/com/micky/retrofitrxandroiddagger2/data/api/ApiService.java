package com.micky.retrofitrxandroiddagger2.data.api;

import com.micky.retrofitrxandroiddagger2.data.api.response.GetIpInfoResponse;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.Response;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;
import rx.Observable;

/**
 * @Project retrofitrxandroiddagger2
 * @Packate com.micky.retrofitrxandroiddagger2.data.api
 * @Description
 * @Author Micky Liu
 * @Email mickyliu@126.com
 * @Date 2015-12-21 17:22
 * @Version 0.1
 * ================================
 * /动态添加头部:authorization
 *@GET("/user")
 *void getUser(@Header("Authorization") String authorization, Callback<User> callback)
 *=================================
 * */
public interface ApiService {
    String a="b";//这个a可以是token @Headers(a，b...)可以添加多个
  /*  @GET("service/getIpInfo.php")
    Call<GetIpInfoResponse> getIpInfo(@Query("ip") String ip);*/

    @GET("service/getIpInfo.php")
    Observable<GetIpInfoResponse> getIpInfo(@Query("ip") String ip);

    @GET("service/getIpInfo.php")
    Call<ResponseBody> getIpInfo2(@Query("ip") String ip);

    @Headers(a)
    @GET("service/getIpInfo.php")
    Call<ResponseBody> getIpInfo3(@Query("ip") String ip);



}
