package ft.com.ofo;

/**
 * Created by taotaofan on 2016/12/1.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegistersActivity extends Activity {

    private EditText edname1;
    private EditText edpassword1;
    private Button btregister1;
    SQLiteDatabase db;

    public static final char[] numberChars = new char[]{'1',
            '0', '9', '8', '7', '6', '5', '4', '3', '2'};

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initView();
        btregister1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = edname1.getText().toString();
                String password = edpassword1.getText().toString();
                if (!(name.equals("") || password.equals(""))) {
                    if (addUser(name, password)) {
                        DialogInterface.OnClickListener ss = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // 跳转到登录界面
                                Intent in = new Intent();
                                in.setClass(RegistersActivity.this,
                                        MainActivity.class);
                                startActivity(in);
                            }
                        };
                        new AlertDialog.Builder(RegistersActivity.this)
                                .setTitle("好样的").setMessage("数据保存成功")
                                .setPositiveButton("确定", ss).show();

                    } else {
                        new AlertDialog.Builder(RegistersActivity.this)
                                .setTitle("数据保存失败").setMessage("请重新输入")
                                .setPositiveButton("确定", null);
                    }
                } else {
                    Log.d("<<<<","帐号密码不能为空");
                    new AlertDialog.Builder(RegistersActivity.this)
                            .setTitle("帐号密码不能为空").setMessage("帐号密码不能为空")
                            .setPositiveButton("确定", null).show();
                }

            }
        });

    }

    private void initView() {
        edname1 = (EditText) findViewById(R.id.edname1);
        edpassword1 = (EditText) findViewById(R.id.edpassword1);
        btregister1 = (Button) findViewById(R.id.btregister1);
        edname1.setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                return numberChars;
            }

            @Override
            public int getInputType() {
                return android.text.InputType.TYPE_CLASS_PHONE;
            }
        });
        edpassword1.setKeyListener(new NumberKeyListener() {
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

    // 添加用户
    public Boolean addUser(String name, String password) {
        String str = "insert into tb_user values(?,?) ";
        MainActivity main = new MainActivity();
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()
                + "/test.dbs", null);
        main.db = db;
     if (isUserinfo(name,password,db)){
         try {
             db.execSQL(str, new String[] { name, password });
             db.close();
             return true;
         } catch (Exception e) {
             main.createDb();
         }
         return false;
     }
        return  false;
    }



    // 判断数据是否已经存在
    public Boolean isUserinfo(String name,String password,SQLiteDatabase db) {
        try{
            Log.d("<<<<","判断账号是否已经存在");
            String str="select * from tb_user where name=? and password=? ";
            Cursor cursor = db.rawQuery(str, new String []{name,password});
            if(cursor.getCount()<=0){
              //这个说明账号和密码不存在
                Log.d("<<<<","账号和密码不存在");
                return true;
            }else {
                Log.d("<<<<","账号和密码已经存在");
                new AlertDialog.Builder(RegistersActivity.this)
                        .setTitle("帐号已经存在").setMessage("请添加新的账号")
                        .setPositiveButton("确定", null).show();
                return false;
                }
        }catch(SQLiteException e){

          }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            RegistersActivity.this.finish();
            Intent intent=new Intent();
            intent.setClass(RegistersActivity.this,MainActivity.class);
            startActivity(intent);
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
