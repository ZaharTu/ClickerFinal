package com.example.clicker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface BalanceResDAO {
    @Query("SELECT * FROM balance_table ORDER BY id DESC LIMIT 1")
    BalanceResEntity getLastBalanceRes();

    @Insert
    void insert(BalanceResEntity balanceResEntity);

    // Другие методы для работы с BalanceResEntity
}
