package com.micky.retrofitrxandroiddagger2;



import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;


/**
 * Created by Administrator on 2017/3/15.
 */
public class MyConverter implements Converter<ResponseBody,String> {

    @Override
    public String convert(ResponseBody value) throws IOException {
        try {
           return value.toString();
        }finally {
            value.close();
        }
    }
}
