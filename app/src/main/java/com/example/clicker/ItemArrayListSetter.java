package com.example.clicker;

import android.content.Context;

import java.util.ArrayList;

public class ItemArrayListSetter {
    private Context mcontext;
    private ArrayList<Item> Item_Array ;
    protected int[] itemsCost;
    protected String[] itemsName;
    protected String[] itemsHint;
    int[] itemsImage = {R.drawable.shovel, R.drawable.leyka,
            R.drawable.slave, R.drawable.plant,
            R.drawable.traktor,R.drawable.village};
    public ItemArrayListSetter(Context context,int[] market){
        mcontext=context;
        Item_Array= new ArrayList<>();
        itemsCost=mcontext.getResources().getIntArray(R.array.MarketCost);
        itemsName=mcontext.getResources().getStringArray(R.array.MarketName);
        itemsHint=mcontext.getResources().getStringArray(R.array.MarketHint);
        for (int i = 0; i < itemsImage.length; i++) {
            Item_Array.add(new Item(itemsName[i],
                    itemsHint[i],
                    itemsCost[i],
                    itemsImage[i],
                    market[i],itemsCost[i]));
        }
    }



    public ArrayList<Item> getItem_Array() {
        return Item_Array;
    }

}
