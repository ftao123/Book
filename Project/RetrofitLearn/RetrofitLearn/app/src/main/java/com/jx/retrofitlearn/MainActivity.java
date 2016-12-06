package com.jx.retrofitlearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.jx.retrofitlearn.converter.gson.GsonConverterFactory;
import com.jx.retrofitlearn.model.Contributor;
import com.jx.retrofitlearn.model.User;
import com.jx.retrofitlearn.response.GetIpInfoResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {

    private static final String ENDPOINT = "http://ip.taobao.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserById();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpLoadActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               login();
            }
        });

    }


    private void getUser() {
        UserClient userClient = ServiceGenerator.createService(UserClient.class);
        Call<List<User>> call = userClient.getUser();
        try {
            List<User> contributors = call.execute().body();
            for (User contributor : contributors) {
                Log.i("MainActivity", "contributor  " + contributor.getName() + " " + contributor.getSex());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getUserById() {
        UserClient userClient = ServiceGenerator.createService(UserClient.class);
        Call<List<User>> call = userClient.getUserById("1");
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.i("MainActivity", response.body().toString());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.i("MainActivity", t.getMessage());
            }
        });
    }

    private void getAsyncUser() {
        UserClient userClient = ServiceGenerator.createService(UserClient.class);
        Call<List<User>> call = userClient.getUser();
        //Call<List<User>> call=userClient.getUser2();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                for (User contributor : response.body()) {
                    Log.i("MainActivity", "contributor  " + contributor.getName() + " " + contributor.getSex());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.i("MainActivity", t.getMessage());
            }
        });
    }


    private void register() {
//        User user = new User("哈哈", "123456", "男", "110", "", "0", "", "9", "100");
//        UserClient userClient = ServiceGenerator.createService2(UserClient.class);
//        Call<String> call = userClient.register(user);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                Log.i("MainActivity", response.body());
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.i("MainActivity", t.getMessage());
//            }
//        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserClient userClient = retrofit.create(UserClient.class);



        Call<GetIpInfoResponse> call = userClient.getIpInfo("63.223.108.42");
        call.enqueue(new Callback<GetIpInfoResponse>() {
            @Override
            public void onResponse(Call<GetIpInfoResponse> call, Response<GetIpInfoResponse> response) {
                GetIpInfoResponse getIpInfoResponse = response.body();
                Log.d("<<<<<",getIpInfoResponse.data.country);

            }

            @Override
            public void onFailure(Call<GetIpInfoResponse> call, Throwable t) {
                Log.d("<<<<<",t.getMessage());
            }

        });


    }

    private void login() {
        UserClient userClient = ServiceGenerator.createService(UserClient.class);
        Call<List<User>> call = userClient.login("13795378745","123456");
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.i("MainActivity", response.body().toString());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.i("MainActivity", t.getMessage());
            }
        });
    }

    private void getContributor() {
        GitHubClient gitHubClient = ServiceGenerator.createService(GitHubClient.class);
        Call<List<Contributor>> call = gitHubClient.contributors("square", "retrofit");
        try {
            List<Contributor> contributors = call.execute().body();
            for (Contributor contributor : contributors) {
                Log.i("MainActivity", "contributor  " + contributor.login + " " + contributor.contributions);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
