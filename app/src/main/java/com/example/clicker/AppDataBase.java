package com.example.clicker;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {ResoursEntity.class}, version = 1)
@TypeConverters(ResoursEntity.Converters.class)
public abstract class AppDataBase extends RoomDatabase{
    public abstract ResoursDAO ResoursDAO();
    public void deleteAllExceptLastAndInsertLast(ResoursEntity lastResours) {
        ResoursDAO dao = ResoursDAO();
        dao.deleteAllExceptLast();
        dao.delete(lastResours);
        dao.insert(lastResours);
    }
}
