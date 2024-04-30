package com.example.clicker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;

@Entity(tableName = "balance_table")
public class BalanceResEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int balance;
    private int gather;
    private int[] market;
    private int[] slaveOnEach;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getGather() {
        return gather;
    }

    public int[] getSlaveOnEach() {
        return slaveOnEach;
    }

    public void setSlaveOnEach(int[] slaveOnEach) {
        this.slaveOnEach = slaveOnEach;
    }

    public void setGather(int gather) {
        this.gather = gather;
    }

    public int[] getMarket() {
        return market;
    }

    public void setMarket(int[] market) {
        this.market = market;
    }

    @TypeConverters({Converters.class})
    public static class Converters {
        @TypeConverter
        public static int[] fromString(String value) {
            Type type = new TypeToken<int[]>() {}.getType();
            return new Gson().fromJson(value, type);
        }

        @TypeConverter
        public static String fromIntArray(int[] array) {
            return new Gson().toJson(array);
        }
    }
}
