package com.example.dell.weibo;

public class commit {
    private String text;
    private String weiboname;
    private String commitname;
    private String time;
    public commit(String text, String  time,String commitname,String weiboname){
        this.time = time;
        this.commitname = commitname;
        this.weiboname = weiboname;
        this.text = text;
    }

    public String getCommitname() {
        return commitname;
    }

    public void setCommitname(String commitname) {
        this.commitname = commitname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeiboname() {
        return weiboname;
    }

    public void setWeiboname(String weiboname) {
        this.weiboname = weiboname;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
