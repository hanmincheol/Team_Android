package com.ict.tablayoutviewpager16.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.ict.tablayoutviewpager16.DetailExercise;
import com.ict.tablayoutviewpager16.Detailfood;
import com.ict.tablayoutviewpager16.R;
import com.ict.tablayoutviewpager16.data.model.Training;

import java.util.ArrayList;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.viewholder> {
    ArrayList<Training> items;
    Context context;

    public TrainAdapter(ArrayList<Training> items){
        this.items = items;
    }

    @NonNull
    @Override
    public TrainAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_train,parent,false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainAdapter.viewholder holder, int position) {
        holder.exerciseid.setText(items.get(position).getEName());
        holder.eType.setText(items.get(position).getEType());

        String id = items.get(position).getEVideoPath().substring(items.get(position).getEVideoPath().lastIndexOf("/")+1);  //맨마지막 '/'뒤에 id가있으므로 그것만 파싱해줌
        Log.d("파싱한 아이디id 값", id);
        String url ="https://img.youtube.com/vi/"+ id+ "/" + "default.jpg";

        Glide.with(context)
                .load(url)//
                .transform(new CenterCrop(),new RoundedCorners(30))
                .into(holder.picVideo);

        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(context, DetailExercise.class);
            intent.putExtra("object",items.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView exerciseid;
        TextView eType;
        ImageView picVideo;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            exerciseid=itemView.findViewById(R.id.titleTRTxt);
            eType=itemView.findViewById(R.id.trainingTxt);
            picVideo = itemView.findViewById(R.id.picVideo);
        }
    }
}
