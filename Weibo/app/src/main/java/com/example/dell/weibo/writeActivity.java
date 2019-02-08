package com.example.dell.weibo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.weibo.Util.Fulltask;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

public class writeActivity extends AppCompatActivity {

    public File file;
    public static final String TAG = "WriteActivity";
    private EditText editText;
    private TextView button;
    private TextView ret;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writelayout);
        editText = findViewById(R.id.edt_3);
        button = findViewById(R.id.send);
        ret = findViewById(R.id.ret);

        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                            if(!editText.getText().toString().equals("")){
                                HashMap<String, String> parameter = new HashMap<>();
                                parameter.put("text", editText.getText().toString());
                                parameter.put("local", "local");
                                parameter.put("name", User.username);
                                parameter.put("time", getTime());
                                parameter.put("commitnum", "0");
                                String s = Fulltask.getResult("http://10.0.2.2:8001/", parameter, "sendweibo/");
                                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(writeActivity.this,"微博不能为空",Toast.LENGTH_SHORT).show();
                            }
                        Looper.loop();
                    }
                }).start();
            }
        });
    }

    public String getTime(){
        Date dt = new Date();
        return dt.toLocaleString();
    }


}
