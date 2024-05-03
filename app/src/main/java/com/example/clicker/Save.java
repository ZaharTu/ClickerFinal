package com.example.clicker;

import android.content.Context;

import androidx.room.Room;

public class Save {
    private static AppDataBase appDatabase;
    public Save(Context context) {
        appDatabase= Room.databaseBuilder(context.getApplicationContext(),
                AppDataBase.class,"app-database").build();
    }
    public void saveBalanceRes(ViewModel.Resourses resourses) {
        ResoursesEntity resoursesEntity = new ResoursesEntity();
        resoursesEntity.setBalance(resourses.getBalance());
        resoursesEntity.setGather(resourses.getGather());
        resoursesEntity.setMarket(resourses.getMarket());
        resoursesEntity.setVolume(resourses.getVolume());
        resoursesEntity.setUsableSlave(resourses.getUsableSlave());
        appDatabase.ResoursesDAO().insert(resoursesEntity);
    }
    public ViewModel.Resourses getBalanceRes() {
        ResoursesEntity resoursesEntity = appDatabase.ResoursesDAO().getLastResourses();
        if (resoursesEntity ==null) return null;
        ViewModel.Resourses resourses = new ViewModel.Resourses();
        resourses.setBalance(resoursesEntity.getBalance());
        resourses.setGather(resoursesEntity.getGather());
        resourses.setMarket(resoursesEntity.getMarket());
        resourses.setUsableSlave(resoursesEntity.getUsableSlave());
        resourses.setVolume(resoursesEntity.getVolume());
        return resourses;
    }

}
