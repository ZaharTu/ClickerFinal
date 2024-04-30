package com.example.clicker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clicker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
private ViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        model=ViewModel.newInstance(getApplicationContext());
        new Thread(() -> model.LoadOfSave()).start();
    }

}