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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.clicker.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {
    private FragmentMainBinding binding;
    private ViewModel model;
    private LiveData<Integer> LiveBalance;
    private LiveData<Integer> LiveGather;
    private LiveData<Float> LiveVolume;
    private LiveData<AllRes.Slave> LiveSlave;
    private AllResRepository repository;
    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayerLeyka;
    private float volume=0.5f;
    private Animation animPotatoBtn;
    private Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= FragmentMainBinding.inflate(getLayoutInflater());
        context=getContext();
        model=new ViewModel(context);
        repository=AllResRepository.getInstance(context);
        LiveBalance=model.getBalanceLiveData();
        LiveGather=model.getGatherLiveData();
        LiveVolume= model.getVolumeLiveData();
        LiveSlave=model.getSlaveLiveData();
        mediaPlayer=MediaPlayer.create(context,R.raw.potato);
        mediaPlayerLeyka=MediaPlayer.create(context,R.raw.potato_with_leyka);
        mediaPlayerLeyka.setVolume(volume,volume);
        mediaPlayer.setVolume(volume,volume);
        animPotatoBtn = AnimationUtils.loadAnimation(context, R.anim.main_potato_anim);
        binding.MainSlaves.setText(repository.getSlave().UsableSlaveGet()+"/"+repository.getSlave().AllSlave);
        binding.MainBtnGather.setText("$"+ repository.getGather());
        binding.MainBtnPotato.setOnClickListener(v -> {
            binding.MainBtnPotato.startAnimation(animPotatoBtn);
            if (repository.incrBalanceClick()){
                mediaPlayerLeyka.start();
            }else{
                mediaPlayer.start();
            }
        });
        binding.MainBtnGather.setOnClickListener(v -> {
            repository.incrBalanceGather();
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LifecycleOwner observer = getViewLifecycleOwner();
        LiveBalance.observe(observer, balance -> {
            binding.MainBalance.setText("$"+ balance);
        });
        LiveVolume.observe(observer, volume->{
            mediaPlayer.setVolume(volume,volume);
            mediaPlayerLeyka.setVolume(volume,volume);
        });
        LiveGather.observe(observer, gather->{
            binding.MainBtnGather.setText("$"+ gather);
        });
        LiveSlave.observe(observer, slave -> {
            binding.MainSlaves.setText(slave.UsableSlaveGet()+"/"+slave.AllSlave);
        });
        return binding.getRoot();
    }
}
