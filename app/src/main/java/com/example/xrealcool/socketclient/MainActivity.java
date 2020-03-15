package com.example.xrealcool.socketclient;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MsgAdapter msgAdapter;
    private Button send;
    private String name = "游客 1";
    private EditText input_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initData();

        send = findViewById(R.id.send);
        inputText = findViewById(R.id.input_text);
        recyclerView = findViewById(R.id.msg_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        input_name = new EditText(MainActivity.this);
        ClientThread clientThread = new ClientThread(this);
        showInputName();

        name = input_name.getText().toString();
        //Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
        Log.d("123",name);
        clientThread.setName(name);
        clientThread.start();
        msgAdapter = new MsgAdapter(msgList);
        recyclerView.setAdapter(msgAdapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* String content = inputText.getText().toString();
                if (!content.equals("")){
                    //创建Msg
                    Msg msg = new Msg(content,Msg.TYPE_SEND);
                    //把它加入到msgList中去，准备显示
                    msgList.add(msg);
                    //提示Adapter有新数据插入
                    msgAdapter.notifyItemInserted(msgList.size()-1);
                    //将recyclerView定位到最后一行
                    recyclerView.scrollToPosition(msgList.size()-1);
                    //清空编辑框
                    inputText.setText("");
                }*/
                String content = inputText.getText().toString();
                Message msg = new Message();
                msg.what = Msg.TYPE_SEND;
                msg.obj = content;
                ClientThread.handler.sendMessage(msg);
                Msg msg1 = new Msg(content, Msg.TYPE_SEND);
                msgList.add(msg1);
                updateView();
            }
        });


    }

    private void showInputName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("请输入你的昵称")
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setView(input_name)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        name = input_name.getText().toString();
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();

    }

    /**
     * 更新聊天列表
     */
    public void updateView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //提示Adapter有新数据插入
                msgAdapter.notifyItemInserted(msgList.size() - 1);
                //将recyclerView定位到最后一行
                recyclerView.scrollToPosition(msgList.size() - 1);

                //清空编辑框
                inputText.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            ClientThread.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
