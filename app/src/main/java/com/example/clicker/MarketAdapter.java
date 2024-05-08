package com.example.clicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clicker.databinding.MarketItemBinding;

import java.util.ArrayList;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.MyViewHolder>{
    ArrayList<MarketItem> marketItems;
    Context mcontext;
    public MarketAdapter(Context context, ArrayList<MarketItem> marketItems){
        mcontext=context;
        this.marketItems = marketItems;
    }
    public void BuyItem(int position){
        marketItems.get(position).IncrCount();
        marketItems.get(position).IncrCost(position);
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public MarketAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(@NonNull MarketAdapter.MyViewHolder holder, int position) {
        MarketItem marketItem = marketItems.get(position);
        holder.binding.MarketImage.setImageResource(marketItem.getImage());
        holder.binding.MarketName.setText(marketItem.getNameItem());
        holder.binding.MarketHint.setText(marketItem.getHintItem());
        holder.binding.MarketCost.setText("$"+ marketItem.getCost());
        holder.binding.MarketCount.setText(marketItem.getCountBuy()+"-ая");
        holder.binding.MarketBuy.setText("Купить");
        if (position>3) {
            holder.binding.MarketCount.setText("");
            holder.binding.MarketName.setText(marketItem.getNameItem() + " УР " + marketItem.getCountBuy());
            holder.binding.MarketBuy.setText("Улучшить");
        }
        holder.binding.MarketBuy.setOnClickListener(v -> {
            if (buttonClickListener != null) {
                buttonClickListener.onButtonClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return marketItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final MarketItemBinding binding;
        public MyViewHolder(MarketItemBinding b) {
            super(b.getRoot());
            binding=b;
        }
    }
}
