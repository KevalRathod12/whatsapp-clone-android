package com.example.whatsappclone;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * ChatActivity — Real-time chat conversation screen
 *
 * - Banne users (sender + receiver) vache unique chatId banave che
 * - Messages Firebase /messages/{chatId}/ ma save thay che
 * - Real-time listener thi nava messages auto-update thay che
 *
 * Operation:
 *   1. MainActivity thi receiverId ane receiverName receive kare che (Intent extra)
 *   2. Chat ID = sender UID + "_" + receiver UID (alphabetically sorted for consistency)
 *   3. Messages RecyclerView ma show thay che
 *   4. Send button → message Firebase ma save kare
 */
public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerMessages;
    private EditText etMessage;
    private ImageButton btnSend;
    private MessageAdapter messageAdapter;
    private List<MessageModel> messageList;

    private FirebaseAuth mAuth;
    private DatabaseReference messagesRef;

    private String currentUserId;
    private String receiverId;
    private String receiverName;
    private String chatId; // Unique ID for this conversation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // --- Receive data from Intent ---
        receiverId   = getIntent().getStringExtra("receiverId");
        receiverName = getIntent().getStringExtra("receiverName");

        // --- Firebase setup ---
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        // Unique chatId: banne UIDs sort karine join karo jethike same chat node use thay
        // Example: "abc123_xyz789" — same for both users in the conversation
        if (currentUserId.compareTo(receiverId) < 0) {
            chatId = currentUserId + "_" + receiverId;
        } else {
            chatId = receiverId + "_" + currentUserId;
        }

        messagesRef = FirebaseDatabase.getInstance().getReference("messages").child(chatId);

        // --- Toolbar setup ---
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(receiverName); // Show receiver's name as title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish()); // Back button

        // --- RecyclerView setup ---
        recyclerMessages = findViewById(R.id.recyclerMessages);
        recyclerMessages.setLayoutManager(new LinearLayoutManager(this));

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList, currentUserId);
        recyclerMessages.setAdapter(messageAdapter);

        // --- UI Elements ---
        etMessage = findViewById(R.id.etMessage);
        btnSend   = findViewById(R.id.btnSend);

        // --- Send button click ---
        btnSend.setOnClickListener(v -> sendMessage());

        // --- Load messages in real-time ---
        loadMessages();
    }

    /**
     * sendMessage() — Firebase ma message save kare che
     */
    private void sendMessage() {
        String messageText = etMessage.getText().toString().trim();

        if (TextUtils.isEmpty(messageText)) {
            return; // Empty message na moklo
        }

        // New MessageModel banavo
        MessageModel message = new MessageModel(
                currentUserId,
                messageText,
                System.currentTimeMillis() // Current timestamp
        );

        // Firebase ma push karo — auto-unique ID generate thay che
        messagesRef.push().setValue(message)
                .addOnSuccessListener(aVoid -> {
                    etMessage.setText(""); // Input clear karo after sending
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to send: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * loadMessages() — Real-time listener — nava messages auto-update thay che
     */
    private void loadMessages() {
        messagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                messageList.clear();

                for (DataSnapshot msgSnap : snapshot.getChildren()) {
                    MessageModel msg = msgSnap.getValue(MessageModel.class);
                    if (msg != null) {
                        messageList.add(msg);
                    }
                }

                messageAdapter.notifyDataSetChanged();
                // Latest message par scroll karo
                if (!messageList.isEmpty()) {
                    recyclerMessages.scrollToPosition(messageList.size() - 1);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ChatActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
