package com.example.whatsappclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * ChatAdapter — RecyclerView adapter for the chat list in MainActivity
 *
 * - Drek chat row ne item_chat.xml sathe connect kare che
 * - Click listener thi ChatActivity open thay che
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ChatModel> chatList;
    private OnChatClickListener listener;

    /**
     * OnChatClickListener — interface for click callback
     * MainActivity ma implement kari ne ChatActivity open karvanu thay
     */
    public interface OnChatClickListener {
        void onChatClick(String userId, String userName);
    }

    // Constructor — list + listener leve
    public ChatAdapter(List<ChatModel> chatList, OnChatClickListener listener) {
        this.chatList = chatList;
        this.listener = listener;
    }

    // item_chat.xml thi ViewHolder banave
    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(view);
    }

    // Chat data ViewHolder ma set kare
    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatModel chat = chatList.get(position);

        holder.name.setText(chat.name);
        holder.message.setText(chat.message);
        holder.time.setText(chat.time);

        // Unread badge show/hide
        if (chat.unreadCount == 0) {
            holder.badge.setVisibility(View.GONE);
        } else {
            holder.badge.setVisibility(View.VISIBLE);
            holder.badge.setText(String.valueOf(chat.unreadCount));
        }

        // Row par click → ChatActivity open karo
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onChatClick(chat.userId, chat.name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    // ViewHolder — item_chat.xml ना views hold kare che
    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView name, message, time, badge;

        public ChatViewHolder(View itemView) {
            super(itemView);
            name    = itemView.findViewById(R.id.txtName);
            message = itemView.findViewById(R.id.txtMessage);
            time    = itemView.findViewById(R.id.txtTime);
            badge   = itemView.findViewById(R.id.badge);
        }
    }
}
