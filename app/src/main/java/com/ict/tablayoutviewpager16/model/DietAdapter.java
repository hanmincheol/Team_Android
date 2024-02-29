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
import com.ict.tablayoutviewpager16.data.model.Diets;

import java.util.ArrayList;

public class DietAdapter extends RecyclerView.Adapter<DietAdapter.viewholder> {
    ArrayList<Diets> items;
    Context context;

    public DietAdapter(ArrayList<Diets> items){
        this.items = items;
    }

    @NonNull
    @Override
    public DietAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
    View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_diet,parent,false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DietAdapter.viewholder holder, int position) {
        holder.titleTxt.setText(items.get(position).getTitle());//
        holder.calorieTxt.setText(items.get(position).getPrice()+"Kcal");//
        holder.starTxt.setText(""+items.get(position).getStar());//
        holder.pic.setImageResource(R.drawable.veggieroll);
//        Glide.with(context)
//                .load(items.get(position).getImagePath())//
//                .transform(new CenterCrop(),new RoundedCorners(30))
//                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView titleTxt,starTxt,calorieTxt;
        ImageView pic;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.titleTxt);
            starTxt=itemView.findViewById(R.id.starTxt);
            calorieTxt=itemView.findViewById(R.id.calorieTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
