package com.example.clicker;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clicker.databinding.PlantBarBinding;

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
        return new MyViewHolder(PlantBarBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(@NonNull PlantAdapter.MyViewHolder holder, int position) {
        viewModel.setProgressBar(position,holder.binding.PlantProgress);
        holder.binding.PlantName.setText(R.string.Plant);
        holder.binding.PlantImage.setImageResource(R.drawable.plant);
        holder.binding.PlantProgress.setMax(100);
        holder.binding.PlantProgress.setProgress(plants.get(position).getProgress());
        viewModel.startSlavesPos(position);
        holder.binding.PlantPlusButton.setOnClickListener(v -> {
            if (viewModel.incrSlavesPos(position)) {
                viewModel.startSlavesPos(position);
                holder.binding.PlantSlaves.setText(""+plants.get(position).getSlave());
            }
        });
        holder.binding.PlantMinusButton.setOnClickListener(v ->{
            if (viewModel.decrSlavesPos(position)){
                holder.binding.PlantSlaves.setText(""+plants.get(position).getSlave());
            }
        } );
        holder.binding.PlantSlaves.setText(""+plants.get(position).getSlave());
    }

    @Override
    public int getItemCount() {
        return plants.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final PlantBarBinding binding;
        public MyViewHolder(PlantBarBinding b) {
            super(b.getRoot());
            binding=b;
        }
    }
}
