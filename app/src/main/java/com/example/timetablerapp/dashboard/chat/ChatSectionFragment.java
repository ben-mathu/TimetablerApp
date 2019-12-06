package com.example.timetablerapp.dashboard.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetablerapp.R;

/**
 * 13/08/19 -bernard
 */
public class ChatSectionFragment extends Fragment {
    private static final String TAG = ChatSectionFragment.class.getSimpleName();

    private RecyclerView recyclerView;

    private ChatAdapter chatAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_section, container, false);

        recyclerView = view.findViewById(R.id.recycler_chat);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        chatAdapter = new ChatAdapter(getActivity());
        recyclerView.setAdapter(chatAdapter);

        Log.d(TAG, "onCreateView: created");
        return view;
    }
}
