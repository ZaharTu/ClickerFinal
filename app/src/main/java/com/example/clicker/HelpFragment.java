package com.example.clicker;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.clicker.databinding.FragmentHelpBinding;

public class HelpFragment extends Fragment {
    private FragmentHelpBinding binding;
    private HelpAdapter adapter;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentHelpBinding.inflate(getLayoutInflater());
        context=getContext();
        adapter=new HelpAdapter(context);
        binding.HelpRecycler.setLayoutManager(new LinearLayoutManager(context));
        binding.HelpRecycler.setAdapter(adapter);
        return binding.getRoot();
    }
}
