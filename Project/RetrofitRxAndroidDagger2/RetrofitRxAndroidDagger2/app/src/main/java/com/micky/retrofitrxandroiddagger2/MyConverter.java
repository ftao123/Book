package com.micky.retrofitrxandroiddagger2;


import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import retrofit.Converter;

/**
 * Created by Administrator on 2017/3/15.
 */
public class MyConverter implements Converter<ResponseBody,String>{

    @Override
    public String convert(ResponseBody value) throws IOException {
        try {
           return value.toString();
        }finally {
            value.close();
        }
    }
}
