package com.ict.tablayoutviewpager16.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ict.tablayoutviewpager16.R;
import com.ict.tablayoutviewpager16.data.model.PhotoMyPage;
import com.squareup.picasso.Picasso;

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
        String imagePath = items.get(position).getImgpath();
        Log.d("MyPage","imagePath : "+imagePath);

        // 이미지가 있는 경우 Picasso 또는 Glide를 사용하여 이미지를 설정
        if (imagePath != null && !imagePath.isEmpty()) {
            Picasso.get().load(imagePath).into(holder.pic);
        } else {

        }
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
