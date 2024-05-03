package com.example.clicker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ResoursesDAO {
    @Query("SELECT * FROM resourses_table ORDER BY id DESC LIMIT 1")
    ResoursesEntity getLastResourses();

    @Insert
    void insert(ResoursesEntity resoursesEntity);

    // Другие методы для работы с BalanceResEntity
}
