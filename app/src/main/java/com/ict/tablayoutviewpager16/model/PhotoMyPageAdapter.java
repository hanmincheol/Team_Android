package com.ict.tablayoutviewpager16.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ict.tablayoutviewpager16.R;
import com.ict.tablayoutviewpager16.data.model.PhotoMyPage;

import java.util.ArrayList;

public class PhotoMyPageAdapter extends RecyclerView.Adapter<PhotoMyPageAdapter.viewholder> {
    ArrayList<PhotoMyPage> items;
    Context context;

    public PhotoMyPageAdapter(ArrayList<PhotoMyPage> items){
        this.items = items;
    }

    @NonNull
    @Override
    public PhotoMyPageAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_photomypage,parent,false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoMyPageAdapter.viewholder holder, int position) {
        holder.pic.setImageResource(R.drawable.gomypage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView content;
        ImageView pic;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.mypagePhoto);
        }
    }
}
