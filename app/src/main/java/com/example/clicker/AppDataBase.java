package com.example.clicker;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {AllResEntity.class}, version = 1)
@TypeConverters(AllResEntity.Converters.class)
public abstract class AppDataBase extends RoomDatabase{
    public abstract AllResDAO AllResDAO();
}
