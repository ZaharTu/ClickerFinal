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
        allResEntity.setResearch(repository.getResearch());
        allResEntity.setGather(repository.getGather());
        allResEntity.setMarket(repository.getMarket());
        allResEntity.setVolumeAll(repository.getVolumeAll());
        allResEntity.setVolumeBack(repository.getVolumeBack());
        allResEntity.setUsableNeighbor(repository.getUsableNeighbor());
        allResEntity.setPotato(repository.getPotato());
        allResEntity.setTime(System.currentTimeMillis());
        appDatabase.AllResDAO().deleteOldData();
        appDatabase.AllResDAO().insert(allResEntity);
    }
    public void getBalanceRes() {
        AllResEntity allResEntity = appDatabase.AllResDAO().getLastResoursd();
        if (allResEntity!=null){
            repository.setBalance(allResEntity.getBalance());
            repository.setResearch(allResEntity.getResearch());
            repository.setMarket(allResEntity.getMarket());
            repository.setUsableSlave(allResEntity.getUsableNeighbor());
            repository.setVolumeAll(allResEntity.getVolumeAll());
            repository.setVolumeBack(allResEntity.getVolumeBack());
            repository.setPotato(allResEntity.getPotato());
            repository.setGather(allResEntity.getGather());
            repository.timeBefore= allResEntity.getTime();
        }
    }

}
