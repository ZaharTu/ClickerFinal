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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.clicker.databinding.FragmentMainBinding;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    private FragmentMainBinding binding;
    private ViewModel model;
    private LiveData<ViewModel.BalanceRes> LiveBalance;
    private LiveData<ArrayList<Plant>> LivePlants;
    private PlantAdapter adapter;
    private MediaPlayer mediaPlayer;
    private Animation animPotatoBtn;
    private Context context;
    private int SlaveAll=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentMainBinding.inflate(getLayoutInflater());
        context=getContext();
        MyDialog mydialog = new MyDialog();
        model=ViewModel.newInstance(context);
        LiveBalance=model.getLiveDataBalance();
        LivePlants=model.getLiveDataPlants();
        NavController controller = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView);
        mediaPlayer=MediaPlayer.create(context,R.raw.digging);
        animPotatoBtn = AnimationUtils.loadAnimation(context, R.anim.main_potato_anim);
        binding.MainBtnPotato.setOnClickListener(v -> {
            binding.MainBtnPotato.setAnimation(animPotatoBtn);
            mediaPlayer.start();
            model.incrBalanceClick();
        });
        binding.MainBtnMarket.setOnClickListener(v -> {
            controller.navigate(R.id.action_mainFragment_to_fragmentMarket);
        });
        binding.MainBtnResearch.setOnClickListener(v -> {
            controller.navigate(R.id.action_mainFragment_to_researchFragment);
        });
        binding.MainBtnGather.setOnClickListener(v -> {
            model.incrBalanceGather();
        });
        adapter=new PlantAdapter(context);
        binding.MainPlantRecycler.setLayoutManager(new LinearLayoutManager(context));
        binding.MainPlantRecycler.setAdapter(adapter);
        binding.MainBtnMenu.setOnClickListener(v -> {
            mydialog.showDialogMenu(context,
                    binding.getRoot(),
                    "Что будем делать?",
                    "Выберите нужное\nнаправление");
        });
        LiveBalance.observe(getViewLifecycleOwner(), balanceRes -> {
            binding.MainBalance.setText("$"+ balanceRes.getBalance());
            binding.MainBtnGather.setText("$"+ balanceRes.getGather());
            SlaveAll= balanceRes.getMarketPos(2);
            if (LiveBalance.getValue().getMarketPos(5)==1){
                binding.MainBtnResearch.setVisibility(View.VISIBLE);
            }
        });
        LivePlants.observe(getViewLifecycleOwner(),plants -> {
            int use = 0;
            for (int i = 0; i < plants.size(); i++) {
                use += plants.get(i).getSlave();
            }
            use=SlaveAll-use;
            binding.MainSlaves.setText(use+"/"+SlaveAll);
        });

        return binding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        new Thread(() -> model.SaveToSave()).start();
    }
}
