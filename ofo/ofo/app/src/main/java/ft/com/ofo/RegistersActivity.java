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
import android.text.InputType;
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

    public static final char numberAllChars[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1',
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
//                        DialogInterface.OnClickListener ss = new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int which) {
//                                // 跳转到登录界面
//                                Intent in = new Intent();
//                                in.setClass(RegistersActivity.this,
//                                        MainActivity.class);
//                                startActivity(in);
//                            }
//                        };
                        new AlertDialog.Builder(RegistersActivity.this)
                                .setTitle("谢谢小主!!!").setMessage("喵喵吃饱了。")
                                .setPositiveButton("确定", null).show();

                    } else {
                        new AlertDialog.Builder(RegistersActivity.this)
                                .setTitle("数据保存失败").setMessage("请重新输入")
                                .setPositiveButton("确定", null);
                    }
                } else {
                    Log.d("<<<<","帐号密码不能为空");
                    new AlertDialog.Builder(RegistersActivity.this)
                            .setTitle("小主你又马虎啦！！！").setMessage("你不爱喵喵了吗？喵喵伤心了。")
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
                return numberAllChars;
            }

            @Override
            public int getInputType() {
                return  InputType.TYPE_TEXT_VARIATION_PASSWORD;
//                return android.text.InputType.TYPE_CLASS_PHONE;
            }
        });
        edpassword1.setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                return numberAllChars;
            }

            @Override
            public int getInputType() {
                return InputType.TYPE_TEXT_VARIATION_PASSWORD;
//                return android.text.InputType.TYPE_CLASS_PHONE;
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
     if (isUserinfo(name,db)){
         try {
             db.execSQL(str, new String[] { name, password });
             return true;
         } catch (Exception e) {
             main.createDb();
         }
         return false;
     }
        return  false;
    }



    // 判断数据是否已经存在
    public Boolean isUserinfo(String name,SQLiteDatabase db) {
        try{
            Log.d("<<<<","判断账号是否已经存在");
            String str="select * from tb_user where name=?  ";
            Cursor cursor = db.rawQuery(str, new String []{name});
            if(cursor.getCount()<=0){
              //这个说明账号和密码不存在
                Log.d("<<<<","账号和密码不存在");
                cursor.close();
                return true;
            }else {
                Log.d("<<<<","账号和密码已经存在");
                new AlertDialog.Builder(RegistersActivity.this)
                        .setTitle("小主你又马虎啦！！！").setMessage("账号已经存在了，要添加新的账号哦。")
                        .setPositiveButton("确定", null).show();
                cursor.close();
                return false;
                }
        }catch(SQLiteException e){

          }
        return false;
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_BACK){
//            RegistersActivity.this.finish();
//            Intent intent=new Intent();
//            intent.setClass(RegistersActivity.this,MainActivity.class);
//            startActivity(intent);
//            return  true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

}
