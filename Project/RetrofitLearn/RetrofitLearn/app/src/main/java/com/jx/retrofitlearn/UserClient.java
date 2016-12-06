package com.jx.retrofitlearn;

import com.jx.retrofitlearn.model.User;
import com.jx.retrofitlearn.response.GetIpInfoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by jiang.xu on 2016/2/22.
 */
public interface UserClient {

    @GET("/index.php?c=User&m=getUser")
    Call<List<User>> getUser();

    @GET("/index.php?c=User&m=getUserById")
    Call<List<User>> getUserById(@Query("id") String id);

    @POST("/index.php?c=User&m=register")
    Call<String> register(@Body User user);

    @FormUrlEncoded
    @POST("/index.php?c=User&m=login")
    Call<List<User>> login(@Field("phone") String phone, @Field("password") String password);

    @GET("service/getIpInfo.php")
    Call<GetIpInfoResponse> getIpInfo(@Query("ip") String ip);
}
