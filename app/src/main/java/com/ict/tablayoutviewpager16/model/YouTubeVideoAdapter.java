package com.ict.tablayoutviewpager16.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ict.tablayoutviewpager16.R;
import com.ict.tablayoutviewpager16.data.model.YoutubeVideo;

import java.util.ArrayList;

public class YouTubeVideoAdapter extends RecyclerView.Adapter<YouTubeVideoAdapter.viewholder> {

    ArrayList<YoutubeVideo> items;
    Context context;

    public YouTubeVideoAdapter(ArrayList<YoutubeVideo> items){
        this.items = items;
    }

    @NonNull
    @Override
    public YouTubeVideoAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_youtube,parent,false);
        return new YouTubeVideoAdapter.viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull YouTubeVideoAdapter.viewholder holder, int position) {
        holder.pic.setImageResource(R.drawable.thumb);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ImageView pic;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.thumbnail);
        }
    }
}
