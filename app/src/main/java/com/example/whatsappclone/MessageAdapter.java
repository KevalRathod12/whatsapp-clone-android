package com.example.whatsappclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * MessageAdapter — RecyclerView adapter for the chat messages in ChatActivity
 *
 * Do view types use kare che:
 *   TYPE_SENT     = 1 → item_message_sent.xml   (right side, green bubble)
 *   TYPE_RECEIVED = 2 → item_message_received.xml (left side, white bubble)
 *
 * Current user na messages TYPE_SENT, bija badha TYPE_RECEIVED.
 */
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_SENT     = 1;
    private static final int TYPE_RECEIVED = 2;

    private List<MessageModel> messageList;
    private String currentUserId; // Logged-in user nu UID

    public MessageAdapter(List<MessageModel> messageList, String currentUserId) {
        this.messageList   = messageList;
        this.currentUserId = currentUserId;
    }

    /**
     * getItemViewType() — Message sent che ke received che e decide kare che
     * senderId = currentUserId → TYPE_SENT, bija badha → TYPE_RECEIVED
     */
    @Override
    public int getItemViewType(int position) {
        MessageModel msg = messageList.get(position);
        if (msg.senderId.equals(currentUserId)) {
            return TYPE_SENT;
        } else {
            return TYPE_RECEIVED;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == TYPE_SENT) {
            // Sent message layout inflate karo
            View view = inflater.inflate(R.layout.item_message_sent, parent, false);
            return new SentViewHolder(view);
        } else {
            // Received message layout inflate karo
            View view = inflater.inflate(R.layout.item_message_received, parent, false);
            return new ReceivedViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageModel msg = messageList.get(position);
        String formattedTime = formatTime(msg.timestamp);

        if (holder instanceof SentViewHolder) {
            SentViewHolder sentHolder = (SentViewHolder) holder;
            sentHolder.tvMessage.setText(msg.message);
            sentHolder.tvTime.setText(formattedTime);
        } else if (holder instanceof ReceivedViewHolder) {
            ReceivedViewHolder receivedHolder = (ReceivedViewHolder) holder;
            receivedHolder.tvMessage.setText(msg.message);
            receivedHolder.tvTime.setText(formattedTime);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    /**
     * formatTime() — timestamp (milliseconds) ne "hh:mm AM/PM" format ma convert kare
     */
    private String formatTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    // --- ViewHolder for SENT messages ---
    static class SentViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvTime;

        public SentViewHolder(View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessageSent);
            tvTime    = itemView.findViewById(R.id.tvTimeSent);
        }
    }

    // --- ViewHolder for RECEIVED messages ---
    static class ReceivedViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvTime;

        public ReceivedViewHolder(View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessageReceived);
            tvTime    = itemView.findViewById(R.id.tvTimeReceived);
        }
    }
}
