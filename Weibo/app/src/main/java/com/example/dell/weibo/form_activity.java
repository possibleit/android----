package com.example.dell.weibo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.weibo.Util.Fulltask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;

import static com.example.dell.weibo.MainActivity.datas;

public class form_activity extends AppCompatActivity {
    private String  json;
    private Button stared;
    private Button bestared;
    private EditText editText1;
    private EditText editText2;
    private TextView user;
    private Button cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_layout);

        stared =  findViewById(R.id.stared);
        bestared = findViewById(R.id.bestared);
        editText1 = findViewById(R.id.edittext1);
        editText2 =  findViewById(R.id.edittext2);
        editText1.setVisibility(View.INVISIBLE);
        editText2.setVisibility(View.INVISIBLE);
        cancel = findViewById(R.id.cancel);
        user = findViewById(R.id.user);
        user.setText(User.username);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result","cancel");
                setResult(0,intent);
                finish();
            }
        });


        stared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(json == null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {//请求微博和评论
                            HashMap<String, String> parameter = new HashMap<>();
                            parameter.put("name", User.username);

                            Message message = new Message();
                            message.what = 1;
                            message.obj = Fulltask.getResult("http://10.0.2.2:8001/", parameter, "frindlist/");
                            handler.sendMessage(message);

                        }
                    }).start();
                }else if(editText1.getVisibility()  == View.INVISIBLE)
                    editText1.setVisibility(View.VISIBLE);
                else
                    editText1.setVisibility(View.INVISIBLE);
            }
        });

        bestared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(json == null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {//请求微博和评论
                            HashMap<String, String> parameter = new HashMap<>();
                            parameter.put("name", User.username);

                            Message message = new Message();
                            message.what = 2;
                            message.obj = Fulltask.getResult("http://10.0.2.2:8001/", parameter, "frindlist/");
                            handler.sendMessage(message);

                        }
                    }).start();
                }else if(editText2.getVisibility()  == View.INVISIBLE)
                    editText2.setVisibility(View.VISIBLE);
                else
                    editText2.setVisibility(View.INVISIBLE);
            }
        });
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            json = msg.obj.toString();
            updateUI();
            switch (msg.what){
                case 1:
                    editText1.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    editText2.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };
    public void updateUI(){
        String s1  = "", s2 = "";
        datas.clear();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray js = jsonObject.getJSONArray("stared");
            for (int i = js.length(); i != 0; i--) {
                JSONObject object = js.getJSONObject(i - 1);
                s1 += object.getString("bestared") + "\n";

            }
            JSONArray js1 = jsonObject.getJSONArray("stared");
            for (int i = js1.length(); i != 0; i--) {
                JSONObject object = js.getJSONObject(i - 1);
                s2 += object.getString("stared") + "\n";

            }
            editText1.setText(s1);
            editText2.setText(s2);

        } catch (Exception e) {
            Toast.makeText(form_activity.this, e.toString(), Toast.LENGTH_LONG).show();
            //Log.e(TAG, e.toString());
        }
    }

}
