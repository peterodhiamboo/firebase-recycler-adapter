package com.example.peter_odhiss.firebase_recycler_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class messageHolder extends RecyclerView.ViewHolder{
        TextView message;
        TextView sender;

        public messageHolder(View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.messageText);
            sender = itemView.findViewById(R.id.messageSender);
        }
}