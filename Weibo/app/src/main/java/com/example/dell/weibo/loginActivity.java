package com.example.dell.weibo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.weibo.Util.Fulltask;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import static com.example.dell.weibo.MainActivity.config;

public class loginActivity extends AppCompatActivity{
    private static final String TAG = "loginActivity";

    private static final int Success = 1;
    private static final int Fail = 0;
    private EditText zhanghao;
    private EditText password;
    private CheckBox checkbox_1;
    private TextView tv_login;
    private Button login;
    private ImageView img;
    private ImageView img1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        zhanghao = findViewById(R.id.zhanghao);
        password = findViewById(R.id.password);
        checkbox_1 = findViewById(R.id.checkbox_1);
        tv_login = findViewById(R.id.tv_login);
        login = findViewById(R.id.bt_1);
        img = findViewById(R.id.img);
        img1 = findViewById(R.id.img2);



        if(config.getBoolean("ischecked",false)  ==  true){
            checkbox_1.setChecked(true);
            zhanghao.setText(config.getString("username",""));
            password.setText(config.getString("password",""));
        }

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhanghao.setText("");
            }
        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setText("");
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(zhanghao.getText().toString().trim().equals("") || password.getText().toString().trim().equals("")) {
                    Toast.makeText(loginActivity.this,"账号或密码不能为空",Toast.LENGTH_LONG).show();
                }else{
                    save();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HashMap<String, String> parameter = new HashMap<>();
                            parameter.put("name", zhanghao.getText().toString());
                            parameter.put("password", password.getText().toString());
                            Message message = new Message();
                            message.what = 1;
                            //注册
                            message.obj = Fulltask.getResult("http://10.0.2.2:8001/", parameter, "login/");
                            handler.sendMessage(message);
                        }
                    }).start();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(zhanghao.getText().toString().trim().equals("") || password.getText().toString().trim().equals("")) {
                    Toast.makeText(loginActivity.this,"账号或密码不能为空",Toast.LENGTH_LONG).show();
                }else {
                    save();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HashMap<String, String> parameter = new HashMap<>();
                            HashMap<String, String> parameter1 = new HashMap<>();
                            parameter.put("name", zhanghao.getText().toString());
                            parameter.put("password", password.getText().toString());
//                        parameter.put("num", "5");
                            Message message = new Message();
                            message.what = 1;
                            //登陆
                            message.obj = Fulltask.getResult("http://10.0.2.2:8001/", parameter, "register/");
                            //message.obj = Fulltask.getResult1("http://10.0.2.2:8001/get/?num=5",parameter1);
                            handler.sendMessage(message);
                        }
                    }).start();
                }
            }
        });
    }

    private void setAnswer(Boolean isLogin){
        Intent data = new Intent();
        data.putExtra("IsLogin",isLogin);
        setResult(RESULT_OK,data);
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case Success:
                    if(msg.obj.toString().equals("登陆成功")) {
                        Toast.makeText(loginActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
                        setAnswer(true);
                        User.username = zhanghao.getText().toString();
                        User.password = password.getText().toString();
                        User.islogin = true;
                        finish();
                    }else{
                        Toast.makeText(loginActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case Fail:
                    Toast.makeText(loginActivity.this,"无网络",Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(loginActivity.this,"注册失败",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    public void save(){
        SharedPreferences.Editor  editor  = config.edit();
        editor.putBoolean("ischecked",checkbox_1.isChecked());
        if(checkbox_1.isChecked()){
            editor.putString("username",zhanghao.getText().toString());
            editor.putString("password",password.getText().toString());
        }
        editor.commit();
    }

}
