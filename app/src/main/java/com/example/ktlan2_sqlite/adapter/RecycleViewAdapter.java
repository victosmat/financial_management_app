package com.example.ktlan2_sqlite.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ktlan2_sqlite.R;
import com.example.ktlan2_sqlite.model.Item;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.HomeViewHolder> {
    private List<Item> list;
    private ItemListener itemListener;

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public RecycleViewAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<Item> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Item getItem(int p) {
        return list.get(p);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Item item = list.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvCategory.setText(item.getCategory());
        holder.tvPrice.setText(item.getPrice());
        holder.tvDate.setText(item.getDate());
        holder.tvScope.setText(item.getScope());
        holder.rating_bar.setRating(item.getRate());
        List<String> object = item.getObject();
        String objectStr = "";
        for (int i = 0; i < object.size() - 1; i++) objectStr += object.get(i) + ", ";
        objectStr += object.get(object.size() - 1);

        holder.tvObject.setText(objectStr);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle, tvCategory, tvPrice, tvDate, tvScope, tvObject;
        private RatingBar rating_bar;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvScope = itemView.findViewById(R.id.tvScope);
            rating_bar = itemView.findViewById(R.id.rating_bar);
            tvObject = itemView.findViewById(R.id.tvObject);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemListener != null) {
                itemListener.OnItemClick(v, getAdapterPosition());
            }

        }
    }


    public interface ItemListener {
        void OnItemClick(View view, int p);
    }
}
