package com.micky.retrofitrxandroiddagger2;


import java.io.ByteArrayInputStream;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;



/**
 * Created by ftao on 2017/3/19.
 */
public class MyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response response = chain.proceed(request);
        return new Response.Builder()
                .body(newResponseBody(response))
                .headers(response.headers())
                .message(response.message())
                .code(response.code())
                .request(response.request())
                .protocol(response.protocol())
                .build();
    }

    private ResponseBody newResponseBody(final Response response){
        return new ResponseBody(){
            @Override
            public MediaType contentType(){
                return response.body().contentType();
            }
            @Override
            public long contentLength(){
                try {
                    return response.body().contentLength();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    return 0L;
                }
            }
            @Override
            public BufferedSource source(){
                try
                {
                    String result = response.body().string();
//                    if(JSON.parseObject(result).getInteger("code") == 500){
                    if(true){
/**
 *这里改变返回的数据，如果服务器返回的是一个HTML网页，
 *那么移动端也能拿到一个Json数据，用于保证数据可解析不至于崩溃
 */
                        ByteArrayInputStream tInputStringStream = new ByteArrayInputStream("{code:500,success:false}".getBytes());
                        BufferedSource source = Okio.buffer(Okio.source(tInputStringStream));
                        return source;
                    }else
                    {
                        ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(result.getBytes());
                        BufferedSource source = Okio.buffer(Okio.source(tInputStringStream));
                        return source;
                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }
}
