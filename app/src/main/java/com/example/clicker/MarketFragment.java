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
    private ItemAdapter adapter;
    private ViewModel model;
    private LiveData<ArrayList<Item>> Live_Data_Items;
    private LiveData<ViewModel.BalanceRes> Live_Data_Balance;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMarketBinding.inflate(getLayoutInflater());
        context=getContext();
        model=ViewModel.newInstance(context);
        Live_Data_Items=model.getLiveDataArray();
        Live_Data_Balance=model.getLiveDataBalance();
        adapter = new ItemAdapter(context,model.getLiveDataArray().getValue());
        binding.MarketRecycler.setLayoutManager(new LinearLayoutManager(context));
        binding.MarketRecycler.setItemAnimator(null);
        binding.MarketRecycler.setAdapter(adapter);
        binding.MarketBalance.setText("$"+Live_Data_Balance.getValue().getBalance());
        Live_Data_Balance.observe(getViewLifecycleOwner(), balanceRes -> {
            binding.MarketBalance.setText("$"+ balanceRes.getBalance());
        });
        Live_Data_Items.observe(getViewLifecycleOwner(),items -> {
            adapter.UpdateItems(model.getLiveDataArray().getValue());
        });
        adapter.setOnButtonClickListener(position -> {
            model.incrCountBuy(position);
        });
        return binding.getRoot();
    }
}
