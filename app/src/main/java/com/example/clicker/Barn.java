package com.example.clicker;

import android.content.Context;
import android.widget.ProgressBar;

public class Barn{
    private int Slave=0;
    private ProgressBar progressBar;
    private int MaxProgress=0;
    private final AllResRepository repository;
    private boolean researched_barn_2;
    public Barn(Context context) {
        repository=AllResRepository.getInstance(context);
    }
    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
        IncrMaxProgress();
        progressBar.setMax(MaxProgress);
        progressBar.setProgress(repository.getPotato());
    }
    public void IncrSlave() {
        Slave++;
        IncrMaxProgress();
    }
    public void DincrSlave() {
        if (Slave>0){
            Slave--;
            IncrMaxProgress();
            repository.MaxPotato();
        }
    }
    public void IncrMaxProgress(){
        if (researched_barn_2) MaxProgress=repository.getMarket()[5]*1000+Slave*500;
        else MaxProgress=repository.getMarket()[5]*1000+Slave*250;
        progressBar.setMax(MaxProgress);
    }
    public int getSlave() {
        return Slave;
    }
    public void setSlave(int slave) {
        Slave = slave;
        IncrMaxProgress();
    }
    public void IncrProgress(int potato){
        progressBar.setProgress(potato);
    }
    public int getMaxProgress(){
        return MaxProgress;
    }
    public void setResearched_barn_2(boolean researched_barn_2) {
        this.researched_barn_2 = researched_barn_2;
    }
}
