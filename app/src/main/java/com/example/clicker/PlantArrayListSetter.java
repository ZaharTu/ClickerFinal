package com.example.clicker;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;

public class PlantArrayListSetter {
    private final ArrayList<Plant> Plant_Array;
    private Context mcontext;
    public PlantArrayListSetter(Context context,int count) {
        mcontext=context;
        Plant_Array=new ArrayList<>();
        Plant_Array.add(new Plant(context,0));
        if (count!=0){
            for (int i = 0; i < count; i++) {
                Plant_Array.add(new Plant(context,i));
            }
        }
    }

    public ArrayList<Plant> getPlant_Array() {
        return Plant_Array;
    }
}
