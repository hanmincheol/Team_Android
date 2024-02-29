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
import com.ict.tablayoutviewpager16.data.model.Post;

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
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_community,parent,false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityAdapter.viewholder holder, int position) {
        holder.userid.setText(items.get(position).getId()+"goyounjoung");
        holder.content.setText(items.get(position).getContent()+"일단 아무말이나 적어야지");
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
            userid=itemView.findViewById(R.id.detailviewitem_profile_textview);
            content=itemView.findViewById(R.id.detailviewitem_explain_textview);

            pic = itemView.findViewById(R.id.detailviewitem_imageview_content);
        }
    }
}
