package com.example.clicker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

@Entity(tableName = "resours_table")
public class ResoursEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("balance")
    private int balance;
    @SerializedName("gather")
    private int gather;
    @SerializedName("market")
    private int[] market;
    @SerializedName("volume")
    private float volume;
    @SerializedName("usableSlave")
    private int usableSlave;
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
    public void setGather(int gather) {
        this.gather = gather;
    }
    public int[] getMarket() {
        return market;
    }
    public void setMarket(int[] market) {
        this.market = market;
    }
    public float getVolume() {
        return volume;
    }
    public void setVolume(float volume) {
        this.volume = volume;
    }
    public int getUsableSlave() {
        return usableSlave;
    }
    public void setUsableSlave(int usableSlave) {
        this.usableSlave = usableSlave;
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
