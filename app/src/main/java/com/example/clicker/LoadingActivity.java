package com.example.clicker;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clicker.databinding.ActivityLoadingBinding;

public class LoadingActivity extends AppCompatActivity{
    private ActivityLoadingBinding binding;
    private int progress=0;
    private AllResRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoadingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository= AllResRepository.getInstance(this);
        new Thread(() -> repository.LoadOfSave()).start();
        Animation rotate = AnimationUtils.loadAnimation(this,R.anim.rotate_potato_loading);
        binding.LoadingPotato.setAnimation(rotate);
        binding.LoadingProgress.setProgress(0);
        new Thread(()->{
            while (progress<100){
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                runOnUiThread(()->{
                    binding.LoadingProgressTxt.setText(progress+"%");
                    binding.LoadingProgress.setProgress(progress);
                });
                progress++;
            }
            Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
            startActivity(intent);
        }).start();
    }

}