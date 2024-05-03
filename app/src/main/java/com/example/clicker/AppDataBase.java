package com.example.clicker;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {ResoursesEntity.class}, version = 1)
@TypeConverters(ResoursesEntity.Converters.class)
public abstract class AppDataBase extends RoomDatabase{
    public abstract ResoursesDAO ResoursesDAO();
}
