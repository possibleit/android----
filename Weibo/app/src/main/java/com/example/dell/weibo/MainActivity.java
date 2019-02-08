package com.example.dell.weibo;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dell.weibo.Util.Fulltask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity{
    public static SharedPreferences config;

    private static final String TAG = "weibo";
    private String json;//微博的json数据
    public static List<commit> commits= new ArrayList<>();//commit列表
    public static List<item> datas = new ArrayList<>();//listview的数据
    private ListView listView;
    private Toolbar toolbar;
    private TextView toolbarTv_1;
    private TextView write;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        config =  getSharedPreferences("config",MODE_PRIVATE);
        toolbar = findViewById(R.id.toolbar_1);
        toolbarTv_1 = findViewById(R.id.toolbarTv_1);
        write = findViewById(R.id.write);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbarTv_1.setOnClickListener(new View.OnClickListener() {//登陆界面
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, loginActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        write.setOnClickListener(new View.OnClickListener() {//发微博界面
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, writeActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
            new Thread(new Runnable() {
                @Override
                public void run() {//请求微博和评论
                    HashMap<String, String> parameter = new HashMap<>();
                    parameter.put("num", "5");
                    Message message = new Message();
                    message.what = 1;
                    message.obj = Fulltask.getResult("http://10.0.2.2:8001/", parameter, "get/");

                    handler.sendMessage(message);

                }
            }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//登陆成功是的返回
        switch (requestCode) {
            case 1:
                if(resultCode == RESULT_OK) {
                    //登陆成功，设置用户名
                    toolbarTv_1.setText(User.username);
                    toolbarTv_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, form_activity.class);
                            startActivityForResult(intent,2);
                        }
                    });
                    break;
                }
            case 2:
                if(data != null){
                    if(data.getStringExtra("result").equals("cancel")) {
                        toolbarTv_1.setText("登录");
                        toolbarTv_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, loginActivity.class);
                                startActivityForResult(intent, 1);
                            }
                        });
                        break;
                    }
                }
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    json = msg.obj.toString();
                    updateUI();
                    //Toast.makeText(MainActivity.this, json, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    public void updateUI(){
        datas.clear();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray js = jsonObject.getJSONArray("body");
            for (int i = js.length(); i != 0; i--) {
                JSONObject object = js.getJSONObject(i - 1);
                datas.add(new item(object.getString("username"),object.getString("time"),
                        object.getString("text"),object.getString("local"),
                        Integer.parseInt(object.getString("commitnum"))));
            }

        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            Log.e(TAG, e.toString());
        }
        listView = findViewById(R.id.listview_1);

        Collections.reverse(datas);
        adapter madapter = new adapter(this, R.layout.recycler_layout, datas);
        listView.setAdapter(madapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),datas.get(position).getName(),Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this,Detail_avtivity.class);
                i.putExtra("position",position);
                startActivity(i);
            }
        });
    }

}
