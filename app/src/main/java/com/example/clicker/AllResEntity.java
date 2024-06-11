package com.example.clicker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

@Entity(tableName = "allRes_table")
public class AllResEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("balance")
    private int balance;
    @SerializedName("gather")
    private int gather;
    @SerializedName("market")
    private int[] market;
    @SerializedName("volumeAll")
    private float volumeAll;
    @SerializedName("volumeBack")
    private float volumeBack;
    @SerializedName("usableNeighbor")
    private int[] usableNeighbor;
    @SerializedName("potato")
    private int potato;
    @SerializedName("time")
    private long time;
    @SerializedName("research")
    private boolean[] research;
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
    public float getVolumeAll() {
        return volumeAll;
    }
    public void setVolumeAll(float volumeAll) {
        this.volumeAll = volumeAll;
    }
    public int[] getUsableNeighbor() {
        return usableNeighbor;
    }
    public void setUsableNeighbor(int[] usableNeighbor) {
        this.usableNeighbor = usableNeighbor;
    }
    public int getPotato() {
        return potato;
    }
    public void setPotato(int potato) {
        this.potato = potato;
    }
    public boolean[] getResearch() {
        return research;
    }
    public void setResearch(boolean[] research) {
        this.research = research;
    }
    public float getVolumeBack() {
        return volumeBack;
    }
    public void setVolumeBack(float volumeBack) {
        this.volumeBack = volumeBack;
    }
    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    @TypeConverters({Converters.class})
    public static class Converters {
        @TypeConverter
        public static int[] fromStringInt(String value) {
            Type type = new TypeToken<int[]>() {}.getType();
            return new Gson().fromJson(value, type);
        }
        @TypeConverter
        public static boolean[] fromStringBoolean(String value) {
            Type type = new TypeToken<boolean[]>() {}.getType();
            return new Gson().fromJson(value, type);
        }
        @TypeConverter
        public static String fromIntArray(int[] array) {
            return new Gson().toJson(array);
        }
        @TypeConverter
        public static String fromBooleanArray(boolean[] array) {
            return new Gson().toJson(array);
        }
    }
}
