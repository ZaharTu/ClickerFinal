package com.example.clicker;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.MyViewHolder> {
    ViewModel viewModel;
    ArrayList<Plant> plants;
    Context mcontext;
    LiveData<ArrayList<Plant>> liveData;
    public PlantAdapter(Context context){
        mcontext=context;
        viewModel= ViewModel.newInstance(context);
        liveData=viewModel.getLiveDataPlants();
        plants=liveData.getValue();
    }

    @NonNull
    @Override
    public PlantAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        View view =layoutInflater.inflate(R.layout.plant_bar,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantAdapter.MyViewHolder holder, int position) {
        viewModel.setProgressBar(position,holder.progressBar);
        holder.tvName.setText(R.string.Plant);
        holder.image.setImageResource(R.drawable.plant);
        holder.progressBar.setMax(100);
        holder.progressBar.setProgress(plants.get(position).getProgress());
        viewModel.startSlavesPos(position);
        holder.plus.setOnClickListener(v -> {
            if (viewModel.incrSlavesPos(position)) {
                viewModel.startSlavesPos(position);
                holder.tvSlaves.setText(""+plants.get(position).getSlave());
            }
        });
        holder.minus.setOnClickListener(v ->{
            if (viewModel.decrSlavesPos(position)){
                holder.tvSlaves.setText(""+plants.get(position).getSlave());
            }
        } );
        holder.tvSlaves.setText(""+plants.get(position).getSlave());
    }

    @Override
    public int getItemCount() {
        return plants.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tvName, tvSlaves;
        ProgressBar progressBar;
        Button plus, minus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.Plant_Image);
            tvName=itemView.findViewById(R.id.Plant_Name);
            tvSlaves=itemView.findViewById(R.id.Plant_Slaves);
            progressBar=itemView.findViewById(R.id.Plant_Progress);
            plus=itemView.findViewById(R.id.Plant_plus_button);
            minus=itemView.findViewById(R.id.Plant_minus_button);
        }
    }
}
