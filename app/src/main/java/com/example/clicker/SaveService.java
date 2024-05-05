package com.example.clicker;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class SaveService extends Service {
    private Save save;
    private ScheduledExecutorService scheduler;
    @Override
    public void onCreate() {
        super.onCreate();
        save=new Save(this,AllResRepository.getInstance(this));
        scheduler= Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(save::saveBalanceRes, 10, 30, TimeUnit.SECONDS);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scheduler.shutdown();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
