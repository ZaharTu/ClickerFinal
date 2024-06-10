package com.example.clicker;

import android.content.Context;

import java.util.ArrayList;

public class MarketArraySetter {
    private final ArrayList<MarketItem> marketItem_Array;
    protected int[] itemsCost;
    protected String[] itemsName;
    protected String[] itemsHint;
    int[] itemsImage = {R.drawable.shovel, R.drawable.leyka,
            R.drawable.neighbor,
            R.drawable.traktor,R.drawable.plant,R.drawable.barn,R.drawable.market};
    public MarketArraySetter(Context context, int[] market){
        marketItem_Array = new ArrayList<>();
        itemsCost= context.getResources().getIntArray(R.array.MarketCost);
        itemsName= context.getResources().getStringArray(R.array.MarketName);
        itemsHint= context.getResources().getStringArray(R.array.MarketHint);
        for (int i = 0; i < itemsImage.length; i++) {
            marketItem_Array.add(new MarketItem(itemsName[i],
                    itemsHint[i],
                    itemsCost[i],
                    itemsImage[i],
                    market[i]));
        }

    }



    public ArrayList<MarketItem> getMarketItem_Array() {
        return marketItem_Array;
    }

}
