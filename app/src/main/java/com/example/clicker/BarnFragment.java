package com.example.clicker;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clicker.databinding.FragmentBarnBinding;

public class BarnFragment extends Fragment {
    private FragmentBarnBinding binding;
    private Barn barn;
    private Context context;
    private AllResRepository repository;
    private ViewModel viewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=FragmentBarnBinding.inflate(getLayoutInflater());
        context=getContext();
        repository=AllResRepository.getInstance(context);
        barn=new Barn(context);
        viewModel=new ViewModel(context);
        barn.setProgressBar(binding.BarnProgress);
        barn.setSlave(repository.getUsableSlave()[1]);
        binding.BarnSlaves.setText(""+barn.getSlave());
        binding.BarnPlusButton.setOnClickListener(v -> {
            if (repository.incrSlavesPos(1)){
                barn.IncrSlave();
                binding.BarnSlaves.setText(""+barn.getSlave());
                binding.BarnPotato.setText(repository.getPotato()+"/"+ barn.getMaxProgress());
            }
        });
        binding.BarnMinusButton.setOnClickListener(v -> {
            if (repository.decrSlavesPos(1)){
                barn.DincrSlave();
                binding.BarnSlaves.setText(""+barn.getSlave());
                binding.BarnPotato.setText(repository.getPotato()+"/"+ barn.getMaxProgress());
            }
        });
        binding.BarnPotato.setText(repository.getPotato()+"/"+ barn.getMaxProgress());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel.getPotatoLiveData().observe(getViewLifecycleOwner(),potato->{
            barn.IncrMaxProgress();
            barn.IncrProgress();
            binding.BarnPotato.setText(repository.getPotato()+"/"+ barn.getMaxProgress());
        });
        return binding.getRoot();
    }
}
