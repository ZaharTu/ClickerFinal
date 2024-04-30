package com.example.clicker;

import android.content.Context;

import androidx.room.Room;

public class Save {
    private static AppDataBase appDatabase;
    public Save(Context context) {
        appDatabase= Room.databaseBuilder(context.getApplicationContext(),
                AppDataBase.class,"app-database").build();
    }
    public void saveBalanceRes(ViewModel.BalanceRes balanceRes) {
        BalanceResEntity balanceResEntity = new BalanceResEntity();
        // данные из balanceRes в balanceResEntity
        balanceResEntity.setBalance(balanceRes.getBalance());
        balanceResEntity.setGather(balanceRes.getGather());
        balanceResEntity.setMarket(balanceRes.getMarket());
        balanceResEntity.setSlaveOnEach(balanceRes.getSlaveOnEach());
        appDatabase.balanceResDAO().insert(balanceResEntity);
    }
    public ViewModel.BalanceRes getBalanceRes() {
        BalanceResEntity balanceResEntity = appDatabase.balanceResDAO().getLastBalanceRes();
        if (balanceResEntity==null) return null;
        ViewModel.BalanceRes balanceRes = new ViewModel.BalanceRes();
        // данные из balanceResEntity в balanceRes
        balanceRes.setBalance(balanceResEntity.getBalance());
        balanceRes.setGather(balanceResEntity.getGather());
        balanceRes.setMarket(balanceResEntity.getMarket());
        balanceRes.setSlaveOnEach(balanceResEntity.getSlaveOnEach());
        balanceRes.setUsableSlave(balanceResEntity.getMarket()[2]);
        return balanceRes;
    }

}
