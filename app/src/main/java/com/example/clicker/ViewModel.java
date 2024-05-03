package com.example.clicker;


import android.content.Context;
import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Random;


public class ViewModel extends androidx.lifecycle.ViewModel {
    private final MutableLiveData<ArrayList<MarketItem>> liveDataMarket = new MutableLiveData<>();
    private final MutableLiveData<Resourses> liveDataResourses = new MutableLiveData<>();
    private ArrayList<MarketItem> marketArray;
    private ArrayList<Plant> plants=new ArrayList<>();
    private static Context mcontext;
    private static ViewModel ModelInstanse = null;
    private Resourses resourses = new Resourses();
    private final Random random=new Random();
    private Save save;
    public static ViewModel newInstance(Context context) {
        mcontext=context;
        if (ModelInstanse==null){
            ModelInstanse=new ViewModel();
        }
        return ModelInstanse;
    }
    public void LoadOfSave(){
        save = new Save(mcontext);
        setBalanceRes(save.getBalanceRes());
        plants.add(new Plant(mcontext));
        plants.get(0).setSlave(resourses.usableSlave);
        liveDataResourses.postValue(resourses);
    }
    public void SaveToSave(){
        save.saveBalanceRes(resourses);
    }
    public void setBalanceRes(Resourses resourses){
        if (resourses !=null){
            this.resourses = resourses;
        }
    }
    public MutableLiveData<ArrayList<MarketItem>> getLiveDataArray() {
        if (liveDataMarket.getValue() == null) {
            loadItems();
        }
        return liveDataMarket;
    }
    public MutableLiveData<Resourses> getLiveDataResourses() {
        liveDataResourses.setValue(resourses);
        return liveDataResourses;
    }
    public static class Resourses {
        private int balance=0;
        private int gather=0;
        private int[] market=new int[7];
        private int usableSlave=0;
        private float volume=0.5f;
        public int getBalance() {
            return balance;
        }
        public int getGather() {
            return gather;
        }
        public int getMarketPos(int position) {
            return market[position];
        }
        public int[] getMarket() {
            return market;
        }
        public int getUsableSlave() {
            return usableSlave;
        }
        public float getVolume() {
            return volume;
        }
        public void setVolume(float volume) {
            this.volume = volume;
        }
        public void setBalance(int balance) {
            this.balance = balance;
        }
        public void setGather(int gather) {
            this.gather = gather;
        }
        public void setMarket(int[] market) {
            this.market = market;
        }
        public void setUsableSlave(int usableSlave) {
            this.usableSlave = usableSlave;
        }
    }
    private void loadItems() {
        marketArray =new MarketArraySetter(mcontext, resourses.getMarket()).getMarketItem_Array();
        for (int i = 0; i < marketArray.size(); i++) {
            marketArray.get(i).IncrCost(i);
        }
        liveDataMarket.setValue(marketArray);
    }
    public ArrayList<Plant> getPlants() {
        return plants;
    }
    public boolean incrSlavesPos(){
        if (resourses.getUsableSlave()<resourses.getMarketPos(2)){
            resourses.usableSlave++;
            plants.get(0).IncrSlave();
            liveDataResourses.setValue(resourses);
            return true;
        }
        return false;
    }
    public boolean decrSlavesPos(){
        if (resourses.getUsableSlave()>0&&plants.get(0).getSlave()>0){
            resourses.usableSlave--;
            plants.get(0).DincrSlave();
            liveDataResourses.setValue(resourses);
            return true;
        }
        return false;
    }
    public void startSlavesPos(int position){
        plants.get(position).start();
    }
    public void setProgressBar(int position, ProgressBar progressBar){
        plants.get(position).setProgressBar(progressBar);
    }
    public void setVolumeRes(float volume) {
        resourses.volume= volume;
        liveDataResourses.setValue(resourses);
    }
    public void incrCountBuy(int position) {
        int cost = marketArray.get(position).getCost();
        if (marketArray != null && position >= 0 && position < marketArray.size() && resourses.balance>=cost) {
            switch (position){
                case 1:
                    if (resourses.getMarketPos(1)<50){
                        decrBalance(cost);
                        marketArray.get(position).IncrCount();
                        marketArray.get(position).IncrCost(1);
                        liveDataMarket.setValue(marketArray);
                        incrMarket(position);
                    }
                    break;
                case 2:
                    decrBalance(cost);
                    marketArray.get(position).IncrCount();
                    marketArray.get(position).IncrCost(2);
                    liveDataMarket.setValue(marketArray);
                    incrMarket(position);
                    resourses.usableSlave++;
                    liveDataResourses.setValue(resourses);
                    break;
                default:
                    decrBalance(cost);
                    marketArray.get(position).IncrCost(position);
                    marketArray.get(position).IncrCount();
                    liveDataMarket.setValue(marketArray);
                    incrMarket(position);
                    break;
            }
        }
    }
    public void incrBalanceClick(){
        double chance;
        double randomd=random.nextDouble();
        if (resourses.getMarketPos(1)>50){
            chance=0.5;
        }
        else{
            chance= resourses.getMarketPos(1)*0.01;
        }
        if (randomd<=chance){
            resourses.balance+=(1+ resourses.getMarketPos(0))*2;
        }else{
            resourses.balance+=1000+ resourses.getMarketPos(0);
        }
        liveDataResourses.setValue(resourses);
    }
    public void incrGatherPlant(){
        resourses.gather+=10*(resourses.getMarketPos(4)+1);
        liveDataResourses.postValue(resourses);
    }
    public void incrBalanceGather(){
        resourses.balance+= resourses.gather;
        resourses.gather=0;
        liveDataResourses.setValue(resourses);
    }
    public void decrBalance(int decr){
        if (resourses.balance>=decr){
            resourses.balance-=decr;
            liveDataResourses.setValue(resourses);
        }
    }
    public void incrMarket(int position){
        resourses.market[position]++;
        liveDataResourses.setValue(resourses);
    }
}