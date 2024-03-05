package com.ict.tablayoutviewpager16.model;

import android.content.Context;
import android.content.Intent;
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
import com.ict.tablayoutviewpager16.DetailCommunity;
import com.ict.tablayoutviewpager16.Detailfood;
import com.ict.tablayoutviewpager16.R;
import com.ict.tablayoutviewpager16.data.model.Post;

import java.util.ArrayList;
import java.util.List;

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
        holder.userid.setText(items.get(position).getId());
        holder.content.setText(items.get(position).getContent());
        holder.like.setText("좋아요 "+items.get(position).getLikes().size());
        Glide.with(context)
                .load(items.get(position).getProfilepath())//
                .transform(new CenterCrop(),new RoundedCorners(30))
                .into(holder.profile);

        holder.itemView.setOnClickListener(v->{
            Intent intent=new Intent(context, DetailCommunity.class);
            intent.putExtra("object",items.get(position));
            context.startActivity(intent);
        });

        // Post 객체의 파일 목록을 String 형식으로 변환하여 InnerAdapter에 전달
        List<String> postList = items.get(position).getFiles();
        List<String> fileList = new ArrayList<>();
        for (String post : postList) {
            fileList.add(post.toString());
        }

        InnerAdapter innerAdapter = new InnerAdapter(fileList);
        holder.innerRecyclerView.setAdapter(innerAdapter);
        // InnerRecyclerView에 레이아웃 관리자 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.innerRecyclerView.setLayoutManager(layoutManager);

        // 파일이 없는 경우 높이를 10dp로 설정
        if (fileList.isEmpty()) {
            ViewGroup.LayoutParams params = holder.innerRecyclerView.getLayoutParams();
            params.height = dpToPx(context, 10); // dpToPx 메서드로 dp 값을 픽셀 값으로 변환
            holder.innerRecyclerView.setLayoutParams(params);
        } else {
            // 파일이 있는 경우 높이를 WRAP_CONTENT로 설정
            ViewGroup.LayoutParams params = holder.innerRecyclerView.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            holder.innerRecyclerView.setLayoutParams(params);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView userid;
        TextView content;
        ImageView profile;
        TextView like;
        RecyclerView innerRecyclerView;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            userid=itemView.findViewById(R.id.detailviewitem_profile_textview);
            content=itemView.findViewById(R.id.detailviewitem_explain_textview);
            profile = itemView.findViewById(R.id.detailviewitem_profile_image);
            like=itemView.findViewById(R.id.detailviewitem_favoritecounter_textview);
            innerRecyclerView = itemView.findViewById(R.id.detailviewitem_imageview_content);
        }
    }

    // dp 값을 px 값으로 변환하는 메서드
    private int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
