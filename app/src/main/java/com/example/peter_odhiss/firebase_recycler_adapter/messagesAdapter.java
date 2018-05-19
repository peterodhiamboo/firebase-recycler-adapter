package com.example.peter_odhiss.firebase_recycler_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class messagesAdapter extends RecyclerView.Adapter<messagesAdapter.messageHolder> {

    private Context context;
    private List<Messages> userDataList;

    public messagesAdapter(Context context, List<Messages> userDataList) {
        this.context = context;
        this.userDataList = userDataList;
    }

    @NonNull
    @Override
    public messagesAdapter.messageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.message_layout, parent, false);
        return new messageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull messagesAdapter.messageHolder holder, int position) {
        Messages userData = userDataList.get(position);
        holder.message.setText(userData.getMessage());
        holder.sender.setText(userData.getSender());
    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    class messageHolder extends RecyclerView.ViewHolder{
        TextView message, sender;

        public messageHolder(View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.messageText);
            sender = itemView.findViewById(R.id.messageSender);
        }
    }
}
