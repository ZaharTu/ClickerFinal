package com.example.clicker;

public class Item {
    private String NameItem;
    private String HintItem;
    private int Cost;
    private int image;
    public int countBuy;
    public Item(String nameItem, String HintItem,int cost, int image, int countBuy) {
        this.HintItem=HintItem;
        this.NameItem = nameItem;
        this.Cost = cost;
        this.image = image;
        this.countBuy = countBuy;
    }
    public void IncrCountBuy(){countBuy++;}
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
