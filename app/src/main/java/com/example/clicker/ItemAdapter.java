package com.example.clicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clicker.databinding.MarketItemBinding;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder>{
    ArrayList<Item> items;
    Context mcontext;
    public ItemAdapter(Context context,ArrayList<Item> items){
        mcontext=context;
        this.items=items;
    }
    public void UpdateItems(ArrayList<Item> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        View view =layoutInflater.inflate(R.layout.market_item,parent,false);
        return new MyViewHolder(MarketItemBinding.bind(view));
    }
    public interface OnButtonClickListener {
        void onButtonClicked(int position);
    }
    private OnButtonClickListener buttonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.buttonClickListener = listener;
    }
    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.MyViewHolder holder, int position) {
        Item item = items.get(position);
        holder.binding.MarketImage.setImageResource(item.getImage());
        holder.binding.MarketName.setText(item.getNameItem());
        holder.binding.MarketHint.setText(item.getHintItem());
        holder.binding.MarketCost.setText("$"+item.getCost());
        holder.binding.MarketCount.setText(item.getCountBuy()+"-ая покупка");
        if (position==3){
            holder.binding.MarketCount.setText(item.getCountBuy()+" из 2");
        }else if(position==5) {
            holder.binding.MarketCount.setText(item.getCountBuy()+" из 1");
        }
        holder.binding.MarketBuy.setOnClickListener(v -> {
            if (buttonClickListener != null) {
                buttonClickListener.onButtonClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final MarketItemBinding binding;
        public MyViewHolder(MarketItemBinding b) {
            super(b.getRoot());
            binding=b;

        }
    }
}
