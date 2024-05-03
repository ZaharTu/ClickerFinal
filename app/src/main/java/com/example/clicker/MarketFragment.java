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
    private LiveData<ArrayList<MarketItem>> Live_Data_Market;
    private LiveData<ViewModel.Resourses> Live_Data_Balance;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMarketBinding.inflate(getLayoutInflater());
        context=getContext();
        model=ViewModel.newInstance(context);
        Live_Data_Market=model.getLiveDataArray();
        Live_Data_Balance=model.getLiveDataResourses();
        adapter = new MarketAdapter(context,model.getLiveDataArray().getValue());
        binding.MarketRecycler.setLayoutManager(new LinearLayoutManager(context));
        binding.MarketRecycler.setItemAnimator(null);
        binding.MarketRecycler.setAdapter(adapter);
        binding.MarketBalance.setText("$"+Live_Data_Balance.getValue().getBalance());
        Live_Data_Balance.observe(getViewLifecycleOwner(), resourses -> {
            binding.MarketBalance.setText("$"+ resourses.getBalance());
        });
        Live_Data_Market.observe(getViewLifecycleOwner(),items -> {
            adapter.UpdateItems(model.getLiveDataArray().getValue());
        });
        adapter.setOnButtonClickListener(position -> {
            model.incrCountBuy(position);
        });
        return binding.getRoot();
    }
}
