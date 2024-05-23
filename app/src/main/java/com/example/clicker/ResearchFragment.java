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

import com.example.clicker.databinding.FragmentResearchBinding;

public class ResearchFragment extends Fragment {
    private FragmentResearchBinding binding;
    private AllResRepository repository;
    private Context context;
    private ViewModel viewModel;
    private MyDialog dialog;
    private LiveData<Integer> LiveBalance;
    private LiveData<Integer> LivePotato;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        context=getContext();
        binding= FragmentResearchBinding.inflate(getLayoutInflater());
        repository=AllResRepository.getInstance(context);
        viewModel=new ViewModel(context);
        dialog=new MyDialog();
        LiveBalance=viewModel.getBalanceLiveData();
        LivePotato=viewModel.getPotatoLiveData();
        binding.ResearchBtnStart.setOnClickListener(v -> {
            dialog.showDialogResearch(context,0);
        });
        binding.ResearchBtnShovel1.setOnClickListener(v -> {
            dialog.showDialogResearch(context,1);
        });
        binding.ResearchBtnShovel2.setOnClickListener(v -> {
            dialog.showDialogResearch(context,2);
        });
        binding.ResearchBtnBarn.setOnClickListener(v -> {
            dialog.showDialogResearch(context,3);
        });
        binding.ResearchBtnClickUp2.setOnClickListener(v -> {
            dialog.showDialogResearch(context,4);
        });
        binding.ResearchBtnLeyka.setOnClickListener(v -> {
            dialog.showDialogResearch(context,5);
        });
        binding.ResearchBtnMarketPlace.setOnClickListener(v -> {
            dialog.showDialogResearch(context,6);
        });
        binding.ResearchBtnSlave.setOnClickListener(v -> {
            dialog.showDialogResearch(context,7);
        });
        binding.ResearchBtnTraktor.setOnClickListener(v -> {
            dialog.showDialogResearch(context,8);
        });
        binding.ResearchBtnVillage.setOnClickListener(v -> {
            dialog.showDialogResearch(context,9);
        });
        binding.ResearchBtnEnd.setOnClickListener(v -> {
            dialog.showDialogResearch(context,10);
        });
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (LivePotato.getValue()!=null) binding.ResearchPotato.setText(LivePotato.getValue()+"🥔");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LiveBalance.observe(getViewLifecycleOwner(),balance -> {
            binding.ResearchBalance.setText("$"+balance);
        });
        LivePotato.observe(getViewLifecycleOwner(),potato -> {
            binding.ResearchPotato.setText(potato+"🥔");
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
