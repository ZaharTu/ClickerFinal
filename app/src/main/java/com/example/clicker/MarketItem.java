package com.example.clicker;

public class MarketItem{
    private final String NameItem;
    private final String HintItem;
    private int Cost;
    private final int baseCost;
    private final int image;
    private int countBuy;
    public MarketItem(String nameItem, String HintItem, int cost, int image, int countBuy) {
        this.HintItem=HintItem;
        this.NameItem = nameItem;
        this.Cost = cost;
        this.image = image;
        this.countBuy = countBuy;
        this.baseCost=cost;
    }
    public void IncrCount(){countBuy++;}
    public void IncrCost(int position){
        if (position<=3){
            Cost=(int)((countBuy/10)+1)*baseCost;
        } else if (position<=6) {
            Cost=(countBuy+1)*baseCost;
        }

    }
    public int getCountBuy() {
        return countBuy;
    }
    public String getNameItem() {
        return NameItem;
    }
    public int getCost() {
        return Cost;
    }
    public int getImage() {
        return image;
    }
    public String getHintItem() {
        return HintItem;
    }
}
