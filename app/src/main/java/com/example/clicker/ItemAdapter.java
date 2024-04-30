package com.example.clicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        return new MyViewHolder(view);
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
        holder.image.setImageResource(item.getImage());
        holder.tvName.setText(item.getNameItem());
        holder.tvHint.setText(item.getHintItem());
        holder.tvCost.setText("$"+item.getCost());
        holder.tvCount.setText(item.getCountBuy()+"-ая покупка");
        holder.btnBuy.setOnClickListener(v -> {
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
        ImageView image;
        TextView tvName, tvHint,tvCost,tvCount;
        Button btnBuy;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.Market_Image);
            tvName=itemView.findViewById(R.id.Market_Name);
            tvHint=itemView.findViewById(R.id.Market_Hint);
            tvCost=itemView.findViewById(R.id.Market_Cost);
            tvCount=itemView.findViewById(R.id.Market_Count);
            btnBuy=itemView.findViewById(R.id.Market_Buy);
        }
    }
}
