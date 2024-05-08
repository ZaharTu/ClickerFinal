package com.example.clicker;

import android.content.Context;
import android.widget.ProgressBar;

public class Marketplace {

    private int prog;
    private int Slave=0;
    private ProgressBar progressBar;
    private int MaxProgress=100;
    private boolean flagStart;
    private final AllResRepository repository;
    public Marketplace(Context context) {
        repository=AllResRepository.getInstance(context);
    }
    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
    public void IncrSlave() {
        Slave++;
        start();
    }
    public void DincrSlave() {
        if (Slave>0){
            Slave--;
        }
    }
    public int getSlave() {
        return Slave;
    }
    public void setSlave(int slave) {
        Slave = slave;
        if (slave>0){start();}
    }
    public void start(){
        if (!flagStart) {
            flagStart = true;
            progressBar.setMax(MaxProgress);
            new Thread(() -> {
                while (Slave > 0) {
                    if (prog<MaxProgress) {
                        prog++;
                        progressBar.setProgress(prog);
                    } else {
                        prog = 0;
                        progressBar.setProgress(0);
                        repository.incrBalancePotato();
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
}
