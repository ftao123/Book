package ft.com.ofo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // 帐号和密码
    private EditText edname;
    private TextView edpassword,edpassword2,textv;

    private Button btregister;
    private Button btlogin;
    // 创建SQLite数据库
    public static SQLiteDatabase db;
    public Cursor cursor;

    private String versionName;
    private int versionCode;

    public static final char numberAllChars[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1',
            '0', '9', '8', '7', '6', '5', '4', '3', '2'};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        db = SQLiteDatabase.openOrCreateDatabase(MainActivity.this.getFilesDir().toString()
                + "/test.dbs", null);
        Log.d("<<<<", MainActivity.this.getFilesDir().toString());
        // 跳转到注册界面
        btregister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RegistersActivity.class);
                startActivity(intent);
            }
        });
        btlogin.setOnClickListener(new LoginListener());

        //获得版本号
        PackageInfo info=null;
        PackageManager manager = this.getPackageManager();
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionName=info.versionName;
        versionCode=info.versionCode;
        textv.setText("versionName:"+versionName+"<<<<"+"versionCode:"+versionCode);


    }

    public void initViews() {
        textv= (TextView) findViewById(R.id.textv);
        edname = (EditText) findViewById(R.id.edname);
        edpassword = (TextView) findViewById(R.id.edpassword);
        edpassword2= (TextView) findViewById(R.id.edpassword2);
        btregister = (Button) findViewById(R.id.btregister);
        btlogin = (Button) findViewById(R.id.btlogin);
        edname.setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                return numberAllChars;
            }

            @Override
            public int getInputType() {
                return InputType.TYPE_TEXT_VARIATION_PASSWORD;
            }
        });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        db.close();
    }


    class LoginListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            String name = edname.getText().toString();
            String password = edpassword.getText().toString();
            if (name.equals("")) {
                // 弹出消息框
                new AlertDialog.Builder(MainActivity.this).setTitle("小主你又马虎啦！！！")
                        .setMessage("喵喵账号不能为空。").setPositiveButton("确定", null)
                        .show();
            } else {
                isUserinfo(name);
            }
        }

        // 判断输入的用户是否正确
        public Boolean isUserinfo(String name) {
            try {
                String str = "select * from tb_user where name=? ";
                cursor = db.rawQuery(str, new String[]{name});
                if (cursor.getCount() <= 0) {
                    new AlertDialog.Builder(MainActivity.this).setTitle("小主你又马虎啦！！！")
                            .setMessage("喵喵账号还没有喂养过，小主请先喂养喵喵。").setPositiveButton("确定", null)
                            .show();
                    return false;
                } else {
                    Log.d("<<<<", "进行数据查询");
                    String a;
                    if (cursor.moveToFirst()) {
                        a = cursor.getString(cursor.getColumnIndex("password"));
                        edpassword.setText(a);
                        MyCount mc = new MyCount();
                        mc.start();
                    }

                    cursor.close();
                    return true;
                }

            } catch (SQLiteException e) {
                createDb();
            }
            return false;
        }

    }

    // 创建数据库和用户表
    public void createDb() {
        db.execSQL("create table tb_user( name varchar(20) primary key,password varchar(20))");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private boolean doubleBackPressed = false;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (doubleBackPressed) {
                    db.close();
                    this.finish();
                    return true;
                }
                doubleBackPressed = true;
                Toast.makeText(this, "再按一次退喵喵助手", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackPressed = false;
                    }
                }, 2000);
            }
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    /* 定义一个倒计时的内部类 */
    class MyCount extends CountDownTimer {
        public MyCount() {
            super(15000, 1000);
        }

        //倒计时结束
        @Override
        public void onFinish() {
            edpassword2.setVisibility(View.GONE);
            edpassword.setText("");
        }

        //倒计时中
        @Override
        public void onTick(long millisUntilFinished) {
            edpassword2.setVisibility(View.VISIBLE);
            edpassword2.setText(millisUntilFinished / 1000 + "s");
        }
    }

}