package com.ict.tablayoutviewpager16.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ict.tablayoutviewpager16.DetailExercise;
import com.ict.tablayoutviewpager16.R;
import com.ict.tablayoutviewpager16.data.model.SCHDto;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private ArrayList<SCHDto> items;
    private Context context;

    public EventAdapter(ArrayList<SCHDto> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_timeline, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SCHDto item = items.get(position);

        // 예외 처리 및 로그 추가
        try {
            holder.schtitle.setText(item.getStitle());
            holder.schcontent.setText(item.getScontent());
            holder.schlocation.setText(item.getSdest());
        } catch (NullPointerException e) {
            // NullPointerException이 발생했을 때 로그를 남깁니다.
            Log.e("EventAdapter", "NullPointerException occurred: " + e.getMessage());

        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<SCHDto> items) {
        this.items = items;
        notifyDataSetChanged(); // 데이터 변경을 알림
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView schtitle;
        TextView schcontent;
        TextView schlocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            schtitle = itemView.findViewById(R.id.schtitle);
            schcontent = itemView.findViewById(R.id.schcontent);
            schlocation = itemView.findViewById(R.id.schlocation);
        }
    }
}
