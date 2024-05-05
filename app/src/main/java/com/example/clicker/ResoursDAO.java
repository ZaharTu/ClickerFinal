package com.example.clicker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ResoursDAO {
    @Query("DELETE FROM resours_table WHERE id NOT IN (SELECT id FROM resours_table ORDER BY id DESC LIMIT 1)")
    void deleteAllExceptLast();
    @Query("SELECT * FROM resours_table ORDER BY id DESC LIMIT 1")
    ResoursEntity getLastResours();
    @Delete
    void delete(ResoursEntity resoursEntity);
    @Insert
    void insert(ResoursEntity resoursEntity);
}
