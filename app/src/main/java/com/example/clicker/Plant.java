package com.example.clicker;

import android.content.Context;
import android.widget.ProgressBar;

public class Plant {
    private int prog;
    private int Slave=0;
    private ProgressBar progressBar;
    private int MaxProgress;
    private boolean flagStart;
    private ViewModel model;
    public Plant(Context context) {
        model=ViewModel.newInstance(context);
    }
    public int getSlave() {
        return Slave;
    }
    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
    public void IncrSlave() {
        Slave++;
    }
    public void DincrSlave() {
        if (Slave>0){
            Slave--;
        }
    }

    public void setSlave(int slave) {
        Slave = slave;
    }

    public void start(){
        if (!flagStart) {
            flagStart = true;
            MaxProgress=100;
            progressBar.setMax(MaxProgress);
                new Thread(() -> {
                    while (Slave > 0) {
                        int progress = prog + Slave;
                        if (progress < MaxProgress) {
                            prog = progress;
                            progressBar.setProgress(progress);
                        } else {
                            prog = 0;
                            progressBar.setProgress(0);
                            model.incrGatherPlant();
                            progressBar.setMax(MaxProgress);
                        }
                        try {

                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    flagStart = false;
                }).start();
        }
    }

    public int getProgress() {
        return prog;
    }
}
