package com.example.clicker;

import android.content.Context;
import android.widget.ProgressBar;

public class Barn{
    private int prog;
    private int Slave=0;
    private ProgressBar progressBar;
    private int MaxProgress=1000;
    private final AllResRepository repository;
    public Barn(Context context) {
        repository=AllResRepository.getInstance(context);
    }
    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
    public void IncrSlave() {
        Slave++;
        IncrMaxProgress();
    }
    public void DincrSlave() {
        if (Slave>0){
            Slave--;
            IncrMaxProgress();
        }
    }
    public void IncrMaxProgress(){
        if (MaxProgress!=repository.getMarket()[4]*1000+Slave*1000+1000){
            MaxProgress=repository.getMarket()[4]*1000+Slave*1000+1000;
            progressBar.setMax(MaxProgress);
        }
    }
    public int getSlave() {
        return Slave;
    }
    public void setSlave(int slave) {
        Slave = slave;
        MaxProgress=repository.getMarket()[4]*1000+Slave*1000;
    }
    public void IncrProgress(){
        prog=repository.getPotato();
        progressBar.setProgress(prog);
    }
    public int getMaxProgress(){
        return MaxProgress;
    }
}
