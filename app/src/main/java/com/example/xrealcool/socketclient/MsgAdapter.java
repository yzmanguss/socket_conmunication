package com.example.xrealcool.socketclient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private List<Msg> msgList ;
    private String name;

    public MsgAdapter(List<Msg> msgList ) {
        this.msgList = msgList;
        //this.name = name;
    }
    /**
     * 用于找到控件的
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout left_layout;
        LinearLayout right_layout;
        TextView left_msg;
        TextView right_msg;
        TextView msg_name_left;
        TextView msg_name_right;
        public ViewHolder(View itemView) {
            super(itemView);

            left_layout = itemView.findViewById(R.id.left_layout);

            right_layout = itemView.findViewById(R.id.right_layout);

            left_msg = itemView.findViewById(R.id.msg_receive);

            right_msg = itemView.findViewById(R.id.msg_send);

            //msg_name_left = itemView.findViewById(R.id.msg_name_left);

            //msg_name_right = itemView.findViewById(R.id.msg_name_right);

        }
    }
    /**
     * 用于获取到View的
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MsgAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_items,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    /**
     * 用于绑定数据的
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MsgAdapter.ViewHolder holder, int position) {
        Msg msg = msgList.get(position);
        if (msg.getType() == Msg.TYPE_RECEIVE){
            //表示该为收到的消息，则显示左边的消息
            holder.left_layout.setVisibility(View.VISIBLE);
            holder.right_layout.setVisibility(View.GONE);
            holder.left_msg.setText(msg.getContent());
            //holder.msg_name_left.setText(name);
        }else {
            holder.right_layout.setVisibility(View.VISIBLE);
            holder.left_layout.setVisibility(View.GONE);
            holder.right_msg.setText(msg.getContent());
            //holder.msg_name_right.setText(name);
        }

    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
