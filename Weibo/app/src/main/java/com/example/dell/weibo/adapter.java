package com.example.dell.weibo;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import static com.example.dell.weibo.MainActivity.commits;

public class adapter extends ArrayAdapter<item> {
    int commitnum;
    int resourceId;
    public adapter(Context context, int resource, List<item> objects){
        super(context,resource,objects);
        resourceId = resource;//记录布局资源id
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        item mitem = getItem(position);
        View view;
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            view = View.inflate(getContext(),resourceId,null);
            viewHolder.textView_1 = view.findViewById(R.id.textview_1);
            viewHolder.textView_2 = view.findViewById(R.id.textview_2);
            viewHolder.textView_3 = view.findViewById(R.id.local);
            viewHolder.textView_4 = view.findViewById(R.id.time);
//            viewHolder.commit = view.findViewById(R.id.commit);
//            viewHolder.submit = view.findViewById(R.id.submit);
//
//            viewHolder.submit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        commitnum =  mitem.getCommitnum();
        if(commitnum > 0){


//            LinearLayout myLinerLayout = (LinearLayout) view.findViewById(R.id.commitTable);
//            for(commit  c : commits){
//                if(c.getTime().equals(mitem.getTime()) || c.getWeiboname().equals(mitem.getName())){
//                    TextView textview = new TextView(getContext());
//                    textview.setText("11");
//                    myLinerLayout.addView(textview);
//                }
//            }
//            while(commitnum > 0) {
//                TextView textview = new TextView(getContext());
//                //commit setText
//                textview.setText("11");
//                myLinerLayout.addView(textview);
//                commitnum--;
//            }
        }



        viewHolder.textView_1.setText(mitem.getName());
        viewHolder.textView_2.setText(mitem.getText());
        viewHolder.textView_3.setText(mitem.getLocal());
        viewHolder.textView_4.setText(mitem.getTime());
        return view;
    }
    static class ViewHolder{
        TextView textView_1;
        TextView textView_2;
        TextView textView_3;
        TextView textView_4;
//        EditText commit;
//        TextView submit;
//        LinearLayout commit_table;
//        TextView commitview;
    }
}
