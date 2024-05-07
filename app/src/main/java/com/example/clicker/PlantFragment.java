package com.example.clicker;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clicker.databinding.FragmentPlantBinding;


public class PlantFragment extends Fragment {
    private FragmentPlantBinding binding;
    private Plant plant;
    private Context context;
    private AllResRepository repository;
    private ViewModel viewModel;
    private String plantName;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= FragmentPlantBinding.inflate(getLayoutInflater());
        context=getContext();
        plant=new Plant(context);
        plantName = getResources().getString(R.string.Plant);
        plant.setProgressBar(binding.PlantProgress);
        repository=AllResRepository.getInstance(context);
        plant.setSlave(repository.getUsableSlave()[0]);
        binding.PlantSlaves.setText(""+plant.getSlave());
        binding.PlantPlusButton.setOnClickListener(v -> {
            if (repository.incrSlavesPos(0)){
                plant.IncrSlave();
                plant.start();
                binding.PlantSlaves.setText(""+plant.getSlave());
            }
        });
        binding.PlantMinusButton.setOnClickListener(v -> {
            if (repository.decrSlavesPos(0)){
                plant.DincrSlave();
                binding.PlantSlaves.setText(""+plant.getSlave());
            }
        });
        viewModel=new ViewModel(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel.getMarketLiveData().observe(getViewLifecycleOwner(),market -> {
            binding.PlantName.setText(plantName+" УР "+market[4]);
        });
        return binding.getRoot();
    }
}
