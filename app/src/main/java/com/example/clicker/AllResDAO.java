package com.example.clicker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AllResDAO {
    @Query("SELECT * FROM ALLRES_TABLE ORDER BY id DESC LIMIT 1")
    AllResEntity getLastResoursd();
    @Insert
    void insert(AllResEntity allResEntity);
    @Query("DELETE FROM allres_table WHERE id NOT IN (SELECT MAX(id) FROM allres_table)")
    void deleteOldData();
}
