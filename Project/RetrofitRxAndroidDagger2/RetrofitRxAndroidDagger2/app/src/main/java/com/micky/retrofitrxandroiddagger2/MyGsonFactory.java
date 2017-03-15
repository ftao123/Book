package com.micky.retrofitrxandroiddagger2;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;
import retrofit.Response;

/**
 * Created by Administrator on 2017/3/14.
 */
public class MyGsonFactory extends Converter.Factory {



    @Override
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        return super.fromResponseBody(type, annotations);

}

    @Override
    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        return super.toRequestBody(type, annotations);


    }
}
