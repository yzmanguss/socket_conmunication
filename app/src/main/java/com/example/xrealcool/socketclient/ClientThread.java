package com.example.xrealcool.socketclient;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ClientThread extends Thread {


    public static Handler handler = new Handler();
    private OutputStream os;
    public static Socket socket;
    MainActivity mainActivity;

    public ClientThread(Context context) {
        this.mainActivity = (MainActivity) context;
    }

    @Override
    public void run() {

        try {

            socket = new Socket("192.168.1.43", 20000);
            socket.setKeepAlive(true);
            os = socket.getOutputStream();


            /**
             * 接收服务器返回信息（属于其他客户端的信息---不包括自己）
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String contentInfo = null;
                        while ((contentInfo = bufferedReader.readLine()) != null) {
                            Log.d("123", contentInfo);
                            Msg msg = new Msg(contentInfo, Msg.TYPE_RECEIVE);
                            mainActivity.msgList.add(msg);
                            mainActivity.updateView();
                        }
                    } catch (IOException e) {
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
                                os = socket.getOutputStream();
                                os.write((msg.obj.toString() + "\n").getBytes("utf-8"));
                            } catch (IOException e) {
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

