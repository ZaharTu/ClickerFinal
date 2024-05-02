package com.example.clicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clicker.databinding.FragmentResearchBinding;

public class ResearchFragment extends Fragment {
    private FragmentResearchBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentResearchBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
