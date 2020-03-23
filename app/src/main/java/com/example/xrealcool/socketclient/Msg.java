package com.example.xrealcool.socketclient;

public class Msg {

    public static final int TYPE_RECEIVE = 0;

    public static final int TYPE_SEND = 1;

    private String content;

    private String name;

    private int type;

    public Msg(String content, int type,String name) {
        this.content = content;
        this.type = type;
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
