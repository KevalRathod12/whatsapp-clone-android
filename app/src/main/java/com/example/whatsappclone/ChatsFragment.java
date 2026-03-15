package com.example.whatsappclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * ChatsFragment — CHATS tab no content
 * Firebase /users/ thi badha users load kare che ane RecyclerView ma show kare
 * User par tap karo to ChatActivity khule
 */
public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private List<ChatModel> chatList;

    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private String currentUserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        recyclerView = view.findViewById(R.id.recyclerChatsFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        chatList = new ArrayList<>();
        adapter = new ChatAdapter(chatList, (userId, userName) -> {
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("receiverId", userId);
            intent.putExtra("receiverName", userName);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        loadUsers();
        return view;
    }

    private void loadUsers() {
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                chatList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    UserModel user = snap.getValue(UserModel.class);
                    if (user != null && !user.uid.equals(currentUserId)) {
                        chatList.add(new ChatModel(user.name, "Tap to chat", "Now", 0, user.uid));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
