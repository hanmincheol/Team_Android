package com.ict.tablayoutviewpager16.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.ict.tablayoutviewpager16.R;
import com.ict.tablayoutviewpager16.data.model.FoodListDto;

import java.util.ArrayList;

public class RecommFoodAdapter extends RecyclerView.Adapter<RecommFoodAdapter.viewholder> {
    private ArrayList<FoodListDto> items;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public RecommFoodAdapter(ArrayList<FoodListDto> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_food, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTxt, calorieTxt;
        private ImageView pic;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            calorieTxt = itemView.findViewById(R.id.calorieTxt);
            pic = itemView.findViewById(R.id.pic);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            titleTxt.setText(items.get(position).getFOODNAME());
            calorieTxt.setText(items.get(position).getCalory() + "Kcal");
            Glide.with(context)
                    .load(items.get(position).getRECIPE_IMG())
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(pic);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }
}
