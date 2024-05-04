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
    private LiveData<ViewModel.Resourses> Live_Data_Balance;
    private ArrayList<MarketItem> marketItems;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentMarketBinding.inflate(getLayoutInflater());
        Context context = getContext();
        model=ViewModel.newInstance(context);
        Live_Data_Balance=model.getLiveDataResourses();
        marketItems=new MarketArraySetter(context,Live_Data_Balance.getValue().getMarket()).getMarketItem_Array();
        for (int i = 0; i < marketItems.size(); i++) {
            marketItems.get(i).IncrCost(i);
        }
        adapter = new MarketAdapter(context,marketItems);
        binding.MarketRecycler.setLayoutManager(new LinearLayoutManager(context));
        binding.MarketRecycler.setItemAnimator(null);
        binding.MarketRecycler.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding.MarketBalance.setText("$"+Live_Data_Balance.getValue().getBalance());
        Live_Data_Balance.observe(getViewLifecycleOwner(), resourses -> {
            binding.MarketBalance.setText("$"+ resourses.getBalance());
        });
        adapter.setOnButtonClickListener(position -> {
            if (model.incrCountBuy(position)){
                adapter.BuyItem(position);
            }
        });
        return binding.getRoot();
    }

}
