package com.example.clicker;

import android.content.Context;
import android.widget.ProgressBar;

public class Barn{
    private int Slave=0;
    private ProgressBar progressBar;
    private int MaxProgress=0;
    private final AllResRepository repository;
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
        if (MaxProgress!=repository.getMarket()[5]*1000+Slave*150){
            MaxProgress=repository.getMarket()[5]*1000+Slave*150;
            progressBar.setMax(MaxProgress);
        }
    }
    public int getSlave() {
        return Slave;
    }
    public void setSlave(int slave) {
        Slave = slave;
        IncrMaxProgress();
    }
    public void IncrProgress(){
        progressBar.setProgress(repository.getPotato());
    }
    public int getMaxProgress(){
        return MaxProgress;
    }
}
