package com.example.xrealcool.socketclient;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientThread extends Thread {


    public static Handler handler = new Handler();
    private OutputStream os;
    public static Socket socket;
    MainActivity mainActivity;
    public static String name;
    private DataOutputStream dos;

    public ClientThread(Context context) {
        this.mainActivity = (MainActivity) context;
    }

    @Override
    public void run() {

        try {

            socket = new Socket("192.168.1.103", 20010);
            //socket = new Socket("192.168.43.143", 20000);
            //os = socket.getOutputStream();
            dos = new DataOutputStream(socket.getOutputStream());
            //os.write(mainActivity.clientThread.getName().getBytes("utf-8"));

            /**
             * 接收服务器返回信息（属于其他客户端的信息---不包括自己）
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        DataInputStream dis = new DataInputStream(socket.getInputStream());
                        String contentInfo = null;
                        while ((contentInfo = dis.readUTF()) != null) {
                            Log.d("123", contentInfo.toString());
                            JSONTokener jsonTokener = new JSONTokener(contentInfo);
                            JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
                            String name = jsonObject.getString("name");
                            String info = jsonObject.getString("info");
                            Msg msg = new Msg(info, Msg.TYPE_RECEIVE, name);
                            mainActivity.msgList.add(msg);
                            mainActivity.updateView();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            /**
             * 把自己输入的消息发送给服务器，服务器进行转发
             */
            //socket.close();
            Looper.prepare();
            handler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    switch (msg.what) {
                        case Msg.TYPE_SEND:
                            try {
                                //os.write((mainActivity.clientThread.getName()+"\n"+ msg.obj.toString() + "\n").getBytes("utf-8"));
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("name", mainActivity.clientThread.getName());
                                jsonObject.put("info", msg.obj.toString());
                                String data = jsonObject.toString();
                                dos.writeUTF(data);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            };
            Looper.loop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

