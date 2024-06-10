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

import com.example.clicker.databinding.FragmentMainMarketplaceBinding;

public class MarketplaceFragment extends Fragment {
    private FragmentMainMarketplaceBinding binding;
    private Marketplace marketplace;
    private Context context;
    private AllResRepository repository;
    private ViewModel viewModel;
    private MutableLiveData<int[]> LiveMarket;
    private MutableLiveData<int[]> LiveResearch;
    private LiveData<Float> LiveVolume;
    private String marketPlaceName;
    private float volume=0.5f;
    private MediaPlayer mediaPlayerAdd;
    private MediaPlayer mediaPlayerDecr;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= FragmentMainMarketplaceBinding.inflate(getLayoutInflater());
        context=getContext();
        marketplace=new Marketplace(context);
        marketPlaceName = getResources().getString(R.string.Market);
        viewModel=new ViewModel(context);
        LiveMarket=viewModel.getMarketLiveData();
        LiveVolume=viewModel.getVolumeLiveData();
        LiveResearch=viewModel.getResearchLiveData();
        mediaPlayerAdd=MediaPlayer.create(context,R.raw.slave_incr);
        mediaPlayerAdd.setVolume(volume,volume);
        mediaPlayerDecr=MediaPlayer.create(context,R.raw.slave_decr);
        mediaPlayerDecr.setVolume(volume,volume);
        marketplace.setProgressBar(binding.MarketPlaceProgress);
        repository=AllResRepository.getInstance(context);
        marketplace.setSlave(repository.getUsableNeighbor()[2]);
        binding.MarketPlaceSlaves.setText(""+marketplace.getSlave());
        binding.MarketPlacePlusButton.setOnClickListener(v -> {
            if (repository.incrSlavesPos(2)){
                marketplace.IncrSlave();
                binding.MarketPlaceSlaves.setText(""+marketplace.getSlave());
                mediaPlayerAdd.start();
            }
        });
        binding.MarketPlaceMinusButton.setOnClickListener(v -> {
            if (repository.decrSlavesPos(2)){
                marketplace.DincrSlave();
                binding.MarketPlaceSlaves.setText(""+marketplace.getSlave());
                mediaPlayerDecr.start();
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(LiveResearch.getValue()!=null){
            if (LiveResearch.getValue()[5]==1) return binding.getRoot();
        }
        return inflater.inflate(R.layout.fragment_main_marketplace_b_and_w,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LiveMarket.observe(getViewLifecycleOwner(),market -> {
            binding.MarketPlaceName.setText(marketPlaceName+" УР "+market[6]);
        });
        LiveVolume.observe(getViewLifecycleOwner(),volume->{
            mediaPlayerAdd.setVolume(volume,volume);
            mediaPlayerDecr.setVolume(volume,volume);
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
