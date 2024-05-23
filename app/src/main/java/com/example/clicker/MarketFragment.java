package com.example.clicker;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.clicker.databinding.FragmentMarketBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MarketFragment extends Fragment {
    private FragmentMarketBinding binding;
    private MarketAdapter adapter;
    private ViewModel model;
    private LiveData<Integer> LiveBalance;
    private LiveData<Float> LiveVolume;
    private AllResRepository repository;
    private MediaPlayer mediaPlayerBuy;
    private MediaPlayer mediaPlayerError;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentMarketBinding.inflate(getLayoutInflater());
        Context context = getContext();
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        model=new ViewModel(context);
        LiveBalance= model.getBalanceLiveData();
        LiveVolume=model.getVolumeLiveData();
        repository = AllResRepository.getInstance(context);
        mediaPlayerBuy=MediaPlayer.create(context,R.raw.buy);
        mediaPlayerBuy.setVolume(0.5f,0.5f);
        mediaPlayerError=MediaPlayer.create(context,R.raw.error);
        ArrayList<MarketItem> marketItems=new MarketArraySetter(context, repository.getMarket()).getMarketItem_Array();
        for (int i = 0; i < marketItems.size(); i++) {
            marketItems.get(i).IncrCost(i);
        }
        adapter = new MarketAdapter(context,marketItems);
        binding.MarketRecycler.setLayoutManager(new LinearLayoutManager(context));
        binding.MarketRecycler.setItemAnimator(null);
        binding.MarketRecycler.setAdapter(adapter);
        adapter.setOnButtonClickListener(position -> {
            if (repository.incrCountBuy(position)){
                adapter.BuyItem(position);
                mediaPlayerBuy.start();
            }else{
                mediaPlayerError.start();
                Snackbar.make(binding.getRoot(),R.string.Dont_Buy,Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(getResources().getColor
                                (R.color.ProgressBar, getContext().getTheme()))
                        .show();
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
        LiveBalance.observe(getViewLifecycleOwner(), balance -> {
            binding.MarketBalance.setText("$"+balance);
        });
        LiveVolume.observe(getViewLifecycleOwner(), volume->{
            mediaPlayerBuy.setVolume(volume,volume);
            mediaPlayerError.setVolume(volume,volume);
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
