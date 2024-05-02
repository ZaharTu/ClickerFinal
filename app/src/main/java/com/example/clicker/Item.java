package com.example.clicker;

public class Item {
    private String NameItem;
    private String HintItem;
    private int Cost;
    private int baseCost;
    private int image;
    public int countBuy;
    public Item(String nameItem, String HintItem,int cost, int image, int countBuy,int baseCost) {
        this.HintItem=HintItem;
        this.NameItem = nameItem;
        this.Cost = cost;
        this.image = image;
        this.countBuy = countBuy;
        this.baseCost=baseCost;
    }
    public void IncrCountBuy(){countBuy++;}
    public void IncrCost(){
        if (NameItem.equals("Плантация")){
            Cost=(countBuy+1)*baseCost;
        }else{
            Cost=(int)((countBuy/10)+1)*baseCost;
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
