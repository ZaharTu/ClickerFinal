package com.example.clicker;

import android.content.Context;

import androidx.room.Room;

public class Save {
    private static AppDataBase appDatabase;
    private AllResRepository repository;

    public Save(Context context,AllResRepository repository) {
        appDatabase= Room.databaseBuilder(context.getApplicationContext(),
                AppDataBase.class,"app-database").build();
        this.repository=repository;
    }
    public void saveBalanceRes() {
        AllResEntity allResEntity = new AllResEntity();
        allResEntity.setBalance(repository.getBalance());
        allResEntity.setGather(repository.getGather());
        allResEntity.setMarket(repository.getMarket());
        allResEntity.setVolume(repository.getVolume());
        allResEntity.setUsableSlave(repository.getUsableSlave());
        allResEntity.setPotato(repository.getPotato());
        appDatabase.AllResDAO().insert(allResEntity);
    }
    public void getBalanceRes() {
        AllResEntity allResEntity = appDatabase.AllResDAO().getLastResoursd();
        if (allResEntity!=null){
            repository.setBalance(allResEntity.getBalance());
            repository.setGather(allResEntity.getGather());
            repository.setMarket(allResEntity.getMarket());
            repository.setUsableSlave(allResEntity.getUsableSlave());
            repository.setVolume(allResEntity.getVolume());
            repository.setPotato(allResEntity.getPotato());
            appDatabase.AllResDAO().deleteOldData();
        }
    }

}
