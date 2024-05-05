package com.example.clicker;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.clicker.databinding.FragmentMarketBinding;

import java.util.ArrayList;

public class MarketFragment extends Fragment {
    private FragmentMarketBinding binding;
    private MarketAdapter adapter;
    private ViewModel model;
    private LiveData<Integer> LiveBalance;
    private AllResRepository repository;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentMarketBinding.inflate(getLayoutInflater());
        Context context = getContext();
        model=new ViewModel(context);
        LiveBalance= model.getBalanceLiveData();
        repository = AllResRepository.getInstance(context);
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
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LiveBalance.observe(getViewLifecycleOwner(), balance -> {
            binding.MarketBalance.setText("$"+balance);
        });
        return binding.getRoot();
    }

}
