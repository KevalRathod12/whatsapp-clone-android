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
 * StatusFragment — STATUS tab no content
 * Dummy/static status data show kare che (college demo mate)
 *
 * Pehelu item = "My Status" — current user no
 * Baki = contacts na status
 */
public class StatusFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerStatus);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        List<StatusModel> statusList = new ArrayList<>();

        // "My Status" — pehelo item
        statusList.add(new StatusModel("My Status", "Add to my status", true));

        // Other contacts' statuses
        statusList.add(new StatusModel("Rahul Sharma",  "Today, 10:45 AM", false));
        statusList.add(new StatusModel("Priya Patel",   "Today, 9:30 AM",  false));
        statusList.add(new StatusModel("Amit Joshi",    "Today, 8:15 AM",  false));
        statusList.add(new StatusModel("Sneha Modi",    "Yesterday",        false));
        statusList.add(new StatusModel("Ravi Kumar",    "Yesterday",        false));
        statusList.add(new StatusModel("Meera Shah",    "2 days ago",       false));

        StatusAdapter adapter = new StatusAdapter(statusList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
