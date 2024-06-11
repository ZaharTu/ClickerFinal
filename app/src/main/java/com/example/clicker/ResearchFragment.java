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
        setButtonListeners();
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
    private void setButtonListener(View button, int position) {
        button.setOnClickListener(v -> dialog.showDialogResearch(context, position, binding.getRoot()));
    }
    private void setButtonListeners() {
        int[][] buttonIdAndPosition = {
                {R.id.Research_Btn_Start, 0},
                {R.id.Research_Btn_Shovel1, 1},
                {R.id.Research_Btn_Shovel2, 2},
                {R.id.Research_Btn_Barn1, 3},
                {R.id.Research_Btn_Barn2, 4},
                {R.id.Research_Btn_Leyka, 5},
                {R.id.Research_Btn_MarketPlace, 6},
                {R.id.Research_Btn_Neighbor, 7},
                {R.id.Research_Btn_Tractor, 8},
                {R.id.Research_Btn_Village, 9},
                {R.id.Research_Btn_End, 10}
        };

        for (int[] pair : buttonIdAndPosition) {
            View button = binding.getRoot().findViewById(pair[0]);
            setButtonListener(button, pair[1]);
        }
    }

}
