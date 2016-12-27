package com.example.myfirst.refreshdialog;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //定义旋转动画Dialog
    private ProgressAlertDialog progress1;
    //定义帧动画dialot
    private ProgressDrawableAlertDialog progress2;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();
        //初始化进度条dialog
        progress1 = new ProgressAlertDialog(this);
        //开始选择的过场动画
        findViewById(R.id.start_rotate_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //让进度条显示出来
                progress1.show();

                //10秒钟后停止动画
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress1.dismiss();
                    }
                }, 10000);
            }
        });

        progress2 = new ProgressDrawableAlertDialog(this);
        //开始选择的过场动画
        findViewById(R.id.start_drawable_anim_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //让进度条显示出来
                progress2.show();

                //10秒钟后停止动画
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress2.dismiss();
                    }
                }, 10000);
            }
        });
    }
}
