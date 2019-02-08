package com.example.dell.weibo;

public class item {
    private String name;
    private String time;
    private String local;
    private String text;
    private int commitnum;

    public item(String name, String  time,String text,String local){
        this.time = time;
        this.name = name;
        this.local = local;
        this.text = text;
        commitnum = 0;
    }
    public item(String name, String  time,String text,String local, int commitnum){
        this.time = time;
        this.name = name;
        this.local = local;
        this.text = text;
        this.commitnum = commitnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCommitnum() {
        return commitnum;
    }

    public void setCommitnum(int commitnum) {
        this.commitnum = commitnum;
    }
}
