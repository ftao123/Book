package com.micky.retrofitrxandroiddagger2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.micky.retrofitrxandroiddagger2.common.utils.CrashHandler;
import com.micky.retrofitrxandroiddagger2.data.api.ApiService;
import com.micky.retrofitrxandroiddagger2.data.api.response.GetIpInfoResponse;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;

import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
//                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//                OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                        .addInterceptor(httpLoggingInterceptor)
//                        .build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ENDPOINT)
//                        .client(okHttpClient)
                        .addConverterFactory(new MyGsonFactory())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
                ApiService apiService = retrofit.create(ApiService.class);
                try{
                    Call<ResponseBody> call=apiService.getIpInfo2("63.223.108.42");
                    call.enqueue(new Callback<ResponseBody>() {

//                    它内部没这个解析适配器
//                        它获取到服务器数据已经就不知道怎么解析。
//                        以后  ResponseBody  自己之前使用Response一直不能成功？？？
                        @Override
                        public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                            String a=response.body().toString();
                            textView.setText(a+"");
                            Log.d("<<<<",response.body().toString()+":::"+a.toString());
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                }catch (Exception e){

                }

                break;
        }
    }
}
