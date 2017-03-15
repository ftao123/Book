package com.micky.retrofitrxandroiddagger2.data.api;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/15.
 */

public class  MyIntercept implements Interceptor {

//    private final static String TAG = LogUtils.makeLogTag(HttpLoggingInterceptor.class);

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
//        LOGD(TAG, "**********************       REQUEST START     **********************");
//        LOGD(TAG, "REQUEST URL -> " + request.urlString());
//        LOGD(TAG, "REQUEST HEADERS -> " + request.headers());
//        LOGD(TAG, "**********************       REQUEST END     **********************");


        Response response = chain.proceed(chain.request());

//
//        LOGD(TAG, "**********************       RESPONSE START     **********************");
//        LOGD(TAG, "RESPONSE CODE -> " + response.code());
//        LOGD(TAG, "RESPONSE HEADERS -> " + response.headers());
//        LOGD(TAG, "**********************       RESPONSE END     **********************");
        return response;
    }
}