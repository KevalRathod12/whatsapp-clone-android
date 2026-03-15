package com.example.whatsappclone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * CallsFragment — CALLS tab no content
 * Dummy call history data show kare che
 * Missed = red, Incoming = normal, Outgoing = green
 */
public class CallsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calls, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerCalls);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        List<CallModel> callList = new ArrayList<>();

        // Dummy call history — name, time, callType
        callList.add(new CallModel("Rahul Sharma",  "Today, 10:45 AM",    "Incoming"));
        callList.add(new CallModel("Priya Patel",   "Today, 9:10 AM",     "Missed"));
        callList.add(new CallModel("Amit Joshi",    "Today, 8:00 AM",     "Outgoing"));
        callList.add(new CallModel("Mom",           "Yesterday, 8:30 PM", "Incoming"));
        callList.add(new CallModel("Sneha Modi",    "Yesterday, 3:00 PM", "Missed"));
        callList.add(new CallModel("Ravi Kumar",    "Yesterday, 12:00 PM","Outgoing"));
        callList.add(new CallModel("Meera Shah",    "Mon, 7:45 PM",       "Incoming"));
        callList.add(new CallModel("College Group", "Sun, 5:30 PM",       "Outgoing"));

        CallAdapter adapter = new CallAdapter(callList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
