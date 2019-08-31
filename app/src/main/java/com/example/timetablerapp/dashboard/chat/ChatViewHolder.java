package com.example.timetablerapp.dashboard.chat;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.timetablerapp.R;

/**
 * 13/08/19 -bernard
 */
public class ChatViewHolder extends RecyclerView.ViewHolder {
    TextView txtDisplayName, txtLastComment;
    CardView cardView;

    public ChatViewHolder(View itemView) {
        super(itemView);

        txtDisplayName = itemView.findViewById(R.id.text_user_group);
        txtLastComment = itemView.findViewById(R.id.text_last_comment);
        cardView = itemView.findViewById(R.id.cardview_chat);
    }
}
