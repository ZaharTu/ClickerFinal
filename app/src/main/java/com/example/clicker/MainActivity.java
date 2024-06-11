package com.example.clicker;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.clicker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
private NavHostFragment navHostFragment;
private NavController navController;
private AllResRepository repository;
private Thread musicThread;
private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewModel model = new ViewModel(this);
        LiveData<Float> volumeData = model.getVolumeBackLiveData();
        mediaPlayer=MediaPlayer.create(this,R.raw.backgroungmusic);
        mediaPlayer.setVolume(0.3f,0.3f);
        repository=AllResRepository.getInstance(this);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        binding.bottomNavigationView.setSelectedItemId(R.id.mainFragment);
        binding.bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            NavigationUI.onNavDestinationSelected(menuItem,navController);
            return true;
        });
        mediaPlayer.setOnCompletionListener(v-> mediaPlayer.start());
        musicThread=new Thread(()-> mediaPlayer.start());
        musicThread.start();
        volumeData.observe(this, volumeMusic-> mediaPlayer.setVolume(volumeMusic,volumeMusic));
        startService(new Intent(this,SaveService.class));

    }
    @Override
    protected void onPause() {
        super.onPause();
        new Thread(()-> repository.SaveToSave()).start();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}