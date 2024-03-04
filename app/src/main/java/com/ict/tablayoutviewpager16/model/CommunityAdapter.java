package com.ict.tablayoutviewpager16.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.ict.tablayoutviewpager16.R;
import com.ict.tablayoutviewpager16.data.model.Post;
import com.ict.tablayoutviewpager16.data.model.YoutubeVideo;

import java.util.ArrayList;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.viewholder> {
    ArrayList<Post> items;
    Context context;





    public CommunityAdapter(ArrayList<Post> items){
        this.items = items;
    }

    @NonNull
    @Override
    public CommunityAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        //       ----------- Youtube Video-------------------

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_community,parent,false);
        return new viewholder(inflate);


    }

    @Override
    public void onBindViewHolder(@NonNull CommunityAdapter.viewholder holder, int position) {
        holder.userid.setText(items.get(position).getId());
        holder.content.setText(items.get(position).getContent());
        Glide.with(context)
                .load(items.get(position).getProfilepath())//
                .transform(new CenterCrop(),new RoundedCorners(30))
                .into(holder.profile);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView userid;
        TextView content;
        ImageView profile;
        RecyclerView recyclerView;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            userid=itemView.findViewById(R.id.detailviewitem_profile_textview);
            content=itemView.findViewById(R.id.detailviewitem_explain_textview);
            profile = itemView.findViewById(R.id.detailviewitem_profile_image);

        }
    }
}

