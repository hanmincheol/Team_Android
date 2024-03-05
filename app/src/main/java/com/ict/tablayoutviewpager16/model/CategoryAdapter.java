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
import com.ict.tablayoutviewpager16.data.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder> {
    ArrayList<Category> items;
    Context context;

    public CategoryAdapter(ArrayList<Category> items){
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
    View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.viewholder holder, int position) {

        holder.titleTxt.setText(items.get(position).getName());

        switch (position){
            case 0: {
                holder.titleTxt.setText("다이어트");
                holder.pic.setImageResource(R.drawable.veggieroll);
                break;
            }
            case 1: {
                holder.titleTxt.setText("영양식");
                holder.pic.setImageResource(R.drawable.cat2);
                break;
            }
            case 2: {
                holder.titleTxt.setText("양식");
                holder.pic.setImageResource(R.drawable.cat3);
                break;
            }
            case 3: {
                holder.titleTxt.setText("일상");
                holder.pic.setImageResource(R.drawable.cat4);
                break;
            }
            case 4: {
                holder.titleTxt.setText("찌개");
                holder.pic.setImageResource(R.drawable.cat5);
                break;
            }
            case 5: {
                holder.titleTxt.setText("육류");
                holder.pic.setImageResource(R.drawable.cat6);
                break;
            }
            case 6: {
                holder.titleTxt.setText("셀러드");
                holder.pic.setImageResource(R.drawable.cat7);
                break;
            }
            case 7: {
                holder.titleTxt.setText("면류");
                holder.pic.setImageResource(R.drawable.cat8);
                break;
            }

        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView titleTxt;
        ImageView pic;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.catName);

            pic = itemView.findViewById(R.id.imgCat);
        }
    }
}
