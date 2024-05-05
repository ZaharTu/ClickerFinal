package com.example.clicker;

import android.content.Context;

import androidx.room.Room;

public class Save {
    private static AppDataBase appDatabase;
    public Save(Context context) {
        appDatabase= Room.databaseBuilder(context.getApplicationContext(),
                AppDataBase.class,"app-database").build();
    }
    public void saveBalanceRes(ViewModel.Resours resours) {
        ResoursEntity resoursEntity = new ResoursEntity();
        resoursEntity.setBalance(resours.getBalance());
        resoursEntity.setGather(resours.getGather());
        resoursEntity.setMarket(resours.getMarket());
        resoursEntity.setVolume(resours.getVolume());
        resoursEntity.setUsableSlave(resours.getUsableSlave());
        appDatabase.ResoursDAO().insert(resoursEntity);
    }
    public ViewModel.Resours getBalanceRes() {
        ResoursEntity resoursEntity = appDatabase.ResoursDAO().getLastResours();
        if (resoursEntity!=null){
            appDatabase.deleteAllExceptLastAndInsertLast(resoursEntity);
            ViewModel.Resours resours = new ViewModel.Resours();
            resours.setBalance(resoursEntity.getBalance());
            resours.setGather(resoursEntity.getGather());
            resours.setMarket(resoursEntity.getMarket());
            resours.setUsableSlave(resoursEntity.getUsableSlave());
            resours.setVolume(resoursEntity.getVolume());
            return resours;
        }
        return null;
    }

}
