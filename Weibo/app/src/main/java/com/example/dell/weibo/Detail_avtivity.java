package com.example.dell.weibo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.weibo.Util.Fulltask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.dell.weibo.MainActivity.commits;
import static com.example.dell.weibo.MainActivity.datas;

public class Detail_avtivity extends AppCompatActivity {
    public static int position;
    private View.OnClickListener listener1, listener2;

    private String commit;
    private LinearLayout myLinearLayout;
    private Toolbar detail_toolbar;
    private TextView detail_1;
    private TextView detail_2;
    private TextView detail_3;
    private TextView detail_4;
    private TextView detail_5;
    private TextView  submit;
    private EditText commit_1;
    private TextView star;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        detail_toolbar = findViewById(R.id.detail_toolbar);
        detail_1 = findViewById(R.id.detail_1);
        detail_2 = findViewById(R.id.detail_2);
        detail_3 = findViewById(R.id.detail_3);
        detail_4 = findViewById(R.id.detail_4);
        detail_5 = findViewById(R.id.detail_5);
        star = findViewById(R.id.star);
        submit = findViewById(R.id.submit);
        commit_1  =  findViewById(R.id.commit);

        detail_toolbar.setTitle("");
        setSupportActionBar(detail_toolbar);

        Intent intent = getIntent();
        position = intent.getIntExtra("position",1);

        detail_1.setText(datas.get(position).getName());
        detail_2.setText(datas.get(position).getText());
        detail_3.setText(datas.get(position).getTime());
        detail_4.setText(datas.get(position).getLocal());
        detail_5.setText("Detail");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!User.islogin){//登陆
//                    Toast.makeText(getApplicationContext(),"请登陆之后",Toast.LENGTH_SHORT).show();
//                }
                //检测是否登陆
                if(!commit_1.getText().toString().equals("")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HashMap<String, String> parameter = new HashMap<>();

                            parameter.put("text", commit_1.getText().toString());
                            parameter.put("weiboname", detail_1.getText().toString());
                            parameter.put("commitname", User.username);
                            parameter.put("time", detail_3.getText().toString());



                            Message message1 = new Message();
                            message1.what = 3;
                            message1.obj = Fulltask.getResult("http://10.0.2.2:8001/", parameter, "sendcommit/");
                            handler.sendMessage(message1);


                        }
                    }).start();
                }else{
                    Toast.makeText(getApplicationContext(),"评论栏不能为空",Toast.LENGTH_SHORT).show();
                }

            }
        });

        star.setOnClickListener(listener2);
//        myLinearLayout = (LinearLayout) findViewById(R.id.committable);
//        if(datas.get(position).getCommitnum() != 0){
//
//            for(commit  c : commits){
//                //if(c.getTime().equals(datas.get(position).getTime()) || c.getWeiboname().equals(datas.get(position).getName())){
//                    TextView textview = new TextView(getApplicationContext());
//                    String s = c.getCommitname() + ":" + c.getText();
//                    textview.setText(s);
//                    myLinearLayout.addView(textview);
//                //}
//            }
//        }

        listener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HashMap<String, String> parameter = new HashMap<>();
                        parameter.put("stared", User.username);
                        parameter.put("bestared", detail_1.getText().toString());

                        Message message1 = new Message();
                        message1.what = 6;
                        message1.obj = Fulltask.getResult("http://10.0.2.2:8001/", parameter, "nostar/");
                        handler.sendMessage(message1);
                    }
                }).start();
            }
        };
        listener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检测是否登陆
                if(!User.islogin){//登陆
                    Toast.makeText(getApplicationContext(),"登录之后才能关注",Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HashMap<String, String> parameter = new HashMap<>();
                            parameter.put("stared", User.username);
                            parameter.put("bestared", detail_1.getText().toString());

                            Message message1 = new Message();
                            message1.what = 4;
                            message1.obj = Fulltask.getResult("http://10.0.2.2:8001/", parameter, "starone/");
                            handler.sendMessage(message1);
                        }
                    }).start();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(aBoolean == true) {
        new Thread(new Runnable() {
            @Override
            public void run() {//请求微博和评论
                HashMap<String, String> parameter = new HashMap<>();
                parameter.put("time", datas.get(position).getTime());
                parameter.put("name", datas.get(position).getName());

                Message message2 = new Message();
                message2.what = 2;
                message2.obj = Fulltask.getResult("http://10.0.2.2:8001/", parameter, "commit_get/");
                handler.sendMessage(message2);

                HashMap<String, String> parameter1 = new HashMap<>();
                parameter1.put("name1", User.username);
                parameter1.put("name2", detail_1.getText().toString());
                Message message3 = new Message();
                message3.what = 5;
                message3.obj = Fulltask.getResult("http://10.0.2.2:8001/", parameter1, "isstared/");
                handler.sendMessage(message3);

            }
        }).start();
//            aBoolean = false;
//        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:
                    Toast.makeText(getApplicationContext(),msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    if(msg.obj.toString().equals("评论发表成功")) {
                        TextView textview = new TextView(getApplicationContext());
                        String s = User.username + ":" + commit_1.getText().toString();
                        textview.setText(s);
                        myLinearLayout.addView(textview);
                    }
                    break;
                case 2:
                    commit = msg.obj.toString();
                    //Toast.makeText(MainActivity.this, commit, Toast.LENGTH_LONG).show();
                    commitsave();
                    updateUi();
                    break;
                case 4:
                    Toast.makeText(Detail_avtivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    star.setText("已关注");
                    star.setOnClickListener(listener1);
                    break;
                case 5:
                    //Toast.makeText(getApplicationContext(),msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    if(msg.obj.toString().equals("true")){
                        star.setText("已关注");
                        star.setOnClickListener(listener1);
                    }else {
                        star.setText("关注");
                        star.setOnClickListener(listener2);
                    }
                    break;
                case 6:
                    Toast.makeText(Detail_avtivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    star.setText("关注");
                    star.setOnClickListener(listener2);
                    break;
            }
        }
    };

    public void commitsave(){
        try {
            commits.clear();
            JSONObject jsonObject2 = new JSONObject(commit);
            JSONArray js2 = jsonObject2.getJSONArray("body");
            for (int i = js2.length(); i != 0; i--) {
                JSONObject object = js2.getJSONObject(i - 1);
                commits.add(new commit(object.getString("text"), object.getString("time"),
                        object.getString("commitname"), object.getString("weiboname")));
            }
            //Toast.makeText(getApplicationContext(),commits.toString(),Toast.LENGTH_SHORT).show();

        }catch (Exception e) {
            //Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            //Log.e(TAG, e.toString());
        }
    }

    public void updateUi(){
        myLinearLayout = (LinearLayout) findViewById(R.id.committable);
        if(datas.get(position).getCommitnum() != 0){

            for(commit  c : commits){
//                if(c.getTime().equals(datas.get(position).getTime()) || c.getWeiboname().equals(datas.get(position).getName())){
                    TextView textview = new TextView(getApplicationContext());
                    String s = c.getCommitname() + ":" + c.getText();
                    textview.setText(s);
                    textview.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                    myLinearLayout.addView(textview);
//                }
            }
        }
    }

}
