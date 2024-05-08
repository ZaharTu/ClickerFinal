package com.example.clicker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.clicker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
private NavHostFragment navHostFragment;
private NavController navController;
private AllResRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository=AllResRepository.getInstance(this);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        binding.bottomNavigationView.setSelectedItemId(R.id.mainFragment);
        binding.bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            NavigationUI.onNavDestinationSelected(menuItem,navController);
            return true;
        });
        startService(new Intent(this,SaveService.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        new Thread(()-> repository.SaveToSave()).start();
    }

}