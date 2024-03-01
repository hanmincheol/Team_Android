package com.ict.tablayoutviewpager16.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.userid.setText(items.get(position).getId()+"goyounjoung");
        holder.pic.setImageResource(R.drawable.muscular2);

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
        TextView userid;
        TextView content;
        ImageView pic;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            userid=itemView.findViewById(R.id.titleTRTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
