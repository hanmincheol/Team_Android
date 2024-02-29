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
import com.ict.tablayoutviewpager16.data.model.Profile;

import java.util.ArrayList;

public class CommunityProfileAdapter extends RecyclerView.Adapter<CommunityProfileAdapter.viewholder> {
    ArrayList<Profile> items;
    Context context;

    public CommunityProfileAdapter(ArrayList<Profile> items){
        this.items = items;
    }

    @NonNull
    @Override
    public CommunityProfileAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_communityprofile,parent,false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityProfileAdapter.viewholder holder, int position) {
        holder.userid.setText(items.get(position).getId()+"goyounjoung");
        holder.pic.setImageResource(R.drawable.go);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView userid;
        TextView content;
        ImageView pic;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            userid=itemView.findViewById(R.id.detailviewitem_profile_id);
            pic = itemView.findViewById(R.id.detailviewitem_profile_image);
        }
    }
}
