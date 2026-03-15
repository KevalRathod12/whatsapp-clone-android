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
 * CallAdapter — Calls tab na RecyclerView mate
 * item_call.xml inflate kare che
 * Missed calls -> red text, others -> normal
 */
public class CallAdapter extends RecyclerView.Adapter<CallAdapter.CallViewHolder> {

    private List<CallModel> callList;

    private static final int[] AVATAR_COLORS = {
            Color.parseColor("#25D366"),
            Color.parseColor("#128C7E"),
            Color.parseColor("#34B7F1"),
            Color.parseColor("#FF6B35"),
            Color.parseColor("#7B68EE"),
            Color.parseColor("#075E54"),
    };

    public CallAdapter(List<CallModel> callList) {
        this.callList = callList;
    }

    @Override
    public CallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_call, parent, false);
        return new CallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CallViewHolder holder, int position) {
        CallModel call = callList.get(position);

        holder.tvName.setText(call.name);
        holder.tvCallType.setText(call.callType);
        holder.tvTime.setText(call.time);

        // Avatar colour
        int color = AVATAR_COLORS[position % AVATAR_COLORS.length];
        holder.imgAvatar.setBackgroundColor(color);

        // Missed call → red text
        if (call.callType.equals("Missed")) {
            holder.tvName.setTextColor(Color.parseColor("#E53935"));
            holder.tvCallType.setTextColor(Color.parseColor("#E53935"));
            holder.imgArrow.setColorFilter(Color.parseColor("#E53935"));
        } else if (call.callType.equals("Outgoing")) {
            holder.tvName.setTextColor(Color.parseColor("#111111"));
            holder.tvCallType.setTextColor(Color.parseColor("#8696A0"));
            holder.imgArrow.setColorFilter(Color.parseColor("#25D366")); // green up arrow for outgoing
        } else {
            // Incoming — normal
            holder.tvName.setTextColor(Color.parseColor("#111111"));
            holder.tvCallType.setTextColor(Color.parseColor("#8696A0"));
            holder.imgArrow.setColorFilter(Color.parseColor("#8696A0"));
        }
    }

    @Override
    public int getItemCount() {
        return callList.size();
    }

    static class CallViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar, imgArrow;
        TextView tvName, tvCallType, tvTime;

        public CallViewHolder(View itemView) {
            super(itemView);
            imgAvatar  = itemView.findViewById(R.id.imgCallAvatar);
            imgArrow   = itemView.findViewById(R.id.imgCallArrow);
            tvName     = itemView.findViewById(R.id.tvCallName);
            tvCallType = itemView.findViewById(R.id.tvCallType);
            tvTime     = itemView.findViewById(R.id.tvCallTime);
        }
    }
}
