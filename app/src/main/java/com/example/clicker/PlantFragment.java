package com.example.clicker;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.clicker.databinding.FragmentMainPlantBinding;


public class PlantFragment extends Fragment {
    private FragmentMainPlantBinding binding;
    private Plant plant;
    private Context context;
    private AllResRepository repository;
    private ViewModel viewModel;
    private MutableLiveData<int[]> LiveMarket;
    private MutableLiveData<int[]> LiveResearch;
    private LiveData<Float> LiveVolume;
    private String plantName;
    private float volume=0.5f;
    private MediaPlayer mediaPlayerAdd;
    private MediaPlayer mediaPlayerDecr;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= FragmentMainPlantBinding.inflate(getLayoutInflater());
        context=getContext();
        viewModel=new ViewModel(context);
        LiveMarket=viewModel.getMarketLiveData();
        LiveVolume=viewModel.getVolumeLiveData();
        LiveResearch=viewModel.getResearchLiveData();
        plant=new Plant(context);
        plantName = getResources().getString(R.string.Plant);
        mediaPlayerAdd=MediaPlayer.create(context,R.raw.slave_incr);
        mediaPlayerAdd.setVolume(volume,volume);
        mediaPlayerDecr=MediaPlayer.create(context,R.raw.slave_decr);
        mediaPlayerDecr.setVolume(volume,volume);
        plant.setProgressBar(binding.PlantProgress);
        repository=AllResRepository.getInstance(context);
        plant.setSlave(repository.getUsableNeighbor()[0]);
        binding.PlantSlaves.setText(""+plant.getSlave());
        binding.PlantPlusButton.setOnClickListener(v -> {
            if (repository.incrSlavesPos(0)){
                plant.IncrSlave();
                binding.PlantSlaves.setText(""+plant.getSlave());
                mediaPlayerAdd.start();
            }
        });
        binding.PlantMinusButton.setOnClickListener(v -> {
            if (repository.decrSlavesPos(0)){
                plant.DincrSlave();
                binding.PlantSlaves.setText(""+plant.getSlave());
                mediaPlayerDecr.start();
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LiveMarket.observe(getViewLifecycleOwner(),market -> {
            binding.PlantName.setText(plantName+" УР "+market[4]);
        });
        LiveVolume.observe(getViewLifecycleOwner(),volume->{
            mediaPlayerAdd.setVolume(volume,volume);
            mediaPlayerDecr.setVolume(volume,volume);
        });
        LiveResearch.observe(getViewLifecycleOwner(),research->{
            if (research[9]==1){
                binding.PlantImage.setImageResource(R.drawable.village);
                plantName="Деревня";
                binding.PlantName.setText(plantName+" УР "+LiveMarket.getValue()[4]);
            }
        });
    }
}
