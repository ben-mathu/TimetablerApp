package com.example.timetablerapp.timetable.chat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetablerapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 13/08/19 -bernard
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    private static final String TAG = ChatAdapter.class.getSimpleName();

    List<String> displayName = new ArrayList<>();
    List<String> lastComments = new ArrayList<>();

    private Context context;

    public ChatAdapter(Context context) {
        this.context = context;

        displayName.add("Computer science");
        displayName.add("Commerce");

        lastComments.add("Code to find altitude");
        lastComments.add("How to find the compound interest");
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat_item, null, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.txtDisplayName.setText(displayName.get(position));
        holder.txtLastComment.setText(lastComments.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, OpenChatActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return displayName.size();
    }
}
