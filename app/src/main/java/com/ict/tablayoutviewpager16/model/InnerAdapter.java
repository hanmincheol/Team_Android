package com.ict.tablayoutviewpager16.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.ict.tablayoutviewpager16.R;
import com.ict.tablayoutviewpager16.data.model.Post;

import java.util.ArrayList;
import java.util.List;

public class InnerAdapter extends RecyclerView.Adapter<InnerAdapter.InnerViewHolder> {
    private List<String> files;
    Context context;

    public InnerAdapter(List<String> files) {
        this.files = files;
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_photopost, parent, false);
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        String fileUrl = files.get(position);
        Log.d("InnerAdapter","InnerAdapter :"+fileUrl);
        // 파일을 로드하여 ImageView에 표시
        Glide.with(context)
                .load(fileUrl)
                .fitCenter()
                .into(holder.postImage);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public static class InnerViewHolder extends RecyclerView.ViewHolder {
        ImageView postImage;

        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.postPhoto);
        }
    }
}

