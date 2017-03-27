package com.micky.retrofitrxandroiddagger2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.micky.retrofitrxandroiddagger2.data.api.ApiService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @Project retrofitrxandroiddagger2
 * @Packate com.micky.retrofitrxandroiddagger2
 * @Description
 * @Author Micky Liu
 * @Email mickyliu@126.com
 * @Date 2015-12-21 17:35
 * @Version 0.1
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String ENDPOINT = "http://ip.taobao.com";
    private TextView mTvContent;
    private ProgressBar mProgressBar;
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        mTvContent = (TextView) findViewById(R.id.tv_content);
//        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        initViews();
        initEvent();
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////
////               Retrofit retrofit = new Retrofit.Builder()
////                        .baseUrl(ENDPOINT)
////                        .addConverterFactory(GsonConverterFactory.create())
////                        .build();
////                ApiService apiService = retrofit.create(ApiService.class);
////
////                mProgressBar.setVisibility(View.VISIBLE);
////
////                Call<GetIpInfoResponse> call = apiService.getIpInfo("63.223.108.42");
////                call.enqueue(new Callback<GetIpInfoResponse>() {
////                    @Override
////                    public void onResponse(Response<GetIpInfoResponse> response, Retrofit retrofit) {
////                        mProgressBar.setVisibility(View.GONE);
////                        GetIpInfoResponse getIpInfoResponse = response.body();
////                        mTvContent.setText(getIpInfoResponse.data.country);
////                    }
////
////                    @Override
////                    public void onFailure(Throwable t) {
////                        mProgressBar.setVisibility(View.GONE);
////                        mTvContent.setText(t.getMessage());
////                    }
////                });
//
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl(ENDPOINT)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                        .build();
//                ApiService apiService = retrofit.create(ApiService.class);
//                apiService.getIpInfo("63.223.108.42")
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Subscriber<GetIpInfoResponse>() {
//                            @Override
//                            public void onCompleted() {
//                                Log.d("<<<<","执行了--onCompleted()");
//                                //mProgressBar.setVisibility(View.GONE);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.d("<<<<","执行了--onError(Throwable e)");
//                                Log.d("<<<<","错误信息打印"+e.getMessage());
//                                mProgressBar.setVisibility(View.GONE);
//                                mTvContent.setText(e.getMessage());
//                            }
//
//                            @Override
//                            public void onNext(GetIpInfoResponse getIpInfoResponse) {
//                                Log.d("<<<<","执行了--onNext(GetIpInfoResponse getIpInfoResponse)");
//                                mTvContent.setText(getIpInfoResponse.data.country);
//                                Log.d("<<<<","正确信息打印"+getIpInfoResponse.data.country);
//                                Log.d("<<<<","正确信息打印"+getIpInfoResponse.data.area);
//                                Log.d("<<<<","正确信息打印"+getIpInfoResponse.data.area_id);
//                                Log.d("<<<<","正确信息打印"+getIpInfoResponse.data.country_id);
//                                Log.d("<<<<","正确信息打印"+getIpInfoResponse.data.ip);
//                            }
//                        });
//            }
//        });
    }


    private void initViews() {
        button= (Button) findViewById(R.id.button);
        textView= (TextView) findViewById(R.id.textView);
//        if(int a==0){
//
//        }
    }
    private void initEvent() {
        button.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:


                // 查看网络request 和 response 的具体的值
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);//设置查看日志的等级
                OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
                okHttpBuilder.interceptors().add(logging);// 这句话是重点
//                okHttpBuilder.addInterceptor(logging);
                //可以自定义，可以使用okHttp 默认的timeout 10s
                okHttpBuilder.connectTimeout(5000, TimeUnit.SECONDS);

                Retrofit retrofit = new Retrofit.Builder()
                        .client(okHttpBuilder.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .baseUrl(ENDPOINT)
                        .build();
                ApiService apiService = retrofit.create(ApiService.class);
                try{
                    Call<ResponseBody> call=apiService.getIpInfo2("63.223.108.42");
                    call.enqueue(new Callback<ResponseBody>() {

                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String a= response.body().string().toString();
                                textView.setText(a);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }

                    });
                }catch (Exception e){

                }

                break;
        }
    }
}
