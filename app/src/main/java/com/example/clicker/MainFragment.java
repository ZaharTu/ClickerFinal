package com.example.clicker;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.clicker.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {
    private FragmentMainBinding binding;
    private ViewModel model;
    private LiveData<ViewModel.Resourses> LiveBalance;
    private PlantAdapter adapter;
    private MediaPlayer mediaPlayer;
    private Animation animPotatoBtn;
    private Context context;
    private int SlaveAll=0;
    private float volume=0.5f;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= FragmentMainBinding.inflate(getLayoutInflater());
        context=getContext();
        model=ViewModel.newInstance(context);
        LiveBalance=model.getLiveDataResourses();
        mediaPlayer=MediaPlayer.create(context,R.raw.digging);
        mediaPlayer.setVolume(volume,volume);
        animPotatoBtn = AnimationUtils.loadAnimation(context, R.anim.main_potato_anim);
        adapter= new PlantAdapter(context);
        binding.MainPlantRecycler.setLayoutManager(new LinearLayoutManager(context));
        binding.MainPlantRecycler.setAdapter(adapter);
        binding.MainBtnPotato.setOnClickListener(v -> {
            binding.MainBtnPotato.setAnimation(animPotatoBtn);
            mediaPlayer.start();
            model.incrBalanceClick();
        });
        binding.MainBtnGather.setOnClickListener(v -> {
            model.incrBalanceGather();
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LiveBalance.observe(getViewLifecycleOwner(), resourses -> {
            binding.MainBalance.setText("$"+ resourses.getBalance());
            binding.MainBtnGather.setText("$"+ resourses.getGather());
            SlaveAll= resourses.getMarketPos(2);
            binding.MainSlaves.setText(resourses.getUsableSlave()+"/"+SlaveAll);
            if (volume!=resourses.getVolume()){
                volume=resourses.getVolume();
                mediaPlayer.setVolume(volume,volume);
            }
        });
        return binding.getRoot();
    }
}
