package com.example.clicker;

import android.content.Context;
import android.widget.ProgressBar;

public class Plant{
    private int prog;
    private int Slave=0;
    private ProgressBar progressBar;
    private int MaxProgress=100;
    private boolean flagStart;
    private final AllResRepository repository;
    public Plant(Context context) {
        repository=AllResRepository.getInstance(context);
    }
    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
    public void IncrSlave() {
        Slave++;
        start();
    }
    private boolean IncrMaxProgress(){
        if (MaxProgress!=100+repository.getMarket()[4]*100){
            MaxProgress=100+repository.getMarket()[4]*100;
            return true;
        }
        return false;
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
        IncrMaxProgress();
        if (slave>0){start();}
    }
    public void start(){
        if (!flagStart) {
            flagStart = true;
            progressBar.setMax(MaxProgress);
                new Thread(() -> {
                    while (Slave > 0) {
                        int progress = prog + Slave;
                        if (progress < MaxProgress) {
                            prog = progress;
                            progressBar.setProgress(progress);
                        } else {
                            if(IncrMaxProgress()){
                                progressBar.setMax(MaxProgress);
                            }
                            prog = 0;
                            progressBar.setProgress(0);
                            repository.incrGatherPlant();
                            repository.incrPotato();
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
