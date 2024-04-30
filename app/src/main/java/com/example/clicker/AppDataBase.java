package com.example.clicker;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {BalanceResEntity.class}, version = 1)
@TypeConverters(BalanceResEntity.Converters.class)
public abstract class AppDataBase extends RoomDatabase{
    public abstract BalanceResDAO balanceResDAO();
}
