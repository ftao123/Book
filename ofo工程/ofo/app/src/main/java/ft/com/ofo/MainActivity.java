package ft.com.ofo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
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
    private TextView edpassword;

    private Button btregister;
    private Button btlogin;
    // 创建SQLite数据库
    public static SQLiteDatabase db;
    public Cursor cursor;

    public static final char[] numberChars = new char[]{'1',
            '0', '9', '8', '7', '6', '5', '4', '3', '2'};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        db = SQLiteDatabase.openOrCreateDatabase(MainActivity.this.getFilesDir().toString()
                + "/test.dbs", null);
        Log.d("<<<<",MainActivity.this.getFilesDir().toString());
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
    }

    public void initViews() {
        edname = (EditText) findViewById(R.id.edname);
        edpassword = (TextView) findViewById(R.id.edpassword);
        btregister = (Button) findViewById(R.id.btregister);
        btlogin = (Button) findViewById(R.id.btlogin);
        edname.setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                return numberChars;
            }

            @Override
            public int getInputType() {
                return android.text.InputType.TYPE_CLASS_PHONE;
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
            if (name.equals("") ) {
                // 弹出消息框
                new AlertDialog.Builder(MainActivity.this).setTitle("错误")
                        .setMessage("车牌号不能空").setPositiveButton("确定", null)
                        .show();
            } else {
                isUserinfo(name);
            }
        }

        // 判断输入的用户是否正确
        public Boolean isUserinfo(String name) {
            try{
                String str="select * from tb_user where name=? ";
                 cursor = db.rawQuery(str, new String []{name});
                if(cursor.getCount()<=0){
                    new AlertDialog.Builder(MainActivity.this).setTitle("错误")
                            .setMessage("车牌号错误！").setPositiveButton("确定", null)
                            .show();
                    return false;
                }else{
                    Log.d("<<<<","进行数据查询");
                    String a;
                    if (cursor.moveToFirst())
                    {
                        a=cursor.getString(cursor.getColumnIndex("password"));
                        int b=Integer.parseInt(a);
                        edpassword.setText(b+"");


                    }

                    cursor.close();
                    return true;
                }

            }catch(SQLiteException e){
                createDb();
            }
            return false;
        }

    }
    // 创建数据库和用户表
    public void createDb() {
        db.execSQL("create table tb_user( name varchar(11) primary key,password varchar(11))");
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
        if ( event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
            if ( event.getAction() == KeyEvent.ACTION_DOWN ) {
                if ( doubleBackPressed ) {
                    this.finish();
                    return true;
                }
                doubleBackPressed = true;
                Toast.makeText(this, "再按一次退出门店助手", Toast.LENGTH_SHORT).show();
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

}