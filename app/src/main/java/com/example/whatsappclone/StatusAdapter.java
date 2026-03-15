package com.example.whatsappclone;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * StatusAdapter — Status tab na RecyclerView mate
 * item_status.xml inflate kare che
 */
public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder> {

    private List<StatusModel> statusList;

    // Avatar colors — drek user ne alag colour milse
    private static final int[] AVATAR_COLORS = {
            Color.parseColor("#25D366"), // green
            Color.parseColor("#075E54"), // dark green
            Color.parseColor("#128C7E"), // teal
            Color.parseColor("#34B7F1"), // blue
            Color.parseColor("#FF6B35"), // orange
            Color.parseColor("#7B68EE"), // purple
    };

    public StatusAdapter(List<StatusModel> statusList) {
        this.statusList = statusList;
    }

    @Override
    public StatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_status, parent, false);
        return new StatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StatusViewHolder holder, int position) {
        StatusModel status = statusList.get(position);
        holder.tvName.setText(status.name);
        holder.tvTime.setText(status.time);

        // Avatar ne position based alag colour aapvo
        int color = AVATAR_COLORS[position % AVATAR_COLORS.length];
        holder.imgAvatar.setBackgroundColor(color);

        // My Status mate camera icon, bija mate person icon
        if (status.isMyStatus) {
            holder.imgAvatar.setImageResource(android.R.drawable.ic_menu_camera);
        } else {
            holder.imgAvatar.setImageResource(android.R.drawable.ic_menu_myplaces);
        }
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    static class StatusViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvName, tvTime;

        public StatusViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgStatusAvatar);
            tvName    = itemView.findViewById(R.id.tvStatusName);
            tvTime    = itemView.findViewById(R.id.tvStatusTime);
        }
    }
}
