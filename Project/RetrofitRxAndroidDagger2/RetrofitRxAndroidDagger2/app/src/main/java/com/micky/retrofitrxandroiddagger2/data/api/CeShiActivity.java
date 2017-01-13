package com.micky.retrofitrxandroiddagger2.data.api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.micky.retrofitrxandroiddagger2.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class CeShiActivity extends AppCompatActivity {

    private static final String TAG = "CeShiActivity";
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ce_shi);

        textView1 = (TextView) findViewById(R.id.ceShiRxjava1);
        textView2 = (TextView) findViewById(R.id.ceShiRxjava2);
        textView3 = (TextView) findViewById(R.id.ceShiRxjava3);



        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(i);
                }
                //结束
                subscriber.onCompleted();
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, e.toString());
            }

            @Override
            public void onNext(Integer integer) {
                Log.i(TAG, integer.toString());
            }
        });








//        Observable observable=Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber< ? super String> subscriber) {
//                if (!subscriber.isUnsubscribed()) {
//                    subscriber.onNext("hello");
//                    subscriber.onNext("my");
//                    subscriber.onNext("fantao");
//                    subscriber.onCompleted();
//                }
//            }
//        }) ;
//        observable.subscribeOn(Schedulers.immediate())
//           .observeOn(AndroidSchedulers.mainThread())
//           .subscribe(new Subscriber<String>() {
//            @Override
//            public void onCompleted() {
//            Log.d(TAG,"onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG,"onError");
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.d(TAG,"onNext");
//                Log.d(TAG,s);
//                //textView1.setText(s);
//                Log.d(TAG,s+"0000000");
//
//
//
//            }
//        });
//    }


//    String[] names ={"a","b","c"} ;
//
//    Observable observable1 = Observable.from(names);
//
//    observable1.subscribe(new Action1<String>() {
//        @Override
//        public void call(String name) {
//            Log.d(TAG, name);
//        }
//    });


    }

}
