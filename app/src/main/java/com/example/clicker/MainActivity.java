package com.example.clicker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

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
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();
        binding.bottomNavigationView.setSelectedItemId(R.id.mainFragment);
        binding.bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            NavigationUI.onNavDestinationSelected(menuItem,navController);
            return true;
        });
    }

}