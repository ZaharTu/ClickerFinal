package com.example.clicker;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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
    private LiveData<Integer> LiveBalance;
    private LiveData<boolean[]> LiveResearch;
    private LiveData<Integer> LiveGather;
    private LiveData<Float> LiveVolume;
    private LiveData<AllRes.Neighbor> LiveSlave;
    private AllResRepository repository;
    private MediaPlayer mediaPlayer;
    private int delay = 5000;
    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, delay);
            repository.incrBalanceClick();
        }
    };

    private Animation animPotatoBtn;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= FragmentMainBinding.inflate(getLayoutInflater());
        Context context = getContext();
        ViewModel model = new ViewModel(context);
        repository=AllResRepository.getInstance(context);
        LiveBalance= model.getBalanceLiveData();
        LiveGather= model.getGatherLiveData();
        LiveVolume= model.getVolumeAllLiveData();
        LiveSlave= model.getSlaveLiveData();
        LiveResearch= model.getResearchLiveData();
        mediaPlayer=MediaPlayer.create(context,R.raw.potato);
        float volume = 0.5f;
        mediaPlayer.setVolume(volume, volume);
        animPotatoBtn = AnimationUtils.loadAnimation(context, R.anim.main_potato_anim);
        binding.MainSlaves.setText(repository.getNeighbor().UsableNeighborGet()
                +"/"+repository.getNeighbor().AllNeighbor +"\uD83D\uDC68\u200D\uD83C\uDF3E");
        binding.MainBtnGather.setText("$"+ repository.getGather());
        binding.MainBtnPotato.setOnClickListener(v -> {
            binding.MainBtnPotato.startAnimation(animPotatoBtn);
            mediaPlayer.start();
            repository.incrBalanceClick();
        });
        binding.MainBtnGather.setOnClickListener(v -> {
            repository.incrBalanceGather();
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
        LifecycleOwner observer = getViewLifecycleOwner();
        LiveBalance.observe(observer, balance -> {
            binding.MainBalance.setText("$"+ balance);
        });
        LiveVolume.observe(observer, volume->{
            mediaPlayer.setVolume(volume,volume);
        });
        LiveGather.observe(observer, gather->{
            binding.MainBtnGather.setText("$"+ gather);
        });
        LiveSlave.observe(observer, neighbor -> {
            binding.MainSlaves.setText(neighbor.UsableNeighborGet()
                    +"/"+ neighbor.AllNeighbor +"\uD83D\uDC68\u200D\uD83C\uDF3E");//👨‍🌾
        });
        LiveResearch.observe(observer,research->{
            if (research[0]){
                if (research[1]) delay=2500;
                handler.post(runnable);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}
